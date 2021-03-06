package org.usfirst.frc.team5115.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5115.robot.Robot;
import org.usfirst.frc.team5115.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class RangeFinder extends Subsystem {
    
	AnalogInput rangefinder;//this rangefinder exists
	
	public RangeFinder() {
		rangefinder = new AnalogInput(RobotMap.rangefinder);		//the rangefinder that exists is, indeed, the rangefinder that exists
	}
	
	public double getRange() {
    	double voltage = rangefinder.getAverageVoltage();		//reads output voltage of the rangefinder
    	double distance = (int)(87.763 * voltage + .0362);		//turns the volts per foot value that we got fron the documentation into just feet
    	if (distance < 12)										// closest rangefinder can read accurately is 12 in
    		distance = -1;
    	return distance;			
    }
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

