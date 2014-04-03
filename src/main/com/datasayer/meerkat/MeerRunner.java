package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.sync.SyncException;

public class MeerRunner extends BSP<Writable, Writable, Writable, Writable, Writable>{

  @Override
  public void bsp(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer)
      throws IOException, SyncException, InterruptedException {
    
  }

  @Override
  public void setup(
      BSPPeer<Writable, Writable, Writable, Writable, Writable> peer)
      throws IOException, SyncException, InterruptedException {
    super.setup(peer);
  }
}
