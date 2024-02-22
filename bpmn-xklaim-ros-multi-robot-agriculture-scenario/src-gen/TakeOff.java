import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.RosString;
import messages.Twist;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import ros.Publisher;
import util.XklaimToRosConnection;

@SuppressWarnings("all")
public class TakeOff extends KlavaProcess {
  private String edge;
  
  public TakeOff(final String edge) {
    this.edge = edge;
  }
  
  @Override
  public void executeProcess() {
    try {
      InputOutput.<String>println("Taking off ..");
      final XklaimToRosConnection bridge = new XklaimToRosConnection("ws://0.0.0.0:9090");
      final Publisher pub = new Publisher("/drone_command_trigger", "std_msgs/String", bridge);
      final RosString triggerMsg = new RosString();
      triggerMsg.setData("takeoff");
      pub.publish(triggerMsg);
      Thread.sleep(5500);
      final Twist twist = new Twist();
      pub.publish(twist);
      out(new Tuple(new Object[] {this.edge}), this.self);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
