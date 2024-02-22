import klava.Tuple;
import klava.topology.KlavaProcess;

@SuppressWarnings("all")
public class Explore extends KlavaProcess {
  private String edge;
  
  public Explore(final String edge) {
    this.edge = edge;
  }
  
  @Override
  public void executeProcess() {
    Activity_Handler _activity_Handler = new Activity_Handler();
    eval(_activity_Handler, this.self);
    WeedDetection _weedDetection = new WeedDetection("Flow_0a2zlxl");
    eval(_weedDetection, this.self);
    Navigation _navigation = new Navigation("Flow_1wovj1u");
    eval(_navigation, this.self);
    in(new Tuple(new Object[] {"Flow_0a2zlxl"}), this.self);
    in(new Tuple(new Object[] {"Flow_1wovj1u"}), this.self);
    out(new Tuple(new Object[] {this.edge}), this.self);
  }
}
