package org.usfirst.frc.team5115.robot.subsystems;

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
    
	AHRS imu;
    CANTalon frontleft;
    CANTalon frontright;
    CANTalon backleft;
    CANTalon backright;
    double[] speeds;
    
    public DriveTrain() {
    	imu = new AHRS(SerialPort.Port.kMXP);
    	imu.reset();
    	
    	frontleft = new CANTalon(1);
    	frontright = new CANTalon(2);
    	backleft = new CANTalon(3);
    	backright = new CANTalon(4);
    	speeds = new double[4];
    }
    
    private void normSpeeds() {
    	double max = 1;
    	for (double s : speeds)
    		if (s > max)
    			max = s;
    	for (double s: speeds)
    		s /= max;
    }
    
    public void drive(double x, double y, double rot) {
    	speeds[0] = x + y + rot;
    	speeds[1] = -x + y - rot;
    	speeds[2] = -x + y + rot;
    	speeds[3] = x + y - rot;
    	normSpeeds();
    	
    	double throttle = Robot.prefs.getDouble("Throttle", 0.5);
    	System.out.println(throttle);
    	
    	frontleft.set(speeds[0] * throttle);
    	frontright.set(speeds[1] * throttle);
    	backleft.set(speeds[2] * throttle);
    	backright.set(speeds[3] * throttle);
    }
    
    public void imuToDashboard() {
    	SmartDashboard.putNumber("X Velocity", imu.getVelocityX());
    	SmartDashboard.putNumber("Y Velocity", imu.getVelocityY());
    	SmartDashboard.putNumber("Yaw", imu.getYaw());
    }
    
    public void imuStart() {
    	imu.reset();
    	imu.resetDisplacement();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

