package edu.wpi.first.wpilibj;

public abstract class SensorBase {
	
	public static final int kDigitalChannels = 26;
	public static final int kAnalogInputChannels = 8;
	public static final int kAnalogOutputChannels = 2;
	public static final int kPwmChannels = 20;
	
	public SensorBase() {
		
	}
	
	protected static void checkDigitalChannel(final int channel) {
		if (channel < 0 || channel >= kDigitalChannels) {
			throw new IndexOutOfBoundsException("Requested digital channel number is out of range");
		}
	}
	
	protected static void checkPWMChannel(final int channel) {
		if (channel < 0 || channel >= kPwmChannels) {
			throw new IndexOutOfBoundsException("Requested PWM channel number is out of range");
		}
	}
	
	protected static void checkAnalogInputChannel(final int channel) {
		if (channel < 0 || channel >= kAnalogInputChannels) {
			throw new IndexOutOfBoundsException("Requested analog input channel number is out of range");
		}
	}
	
	protected static void checkAnalogOutputChannel(final int channel) {
		if (channel < 0 || channel >= kAnalogOutputChannels) {
			throw new IndexOutOfBoundsException("Requested analog output channel number is out of range");
		}
	}
}
