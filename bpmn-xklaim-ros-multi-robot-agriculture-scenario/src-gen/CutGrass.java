import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.RosString;
import messages.Wrench;
import org.eclipse.xtext.xbase.lib.Exceptions;
import ros.Publisher;
import util.XklaimToRosConnection;

@SuppressWarnings("all")
public class CutGrass extends KlavaProcess {
  private String edge;
  
  public CutGrass(final String edge) {
    this.edge = edge;
  }
  
  @Override
  public void executeProcess() {
    try {
      final XklaimToRosConnection bridge = new XklaimToRosConnection("ws://127.0.0.1:9090");
      final Publisher pub = new Publisher("/tractor_1/blade_force", "geometry_msgs/msg/Wrench", bridge);
      final Wrench wrenchMsg = new Wrench();
      wrenchMsg.force.x = 0.5;
      wrenchMsg.force.y = 0.0;
      wrenchMsg.force.z = 0.0;
      wrenchMsg.torque.x = 0.0;
      wrenchMsg.torque.y = 0.0;
      wrenchMsg.torque.z = 0.0;
      pub.publish(wrenchMsg);
      Thread.sleep(2000);
      final Publisher pubremove = new Publisher("/delete_entity_trigger", "std_msgs/String", bridge);
      final RosString modelName = new RosString();
      modelName.setData("weed");
      pubremove.publish(modelName);
      out(new Tuple(new Object[] {this.edge}), this.self);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
