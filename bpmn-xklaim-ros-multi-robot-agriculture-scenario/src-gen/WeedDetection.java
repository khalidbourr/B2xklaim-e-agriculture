import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import messages.Range;
import org.eclipse.xtext.xbase.lib.Exceptions;
import ros.RosListenDelegate;
import ros.SubscriptionRequestMsg;
import util.XklaimToRosConnection;

@SuppressWarnings("all")
public class WeedDetection extends KlavaProcess {
  private String edge;
  
  public WeedDetection(final String edge) {
    this.edge = edge;
  }
  
  @Override
  public void executeProcess() {
    final Locality local = this.self;
    final XklaimToRosConnection bridge = new XklaimToRosConnection("ws://0.0.0.0:9090");
    final RosListenDelegate _function = (JsonNode rangeData, String stringRep) -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rangeNode = rangeData.get("msg");
        Range currentRange = mapper.<Range>treeToValue(rangeNode, Range.class);
        if ((currentRange.range < 1.4)) {
          out(new Tuple(new Object[] {"weed_detected"}), local);
          Thread.sleep(500);
          in(new Tuple(new Object[] {"weed_detected"}), local);
          bridge.unsubscribe("/drone_1/range");
          out(new Tuple(new Object[] {this.edge}), local);
        }
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    bridge.subscribe(
      SubscriptionRequestMsg.generate("/drone_1/range").setType("sensor_msgs/Range").setThrottleRate(Integer.valueOf(11)).setQueueLength(Integer.valueOf(1)), _function);
  }
}
