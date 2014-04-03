package main.com.datasayer.meerkat.tail;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.sync.SyncException;

public class TailBSP extends BSP<NullWritable,NullWritable,NullWritable,NullWritable,NullWritable>{
  private BSPPeer peer;
  
  @Override
  public void bsp(
      BSPPeer<NullWritable, NullWritable, NullWritable, NullWritable, NullWritable> peer)
      throws IOException, SyncException, InterruptedException {
    
  }

  @Override
  public void setup(
      BSPPeer<NullWritable, NullWritable, NullWritable, NullWritable, NullWritable> peer)
      throws IOException, SyncException, InterruptedException {
    super.setup(peer);
    this.peer = peer;
  }
  
  public final BSPPeer getPeer() {
    return this.peer;
  }
}
