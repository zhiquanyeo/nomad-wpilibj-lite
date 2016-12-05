package edu.wpi.first.wpilibj;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.zhiquanyeo.nomad.network.DebugProtocol;
import com.zhiquanyeo.nomad.network.DirectConnectProtocol;
import com.zhiquanyeo.nomad.network.NomadConnection;
import com.zhiquanyeo.nomad.network.NomadProtocol;

public abstract class RobotBase {
	
	protected final DriverStation d_ds;
	
	protected RobotBase() {
		// Start up communications
		d_ds = DriverStation.getInstance();
	}
	
	public void free() {}
	
	public static boolean isSimulation() {
		return false;
	}
	
	public static boolean isReal() {
		return true;
	}
	
	public boolean isDisabled() {
		return d_ds.isDisabled();
	}
	
	public boolean isEnabled() {
		return d_ds.isEnabled();
	}
	
	public boolean isAutonomous() {
		return d_ds.isAutonomous();
	}
	
	public boolean isTest() {
		return d_ds.isTest();
	}
	
	public boolean isOperatorControl() {
		return d_ds.isOperatorControl();
	}
	
	public boolean isNewDataAvailable() {
		return true;
	}
	
	public abstract void startCompetition() throws InterruptedException;
	
}
