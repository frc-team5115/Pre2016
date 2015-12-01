package org.usfirst.frc.team5115.robot.subsystems;

import org.usfirst.frc.team5115.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TalonTest extends Subsystem {
	
	CANTalon testMotor;
	boolean motorOn = false;
	
	
    public TalonTest() {
    	testMotor = new CANTalon(5);
    }
    
    public void toggleMotor() {
    	if (motorOn)
    		testMotor.set(0);
    	else
    		testMotor.set(Robot.prefs.getDouble("TestSpeed" , 1));
    	
    	motorOn  = !motorOn;
    }
    
    public void initDefaultCommand() {}
    
    
}

