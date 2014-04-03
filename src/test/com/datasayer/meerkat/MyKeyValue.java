package test.com.datasayer.meerkat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class MyKeyValue implements Writable {
  private String key;
  private int value;
  
  public MyKeyValue(String key, int value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    key = in.readUTF();
    value = in.readInt();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeUTF(key);
    out.writeInt(value);
  }

  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public int getValue() {
    return value;
  }
  public void setValue(int value) {
    this.value = value;
  }

}
