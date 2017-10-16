package edu.wpi.first.wpilibj;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import com.zhiquanyeo.nomad.network.NomadConnection;
import com.zhiquanyeo.nomad.network.NomadConnection.ControlState;

public class DriverStation implements RobotState.Interface {
	
	public static final int kJoystickPorts = 2;
	
	private class HALJoystickButtons {
		public int d_buttons;
		public byte d_count;
	}
	
	private class HALJoystickAxes {
		public float[] d_axes;
		public short d_count;
		
		public HALJoystickAxes(int count) {
			d_axes = new float[count];
		}
	}
	
	private static class DriverStationTask implements Runnable {
		private DriverStation d_ds;
		
		DriverStationTask(DriverStation ds) {
			d_ds = ds;
		}
		
		public void run() {
			System.out.println("DriverStationTask requested to run()");
			d_ds.run();
		}
	} /* DriverStationTask */
	
	// Socket to listen out for joystick message from
	private DatagramSocket d_socket;
	
	// Internal Driver Station thread
	private Thread d_thread;
	private volatile boolean d_threadKeepAlive = true;
	
	private static DriverStation instance = new DriverStation();
	
	// User Data
	private HALJoystickAxes[] d_joystickAxes = new HALJoystickAxes[kJoystickPorts];
	private HALJoystickButtons[] d_joystickButtons = new HALJoystickButtons[kJoystickPorts];
	
	// Cached Data
	private HALJoystickAxes[] d_joystickAxesCache = new HALJoystickAxes[kJoystickPorts];
	private HALJoystickButtons[] d_joystickButtonsCache = new HALJoystickButtons[kJoystickPorts];
	
	private final Object d_joystickMutex;
	
	public static DriverStation getInstance() {
		return DriverStation.instance;
	}
	
	protected DriverStation() {
		d_joystickMutex = new Object();
		for (int i = 0; i < kJoystickPorts; i++) {
			d_joystickButtons[i] = new HALJoystickButtons();
			d_joystickAxes[i] = new HALJoystickAxes(4);
			
			d_joystickButtonsCache[i] = new HALJoystickButtons();
			d_joystickAxesCache[i] = new HALJoystickAxes(4);
		}
		
		System.out.println("Starting DriverStationTask thread");
		d_thread = new Thread(new DriverStationTask(this), "DriverStation");
		d_thread.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
		
		d_thread.start();
	}
	
	// Kill the thread
	public void release() {
		d_threadKeepAlive = false;
	}
	public double getStickAxis(int stick, int axis) {
		if (stick < 0 || stick >= kJoystickPorts) {
			throw new RuntimeException("Joystick index is out of range");
		}
		if (axis < 0 || axis >= 4) {
			throw new RuntimeException("Joystick axis is out of range");
		}
		
		boolean error = false;
		double retVal = 0.0;
		synchronized (d_joystickMutex) {
			if (axis >= d_joystickAxes[stick].d_count) {
				error = true;
				retVal = 0.0;
			}
			else {
				retVal = d_joystickAxes[stick].d_axes[axis];
			}
		}
		if (error) {
			System.err.println("Joystick Axis " + axis + " on port " + stick + " not available");
		}
		return retVal;
	}
	
	public int getStickButtons(int stick) {
		if (stick < 0 || stick >= kJoystickPorts) {
			throw new RuntimeException("Joystick index is out of range");
		}
		synchronized(d_joystickMutex) {
			return d_joystickButtons[stick].d_buttons;
		}
	}
	
	public boolean getStickButton(int stick, byte button) {
		if (button <= 0) {
			System.err.println("Button indices begin at 1");
			return false;
		}
		if (stick < 0 || stick >= kJoystickPorts) {
			throw new RuntimeException("Joystick index is out of range");
		}
		
		boolean error = false;
		boolean retVal = false;
		synchronized(d_joystickMutex) {
			if (button > d_joystickButtons[stick].d_count) {
				error = true;
				retVal = false;
			}
			else {
				retVal = ((0x1 << (button - 1)) & d_joystickButtons[stick].d_buttons) != 0;
			}
		}
		if (error) {
			System.err.println("Joystick Button " + button + " on port " + stick + " not available");
		}
		return retVal;
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
                	System.err.println("Packet too short");
                    continue;
                }
                byte[] packetBuf = packet.getData();
                                
                //double val = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getDouble();
                if (!((packetBuf[0] & 0xFF) == 0xDE && (packetBuf[1] & 0xFF) == 0xAD)) {
                	System.err.println("Signature mismatch");
                    continue;
                }

                int numAxes = packetBuf[2];
                //verify packet length
                if (packet.getLength() != 7 + (numAxes * 4)) {
                	System.err.println("Packet length mismatch. Expected " + (7 + numAxes * 4));
                    continue;
                }
                
                d_joystickAxesCache[0].d_count = (short)numAxes;
                
                // Get the axis data
                for (int i = 0; i < numAxes; i++) {
                    int offset = 3 + (i * 4);
                    byte[] axisData = Arrays.copyOfRange(packetBuf, offset, offset + 4);
                    float axisVal = ByteBuffer.wrap(axisData).order(ByteOrder.BIG_ENDIAN).getFloat();
                    
                    d_joystickAxesCache[0].d_axes[i] = axisVal;
                    
                }
                
                // Button data = read the last 3 bytes
                int buttonCountIdx = packet.getLength() - 4;
                int buttonCount = packetBuf[buttonCountIdx];
                int buttonData = packetBuf[packet.getLength() - 1] |
                				 (packetBuf[packet.getLength() - 2] << 8) |
                				 (packetBuf[packet.getLength() - 3] << 16);
                
                d_joystickButtonsCache[0].d_count = (byte)buttonCount;
                d_joystickButtonsCache[0].d_buttons = buttonData;
                
                // Lock joystick mutex to swap cache data
                synchronized(d_joystickMutex) {
                	HALJoystickAxes[] currentAxes = d_joystickAxes;
                	d_joystickAxes = d_joystickAxesCache;
                	d_joystickAxesCache = currentAxes;
                	
                	HALJoystickButtons[] currentButtons = d_joystickButtons;
                	d_joystickButtons = d_joystickButtonsCache;
                	d_joystickButtonsCache = currentButtons;
                }
                
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
