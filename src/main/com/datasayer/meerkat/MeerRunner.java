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
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.sync.SyncException;
import org.apache.hama.util.ReflectionUtils;

public class MeerRunner extends BSP<Writable, Writable, Writable, Writable, Writable> {

  @Override
  public void bsp(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) throws IOException, SyncException, InterruptedException {
    
  }

  @Override
  public void setup(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) throws IOException, SyncException, InterruptedException {
    super.setup(peer);
  }
  
  public final BSPPeer<Writable, Writable, Writable, Writable, Writable> getPeer() {
    return null;
  }
  
  private void tail(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) {
    
  }
}
