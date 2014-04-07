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
package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;
import org.apache.hama.bsp.NullOutputFormat;


public class MeerJob extends BSPJob {
  
  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
  }
  
  public void setPollingInterval(long pollingInterval) {
    
  }
  
  public void setAggregateInterval(long aggregateInterval) {
    
  }
  
  public <G extends GuardMeer<Writable>> void setGuardMeer(Class<G> guardmeer) {
    
  }
  
  public <B extends BossMeer<Writable,Writable>> void setBossMeer(Class<B> bossmeer) {
    
  }
}
