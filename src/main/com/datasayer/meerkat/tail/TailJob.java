package main.com.datasayer.meerkat.tail;

import java.io.IOException;

import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;


public class TailJob extends BSPJob {
  private final String parserUri = "com.datasayer.meerkat.tail.Parser";
  private final String aggregaterUri = "com.datasayer.meerkat.tail.Aggregater";
  private final String intervalUri = "com.datasayer.meerkat.tail.resource.interval";
  private long interval = 5000;
  private Class<? extends ParserInterface> parser;
  
  public TailJob(HamaConfiguration conf) throws IOException {
    super(conf);
  }

  public void setPollingInterval(long interval) {
    this.interval = interval;
    this.conf.setLong(intervalUri, this.interval);
  }
  
  public void setParser(Class<? extends ParserInterface> parser) {
    this.parser = parser;
    this.conf.setClass(parserUri, parser, ParserInterface.class);
  }
}
