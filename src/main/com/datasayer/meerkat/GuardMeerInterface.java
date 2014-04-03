package main.com.datasayer.meerkat;

import org.apache.hadoop.io.Writable;


/**
 * <pre>
 * main.com.datasayer.meerkat
 * GuardMeerInterface.java
 * </pre>
 * 
 * @author : garuda
 * 
 * @param <M> Message Object Type to Send Boss
 */
public interface GuardMeerInterface<M extends Writable> {
  /**
   * method : compute
   * description : User Define Function.<br>
   * Recieve String Data from MeerRunner
   * and Parse Data to Send Message Writable<M> What User Want.
   * @param readLine<br>
   * String data has gathered from File or etc..
   */
  public void compute(String readLine);
  
  /**
   * method : sendMessage
   * description : Send Message to Boss
   * @param message<br>
   * Message Object Type to Send Boss
   */
  public void sendMessage(M message);
}
