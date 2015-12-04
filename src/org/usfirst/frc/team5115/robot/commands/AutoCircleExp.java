package org.usfirst.frc.team5115.robot.commands;

import org.usfirst.frc.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoCircleExp extends Command {
	
	double startYaw;
	double rot;
	double offX;
	double offY;

    public AutoCircleExp(double offsetX, double offsetY, double rotationSpeed) {
    	rot = rotationSpeed;
    	offX = offsetX;
    	offY = offsetY;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startYaw = Robot.drivetrain.imu.getYaw();
    	// time to go in a circle(t): pi*diameter/speed  translation speed: offset/t
    	// at 80% power, 10 ft/s   120 in/s  0.785 seconds for a circle  chassis has a diameter of about 33 in.
    	// at 100% power, 12.5 ft/s  150 in/s 0.63 seconds for a circle
    	// to offset 12 in. speed = 19.0 in/s  or 12.7% power
    	Robot.drivetrain.drive(rot*offY/(Math.PI * 33), rot*offX/(Math.PI * 33), rot);
    	System.out.println("Started");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.drivetrain.imu.getYaw()-startYaw)>355);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Timer.delay(Robot.prefs.getDouble("TurnTime", 0.63/rot));
    	Robot.drivetrain.drive(0, 0, 0);
    	System.out.println("Over");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Interrupted");
    }
}
