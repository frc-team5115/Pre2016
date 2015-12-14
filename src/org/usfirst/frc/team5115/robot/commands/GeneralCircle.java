package org.usfirst.frc.team5115.robot.commands;

import org.usfirst.frc.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GeneralCircle extends Command {
	
	double rtime;	//rotation time
	double radius;	//nose-in radius
	double rspeed;	//rotation speed
	double tspeed;	//translationspeed
	double kx;
	double kr;
	float startAngle;	//the angle that the robot is pointing at at the start
	boolean canStop = false;	//can the thing stop rotating or not?

    public GeneralCircle() {}

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!Robot.drivetrain.inuse) {
	    	Robot.drivetrain.inuse = true;	//tells everything not to accept controller inputs for the moment being
	    	
	    	
	    	rtime = Robot.prefs.getDouble("RotationTime", 10);	//so we can adjust rtime from smart dashboard
	    	radius = Robot.prefs.getDouble("Radius", 3);		//so we can adjust radius from smart dashboard
	    	kx = Robot.prefs.getDouble("kx", 1);
	    	kr = Robot.prefs.getDouble("kr", 1);
	    	//rspeed = Robot.prefs.getDouble("RotationSpeed", 0.4);

	    	startAngle = Robot.drivetrain.imu.getYaw();		//records start angle
	    	Robot.drivetrain.drive(-(2 * radius * Math.PI / rtime) * kx, 0, (2 * Math.PI / rtime) * kr);	//sets the motors to begin moving therobot across its path 
	    	Timer.delay(2);
	    	canStop = true;										//allows the robot to stop turning
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("StartAngle", startAngle);	//angle at start
    	SmartDashboard.putNumber("CurrentAngle", Robot.drivetrain.imu.getYaw()); //the angle that the robot is driving at
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(startAngle + 180 - Robot.drivetrain.imu.getYaw()) < 5 && canStop;		//should stop once the robot is within 1 degree of
    }																					//the target and the robot is allowed to stop, but it doesn't

    // Called once after isFinished returns true
    protected void end() {
    	//Timer.delay(5);
    	Robot.drivetrain.drive(0, 0, 0);	//sets motors to 0
    	
    	Robot.drivetrain.inuse = false;		//returns control to StickDrive
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
