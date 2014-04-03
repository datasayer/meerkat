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
  private GuardMeerInterface guardmeer;
  private BossMeerInterface bossmeer;
  private BSPPeer<Writable, Writable, Writable, Writable, Writable> peer;

  @Override
  public void bsp(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) throws IOException, SyncException, InterruptedException {

  }

  @Override
  public void setup(BSPPeer<Writable, Writable, Writable, Writable, Writable> peer) throws IOException, SyncException, InterruptedException {
    super.setup(peer);
    this.peer = peer;
    conf = peer.getConfiguration();
    conf.getLong(MeerAttribute.intervalUri, pollingInterval);
    
    Class<? extends GuardMeerInterface> iguard = conf.getClass(MeerAttribute.guardMeerUri, GuardMeer.class, GuardMeerInterface.class);
    guardmeer = ReflectionUtils.newInstance(iguard);
    guardmeer.setBossIndex(this.bossIndex);
    ((GuardMeer)guardmeer).setMeerRunner(this);
    
    Class<? extends BossMeerInterface> iboss = conf.getClass(MeerAttribute.bossMeerUri, BossMeer.class, BossMeerInterface.class);
    bossmeer = ReflectionUtils.newInstance(iboss);
  }
  
  public final BSPPeer<Writable, Writable, Writable, Writable, Writable> getPeer() {
    return this.peer;
  }
}
