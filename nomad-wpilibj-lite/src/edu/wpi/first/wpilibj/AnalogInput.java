package edu.wpi.first.wpilibj;

import com.zhiquanyeo.nomad.network.INomadMessageSubscriber;
import com.zhiquanyeo.nomad.network.NomadConnection;

import edu.wpi.first.wpilibj.util.AllocationException;
import edu.wpi.first.wpilibj.util.CheckedAllocationException;

public class AnalogInput extends SensorBase implements INomadMessageSubscriber {
	
	private static Resource channels = new Resource(kAnalogInputChannels);
	private int d_channel;
	private double d_voltage = 0.0;
	
	public AnalogInput(final int channel) {
		d_channel = channel;
		checkAnalogInputChannel(channel);
		
		try {
			channels.allocate(d_channel);
		}
		catch (CheckedAllocationException e) {
			throw new AllocationException("Analog input channel " + d_channel + " is already allocated");
		}
		
		NomadConnection.subscribeAnalogInput(d_channel, this);
	}
	
	public void free() {
		channels.free(d_channel);
		NomadConnection.unsubscribeAnalogInput(d_channel);
		d_channel = -1;
	}
	
	protected int voltageToValue(double voltage) {
		//12 bit, 0 - 5V
		return (int)((voltage / 5.0) * 4095);
	}
	
	public int getValue() {
		return voltageToValue(d_voltage);
	}
	
	public int getAverageValue() {
		return getValue();
	}
	
	public double getVoltage() {
		return d_voltage;
	}
	
	public double getAverageVoltage() {
		return getVoltage();
	}
	
	public int getChannel() {
		return d_channel;
	}
	
	@Override
	public void onMessageReceived(String message) {
		double temp = Double.parseDouble(message);
		if (!Double.isNaN(temp)) {
			d_voltage = temp;
		}
	}

}
