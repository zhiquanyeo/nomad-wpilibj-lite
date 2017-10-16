package edu.wpi.first.wpilibj;

public class DemoRobot extends SampleRobot {
	Joystick stick = new Joystick(0);
	
	protected void robotInit() {
		System.out.println("Hello! I am DemoRobot");
	}
	
	protected void autonomous() {
		
	}
	
	protected void operatorControl() {
		while (this.isOperatorControl() && this.isEnabled()) {
			System.out.println("Joystick Axes: " + stick.getRawAxis(0) + ", " + stick.getRawAxis(1) +
								", " + stick.getRawAxis(2) + ", " + stick.getRawAxis(3));
		}
	}
	
	protected void disabled() {
		System.out.println("I've been disabled");
		while (this.isDisabled()) {
			System.out.println("Joystick Axes: " + stick.getRawAxis(0) + ", " + stick.getRawAxis(1) +
								", " + stick.getRawAxis(2) + ", " + stick.getRawAxis(3));
		}
	}
}
