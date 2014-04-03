package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hama.bsp.sync.SyncException;

public abstract class GuardMeer<M extends Writable> implements GuardMeerInterface<M> {
  
  protected void setMeerRunner(MeerRunner runner) {
    
  }
  
  @Override
  public void sendMessage(M message) {
    
  }
}
