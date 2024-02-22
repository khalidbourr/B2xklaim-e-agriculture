package messages;

public class ModelState {
	public RosString model_name  ;
	public Pose pose = new Pose();
	public Twist twist = new Twist();
	public RosString reference_frame;


    public ModelState(){}

    public ModelState(RosString names, Pose pose, Twist twist, RosString reference_frame) {
        this.model_name = names;
        this.pose = pose;
        this.twist = twist;
        this.reference_frame=reference_frame;
    }

}
