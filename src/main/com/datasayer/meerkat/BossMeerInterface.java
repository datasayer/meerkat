package main.com.datasayer.meerkat;

import org.apache.hadoop.io.Writable;

/**
 * <pre>
 * main.com.datasayer.meerkat
 * BossMeerInterface.java
 * </pre>
 * 
 * @author : garuda
 * 
 * @param <M> Recieve Message Object Type from Guard
 */
public interface BossMeerInterface<M extends Writable> {
  /**
   * method : compute
   * description : User Define Function.<br>
   * Aggregate & Compute Data has Recieved from Guard.
   * @param message<br>
   * Recieve Message Object Type from Guard
   */
  public void compute(M message);
}
