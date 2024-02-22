import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.Odometry;
import messages.Twist;
import org.eclipse.xtext.xbase.lib.Exceptions;
import ros.Publisher;
import ros.RosListenDelegate;
import ros.SubscriptionRequestMsg;
import util.XklaimToRosConnection;

@SuppressWarnings("all")
public class Navigation extends KlavaProcess {
  private String edge;
  
  public Navigation(final String edge) {
    this.edge = edge;
  }
  
  @Override
  public void executeProcess() {
    final Locality local = this.self;
    final XklaimToRosConnection bridge = new XklaimToRosConnection("ws://0.0.0.0:9090");
    final Publisher pub = new Publisher("/drone_1/cmd_vel", "geometry_msgs/Twist", bridge);
    out(new Tuple(new Object[] {"position", 0.0, 0.0}), this.self);
    final RosListenDelegate _function = (JsonNode odometryData, String stringRep) -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode odomNode = odometryData.get("msg");
        Odometry currentOdom = mapper.<Odometry>treeToValue(odomNode, Odometry.class);
        double odomX = currentOdom.pose.pose.position.x;
        double odomY = currentOdom.pose.pose.position.y;
        Double currentX = null;
        Double currentY = null;
        Tuple _Tuple = new Tuple(new Object[] {"position", Double.class, Double.class});
        in(_Tuple, local);
        currentX = (Double) _Tuple.getItem(1);
        currentY = (Double) _Tuple.getItem(2);
        out(new Tuple(new Object[] {"position", odomX, odomY}), local);
        Twist velocity = new Twist();
        final double tolerance = 0.2;
        double _floor = Math.floor(odomY);
        double _modulo = (_floor % 2);
        boolean _equals = (_modulo == 0);
        if (_equals) {
          if ((odomX < (9.7 - tolerance))) {
            velocity.linear.x = 1.5;
            velocity.linear.y = 0.0;
          } else {
            if ((odomY < (9.7 - tolerance))) {
              velocity.linear.x = 0.0;
              velocity.linear.y = 1.5;
            } else {
              velocity.linear.x = 0.0;
              velocity.linear.y = 0.0;
            }
          }
        } else {
          if ((odomX > (0.2 + tolerance))) {
            velocity.linear.x = (-1.5);
            velocity.linear.y = 0.0;
          } else {
            if ((odomY < (9.7 - tolerance))) {
              velocity.linear.x = 0.0;
              velocity.linear.y = 1.5;
            } else {
              velocity.linear.x = 0.0;
              velocity.linear.y = 0.0;
              bridge.unsubscribe("/drone_1/odom");
              out(new Tuple(new Object[] {this.edge}), local);
            }
          }
        }
        pub.publish(velocity);
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    bridge.subscribe(
      SubscriptionRequestMsg.generate("/drone_1/odom").setType("nav_msgs/Odometry").setThrottleRate(Integer.valueOf(1)).setQueueLength(Integer.valueOf(1)), _function);
  }
}
