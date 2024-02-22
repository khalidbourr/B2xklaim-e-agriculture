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
import util.EulerAngles;
import util.XklaimToRosConnection;

@SuppressWarnings("all")
public class MoveTo extends KlavaProcess {
  private String edge;
  
  private Double x;
  
  private Double y;
  
  public MoveTo(final String edge, final Double x, final Double y) {
    this.edge = edge;
    this.x = x;
    this.y = y;
  }
  
  @Override
  public void executeProcess() {
    final Locality local = this.self;
    final String robotId = "tractor_1";
    final double distanceTolerance = 0.1;
    final double angleTolerance = 0.1;
    final double targetOffset = 0.35;
    final XklaimToRosConnection bridge = new XklaimToRosConnection("ws://0.0.0.0:9090");
    final Publisher pub = new Publisher((("/" + robotId) + "/cmd_vel"), "geometry_msgs/Twist", bridge);
    final Twist vel_msg = new Twist();
    final double PI = 3.141592654;
    final double K_l_max = 1.1;
    final double K_a_max = 1.2;
    final double K_l_min = 0.1;
    final double K_a_min = 0.1;
    final double maxDistance = 5.0;
    final double minDistance = 0.5;
    final RosListenDelegate _function = (JsonNode data, String stringRep) -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rosMsgNode = data.get("msg");
        Odometry odom = mapper.<Odometry>treeToValue(rosMsgNode, Odometry.class);
        double currentX = odom.pose.pose.position.x;
        double currentY = odom.pose.pose.position.y;
        EulerAngles angle = new EulerAngles(odom.pose.pose.orientation);
        double currentTheta = angle.getYaw();
        double deltaX = ((this.x).doubleValue() - currentX);
        double deltaY = ((this.y).doubleValue() - currentY);
        double distance = Math.sqrt(((deltaX * deltaX) + (deltaY * deltaY)));
        double effectiveDistance = (distance - targetOffset);
        double angular = Math.atan2(deltaY, deltaX);
        double headingError = (angular - currentTheta);
        if ((headingError > PI)) {
          double _headingError = headingError;
          headingError = (_headingError - (2 * PI));
        }
        if ((headingError < (-PI))) {
          double _headingError_1 = headingError;
          headingError = (_headingError_1 + (2 * PI));
        }
        double absAngularError = Math.abs(headingError);
        double angularVelocityFactor = (absAngularError / PI);
        double adjustedK_a = (K_a_min + (angularVelocityFactor * (K_a_max - K_a_min)));
        double minAngularVelocity = 0.2;
        double angularVelocity = (adjustedK_a * headingError);
        if (((absAngularError > angleTolerance) && (Math.abs(angularVelocity) < minAngularVelocity))) {
          angularVelocity = Math.copySign(minAngularVelocity, angularVelocity);
        }
        vel_msg.angular.z = angularVelocity;
        double _min = Math.min(distance, maxDistance);
        double _minus = (_min - minDistance);
        double _divide = (_minus / (maxDistance - minDistance));
        double _multiply = (_divide * (K_l_max - K_l_min));
        double K_l = (K_l_min + _multiply);
        K_l = Math.max(K_l_min, Math.min(K_l, K_l_max));
        if ((effectiveDistance > distanceTolerance)) {
          double _abs = Math.abs(headingError);
          boolean _greaterThan = (_abs > angleTolerance);
          if (_greaterThan) {
            vel_msg.linear.x = 0.0;
          } else {
            vel_msg.linear.x = (K_l * effectiveDistance);
            vel_msg.angular.z = 0.0;
          }
          pub.publish(vel_msg);
        } else {
          vel_msg.linear.x = 0;
          vel_msg.angular.z = 0;
          pub.publish(vel_msg);
          bridge.unsubscribe((("/" + robotId) + "/odom"));
          out(new Tuple(new Object[] {this.edge}), local);
        }
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    bridge.subscribe(
      SubscriptionRequestMsg.generate((("/" + robotId) + "/odom")).setType("nav_msgs/Odometry").setThrottleRate(Integer.valueOf(1)).setQueueLength(Integer.valueOf(1)), _function);
  }
}
