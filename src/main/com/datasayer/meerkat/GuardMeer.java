package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hadoop.io.Writable;

public abstract class GuardMeer<M extends Writable> implements GuardMeerInterface<M> {
  private int bossIndex = 0;
  private MeerRunner runner;
  
  @Override
  public void setBossIndex(int index) {
    this.bossIndex = index;
  }
  
  @Override
  public int getBossIndex() {
    return this.bossIndex;
  }
  
  protected void setMeerRunner(MeerRunner runner) {
    this.runner = runner;
  }
  
  @Override
  public void sendMessage(M message) {
    try {
      runner.getPeer().send(runner.getPeer().getPeerName(this.bossIndex), message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
