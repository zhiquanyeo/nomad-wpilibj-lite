package com.zhiquanyeo.nomad.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class DirectConnectProtocol extends NomadProtocol {
	private String d_host;
	private int d_port;
	private Socket d_socket;
	private boolean d_isActive;
	
	private Map<Integer, Boolean> d_digitalInputValues = new HashMap<>();
	private Map<Integer, Double> d_analogInputValues = new HashMap<>();
	
	private BufferedReader d_in;
	private PrintWriter d_out;
	
	public DirectConnectProtocol(String host) {
		super("DirectConnect", "1.0");
		try {
			URI tempUrl = new URI(host);
			d_host = tempUrl.getHost();
			d_port = tempUrl.getPort();
			if (d_port < 0) {
				System.err.println("No port provided, reverting to default");
				d_port = 1234;
			}
		}
		catch (URISyntaxException e) {
			System.err.println("Malformed URL. Reverting to defaults: " + e.getMessage());
			d_host = "localhost";
			d_port = 1234;
		}
		
		System.out.println("Host: " + d_host);
		System.out.println("Port: " + d_port);
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		
		return false;
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
	public void setPWMOutput(int channel, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDigitalInput(int channel) {
		if (d_digitalInputValues.containsKey(channel)) {
			return d_digitalInputValues.get(channel);
		}
		else {
			System.err.println("Unknown Digital Input Channel " + channel);
			return false;
		}
	}

	@Override
	public double getAnalogInput(int channel) {
		if (d_analogInputValues.containsKey(channel)) {
			return d_analogInputValues.get(channel);
		}
		else {
			System.err.println("Unknown Analog Input Channel " + channel);
			return 0.0;
		}
	}

	@Override
	public void shutdown() {
		if (!d_socket.isClosed()) {
			try {
				d_socket.close();
			}
			catch (IOException e) {
				System.err.println("Error closing socket: " + e.getMessage());
			}
		}
		d_isActive = false;
	}

	@Override
	public void reset() {
		// Reset the state of the protocol object but keep everything running
	}
	
	private void processLine(String line) {
		// This is where the protocol meat comes in
	}
	
	@Override
	public void run() {
		try {
			d_socket = new Socket(d_host, d_port);
			d_in = new BufferedReader(
					new InputStreamReader(d_socket.getInputStream()));
			d_out = new PrintWriter(d_socket.getOutputStream(), true);
		}
		catch (IOException e) {
			System.err.println("Could not connect to host: " + d_host);
			return;
		}
				
		while (d_isActive) {
			try {
				String fromServer = d_in.readLine();
				processLine(fromServer);
			}
			catch (IOException e) {
				System.err.println("IOException from server");
				d_isActive = false;
			}
		}
		
		// Shutdown everything
		try {
			d_in.close();
		}
		catch (IOException e) {
			System.err.println("Error while shutting down protocol");
		}
	}
}
