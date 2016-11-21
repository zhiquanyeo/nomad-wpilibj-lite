package com.zhiquanyeo.nomad.network;

public class DebugProtocol extends NomadProtocol {

	public DebugProtocol() {
		super("Debug", "1.0");
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void setDigitalOutput(int channel, boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAnalogOutput(int channel, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDigitalInput(int channel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getAnalogInput(int channel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPWMOutput(int channel, double value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
