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
import org.apache.hama.bsp.NullOutputFormat;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;

public class MeerJob extends BSPJob {
  // For Default Value Check
  private long aggregationInterval = MeerkatConstants.AGGREGATION_INTERVAL;
  private Class<? extends GuardMeer<? extends Writable>> guardMeer;
  private Class<? extends BossMeer<? extends Writable, ? extends Writable>> bossMeer;
  private Class<? extends SignalMeer<? extends Writable>> signalMeer;
  private Path logPath;
  private boolean isSignalServerNeed = MeerkatConstants.IS_SIGNAL_SERVER_SETUP;
  // For Default Value Check

  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
    this.setOutputFormat(NullOutputFormat.class);
    this.setBspClass(MeerJobRunner.class);
    this.setNumBspTask(1);

    this.conf.set(MeerkatConstants.SIGNAL_HOSTNAME_URI,
        MeerkatConstants.SIGNAL_HOSTNAME);
    this.conf.setInt(MeerkatConstants.SIGNAL_PORT_URI,
        MeerkatConstants.SIGNAL_PORT);
    this.conf.setInt(MeerkatConstants.SIGNAL_THREAD_COUNT_URI,
        MeerkatConstants.SIGNAL_THREAD_COUNT);
  }

  public void setBossAggregationInterval(long aggregationInterval) {
    this.aggregationInterval = aggregationInterval;
    this.getConfiguration().setLong(MeerkatConstants.AGGREGATION_INTERVAL_URI,
        this.aggregationInterval);
  }

  public <G extends GuardMeer<? extends Writable>> void setGuardMeerClass(
      Class<G> guardMeer) {
    this.guardMeer = guardMeer;
    this.conf.setClass(MeerkatConstants.GUARDMEER_CLASS_URI, this.guardMeer,
        GuardMeerInterface.class);
  }

  public <B extends BossMeer<? extends Writable, ? extends Writable>> void setBossMeerClass(
      Class<B> bossMeer) {
    this.bossMeer = bossMeer;
    this.conf.setClass(MeerkatConstants.BOSSMEER_CLASS_URI, this.bossMeer,
        BossMeerInterface.class);
  }

  public <R extends SignalMeer<? extends Writable>> void setSignalMeerClass(
      Class<R> signalMeer) {
    this.signalMeer = signalMeer;
    this.conf.setClass(MeerkatConstants.SIGNALMEER_CLASS_URI, this.signalMeer,
        SignalMeerInterface.class);
  }

  public void setSignalServer(boolean isSignalServerNeed) {
    this.isSignalServerNeed = isSignalServerNeed;
    this.conf.setBoolean(MeerkatConstants.SIGNAL_SERVER_SETUP_URI,
        this.isSignalServerNeed);
  }

  /**
   * Sets the path of log directory
   * 
   * @param path
   */
  public void setLogPath(Path logPath) {
    this.logPath = logPath;
    this.conf.set(MeerkatConstants.LOG_PATH_URI, this.logPath.toString());
  }
  
  /*
  @Override
  public void submit() {
    // default values check is here?
  }
  */
}
