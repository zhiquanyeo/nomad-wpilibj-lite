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
		s_instance.subscribe("D-" + channel, subscriber);
	    return true;
	}
	
	public static void unsubscribeDigitalInput(int channel) {
		
	}
	
	public static boolean subscribeAnalogInput(int channel, INomadMessageSubscriber subscriber) {
	    s_instance.subscribe("A-" + channel, subscriber);
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
			d_protocol.removeSubscriber(this);
			d_protocol.shutdown();
		}
		
		d_protocol = proto;
		d_protocol.addSubscriber(this);
		
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
		
		// Convert 0 - 255 to -255 to 255
		// shift left by 127
		int inMin = 0, inMax = 255;
		int outMin = -255, outMax = 255;
		
		int outVal = ((int)value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin; 
		
		d_protocol.setPWMOutput(channel, outVal);
	}
	
	protected boolean publish(String topic, byte[] payload) {
		// TODO Send a publish message on the protocol
		return true;
	}
	
	protected synchronized ControlState getStateImpl() {
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
		if (d_subscriptions.containsKey("D-" + channel)) {
		    d_subscriptions.get("D-" + channel).onMessageReceived(value ? "1" : "0");
		}
	}

	@Override
	public void onEndpointAnalogInputChanged(int channel, double value) {
	    if (d_subscriptions.containsKey("A-" + channel)) {
            d_subscriptions.get("A-" + channel).onMessageReceived(Double.toString(value));
        }
	}

	@Override
	public void onEndpointStatusMessage(String statusType, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void onModeChanged(String mode) {
		switch (mode) {
			case "disabled":
				d_state = ControlState.Disabled;
				break;
				
			case "auto":
				d_state = ControlState.Autonomous;
				break;
				
			case "teleop":
				d_state = ControlState.Teleop;
				break;
				
			case "test":
				d_state = ControlState.Test;
				break;
				
			default:
				System.err.println("Invalid Mode Detected. Disabling");
				d_state = ControlState.Disabled;
		}
	}
	
	
}
