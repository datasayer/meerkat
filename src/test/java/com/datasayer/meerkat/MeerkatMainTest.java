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
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hama.HamaConfiguration;

public class MeerkatMainTest {
  public MeerkatMainTest() {
    
  }

  public static class ObserverMeers extends GuardMeer<IntWritable> {

    @Override
    public void observe(String line) {
      try {
        this.sendToBoss(new IntWritable(line.length()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public static class FindMaxLength extends BossMeer<IntWritable, Text> {
    private int maxLineLength = 0;
    
    @Override
    public void masterCompute(Iterator<IntWritable> values,
        SignalMeer<Text> signalMeer) {

      while (values.hasNext()) {
        int currValue = values.next().get();
        if (maxLineLength < currValue) {
          maxLineLength = currValue;
        }
      }
      System.out.println("find max : " + maxLineLength);

      signalMeer.setResult(new Text("Current max length of line is :"
          + maxLineLength));
    }

  }

  public static void main(String[] args) throws Exception {
    // Job launcher
    HamaConfiguration conf = new HamaConfiguration();
    MeerJob testJob = new MeerJob(conf);

    testJob.setLogPath(new Path("src/test/resources/catalina.log"));
    testJob.setGuardMeerClass(ObserverMeers.class);
    testJob.setBossMeerClass(FindMaxLength.class);
    testJob.setSignalMeerClass(TextSignalMeer.class);

    testJob.setBossAggregationInterval(1000);
    testJob.setNumBspTask(1);
    testJob.setSignalServer(true);
    testJob.waitForCompletion(false);

    // Client-side
    /*
    TextSignalMeer client = new TextSignalMeer(conf);
    while (true) {
      System.out.println(client.getLatestResult());
    }
    */
  }

}
