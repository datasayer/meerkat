package test.com.datasayer.meerkat;

import main.com.datasayer.meerkat.GuardMeer;

/**
 * <pre>
 * test.com.datasayer.meerkat
 * MyGuard.java
 * </pre>
 * 
 * @author : garuda
 * 
 * @param <M> Message Object Type to Send Boss
 */
public class MyGuard<M extends MyKeyValue> extends GuardMeer<MyKeyValue> {

  @Override
  public void compute(String readLine) {
    // data parsing here
    // and make message to send boss
    // hey boss! here's my data!! :-)
  }

}
