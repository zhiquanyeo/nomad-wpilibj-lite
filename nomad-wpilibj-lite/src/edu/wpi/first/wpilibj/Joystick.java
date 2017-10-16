package edu.wpi.first.wpilibj;

public class Joystick extends JoystickBase {
	
	static final byte kDefaultXAxis = 0;
	static final byte kDefaultYAxis = 1;
	static final byte kDefaultZAxis = 2;
	static final byte kDefaultTwistAxis = 2;
	static final byte kDefaultThrottleAxis = 3;
	static final byte kDefaultTriggerButton = 1;
	static final byte kDefaultTopButton = 2;
	
	public enum AxisType {
		kX(0), kY(1), kZ(2), kTwist(3), kThrottle(4), kNumAxis(5);
		
		public final int value;
		
		private AxisType(int value) {
			this.value = value;
		}
	}
	
	public enum ButtonType {
		kTrigger(0), kTop(1), kNumButton(2);
		
		public final int value;
		
		private ButtonType(int value) {
			this.value = value;
		}
	}
	
	private final DriverStation d_ds;
	private final byte[] d_axes;
	private final byte[] d_buttons;
	
	public Joystick(final int port) {
		this(port, AxisType.kNumAxis.value, ButtonType.kNumButton.value);
		
		d_axes[AxisType.kX.value] = kDefaultXAxis;
		d_axes[AxisType.kY.value] = kDefaultYAxis;
		d_axes[AxisType.kZ.value] = kDefaultZAxis;
		d_axes[AxisType.kTwist.value] = kDefaultTwistAxis;
		d_axes[AxisType.kThrottle.value] = kDefaultThrottleAxis;
		
		d_buttons[ButtonType.kTrigger.value] = kDefaultTriggerButton;
		d_buttons[ButtonType.kTop.value] = kDefaultTopButton;
	}
	
	protected Joystick(int port, int numAxisTypes, int numButtonTypes) {
		super(port);
		
		d_ds = DriverStation.getInstance();
		d_axes = new byte[numAxisTypes];
		d_buttons = new byte[numButtonTypes];
	}
	
	@Override
	public final double getX(Hand hand) {
		return getRawAxis(d_axes[AxisType.kX.value]);
	}
	
	@Override
	public final double getY(Hand hand) {
		return getRawAxis(d_axes[AxisType.kY.value]);
	}
	
	@Override
	public final double getZ(Hand hand) {
		return getRawAxis(d_axes[AxisType.kZ.value]);
	}
	
	@Override
	public final double getTwist() {
		return getRawAxis(d_axes[AxisType.kTwist.value]);
	}
	
	@Override
	public final double getThrottle() {
		return getRawAxis(d_axes[AxisType.kThrottle.value]);
	}
	
	public double getRawAxis(final int axis) {
		return d_ds.getStickAxis(getPort(), axis);
	}

	public double getAxis(final AxisType axis) {
		switch (axis) {
			case kX:
				return getX();
			case kY:
				return getY();
			case kZ:
				return getZ();
			case kTwist:
				return getTwist();
			case kThrottle:
				return getThrottle();
			default:
				return 0.0;
		}
	}
	
	public int getAxisCount() {
		return 4;
	}
	
	public boolean getTrigger(Hand hand) {
		return getRawButton(d_buttons[ButtonType.kTrigger.value]);
	}
	
	public boolean getTop(Hand hand) {
		return getRawButton(d_buttons[ButtonType.kTop.value]);
	}
	
	@Override
	public int getPOV(int pov) {
		return 0;
	}
	
	@Override
	public int getPOVCount() {
		return 0;
	}
	
	public boolean getBumper(Hand hand) {
		return false;
	}
	
	public boolean getRawButton(final int button) {
		return d_ds.getStickButton(getPort(), (byte)button);
	}
	
	public int getButtonCount() {
		return 16;
	}
	
	public boolean getButton(ButtonType button) {
		switch (button) {
			case kTrigger:
				return getTrigger();
			case kTop:
				return getTop();
			default:
				return false;
		}
	}
	
	public double getMagnitude() {
		return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
	}
	
	public double getDirectionRadians() {
		return Math.atan2(getX(), -getY());
	}
	
	public double getDirectionDegrees() {
		return Math.toDegrees(getDirectionRadians());
	}
	
	public String getName() {
		return "Joystick";
	}
}
