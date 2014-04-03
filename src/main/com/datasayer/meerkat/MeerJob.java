package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;
import org.apache.hama.bsp.NullOutputFormat;


public class MeerJob extends BSPJob {
  private HamaConfiguration conf;
  private long pollingInterval = 5000;
  private Class<? extends GuardMeerInterface> guardmeer;
  private Class<? extends BossMeerInterface> bossmeer;

  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
    this.conf = conf;
    this.setOutputFormat(NullOutputFormat.class);
    this.setBspClass(MeerRunner.class);
  }
  
  public void setPollingInterval(long interval) {
    this.pollingInterval = interval;
    this.conf.setLong(MeerAttribute.intervalUri, interval);
  }
  
  public void setGuardMeer(Class<? extends GuardMeerInterface> guardmeer) {
    this.guardmeer = guardmeer;
    this.conf.setClass(MeerAttribute.guardMeerUri, GuardMeer.class, GuardMeerInterface.class);
  }
  
  public void setBossMeer(Class<? extends BossMeerInterface> bossmeer) {
    this.bossmeer = bossmeer;
    this.conf.setClass(MeerAttribute.bossMeerUri, BossMeer.class, BossMeerInterface.class);
  }
}
