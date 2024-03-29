package messages;


public class JointTrajectory {
	public Header header = new Header(0, new Time(), "");
	public RosString[] joint_names;
	public JointTrajectoryPoint[] points = new JointTrajectoryPoint[1];

	public JointTrajectory() {

	}

	public JointTrajectory jointNames(RosString[] joint_names) {
		this.joint_names = joint_names;
		return this;
	}

	public JointTrajectory header(Header header) {
		this.header = header;
		return this;
	}

	public JointTrajectory points(JointTrajectoryPoint[] points) {
		this.points = points;
		return this;
	}

	public JointTrajectory point(JointTrajectoryPoint point) {
		return points(new JointTrajectoryPoint[] {point});
	}

	public JointTrajectory positions(double[] positions) {
		return point(new JointTrajectoryPoint().positions(positions));
	}
	public JointTrajectory positions(double[] positions,double[] velocities) {
		return point(new JointTrajectoryPoint().positions(positions).velocities(velocities));
	}
}
