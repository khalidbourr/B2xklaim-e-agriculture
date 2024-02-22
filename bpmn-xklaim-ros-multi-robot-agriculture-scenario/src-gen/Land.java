import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.RosString;
import messages.Twist;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import ros.Publisher;
import util.XklaimToRosConnection;

@SuppressWarnings("all")
public class Land extends KlavaProcess {
  private String edge;
  
  public Land(final String edge) {
    this.edge = edge;
  }
  
  @Override
  public void executeProcess() {
    try {
      InputOutput.<String>println("Landing ...");
      final XklaimToRosConnection bridge = new XklaimToRosConnection("ws://0.0.0.0:9090");
      final Publisher pub = new Publisher("/drone_command_trigger", "std_msgs/String", bridge);
      final RosString triggerMsg = new RosString();
      triggerMsg.setData("land");
      pub.publish(triggerMsg);
      Thread.sleep(5000);
      final Twist twist = new Twist();
      pub.publish(twist);
      InputOutput.<String>println("Landed");
      out(new Tuple(new Object[] {this.edge}), this.self);
      out(new Tuple(new Object[] {this.edge}), this.self);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
