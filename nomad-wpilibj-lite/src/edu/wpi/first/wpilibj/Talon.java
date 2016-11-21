package edu.wpi.first.wpilibj;

public class Talon extends SafePWM implements SpeedController {
	
	public Talon(int channel) {
		super(channel);
	}

	@Override
	public double get() {
		return getSpeed();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		setSpeed(speed);
	}

	@Override
	public void set(double speed) {
		setSpeed(speed);
	}


}
