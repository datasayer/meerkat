package main.com.datasayer.meerkat;

import org.apache.hadoop.io.Writable;

public interface BossMeerInterface<M extends Writable> {
  public void compute();
}
