package edu.wpi.first.wpilibj;

public class SampleRobot extends RobotBase {
	private boolean d_robotMainOverridden;
	
	public SampleRobot() {
		super();
		d_robotMainOverridden = true;
	}
	
	protected void robotInit() {
		System.out.println("Default robotInit() method running, consider providing your own");
	}
	
	protected void disabled() {
		System.out.println("Default disabled() method running, consider providing your own");
	}
	
	protected void autonomous() {
		System.out.println("Default autonomous() method running, consider providing your own");
	}
	
	protected void operatorControl() {
		System.out.println("Default operatorControl() method running, consider providing your own");
	}
	
	protected void test() {
		System.out.println("Default test() method running, consider providing your own");
	}
	
	public void robotMain() {
		d_robotMainOverridden = false;
	}
	
	@Override
	public void startCompetition() {
		robotInit();
		
		// TODO Tell the DS that the robot is ready
		
		robotMain();
		if (!d_robotMainOverridden) {
			while (true) {
				// TODO implement
			}
		}
	}

}
