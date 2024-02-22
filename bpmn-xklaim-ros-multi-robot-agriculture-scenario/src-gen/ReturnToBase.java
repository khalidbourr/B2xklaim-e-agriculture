import klava.Tuple;
import klava.topology.KlavaProcess;

@SuppressWarnings("all")
public class ReturnToBase extends KlavaProcess {
  private String edge;
  
  public ReturnToBase(final String edge) {
    this.edge = edge;
  }
  
  @Override
  public void executeProcess() {
    final double x = 0.0;
    final double y = 2.0;
    MoveTo _moveTo = new MoveTo(this.edge, Double.valueOf(x), Double.valueOf(y));
    eval(_moveTo, this.self);
    out(new Tuple(new Object[] {this.edge}), this.self);
  }
}
