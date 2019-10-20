package edu.wpi.first.wpilibj;

public abstract class IterativeRobotBase extends RobotBase {
    protected double m_period;

    private enum Mode {
        kNone,
        kDisabled,
        kAutonomous,
        kTeleop,
        kTest
    };

    private Mode m_lastMode = Mode.kNone;

    protected IterativeRobotBase(double period) {
        m_period = period;
        // TODO watchdog?
    }

    @Override
    public abstract void startCompetition();

    public void robotInit() {
        System.out.println("Default robotInit() method... Override me!");
    }

    public void disabledInit() {
        System.out.println("Default disabledInit() method... Override me!");
    }

    public void autonomousInit() {
        System.out.println("Default autonomousInit() method... Override me!");
    }

    public void teleopInit() {
        System.out.println("Default teleopInit() method... Override me!");
    }

    public void testInit() {
        System.out.println("Default testInit() method... Override me!");
    }

    // ---- Periodic code ----
    private boolean m_rpFirstRun = true;

    public void robotPeriodic() {
        if (m_rpFirstRun) {
            System.out.println("Default robotPeriodic() method... Override me!");
            m_rpFirstRun = false;
        }
    }

    private boolean m_dpFirstRun = true;

    public void disabledPeriodic() {
        if (m_dpFirstRun) {
            System.out.println("Default disabledPeriodic() method... Override me!");
            m_dpFirstRun = false;
        }
    }

    private boolean m_apFirstRun = true;

    public void autonomousPeriodic() {
        if (m_apFirstRun) {
            System.out.println("Default autonomousPeriodic() method... Override me!");
            m_apFirstRun = false;
        }
    }

    private boolean m_tpFirstRun = true;

    public void teleopPeriodic() {
        if (m_tpFirstRun) {
            System.out.println("Default teleopPeriodic() method... Override me!");
            m_tpFirstRun = false;
        }
    }

    private boolean m_tmpFirstRun = true;

    public void testPeriodic() {
        if (m_tmpFirstRun) {
            System.out.println("Default testPeriodic() method... Override me!");
            m_tmpFirstRun = false;
        }
    }

    protected void loopFunc() {
        if (isDisabled()) {
            // Call disabled init if we are just now entering disabled mode
            if (m_lastMode != Mode.kDisabled) {
                disabledInit();
                m_lastMode = Mode.kDisabled;
            }

            disabledPeriodic();
        }
        else if (isAutonomous()) {
            if (m_lastMode != Mode.kAutonomous) {
                autonomousInit();
                m_lastMode = Mode.kAutonomous;
            }

            autonomousPeriodic();
        }
        else if (isOperatorControl()) {
            if (m_lastMode != Mode.kTeleop) {
                teleopInit();
                m_lastMode = Mode.kTeleop;
            }

            teleopPeriodic();
        }
        else {
            if (m_lastMode != Mode.kTest) {
                testInit();
                m_lastMode = Mode.kTest;
            }

            testPeriodic();
        }

        robotPeriodic();

    }
}