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
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;


public class MeerJob extends BSPJob {
  private long aggregationInterval = MeerkatCommon.AGGREGATION_INTERVAL;
  private long pollingInterval = MeerkatCommon.POLLING_INTERVAL;
  private Class<? extends GuardMeer> guardMeer;
  private Class<? extends BossMeer> bossMeer;
  private Class<? extends SignalMeer> reportMeer;
  private Path logPath;
  

  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
    this.setBspClass(MeerJobRunner.class);
  }

  public void setPollingInterval(long pollingInterval) {
    this.pollingInterval = pollingInterval;
    this.getConfiguration().setLong(
        MeerkatCommon.POLLING_INTERVAL_URI, this.pollingInterval);
  }
  
  public void setBossAggregationInterval(long aggregationInterval) {
    this.aggregationInterval = aggregationInterval;
    this.getConfiguration().setLong(
        MeerkatCommon.AGGREGATION_INTERVAL_URI, this.aggregationInterval);
  }

  public <G extends GuardMeer> void setGuardMeerClass(Class<G> guardMeer) {
    this.guardMeer = guardMeer;
    this.conf.setClass(MeerkatCommon.GUARDMEER_CLASS_URI, GuardMeer.class, GuardMeerInterface.class);
  }

  public <B extends BossMeer> void setBossMeerClass(Class<B> bossMeer) {
    this.bossMeer = bossMeer;
    this.conf.setClass(MeerkatCommon.BOSSMEER_CLASS_URI, BossMeer.class, BossMeerInterface.class);
  }

  public <R extends SignalMeer> void setMeerCommunicator(Class<R> reportMeer) {
    this.reportMeer = reportMeer;
    this.conf.setClass(MeerkatCommon.REPORTMEER_CLASS_URI, SignalMeer.class, SignalMeerInterface.class);
  }

  /**
   * Sets the path of log directory
   * 
   * @param path
   */
  public void setLogPath(Path logPath) {
    this.logPath = logPath;
    this.conf.set(MeerkatCommon.LOG_PATH_URI, this.logPath.toString());
  }

}
