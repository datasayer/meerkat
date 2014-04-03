package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;
import org.apache.hama.bsp.NullOutputFormat;


public class MeerJob extends BSPJob {
  private HamaConfiguration conf;
  private long pollingInterval = MeerAttribute.intervalDefault;
  private Class<? extends GuardMeer<Writable>> guardmeer;
  private Class<? extends BossMeer<Writable>> bossmeer;

  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
    this.conf = conf;
    this.setOutputFormat(NullOutputFormat.class);
    this.setBspClass(MeerRunner.class);
  }
  
  public void setPollingInterval(long interval) {
    this.pollingInterval = interval;
    this.conf.setLong(MeerAttribute.intervalUri, this.pollingInterval);
  }
  
  public <G extends GuardMeer<Writable>> void setGuardMeer(Class<G> guardmeer) {
    this.guardmeer = guardmeer;
    this.conf.setClass(MeerAttribute.guardMeerUri, this.guardmeer, GuardMeer.class);
  }
  
  public <B extends BossMeer<Writable>> void setBossMeer(Class<B> bossmeer) {
    this.bossmeer = bossmeer;
    this.conf.setClass(MeerAttribute.bossMeerUri, this.bossmeer, BossMeer.class);
  }
}
