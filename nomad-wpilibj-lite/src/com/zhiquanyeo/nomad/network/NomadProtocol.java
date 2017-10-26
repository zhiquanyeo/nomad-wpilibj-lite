package com.zhiquanyeo.nomad.network;

import java.util.ArrayList;

public abstract class NomadProtocol implements Runnable {
	public static enum NomadPinType {
		OUTPUT,
		INPUT,
		INPUT_PULLUP
	};
	
	protected String d_protocolName;
	protected String d_protocolVersion;
	
	protected ArrayList<INomadProtocolListener> d_subscribers = new ArrayList<>();
	
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
	
	public abstract void configureDigitalPin(int channel, NomadPinType pinType);
	
	public synchronized void addSubscriber(INomadProtocolListener subscriber) {
		d_subscribers.add(subscriber);
	}
	
	public synchronized void removeSubscriber(INomadProtocolListener subscriber) {
		while (d_subscribers.contains(subscriber)) {
			d_subscribers.remove(subscriber);
		}
	}
}
