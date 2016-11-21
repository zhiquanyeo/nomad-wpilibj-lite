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
	
	public static void initializeHardwareConfiguration() {
		
	}
	
	// Application Starting point
	public static void main(String[] args) {
		Options options = new Options();
		
		// Add the command line options that we take
		// -m|--mode mode (nomad|direct) defaults to nomad
		options.addOption("m", "mode", true, "Communication Mode (nomad/direct)");
		
		// -h|--host Host Location
		options.addOption("h", "host", true, "Host Address and Port (e.g. localhost:1234");
	
		options.addOption("c", "clientid", true, "Client ID");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine line;
		try {
			line = parser.parse(options, args);
		}
		catch (ParseException e) {
			System.err.println("Unexpected Exception: " + e.getMessage());
			System.exit(1);
			return;
		}
		
		String operatingMode = "nomad";
		String host = "tcp://localhost:1234";
		String clientId = "randomnoise";
		
		if (line.hasOption("mode")) {
			String incomingMode = line.getOptionValue("mode");
			if (!incomingMode.equals("nomad") && !incomingMode.equals("direct") && !incomingMode.equals("debug")) {
				System.err.println("Invalid mode provided. Should be 'nomad' or 'direct' or 'debug'");
				System.exit(1);
				return;
			}
			operatingMode = incomingMode;
		}
		
		if (line.hasOption("host")) {
			host = line.getOptionValue("host");
		}
		
		if (line.hasOption("clientid")) {
			clientId = line.getOptionValue("clientid");
		}
		
		String[] otherArgs = line.getArgs();
		if (otherArgs.length < 1) {
			System.err.println("Robot Class name needs to be provided");
			System.exit(1);
			return;
		}
		
		String robotName = otherArgs[0];
		
		System.out.println("--- SUMMARY ---");
		System.out.println("Mode: " + operatingMode);
		System.out.println("Host: " + host);
		System.out.println("ClientID: " + clientId);
		System.out.println("Robot Class: " + otherArgs[0]);
		
		// Now we can start
		
		NomadProtocol protocol;
		if (operatingMode.equals("direct")) {
			protocol = new DirectConnectProtocol(host);
		}
		else {
			// Temporary for now, shuttle everything to debug
			protocol = new DebugProtocol();
		}
		NomadConnection.setProtocol(protocol);
		RobotState.SetImplementation(DriverStation.getInstance());
		
		RobotBase robot = new SampleRobot();
//		try {
//			robot = (RobotBase) Class.forName(robotName).newInstance();
//		}
//		catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
//			System.err.println("Unable to load robot");
//			System.err.println(e.getMessage());
//			return;
//		}
		
		boolean errorOnExit = false;
		try {
			robot.startCompetition();
		}
		catch (Throwable t) {
			t.printStackTrace();
			errorOnExit = true;
		}
		finally {
			System.err.println("WARNING: Robots don't quit");
			if (errorOnExit) {
				System.err.println("---> The startCompeition() method (or methods called by it) should have handled the exception above");
			}
			else {
				System.err.println("---> Unexpected return from startCompetition() method");
			}
		}
	}
}
