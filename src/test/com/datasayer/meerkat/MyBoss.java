package test.com.datasayer.meerkat;

import main.com.datasayer.meerkat.BossMeer;

/**
 * <pre>
 * test.com.datasayer.meerkat
 * MyBoss.java
 * </pre>
 * 
 * @author : garuda
 * 
 * @param <M> Recieve Message Object Type from Guard
 */
public class MyBoss<M extends MyKeyValue> extends BossMeer<MyKeyValue> {

  @Override
  public void compute(MyKeyValue message) {
    // calculation logic here
    // print out data calculation ended.
    // print out is whatever u want local file / System.out / JDBC
    // it depends your code
  }

}
