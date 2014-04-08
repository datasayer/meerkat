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

import org.apache.hama.HamaConfiguration;
import org.apache.hama.ipc.Client;
import org.apache.hama.ipc.RPC;
import org.apache.hama.ipc.Server;

public abstract class SignalMeer<RESULT> implements
    SignalMeerInterface<RESULT> {

  private boolean isSignalServerNeed = false;
  private HamaConfiguration conf;
  private Server signalServer;

  public SignalMeer(HamaConfiguration conf) throws Exception {
    this.conf = conf;
  }
  
  protected void setServer(boolean isSignalServerNeed) throws IOException {
    this.isSignalServerNeed = isSignalServerNeed;
    if (isSignalServerNeed) {
      
      signalServer = RPC.getServer(
          this
          , conf.get(MeerkatConstants.SIGNAL_HOSTNAME_URI)
          , conf.getInt(MeerkatConstants.SIGNAL_PORT_URI, MeerkatConstants.SIGNAL_PORT)
          , conf.getInt(MeerkatConstants.SIGNAL_THREAD_COUNT_URI, MeerkatConstants.SIGNAL_THREAD_COUNT)
          , false
          , conf);
      signalServer.start();
      
    }
  }

  public Client getClient(HamaConfiguration conf) {
    // return RPC.getProxy(org.apache.hama.ipc.HamaRPCProtocolVersion, 0.1, conf
    // .get("com.datasayer.meerkat.communicator.ip"), conf.getInt(
    // "com.datasayer.meerkat.communicator.port", 1234));

    return null;
  }

}
