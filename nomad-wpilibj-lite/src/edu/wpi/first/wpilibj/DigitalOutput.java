package edu.wpi.first.wpilibj;

import com.zhiquanyeo.nomad.network.NomadConnection;

public class DigitalOutput extends DigitalSource {
	public DigitalOutput(int channel) {
		initDigitalChannel(channel, false);
	}
	
	public void free() {
		super.free();
	}
	
	public void set(boolean value) {
		NomadConnection.publishDigital(d_channel, value);
	}
	
	public void pulse(final int channel, final float pulseLength) {
		// TODO Implement
	}
	
	public boolean isPulsing() {
		return false;
	}
	
	public int getChannel() {
		return d_channel;
	}
}
