package test.com.datasayer.meerkat;

import java.io.IOException;

import main.com.datasayer.meerkat.MeerJob;

import org.apache.hama.HamaConfiguration;

public class MeerkatMain {

  public static void main(String[] args) {
    try {
      HamaConfiguration conf = new HamaConfiguration();
      MeerJob job = new MeerJob(conf);
      job.setNumBspTask(1);
      job.waitForCompletion(true);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
}
