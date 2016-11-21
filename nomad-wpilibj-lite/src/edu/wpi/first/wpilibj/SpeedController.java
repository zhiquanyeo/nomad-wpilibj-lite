package edu.wpi.first.wpilibj;

public interface SpeedController {
	double get();
	void set(double speed, byte syncGroup);
	void set(double speed);
	void disable();
}
