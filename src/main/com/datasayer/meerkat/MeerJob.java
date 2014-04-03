package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;
import org.apache.hama.bsp.NullOutputFormat;


public class MeerJob extends BSPJob {
  
  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
  }
  
  public void setPollingInterval(long interval) {
    
  }
  
  public <G extends GuardMeer<Writable>> void setGuardMeer(Class<G> guardmeer) {
    
  }
  
  public <B extends BossMeer<Writable>> void setBossMeer(Class<B> bossmeer) {
    
  }
}
