package edu.wpi.first.wpilibj;

public class Timer {
	public static void delay(final double seconds) {
		try {
			Thread.sleep((long)(seconds * 1e3));
		}
		catch (final InterruptedException e) {}
	}
	
	public static double getFPGATimestamp() {
		return System.currentTimeMillis() / 1e3;
	}
}
