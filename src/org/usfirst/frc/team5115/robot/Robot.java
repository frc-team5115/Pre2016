
package org.usfirst.frc.team5115.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5115.robot.commands.AutoCircle;
import org.usfirst.frc.team5115.robot.commands.StickDrive;
import org.usfirst.frc.team5115.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5115.robot.subsystems.TalonTest;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Preferences prefs;
	public static OI oi;
	public static DriveTrain drivetrain;
	public static TalonTest talontest;
	public StickDrive sd;
	public AutoCircle ac;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	prefs = Preferences.getInstance();
		oi = new OI();
		drivetrain = new DriveTrain();
		talontest = new TalonTest();
		sd = new StickDrive();
		ac = new AutoCircle();
		
		SmartDashboard.putData("Turn Command", ac);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {}

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	sd.start();
    	drivetrain.imuStart();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){}

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	drivetrain.imuToDashboard();
        Scheduler.getInstance().run();
        
        Timer.delay(0.02);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
