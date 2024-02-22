import klava.Tuple;
import klava.topology.KlavaProcess;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Activity_Handler extends KlavaProcess {
  public Activity_Handler() {
    
  }
  
  @Override
  public void executeProcess() {
    try {
      read(new Tuple(new Object[] {"weed_detected"}), this.self);
      Double currentX = null;
      Double currentY = null;
      Tuple _Tuple = new Tuple(new Object[] {"position", Double.class, Double.class});
      read(_Tuple, this.self);
      currentX = (Double) _Tuple.getItem(1);
      currentY = (Double) _Tuple.getItem(2);
      InputOutput.<String>println((((("Weed detected at position: (" + currentX) + ", ") + currentY) + ")"));
      out(new Tuple(new Object[] {"Signal_1fgvsug", currentX, currentY}), this.self);
      Thread.sleep(500);
      Double X = null;
      Double Y = null;
      Tuple _Tuple_1 = new Tuple(new Object[] {"Signal_1fgvsug", Double.class, Double.class});
      in(_Tuple_1, this.self);
      X = (Double) _Tuple_1.getItem(1);
      Y = (Double) _Tuple_1.getItem(2);
      Activity_Handler _activity_Handler = new Activity_Handler();
      eval(_activity_Handler, this.self);
      this.stop();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
