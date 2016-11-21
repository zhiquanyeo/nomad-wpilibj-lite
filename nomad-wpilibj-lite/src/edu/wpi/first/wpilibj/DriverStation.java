package edu.wpi.first.wpilibj;

import com.zhiquanyeo.nomad.network.NomadConnection;
import com.zhiquanyeo.nomad.network.NomadConnection.ControlState;

public class DriverStation implements RobotState.Interface {
	
	public static final int kJoystickPorts = 6;
	public static final int kJoystickAxes = 6;
	public static final double kDSAnalogInScaling = 5.0 / 1023.0;
	
	private static DriverStation instance = new DriverStation();
	
	public static DriverStation getInstance() {
		return DriverStation.instance;
	}
	
	protected DriverStation() {
		
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
