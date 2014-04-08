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
  private long aggregationInterval = MeerkatCommon.AGGREGATION_INTERVAL;
  private long pollingInterval = MeerkatCommon.POLLING_INTERVAL;
  private GuardMeerInterface guardMeer;
  private BossMeerInterface bossMeer;
  private SignalMeerInterface reportMeer;
  private Path logPath;
  private int masterIndex = 0;
  private BSPPeer<Writable, Writable, Writable, Writable, Writable> peer;

  @Override
  public void bsp(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer)
      throws IOException, SyncException, InterruptedException {

    // guardMeer.observe();
    
  }

  @Override
  public void setup(
      BSPPeer<Writable, Writable, Writable, Writable, Writable> peer)
      throws IOException, SyncException, InterruptedException {
    super.setup(peer);
    this.peer = peer;
    this.conf = peer.getConfiguration();
    this.masterIndex = peer.getNumPeers() / 2;
    this.logPath = new Path(conf.get(MeerkatCommon.LOG_PATH_URI));
    this.aggregationInterval = conf.getLong(MeerkatCommon.AGGREGATION_INTERVAL_URI, this.aggregationInterval);
    this.pollingInterval = conf.getLong(MeerkatCommon.POLLING_INTERVAL_URI, this.pollingInterval);
    
    Class<? extends GuardMeerInterface> guardClass = conf.getClass(MeerkatCommon.GUARDMEER_CLASS_URI, GuardMeer.class, GuardMeerInterface.class);
    guardMeer = ReflectionUtils.newInstance(guardClass);
    ((GuardMeer)guardMeer).setPeer(peer, masterIndex);
    
    Class<? extends BossMeerInterface> bossClass = conf.getClass(MeerkatCommon.BOSSMEER_CLASS_URI, BossMeer.class, BossMeerInterface.class);
    bossMeer = ReflectionUtils.newInstance(bossClass);
    
    Class<? extends SignalMeerInterface> reportClass = conf.getClass(MeerkatCommon.REPORTMEER_CLASS_URI, SignalMeer.class, SignalMeerInterface.class);
    reportMeer = ReflectionUtils.newInstance(reportClass);
  }

  public final BSPPeer<Writable, Writable, Writable, Writable, Writable> getPeer() {
    return this.peer;
  }
}
