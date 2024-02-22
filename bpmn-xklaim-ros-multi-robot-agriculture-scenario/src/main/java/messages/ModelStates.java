package messages;

public class ModelStates {
	public RosString[] name;
	public Pose[] pose = new Pose[1];
	public Twist[] twist = new Twist[1];
	


    public ModelStates(){}

    public ModelStates(RosString[] names, Pose[] pose, Twist[] twist) {
        this.name = names;
        this.pose = pose;
        this.twist = twist;
    }

}
