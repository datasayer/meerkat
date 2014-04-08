package com.datasayer.meerkat;

import org.apache.hadoop.io.Text;
import org.apache.hama.HamaConfiguration;

public class TextSignalMeer extends SignalMeer<Text> {

  public TextSignalMeer(HamaConfiguration conf) throws Exception {
    super(conf);
  }

  Text result = new Text();

  @Override
  public void setResult(Text result) {
    this.result.set(result);
  }

  @Override
  public Text getLatestResult() {
    return result;
  }
}