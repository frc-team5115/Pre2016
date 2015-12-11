package org.usfirst.frc.team5115.robot.commands;

import org.usfirst.frc.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoCircle extends Command {
	
	double startYaw;	// initial direction, not actually used

    public AutoCircle() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startYaw = Robot.drivetrain.imu.getYaw();		//sets startYaw
    	Robot.drivetrain.drive(Robot.prefs.getDouble("TranslateSpeed", -0.3), 0, Robot.prefs.getDouble("TurnSpeed", 0.3));	//begin driving with smartdashboard values
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // return (Math.abs(Robot.drivetrain.imu.getYaw()-startYaw)<5);			Something? Meant to stop once the yaw has returned to the initial yaw, but doesn't actually work
    	return true;		//immidietely run end
    }

    // Called once after isFinished returns true
    protected void end() {
    	Timer.delay(Robot.prefs.getDouble("TurnTime", 5));		//Allow full circle using smartdashboard values
    	Robot.drivetrain.drive(0, 0, 0);		//stops the chassis
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }
}
