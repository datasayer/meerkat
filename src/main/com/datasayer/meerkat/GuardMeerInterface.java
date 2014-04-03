package main.com.datasayer.meerkat;

import org.apache.hadoop.io.Writable;

public interface GuardMeerInterface<M extends Writable> {
  public void compute();
}
