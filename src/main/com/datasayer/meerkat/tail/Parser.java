package main.com.datasayer.meerkat.tail;

import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;

public abstract class Parser<M extends Writable> implements ParserInterface<M>{
  private TailBSP bsp;
  
  @Override
  public void setConf(HamaConfiguration conf) {
    
  }
  
  @Override
  public HamaConfiguration getConf() {
    return this.bsp.getPeer().getConfiguration();
  }
  
  protected void setBSP(TailBSP bsp) {
    this.bsp = bsp; 
  }
}
