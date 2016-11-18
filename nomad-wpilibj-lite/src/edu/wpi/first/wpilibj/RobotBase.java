package edu.wpi.first.wpilibj;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public abstract class RobotBase {
	
	protected RobotBase() {
		// Start up communications
	}
	
	public void free() {}
	
	public static boolean isSimulation() {
		return false;
	}
	
	public static boolean isReal() {
		return true;
	}
	
	public boolean isDisabled() {
		return false; // TODO - Implement
	}
	
	public boolean isEnabled() {
		return true; // TODO - Implement
	}
	
	public boolean isAutonomous() {
		return true;
	}
	
	public boolean isTest() {
		return false;
	}
	
	public boolean isOperatorControl() {
		return false;
	}
	
	public boolean isNewDataAvailable() {
		return true;
	}
	
	public abstract void startCompetition() throws InterruptedException;
	
	public static void initializeHardwareConfiguration() {
		// Set up "hardware"
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
			if (!incomingMode.equals("nomad") && !incomingMode.equals("direct")) {
				System.err.println("Invalid mode provided. Should be 'nomad' or 'direct'");
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
		
		// TBD - Initialize the global systems
		
		RobotBase robot;
		
	}
}
