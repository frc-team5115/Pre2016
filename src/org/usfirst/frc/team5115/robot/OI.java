package org.usfirst.frc.team5115.robot;

import org.usfirst.frc.team5115.robot.commands.AutoCircle;
import org.usfirst.frc.team5115.robot.commands.AutoCircleExp;
import org.usfirst.frc.team5115.robot.commands.GeneralCircle;
import org.usfirst.frc.team5115.robot.commands.TestDrive;
import org.usfirst.frc.team5115.robot.commands.FindRange;
import org.usfirst.frc.team5115.robot.commands.ToggleDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
    Joystick joy;	// both joysticks
    Button tmt;		// testMotorToggler
    Button nosein;	// activates rotation about the nose
    Button toggleorientation;
    AnalogInput rangefind;
    
    public OI() {
    	//mapping
    	joy = new Joystick(0);
    	tmt = new JoystickButton(joy, 1);
    	nosein = new JoystickButton(joy, 2);
    	rangefind = new AnalogInput(0);
    	toggleorientation = new JoystickButton(joy, 10);
    	
    	tmt.whenPressed(new TestDrive());		//run TestDrive, which toggles TalonTest
    	nosein.whenPressed(new GeneralCircle());	//run GeneralCircle(), which rotates the chassis about its nose for a period of time
    	toggleorientation.whenPressed(new ToggleDrive());
    }
    
    public double getX() {			//reads x value on controller
    	double x = joy.getX();
    	return Math.pow(x, 2) * Math.signum(x);
    }
    
    public double getY() {			//reads y value on controller
    	double y = joy.getY();
    	return Math.pow(y, 2) * Math.signum(y);
    }
    
    public double getRot() {		//reads z value on controller
    	double rot = joy.getZ();
    	return Math.pow(rot, 2) * Math.signum(rot);
    }

}


