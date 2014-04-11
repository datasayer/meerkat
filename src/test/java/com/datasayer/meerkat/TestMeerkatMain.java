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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Random;

import junit.framework.TestCase;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hama.HamaConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;


public class TestMeerkatMain extends TestCase {
  private static final Log LOG = LogFactory.getLog(TestMeerkatMain.class);
  private final int maxWord = 1000;
  private final String testLogFileName = "./meerkat.log";

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
  
  private String randomString(final int length) {
    Random r = new Random();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < length; i++) {
        char c = (char)(r.nextInt((int)(Character.MAX_VALUE)));
        sb.append(c);
    }
    sb.append(System.lineSeparator());
    return sb.toString();
  }
  
  @Before
  public void setUp() throws Exception {
    new Thread(
      new Runnable() {
        @Override
        public void run() {
          try{
            String randomWord = "";
            
            for(int a=1;a<=maxWord;a++) {
              randomWord = randomString(a);
              LOG.info(randomWord);
              Thread.sleep(20);
            }
            /*
            File testLogFile = new File(testLogFileName);
            if(testLogFile.exists()) {
              testLogFile.delete();
            }else{
              testLogFile.createNewFile();
            }
            OutputStreamWriter writer;
            for(int a=1;a<=maxWord;a++) {
              writer = new OutputStreamWriter(new FileOutputStream(testLogFile, true), "UTF-8"); 
              randomWord = randomString(a);
              writer.write(randomWord);
              writer.close();
              
              //System.out.println(randomWord.length());
              Thread.sleep(100);
              // print out string size will be 101
              // (100 string + lineSeparator)
              // but file reader will be remove lineSeparator
            }
            */
          }catch(Exception e) {
            e.printStackTrace();
          }
        }
      }
    ).start();
  }

  @Test
  public void test() throws Exception {
    //fail("Not yet implemented");
    // Job launcher
    HamaConfiguration conf = new HamaConfiguration();
    FileSystem fs = FileSystem.get(conf);
    fs.deleteOnExit(new Path(testLogFileName));
    MeerJob testJob = null;
    try {
      testJob = new MeerJob(conf);
      testJob.setLogPath(new Path(testLogFileName));
      testJob.setGuardMeerClass(ObserverMeers.class);
      testJob.setBossMeerClass(FindMaxLength.class);
      testJob.setSignalMeerClass(TextSignalMeer.class);

      testJob.setBossAggregationInterval(1000);
      testJob.setSignalServer(true);
      testJob.waitForCompletion(false);
      //testJob.submit();
    } catch (IOException e) {
      e.printStackTrace();
    }/*
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    */
    
    // Client-side
    /*
    TextSignalMeer client = new TextSignalMeer(conf);
    while (true) {
      System.out.println(client.getLatestResult());
    }
    */
  }

}
