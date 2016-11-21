package com.zhiquanyeo.nomad.network;

public abstract class NomadProtocol implements Runnable {
	protected String d_protocolName;
	protected String d_protocolVersion;
	
	public String getName() {
		return d_protocolName;
	}
	
	public String getVersion() {
		return d_protocolVersion;
	}
	
	protected NomadProtocol(String name, String version) {
		d_protocolName = name;
		d_protocolVersion = version;
	}
	
	public abstract boolean isActive();
	
	public abstract void setDigitalOutput(int channel, boolean value);
	public abstract void setAnalogOutput(int channel, double value);
	public abstract void setPWMOutput(int channel, double value);
	public abstract boolean getDigitalInput(int channel);
	public abstract double getAnalogInput(int channel);
	
	public abstract void shutdown();
	public abstract void reset();
}
