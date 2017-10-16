package edu.wpi.first.wpilibj;

public abstract class JoystickBase extends GenericHID {
	
	public JoystickBase(int port) {
		super(port);
	}
	
	public abstract double getZ(Hand hand);
	
	public abstract double getTwist();
	
	public abstract double getThrottle();
		
	public abstract boolean getTrigger(Hand hand);
		
	public abstract boolean getTop(Hand hand);
	
	public abstract int getPOV(int pov);
	
	public abstract int getPOVCount();
	
	public abstract String getName();
	
	
	
}
