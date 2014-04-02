package com.datasayer.meerkat.tail;

import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;

public interface ParserInterface<M extends Writable>{
  public void setConf(HamaConfiguration conf);
  public HamaConfiguration getConf();
  public void parse(String readLine);
  public void send(M message);
}
