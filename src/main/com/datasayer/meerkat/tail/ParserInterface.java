package main.com.datasayer.meerkat.tail;

import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;

/**
 * <pre>
 * com.datasayer.meerkat.tail
 * ParserInterface.java
 * </pre>
 * 
 * @author : garuda
 * 
 * @param <M> generic parsed object to send BSP Node
 */
public interface ParserInterface<M extends Writable>{
  public void setConf(HamaConfiguration conf);
  public HamaConfiguration getConf();
  public void parse(String readLine);
  public void send(M message);
  public void aggregate();
}
