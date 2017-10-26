package com.zhiquanyeo.nomad.network;

import java.net.Socket;

public class DebugProtocol extends NomadProtocol {
	private boolean d_isActive;
	
	public DebugProtocol() {
		super("Debug", "1.0");
		d_isActive = true;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void setDigitalOutput(int channel, boolean value) {
		System.out.println("Setting Digital #" + channel + ": " + value);
	}

	@Override
	public void setAnalogOutput(int channel, double value) {
		System.out.println("Setting Analog #" + channel + ": " + value);
	}
	
	@Override
	public void setPWMOutput(int channel, double value) {
		System.out.println("Setting PWM #" + channel + ": " + value);
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
		d_isActive = false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		System.out.println("Running debug protocol");
		while (d_isActive) {
			// DO STUFF
		}
		System.out.println("DONE");
	}

	@Override
	public void configureDigitalPin(int channel, NomadPinType pinType) {
		// TODO Auto-generated method stub
		
	}
	
}
