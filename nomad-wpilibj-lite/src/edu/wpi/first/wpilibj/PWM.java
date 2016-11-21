package edu.wpi.first.wpilibj;

public class PWM extends SensorBase {
	private int d_channel;
	private double d_rawVal = 0.0;
	private int d_rawValInt = 0;
	
	private void initPWM(final int channel) {
		checkPWMChannel(channel);
		d_channel = channel;
	}
	
	public PWM(final int channel) {
		initPWM(channel);
	}
	
	public int getChannel() {
		return d_channel;
	}
	
	public void setPosition(double pos) {
		if (pos < 0.0) {
			pos = 0.0;
		}
		else if (pos > 1.0) {
			pos = 1.0;
		}
		
		int rawValue = (int)(255 * (pos / 1.0));
		setRaw(rawValue);
	}
	
	public double getPosition() {
		int value = getRaw();
		return (value / 255.0);
	}
	
	public void setSpeed(double speed) {
		if (speed < -1.0) {
			speed = -1.0;
		}
		else if (speed > 1.0) {
			speed = 1.0;
		}
		
		// Raw value is 0-255
		int rawValue = (int)(((speed + 1.0) / 2.0) * 255);
		setRaw(rawValue);
	}
	
	public double getSpeed() {
		int value = getRaw();
		
		double speed = (((double)(value / 255.0)) * 2.0) - 1.0;
		return speed;
	}
	
	/**
	 * Set the PWM value directly to the hardware
	 * @param value Raw PWM value. Range 0 - 255
	 */
	public void setRaw(int value) {
		d_rawValInt = value;
		// TODO Publish This
	}
	
	public int getRaw() {
		return d_rawValInt;
	}
	
	public void free() {
		
	}
}
