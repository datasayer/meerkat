package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;

public class MeerJob extends BSPJob {

  public MeerJob(HamaConfiguration conf) throws IOException {
    super(conf);
  }

}
