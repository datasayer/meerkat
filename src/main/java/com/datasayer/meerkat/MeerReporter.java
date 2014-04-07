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

import org.apache.hama.HamaConfiguration;
import org.apache.hama.ipc.Client;
import org.apache.hama.ipc.RPC;
import org.apache.hama.ipc.Server;

public abstract class MeerReporter<RESULT> implements
    MeerReporterInterface<RESULT> {

  Boolean isReportServerNeeded = true;
  Server reportServer;

  public MeerReporter(HamaConfiguration conf) throws Exception {
    if (isReportServerNeeded) {
      reportServer = RPC.getServer(this, conf
          .get("com.datasayer.meerkat.communicator.ip"), conf.getInt(
          "com.datasayer.meerkat.communicator.port", 1234), conf.getInt(
          "com.datasayer.meerkat.communicator.handler.threads.num", 5), false,
          conf);
    }
  }

  public Client getClient(HamaConfiguration conf) {
    // return RPC.getProxy(org.apache.hama.ipc.HamaRPCProtocolVersion, 0.1, conf
    // .get("com.datasayer.meerkat.communicator.ip"), conf.getInt(
    // "com.datasayer.meerkat.communicator.port", 1234));

    return null;
  }

}
