package com.datasayer.meerkat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.Writable;

public class MessageIterator implements Iterator<Writable> {
  private List<Writable> messageList = new ArrayList<Writable>();
  private int currentIndex = 0;
  
  public void add(Writable messageWritable) {
    messageList.add(messageWritable);
  }
  
  @Override
  public boolean hasNext() {
    return currentIndex < messageList.size();
  }

  @Override
  public Writable next() {
    Writable returnWritable = messageList.get(currentIndex);
    currentIndex++;
    return returnWritable;
  }

  @Override
  public void remove() {
    // TODO Auto-generated method stub
  }

}
