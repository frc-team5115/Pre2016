package org.usfirst.frc.team5115.robot.commands;

import org.usfirst.frc.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoCircle extends Command {
	
	double startYaw;

    public AutoCircle() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startYaw = Robot.drivetrain.imu.getYaw();
    	// time to go in a circle(t): 2pi*radius/speed  translation speed: offset/t
    	// at 80% power, 10 ft/s   120 in/s  0.785 seconds for a circle
    	// to offset 12 in. speed = 15.3 in/s  or 10% power
    	Robot.drivetrain.drive(Robot.prefs.getDouble("TranslateSpeed", -0.3), 0, Robot.prefs.getDouble("TurnSpeed", 0.3));
    	System.out.println("Started");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.drivetrain.imu.getYaw()-startYaw)<5);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Timer.delay(Robot.prefs.getDouble("TurnTime", 5));
    	Robot.drivetrain.drive(0, 0, 0);
    	System.out.println("Over");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Interrupted");
    }
}
