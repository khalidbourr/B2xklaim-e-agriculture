import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;

@SuppressWarnings("all")
public class DroneBehavior extends KlavaProcess {
  private Locality Tractor;
  
  public DroneBehavior(final Locality Tractor) {
    this.Tractor = Tractor;
  }
  
  @Override
  public void executeProcess() {
    Activity_WeedHandler _activity_WeedHandler = new Activity_WeedHandler(this.Tractor);
    eval(_activity_WeedHandler, this.self);
    TakeOff _takeOff = new TakeOff("Flow_1nkd51d");
    eval(_takeOff, this.self);
    in(new Tuple(new Object[] {"Flow_1nkd51d"}), this.self);
    Explore _explore = new Explore("Flow_1y8di18");
    eval(_explore, this.self);
    in(new Tuple(new Object[] {"Flow_1y8di18"}), this.self);
    Land _land = new Land("Flow_07o8u8h");
    eval(_land, this.self);
    in(new Tuple(new Object[] {"Flow_07o8u8h"}), this.self);
  }
}
