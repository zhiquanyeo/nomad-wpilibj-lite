package edu.wpi.first.wpilibj;

public class DigitalInput extends DigitalSource {
	
	public DigitalInput(int channel) {
		initDigitalChannel(channel, true);
	}
	
	public boolean get() {
		return getValue();
	}
	
	public int getChannel() {
		return d_channel;
	}
}
