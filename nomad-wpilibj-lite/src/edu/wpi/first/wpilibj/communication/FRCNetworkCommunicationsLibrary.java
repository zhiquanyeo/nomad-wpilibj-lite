package edu.wpi.first.wpilibj.communication;

import com.zhiquanyeo.nomad.network.NomadProtocol;

public class FRCNetworkCommunicationsLibrary {
	
	private static FRCNetworkCommunicationsLibrary s_instance = new FRCNetworkCommunicationsLibrary();
	
	private static NomadProtocol s_protocol;
	
	public static void setProtocol(NomadProtocol proto) {
		if (s_protocol != null) {
			s_protocol.reset();
		}
		
		s_protocol = proto;
		
		s_instance.resetNetwork();
	}
	
	
	public static FRCNetworkCommunicationsLibrary getInstance() {
		return s_instance;
	}
	
	protected void resetNetwork() {
		
	}
}
