package org.usfirst.frc.team5115.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team5115.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	public AHRS imu;
	
	// key for both maps will be the position of the motor (ex 'frontleft')
    Map<String, CANTalon> motors;
    Map<String, Double> speeds;
    
    double xAccum = 0;
    double yAccum = 0;
    
    public DriveTrain() {
    	imu = new AHRS(SerialPort.Port.kMXP);
    	
    	// intialize the motors with the correct CAN ids
    	motors = new HashMap<String, CANTalon>();
    	motors.put("frontleft", new CANTalon(1));
    	motors.put("frontright", new CANTalon(2));
    	motors.put("backleft", new CANTalon(3));
    	motors.put("backright", new CANTalon(4));
    	
    	// initialize an empty map for speeds
    	speeds = new HashMap<String, Double>();
    }
    
    // searches the speeds for the highest value greater than 1, then divides all the speeds by that value
    // ensures the highest speed is less than one, and keeps the ratio between each speed constant
    // (if no speeds are greater than 1, no change must be made)
    private void normSpeeds() {
    	double max = 1;
    	for (double s : speeds.values())
    		if (s > max)
    			max = s;
    	for (double s: speeds.values())
    		s /= max;
    }
    
    // converts translation and rotation values into speeds for each motor and normalizes them
    // gets the throttle from smartdashboard
    // iterates through the maps keys, setting each motor to its corresponding speed
    public void drive(double x, double y, double rot) {
    	speeds.put("frontleft", x + y + rot);
    	speeds.put("frontright", -x + y - rot);
    	speeds.put("backleft", -x + y + rot);
    	speeds.put("backright", x + y - rot);
    	normSpeeds();
    	
    	double throttle = Robot.prefs.getDouble("Throttle", 0.5);
    	
    	for (String key : speeds.keySet())
    		motors.get(key).set(speeds.get(key) * throttle);
    }
    
    // sends useful data to the computer
    public void imuToDashboard() {
    	xAccum += imu.getVelocityX() * 0.02;
    	yAccum += imu.getVelocityY() * 0.02;

    	SmartDashboard.putNumber("X Accel", imu.getRawAccelX() * 1000);
    	SmartDashboard.putNumber("Y Accel", imu.getRawAccelY() * 1000);
    	SmartDashboard.putNumber("X Accumulation", xAccum);
    	SmartDashboard.putNumber("Y Accumulation", yAccum);
    	SmartDashboard.putNumber("X Displacement", imu.getDisplacementX());
    	SmartDashboard.putNumber("Y Displacement", imu.getDisplacementY());
    	SmartDashboard.putNumber("Yaw", imu.getYaw());
    }
    
    // initializes all imu values and accumulators
    public void imuStart() {
    	imu.reset();
    	imu.resetDisplacement();
    	xAccum = 0;
    	yAccum = 0;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

