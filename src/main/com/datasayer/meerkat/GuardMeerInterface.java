package main.com.datasayer.meerkat;

import org.apache.hadoop.io.Writable;

public interface GuardMeerInterface<M extends Writable> {
  public void setBossIndex(int index);
  public int getBossIndex();
  public void sendMessage(M message);
  public void compute(String readLine);
}
