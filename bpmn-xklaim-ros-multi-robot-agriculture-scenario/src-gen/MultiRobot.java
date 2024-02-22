import klava.LogicalLocality;
import klava.PhysicalLocality;
import klava.topology.ClientNode;
import klava.topology.KlavaNodeCoordinator;
import klava.topology.LogicalNet;
import org.mikado.imc.common.IMCException;

@SuppressWarnings("all")
public class MultiRobot extends LogicalNet {
  private static final LogicalLocality Drone = new LogicalLocality("Drone");
  
  private static final LogicalLocality Tractor = new LogicalLocality("Tractor");
  
  public static class Drone extends ClientNode {
    private static class DroneProcess extends KlavaNodeCoordinator {
      @Override
      public void executeProcess() {
        DroneBehavior _droneBehavior = new DroneBehavior(MultiRobot.Tractor);
        eval(_droneBehavior, this.self);
      }
    }
    
    public Drone() {
      super(new PhysicalLocality("localhost:9999"), new LogicalLocality("Drone"));
    }
    
    public void addMainProcess() throws IMCException {
      addNodeCoordinator(new MultiRobot.Drone.DroneProcess());
    }
  }
  
  public static class Tractor extends ClientNode {
    private static class TractorProcess extends KlavaNodeCoordinator {
      @Override
      public void executeProcess() {
        TractorBehavior _tractorBehavior = new TractorBehavior();
        eval(_tractorBehavior, this.self);
      }
    }
    
    public Tractor() {
      super(new PhysicalLocality("localhost:9999"), new LogicalLocality("Tractor"));
    }
    
    public void addMainProcess() throws IMCException {
      addNodeCoordinator(new MultiRobot.Tractor.TractorProcess());
    }
  }
  
  public MultiRobot() throws IMCException {
    super(new PhysicalLocality("localhost:9999"));
  }
  
  public void addNodes() throws IMCException {
    MultiRobot.Drone drone = new MultiRobot.Drone();
    MultiRobot.Tractor tractor = new MultiRobot.Tractor();
    drone.addMainProcess();
    tractor.addMainProcess();
  }
}
