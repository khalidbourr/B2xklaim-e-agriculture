import klava.Tuple;
import klava.topology.KlavaProcess;

@SuppressWarnings("all")
public class TractorBehavior extends KlavaProcess {
  public TractorBehavior() {
    
  }
  
  @Override
  public void executeProcess() {
    Double x = null;
    Double y = null;
    Tuple _Tuple = new Tuple(new Object[] {"Message_31nq93q", Double.class, Double.class});
    in(_Tuple, this.self);
    x = (Double) _Tuple.getItem(1);
    y = (Double) _Tuple.getItem(2);
    MoveTo _moveTo = new MoveTo("Flow_1650rev", x, y);
    eval(_moveTo, this.self);
    in(new Tuple(new Object[] {"Flow_1650rev"}), this.self);
    CutGrass _cutGrass = new CutGrass("Flow_0xckd21");
    eval(_cutGrass, this.self);
    in(new Tuple(new Object[] {"Flow_0xckd21"}), this.self);
    ReturnToBase _returnToBase = new ReturnToBase("Flow_01jpx6x");
    eval(_returnToBase, this.self);
    in(new Tuple(new Object[] {"Flow_01jpx6x"}), this.self);
  }
}
