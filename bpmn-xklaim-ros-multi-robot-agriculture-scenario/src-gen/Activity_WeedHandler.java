import klava.Locality;
import klava.Tuple;
import klava.topology.KlavaProcess;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Activity_WeedHandler extends KlavaProcess {
  private Locality Tractor;
  
  public Activity_WeedHandler(final Locality Tractor) {
    this.Tractor = Tractor;
  }
  
  @Override
  public void executeProcess() {
    Double x = null;
    Double y = null;
    Tuple _Tuple = new Tuple(new Object[] {"Signal_1fgvsug", Double.class, Double.class});
    read(_Tuple, this.self);
    x = (Double) _Tuple.getItem(1);
    y = (Double) _Tuple.getItem(2);
    InputOutput.<String>println((((("WeedHandler processing detected weed at: (" + x) + ", ") + y) + ")"));
    out(new Tuple(new Object[] {"Message_31nq93q", x, y}), this.Tractor);
    Activity_WeedHandler _activity_WeedHandler = new Activity_WeedHandler(this.Tractor);
    eval(_activity_WeedHandler, this.self);
  }
}
