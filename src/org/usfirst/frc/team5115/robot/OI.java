package org.usfirst.frc.team5115.robot;

import org.usfirst.frc.team5115.robot.commands.AutoCircle;
import org.usfirst.frc.team5115.robot.commands.TestDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
    Joystick joy;
    Button tmt;		// testMotorToggler
    Button nosein;
    
    public OI() {
    	joy = new Joystick(0);
    	tmt = new JoystickButton(joy, 1);
    	nosein = new JoystickButton(joy, 2);
    	
    	tmt.whenPressed(new TestDrive());
    	nosein.whenPressed(new AutoCircle());
    }
    
    public double getX() {
    	double x = joy.getX();
    	return Math.pow(x, 2) * Math.signum(x);
    }
    
    public double getY() {
    	double y = joy.getY();
    	return Math.pow(y, 2) * Math.signum(y);
    }
    
    public double getRot() {
    	double rot = joy.getZ();
    	return Math.pow(rot, 2) * Math.signum(rot);
    }
}

