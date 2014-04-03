package main.com.datasayer.meerkat;

import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.sync.SyncException;
import org.apache.hama.util.ReflectionUtils;

public class MeerRunner extends BSP<Writable, Writable, Writable, Writable, Writable> {
  private HamaConfiguration conf;
  private int bossIndex = 0;
  private long pollingInterval = MeerAttribute.intervalDefault;
  private GuardMeer<Writable> guardmeer;
  private BossMeer<Writable> bossmeer;
  private BSPPeer<Writable, Writable, Writable, Writable, Writable> peer;

  @Override
  public void bsp(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) throws IOException, SyncException, InterruptedException {
    tail(peer);
  }

  @Override
  public void setup(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) throws IOException, SyncException, InterruptedException {
    super.setup(peer);
    this.peer = peer;
    conf = peer.getConfiguration();
    conf.getLong(MeerAttribute.intervalUri, pollingInterval);
    
    Class<? extends GuardMeer<Writable>> guardClass = (Class<? extends GuardMeer<Writable>>) conf.getClass(MeerAttribute.guardMeerUri, GuardMeer.class, GuardMeerInterface.class);
    guardmeer = ReflectionUtils.newInstance(guardClass);
    guardmeer.setBossIndex(this.bossIndex);
    guardmeer.setMeerRunner(this);
    
    Class<? extends BossMeer<Writable>> bossClass = (Class<? extends BossMeer<Writable>>) conf.getClass(MeerAttribute.bossMeerUri, BossMeer.class, BossMeer.class);
    bossmeer = ReflectionUtils.newInstance(bossClass);
  }
  
  public final BSPPeer<Writable, Writable, Writable, Writable, Writable> getPeer() {
    return this.peer;
  }
  
  private void tail(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) {
    String readLine = "teststring 1";
    guardmeer.compute(readLine);
  }
}
