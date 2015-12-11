package org.usfirst.frc.team5115.robot.subsystems;

import org.usfirst.frc.team5115.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TalonTest extends Subsystem {
	
	// defines motor for Talon
	CANTalon testMotor;
	// switch to allow the created object to self identify whether or not it is on
	boolean motorOn = false;
		
		
    public TalonTest() {
    	// sets testMotor as 5
    	testMotor = new CANTalon(5);
    }
	    
    public void toggleMotor() {
    	// toggles the motor
    	if (motorOn)
    		testMotor.set(0);
    	else
    		testMotor.set(Robot.prefs.getDouble("TestSpeed" , 1));
    	
    	// toggles the switch so the created object can identify that the motor has been toggled.
    	motorOn  = !motorOn;
    }
	    
    public void initDefaultCommand() {}
    
    
}

