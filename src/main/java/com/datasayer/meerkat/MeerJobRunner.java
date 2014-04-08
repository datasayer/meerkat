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
  private long pollingInterval = MeerkatConstants.POLLING_INTERVAL;
  private GuardMeer guardMeer;
  private BossMeer bossMeer;
  private SignalMeer signalMeer;
  private Path logPath;
  private int masterIndex = 0;
  private String masterName = "";
  private BSPPeer<Writable, Writable, Writable, Writable, Writable> peer;
  private long filePointer = 0;
  private boolean isSignalServerNeed = MeerkatConstants.IS_SIGNAL_SERVER_SETUP;

  @Override
  public void bsp(final BSPPeer<Writable, Writable, Writable, Writable, Writable> peer)
      throws IOException, SyncException, InterruptedException {
    tail();
    aggregate(peer);
  }

  private void tail() {
    new Thread(new Runnable(){
      @Override
      public void run() {
        while(true) {
          try {
            FileSystem fs = FileSystem.get(conf);
            if(!fs.isFile(logPath)){
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
            }else{
              // nothing to do
            }
            file.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }
  
  private void aggregate(final BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) {
    new Runnable(){
      BSPPeer<Writable, Writable, Writable, Writable, Writable> innerPeer;
      @Override
      public void run() {
        while(true) {
          try {
            Thread.sleep(aggregationInterval);
            if (peer.getPeerName().equals(masterName)) {
              peer.sync();
              MessageIterator iterator = new MessageIterator();
              Writable msg;
              while( (msg = peer.getCurrentMessage()) != null) {
                iterator.add(msg);
              }
              bossMeer.masterCompute(iterator, signalMeer);
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          } catch (SyncException e) {
            e.printStackTrace();
          }
        }
      }
      public Runnable init(BSPPeer<Writable, Writable, Writable, Writable, Writable> innerPeer) {
        this.innerPeer = innerPeer;
        return(this);
      }
    }.init(peer).run();
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
    this.aggregationInterval = conf.getLong(MeerkatConstants.AGGREGATION_INTERVAL_URI, this.aggregationInterval);
    this.pollingInterval = conf.getLong(MeerkatConstants.POLLING_INTERVAL_URI, this.pollingInterval);
    this.isSignalServerNeed = conf.getBoolean(MeerkatConstants.SIGNAL_SERVER_SETUP_URI, this.isSignalServerNeed);
    
    Class<? extends GuardMeerInterface> guardClass = conf.getClass(MeerkatConstants.GUARDMEER_CLASS_URI, GuardMeer.class, GuardMeerInterface.class);
    guardMeer = (GuardMeer)ReflectionUtils.newInstance(guardClass);
    guardMeer.setRunner(this);
    
    Class<? extends BossMeerInterface> bossClass = conf.getClass(MeerkatConstants.BOSSMEER_CLASS_URI, BossMeer.class, BossMeerInterface.class);
    bossMeer = (BossMeer)ReflectionUtils.newInstance(bossClass);
    
    Class<? extends SignalMeerInterface> signalClass = conf.getClass(MeerkatConstants.SIGNALMEER_CLASS_URI, SignalMeer.class, SignalMeerInterface.class);
    signalMeer = (SignalMeer)ReflectionUtils.newInstance(signalClass, new Object[]{ this.conf });
    signalMeer.setServer(this.isSignalServerNeed);

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
