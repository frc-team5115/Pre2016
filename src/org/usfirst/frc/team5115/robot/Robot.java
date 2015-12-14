
package org.usfirst.frc.team5115.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5115.robot.commands.AutoCircle;
import org.usfirst.frc.team5115.robot.commands.FieldDrive;
import org.usfirst.frc.team5115.robot.commands.StickDrive;
import org.usfirst.frc.team5115.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5115.robot.subsystems.RangeFinder;
import org.usfirst.frc.team5115.robot.subsystems.TalonTest;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Preferences prefs;			//subsystems used
	public static OI oi;
	public static DriveTrain drivetrain;
	public static TalonTest talontest;
	public static RangeFinder rangefinder;
	
	public static StickDrive sd;		// commands
	public static FieldDrive fd;
	public static AutoCircle ac;
	
	public static boolean fieldOriented = false;	//whether it is driving in field oriented mode

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	prefs = Preferences.getInstance();
		oi = new OI();					//making objects
		drivetrain = new DriveTrain();
		talontest = new TalonTest();
		rangefinder = new RangeFinder();
		sd = new StickDrive();
		fd = new FieldDrive();
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
    public void autonomousPeriodic() {			//nothing here yet cos we don't have autonomous strategies
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	drivetrain.inuse = false;
    	sd.start();					//starts StickDrive
    	drivetrain.imuStart();		//starts imu
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
    	drivetrain.imuToDashboard();		//displays imu values on dashboard
    	rangefinder.getRange();				//displays rangefinder value on dashboard
        Scheduler.getInstance().run();		//runs scheduler
        
        Timer.delay(0.02);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
