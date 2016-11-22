package com.zhiquanyeo.nomad.network;

public interface INomadMessageSubscriber {
	void onMessageReceived(String message);
}
