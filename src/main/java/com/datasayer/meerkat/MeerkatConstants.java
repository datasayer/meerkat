package com.datasayer.meerkat;

public class MeerkatConstants {
  public final static long AGGREGATION_INTERVAL = 5000L;
  public final static long POLLING_INTERVAL = 5000L;
  public final static int SIGNAL_PORT = 55555;
  public final static int SIGNAL_THREAD_COUNT = 5;
  public final static String SIGNAL_HOSTNAME = "localhost";
  public final static boolean IS_SIGNAL_SERVER_SETUP = false;
  
  public final static String AGGREGATION_INTERVAL_URI = "com.datasayer.meerkat.uri.interval.aggregation";
  public final static String POLLING_INTERVAL_URI = "com.datasayer.meerkat.uri.interval.polling";
  public final static String GUARDMEER_CLASS_URI = "com.datasayer.meerkat.uri.class.guardmeer";
  public final static String BOSSMEER_CLASS_URI = "com.datasayer.meerkat.uri.class.bossmeer";
  public final static String SIGNALMEER_CLASS_URI = "com.datasayer.meerkat.uri.class.signalmeer";
  public final static String LOG_PATH_URI = "com.datasayer.meerkat.uri.logpath";
  public final static String SIGNAL_HOSTNAME_URI = "com.datasayer.meerkat.signal.hostname";
  public final static String SIGNAL_PORT_URI = "com.datasayer.meerkat.signal.port";
  public final static String SIGNAL_THREAD_COUNT_URI = "com.datasayer.meerkat.signal.handler.threads.count";
  public final static String SIGNAL_SERVER_SETUP_URI = "com.datasayer.meerkat.uri.setup.signalserver";
}
