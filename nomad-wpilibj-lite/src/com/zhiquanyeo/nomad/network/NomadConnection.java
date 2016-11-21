package com.zhiquanyeo.nomad.network;

import java.util.HashMap;
import java.util.Map;

public class NomadConnection implements INomadProtocolListener {
	
	public static enum ControlState {
		Disabled,
		Autonomous,
		Teleop,
		Test
	}
	
	public static void setProtocol(NomadProtocol proto) {
		s_instance.setProtocolImpl(proto);
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
	
	public static ControlState getState() {
		return s_instance.getStateImpl();
	}
	
	private static NomadConnection s_instance = new NomadConnection();
	
	private final Map<String, INomadMessageSubscriber> d_subscriptions = new HashMap<>();
	private NomadProtocol d_protocol;
	private Thread d_protoThread;
	private ControlState d_state = ControlState.Disabled;
	
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
		
		// Start up the protocol
		d_protoThread = new Thread(d_protocol);
		d_protoThread.start();
	}
	
	protected boolean hasProtocol() {
		return d_protocol != null;
	}
	
	protected boolean subscribe(String topic, INomadMessageSubscriber subscriber) {
		if (d_subscriptions.containsKey(topic)) {
			System.err.println("Already have a subscription for " + topic);
			return false;
		}
		
		d_subscriptions.put(topic, subscriber);
		return true;
	}
	
	protected void unsubscribe(String topic) {
		d_subscriptions.remove(topic);
	}
	
	protected void publishDigitalImpl(int channel, boolean value) {
		if (d_protocol == null) {
			System.err.println("No Protocol set. Can't publish");
			return;
		}
		
		d_protocol.setDigitalOutput(channel, value);
	}
	
	protected void publishAnalogImpl(int channel, double value) {
		if (d_protocol == null) {
			System.err.println("No Protocol set. Can't publish");
			return;
		}
		
		d_protocol.setAnalogOutput(channel, value);
	}
	
	protected void publishPWMImpl(int channel, double value) {
		if (d_protocol == null) {
			System.err.println("No Protocol set. Can't publish");
			return;
		}
		
		d_protocol.setPWMOutput(channel, value);
	}
	
	protected boolean publish(String topic, byte[] payload) {
		// TODO Send a publish message on the protocol
		return true;
	}
	
	protected ControlState getStateImpl() {
		return d_state;
	}

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionLost(String cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndpointDigitalInputChanged(int channel, boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndpointAnalogInputChanged(int channel, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndpointStatusMessage(String statusType, String message) {
		// TODO Auto-generated method stub
		
	}
	
	
}
