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
package test.com.datasayer.meerkat;

import java.io.IOException;

import main.com.datasayer.meerkat.MeerJob;

import org.apache.hama.HamaConfiguration;

public class MeerkatMain {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    try {
      HamaConfiguration conf = new HamaConfiguration();
      MeerJob job = new MeerJob(conf);
      job.setNumBspTask(1);
      /*
       * set Polling Interval for Tail
       */
      job.setPollingInterval(5000);
      
      /*
       * set Aggregate Interval for Tail
       */
      job.setAggregateInterval(5000);
      
      /*
       * set custom GuardMeer class implemented from GuardMeer
       */
      job.setGuardMeer(MyGuard.class);
      
      /*
       * set custom BossMeer class implemented from BossMeer
       */
      job.setBossMeer(MyBoss.class);
      
      job.waitForCompletion(true);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
}
