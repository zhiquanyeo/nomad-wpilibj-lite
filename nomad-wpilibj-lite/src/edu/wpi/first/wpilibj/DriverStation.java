package edu.wpi.first.wpilibj;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import com.zhiquanyeo.nomad.network.NomadConnection;
import com.zhiquanyeo.nomad.network.NomadConnection.ControlState;

public class DriverStation implements RobotState.Interface {
	
	public static final int kJoystickPorts = 6;
	public static final int kJoystickAxes = 6;
	public static final double kDSAnalogInScaling = 5.0 / 1023.0;
	
	private DatagramSocket d_socket;
	
	// Internal Driver Station thread
	private Thread d_thread;
	private volatile boolean d_threadKeepAlive = true;
	
	private static DriverStation instance = new DriverStation();
	
	public static DriverStation getInstance() {
		return DriverStation.instance;
	}
	
	protected DriverStation() {
		
	}
	
	private void run() {
	    try {
	        d_socket = new DatagramSocket(1120);
	    
	    }
	    catch(SocketException e) {
	        System.err.print("Cannot start driver station server");
	        return;
	    }
	    
	    byte[] buf = new byte[256];
	    DatagramPacket packet = new DatagramPacket(buf, buf.length);
	    
	    while (d_threadKeepAlive) {
	        try {
                d_socket.receive(packet);
                // packet format:
                // [ 0xDE ][ 0xAD ][axisCount][axis data]...[buttonCount][ 3 bytes of button]
                // ensure packet length is at least 11 bytes (1 axis)
                if (packet.getLength() < 11) {
                    continue;
                }
                byte[] packetBuf = packet.getData();
                //double val = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getDouble();
                if (!(packetBuf[0] == 0xDE && packetBuf[1] == 0xAD)) {
                    continue;
                }
                
                int numAxes = packetBuf[2];
                //verify packet length
                if (packet.getLength() != 7 + (numAxes * 4)) {
                    continue;
                }
                
                // Get the axis data
                for (int i = 0; i < numAxes; i++) {
                    int offset = 3 + (i * 4);
                    byte[] axisData = Arrays.copyOfRange(packetBuf, offset, offset + 4);
                    double axisVal = ByteBuffer.wrap(axisData).order(ByteOrder.BIG_ENDIAN).getDouble();
                    
                    // TODO Update the cache
                }
                
                // TODO do the buttons
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    }
	}
	
	@Override
	public boolean isDisabled() {
		return NomadConnection.getState() == ControlState.Disabled;
	}

	@Override
	public boolean isEnabled() {
		return NomadConnection.getState() != ControlState.Disabled;
	}

	@Override
	public boolean isOperatorControl() {
		return NomadConnection.getState() == ControlState.Teleop;
	}

	@Override
	public boolean isAutonomous() {
		return NomadConnection.getState() == ControlState.Autonomous;
	}

	@Override
	public boolean isTest() {
		return NomadConnection.getState() == ControlState.Test;
	}

}
