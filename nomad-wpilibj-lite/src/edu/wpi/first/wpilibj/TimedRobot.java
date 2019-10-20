package edu.wpi.first.wpilibj;

public class TimedRobot extends IterativeRobotBase {
    public static final double kDefaultPeriod = 0.02;

    private double m_expirationTime;

    protected TimedRobot() {
        this(kDefaultPeriod);
    }

    protected TimedRobot(double period) {
        super(period);
    }

    protected void finalize() {
        // TODO
    }

    public void startCompetition() {
        robotInit();

        m_expirationTime = Timer.getFPGATimestamp() * 1e-6 + m_period;
        updateAlarm();

        while (true) {
            // Check for time expiration
            // TODO Implement NotifierJNI.waitForNotifierAlarm
            // This is terrible btw...
            Timer.delay(m_period);

            m_expirationTime += m_period;
            updateAlarm();

            loopFunc();
        }
    }

    public double getPeriod() {
        return m_period;
    }

    private void updateAlarm() {
        // TODO Implement NotifierJNI.updateNotifierAlarm()
    }
}