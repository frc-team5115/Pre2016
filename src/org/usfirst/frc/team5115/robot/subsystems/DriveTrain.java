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
	
	public boolean inuse;	//lets outside commands know what is happening
    
	public AHRS imu;		//altitude and heading reference system of various sensors
	
	// key for both maps will be the position of the motor (ex 'frontleft')
    Map<String, CANTalon> motors;	// mapping text (string) to motors
    Map<String, Double> speeds;		// mapping text (string) to the speed of each motor
    
    double xv = 0;			// accumulators for imu
    double yv = 0;
    double xd = 0;
    double yd = 0;
    
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
    	double max = 1;		// the highest motor speed
    	for (double s : speeds.values())	// for every value in speed...
    		if (s > max)					// check whether it is over the max speed
    			max = s;					// and if so, set it as the max speed
    	for (double s: speeds.values())		// then, divide every speed by the max speed so as to stay below 1 and mantain ratio
    		s /= max;
    }
    
    // converts translation and rotation values into speeds for each motor and normalizes them
    // gets the throttle from smartdashboard
    // iterates through the maps keys, setting each motor to its corresponding speed
    public void drive(double x, double y, double rot) {
    	speeds.put("frontleft", x + y + rot);		// set speeds
    	speeds.put("frontright", -x + y - rot);
    	speeds.put("backleft", -x + y + rot);
    	speeds.put("backright", x + y - rot);
    	normSpeeds();								//make sure none of the speeds are over the max, and if so, scale them down
    	
    	double throttle = Robot.prefs.getDouble("Throttle", 0.5);		// get the smartdashboard value for the throttle
    	
    	for (String key : speeds.keySet())			//sets the motors to the speed values times the throttle
    		motors.get(key).set(speeds.get(key) * throttle);
    }
    
    public void driveVector(double mag, double angle, double rot) {
    	double x = mag * Math.cos(angle);
    	double y = mag * Math.sin(angle);
    	drive(x, y, rot);
    }
    
    public void driveField(double x, double y, double rot) {
    	double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double ang = Math.atan2(y, x);
		driveVector(mag, ang - Math.toRadians(imu.getYaw()), rot);
    }
    
    // sends useful data to the computer
    public void imuToDashboard() {
    	xv += imu.getWorldLinearAccelX() * 0.02;	// add to distance trackers
    	yv += imu.getWorldLinearAccelY() * 0.02;
    	xd += xv;
    	yd += yv;

    	SmartDashboard.putNumber("X Accel", imu.getWorldLinearAccelX());		// sent to smartdashboard
    	SmartDashboard.putNumber("Y Accel", imu.getWorldLinearAccelX());
    	SmartDashboard.putNumber("X Velocity", xv);
    	SmartDashboard.putNumber("Y Velocity", yv);
    	SmartDashboard.putNumber("X Displacement", xd);
    	SmartDashboard.putNumber("Y Displacement", yd);
    	SmartDashboard.putNumber("Yaw", imu.getYaw());
    }
    
    // initializes all imu values and accumulators
    public void imuStart() {
    	imu.reset();
    	imu.resetDisplacement();
    	xv = 0;
    	xv = 0;
    	xd = 0;
    	yd = 0;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

