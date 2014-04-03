package test.com.datasayer.meerkat;

import main.com.datasayer.meerkat.GuardMeer;

public class MyGuard<M extends MyKeyValue> extends GuardMeer<MyKeyValue> {

  @Override
  public void compute(String readLine) {
    System.out.println(String.format("myguard recieved message : %s", readLine));
    String[] tmpValue = readLine.split(" ");
    MyKeyValue message = new MyKeyValue(tmpValue[0],Integer.parseInt(tmpValue[1]));
    this.sendMessage(message);
  }

}
