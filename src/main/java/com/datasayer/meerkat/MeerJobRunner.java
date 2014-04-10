/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datasayer.meerkat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.sync.SyncException;
import org.apache.hama.util.ReflectionUtils;

public class MeerJobRunner extends
    BSP<Writable, Writable, Writable, Writable, Writable> {
  private HamaConfiguration conf;
  private long aggregationInterval = MeerkatConstants.AGGREGATION_INTERVAL;
  private GuardMeer guardMeer;
  private BossMeer bossMeer;
  private SignalMeer signalMeer;
  private Path logPath;
  private int masterIndex = 0;
  private String masterName = "";
  private BSPPeer<Writable, Writable, Writable, Writable, Writable> peer;
  private long filePointer = 0;
  private boolean isSignalServerNeed = MeerkatConstants.IS_SIGNAL_SERVER_SETUP;
  private long lastAggregatedTime = 0L;
  private static final Log LOG = LogFactory.getLog(MeerJobRunner.class);

  @SuppressWarnings("unchecked")
  @Override
  public void bsp(
      final BSPPeer<Writable, Writable, Writable, Writable, Writable> peer)
      throws IOException, SyncException, InterruptedException {

    while (true) {
      try {
        long currentTime = System.currentTimeMillis();
        FileSystem fs = FileSystem.get(conf);
        if (!fs.isFile(logPath)) {
          System.out.println("can not read input file");
          return;
        }
        RandomAccessFile file = new RandomAccessFile(logPath.toString(), "r");
        long fileLength = file.length();

        if (fileLength > filePointer) {
          file.seek(filePointer);
          String line = null;
          while (file.length() > file.getFilePointer()) {
            line = file.readLine();
            line = new String(line.getBytes("8859_1"), "utf-8");
            guardMeer.observe(line);
          }
          filePointer = file.getFilePointer();
        } else {
          // nothing to do
        }
        file.close();

        long timeDiff = currentTime - this.lastAggregatedTime;
        if (timeDiff >= this.aggregationInterval) {

          peer.sync();

          if (peer.getPeerName().equals(masterName)) {
            bossMeer.masterCompute(new Iterator<Writable>() {

              private final int producedMessages = peer.getNumCurrentMessages();
              private int consumedMessages = 0;

              @Override
              public boolean hasNext() {
                return producedMessages > consumedMessages;
              }

              @Override
              public Writable next() throws NoSuchElementException {
                if (consumedMessages >= producedMessages) {
                  throw new NoSuchElementException();
                }

                try {
                  consumedMessages++;
                  return peer.getCurrentMessage();
                } catch (IOException e) {
                  throw new NoSuchElementException();
                }
              }

              @Override
              public void remove() {
                // BSPPeer.getCurrentMessage originally deletes a message.
                // Thus, it doesn't need to throw exception.
                // throw new UnsupportedOperationException();
              }

            }, signalMeer);
            this.lastAggregatedTime = currentTime;
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void setup(
      BSPPeer<Writable, Writable, Writable, Writable, Writable> peer)
      throws IOException, SyncException, InterruptedException {
    super.setup(peer);
    this.peer = peer;
    this.conf = peer.getConfiguration();
    this.masterIndex = peer.getNumPeers() / 2;
    this.masterName = peer.getPeerName(this.masterIndex);
    this.logPath = new Path(conf.get(MeerkatConstants.LOG_PATH_URI));
    this.aggregationInterval = conf.getLong(
        MeerkatConstants.AGGREGATION_INTERVAL_URI, this.aggregationInterval);
    this.isSignalServerNeed = conf.getBoolean(
        MeerkatConstants.SIGNAL_SERVER_SETUP_URI, this.isSignalServerNeed);

    Class<? extends GuardMeerInterface> guardClass = conf.getClass(
        MeerkatConstants.GUARDMEER_CLASS_URI, GuardMeer.class,
        GuardMeerInterface.class);
    guardMeer = (GuardMeer) ReflectionUtils.newInstance(guardClass);
    guardMeer.setRunner(this);
    
    if(this.masterName.equals(peer.getPeerName())) {
      Class<? extends BossMeerInterface> bossClass = conf.getClass(
          MeerkatConstants.BOSSMEER_CLASS_URI, BossMeer.class,
          BossMeerInterface.class);
      bossMeer = (BossMeer) ReflectionUtils.newInstance(bossClass);

      Class<? extends SignalMeerInterface> signalClass = conf.getClass(
          MeerkatConstants.SIGNALMEER_CLASS_URI, SignalMeer.class,
          SignalMeerInterface.class);
      signalMeer = (SignalMeer) ReflectionUtils.newInstance(signalClass,
          new Object[] { this.conf });
      signalMeer.setServer(this.isSignalServerNeed);
    }
  }

  public final BSPPeer<Writable, Writable, Writable, Writable, Writable> getPeer() {
    return this.peer;
  }

  public final int getMasterIndex() {
    return this.masterIndex;
  }

  public final String getMasterName() {
    return this.masterName;
  }
}
