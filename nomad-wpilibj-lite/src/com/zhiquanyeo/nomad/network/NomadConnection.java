package com.zhiquanyeo.nomad.network;

public class NomadConnection {
	private NomadProtocol d_protocol;
	
	public static void setProtocol(NomadProtocol proto) {
		
	}
	
	public static boolean subscribeDigitalInput(int channel, INomadMessageSubscriber subscriber) {
		return true;
	}
	
	public static void unsubscribeDigitalInput(int channel) {
		
	}
	
	public static boolean subscribeAnalogInput(int channel, INomadMessageSubscriber subscriber) {
		return true;
	}
	
	public static void unsubscribeAnalogInput(int channel) {
		
	}
	
	public static void publishDigital(int channel, boolean value) {
		s_instance.publishDigitalImpl(channel, value);
	}
	
	public static void publishAnalog(int channel, double value) {
		s_instance.publishAnalogImpl(channel, value);
	}
	
	public static void publishPWM(int channel, double value) {
		s_instance.publishPWMImpl(channel, value);
	}
	
	public static void publishDisable() {
		
	}
	
	private static NomadConnection s_instance = new NomadConnection();
	
	protected NomadConnection() {}
	
	protected synchronized void setProtocolImpl(NomadProtocol proto) {
		if (proto == null) {
			throw new NullPointerException("Cannot assign null protocol");
		}
		
		if (d_protocol != null) {
			// TODO Remove this as a subscriber
			d_protocol.shutdown();
		}
		
		d_protocol = proto;
		// TODO Add subscription to the protocol
	}
	
	protected boolean hasProtocol() {
		return d_protocol != null;
	}
	
	protected boolean subscribe(String topic, INomadMessageSubscriber subscriber) {
		
		return true;
	}
	
	protected void unsubscribe(String topic) {
		
	}
	
	protected void publishDigitalImpl(int channel, boolean value) {
		
	}
	
	protected void publishAnalogImpl(int channel, double value) {
		
	}
	
	protected void publishPWMImpl(int channel, double value) {
		
	}
	
	protected boolean publish(String topic, byte[] payload) {
		// TODO Send a publish message on the protocol
		return true;
	}
}
