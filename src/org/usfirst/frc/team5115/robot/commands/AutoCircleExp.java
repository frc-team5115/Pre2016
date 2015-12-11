package org.usfirst.frc.team5115.robot.commands;

import org.usfirst.frc.team5115.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *None of this actually works
 */
public class AutoCircleExp extends Command {
	
	double startYaw;	//direction the chassis is originally facing
	double rot;			// how fast it should rotate
	double offX;		
	double offY;

    public AutoCircleExp(double offsetX, double offsetY, double rotationSpeed) {
    	rot = rotationSpeed; //sets local variables to parameters
    	offX = offsetX;
    	offY = offsetY;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startYaw = Robot.drivetrain.imu.getYaw();  //set startYaw
    	/** time to go in a circle(t): pi*diameter/speed  translation speed: offset/t
    	at 80% power, 10 ft/s   120 in/s  0.785 seconds for a circle  chassis has a diameter of about 33 in.
    	at 100% power, 12.5 ft/s  150 in/s 0.63 seconds for a circle
    	to offset 12 in. speed = 19.0 in/s  or 12.7% power 
    	None of that was actuallly correct**/
    	Robot.drivetrain.drive(rot*offY/(Math.PI * 33), rot*offX/(Math.PI * 33), rot);  //begin rotating
    	System.out.println("Started");	//Troubleshooting
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;	//stop immidietely
    }

    // Called once after isFinished returns true
    protected void end() {
    	Timer.delay(Robot.prefs.getDouble("TurnTime", 0.63/rot));	//allow full rotation to occur
    	Robot.drivetrain.drive(0, 0, 0);		//stop driving
    	System.out.println("Over");				//Troubleshooting
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Interrupted");		//Troubleshootin
    }
}
