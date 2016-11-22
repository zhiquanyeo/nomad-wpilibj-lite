package edu.wpi.first.wpilibj;

import com.zhiquanyeo.nomad.network.INomadMessageSubscriber;
import com.zhiquanyeo.nomad.network.NomadConnection;

import edu.wpi.first.wpilibj.util.AllocationException;
import edu.wpi.first.wpilibj.util.CheckedAllocationException;

public class DigitalSource extends SensorBase implements INomadMessageSubscriber {
	protected static Resource channels = new Resource(kDigitalChannels);
	protected int d_channel;
	protected boolean d_sourceVal;
	protected boolean d_isInput;
	
	protected void initDigitalChannel(int channel, boolean input) {
		d_channel = channel;
		d_isInput = input;
		checkDigitalChannel(d_channel);
		
		try {
			channels.allocate(d_channel);
		}
		catch (CheckedAllocationException e) {
			throw new AllocationException("Digital input " + d_channel + " is already allocated");
		}
		
		if (d_isInput) {
			NomadConnection.subscribeDigitalInput(d_channel, this);
		}
	}
	
	public void free() {
		channels.free(d_channel);
		if (d_isInput) {
			NomadConnection.unsubscribeDigitalInput(d_channel);
		}
		
		d_channel = 0;
	}
	
	protected boolean getValue() {
		return d_sourceVal;
	}
	
	@Override
	public void onMessageReceived(String message) {
		if (message.equals("1") || message.equals("true")) {
			d_sourceVal = true;
		}
		else if (message.equals("0") || message.equals("false")) {
			d_sourceVal = false;
		}
		else {
			System.err.println("Invalid data for digital port " + d_channel);
		}
	}

}
