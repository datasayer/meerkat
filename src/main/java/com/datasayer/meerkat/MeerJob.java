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

  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
  }

  public void setBossAggregationInterval(long aggregationInterval) {
    this.getConfiguration().setLong(
        "com.datasayer.meerkat.aggregation.interval", aggregationInterval);
  }

  public void setGuardMeerClass(Class<?> clazz) {
    // TODO Auto-generated method stub
    
  }

  public void setBossMeerClass(Class<?> clazz) {
    // TODO Auto-generated method stub
    
  }

  public void setMeerCommunicator(Class<?> clazz) {
    // TODO Auto-generated method stub
    
  }

  /**
   * Sets the path of log directory
   * 
   * @param path
   */
  public void setLogPath(Path path) {
    // TODO Auto-generated method stub
    
  }

}
