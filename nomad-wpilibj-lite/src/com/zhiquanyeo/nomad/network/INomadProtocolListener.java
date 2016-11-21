package com.zhiquanyeo.nomad.network;

public interface INomadProtocolListener {
	void onConnected();
	void onDisconnected();
	void onConnectionLost(String cause);
	
	void onEndpointDigitalInputChanged(int channel, boolean value);
	void onEndpointAnalogInputChanged(int channel, double value);
	void onEndpointStatusMessage(String statusType, String message);
}
