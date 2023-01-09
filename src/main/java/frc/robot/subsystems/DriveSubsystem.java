// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  
  // Drivetrain Motor Controllers
  private VictorSP m_leftFrontMotor;
  private VictorSP m_rightFrontMotor;
  private VictorSP m_leftRearMotor;
  private VictorSP m_rightRearMotor;

  private Encoder leftDriveEncoder;
  private Encoder rightDriveEncoder;
  
  private AHRS navx = new AHRS(SerialPort.Port.kUSB);
  SendableChooser<Double> driveScaleChooser = new SendableChooser<Double>();

  public double CURRENT_DRIVE_SCALE;

  private double leftPositionZero;
  private double rightPositionZero;

  /** Subsystem for controlling the Drivetrain and accessing the NavX Gyroscope */
  public DriveSubsystem() {
    // Instantiate the Drivetrain motor controllers
    m_leftFrontMotor = new VictorSP(Constants.LEFT_FRONT_DRIVE_MOTOR_ID);
    m_rightFrontMotor = new VictorSP(Constants.RIGHT_FRONT_DRIVE_MOTOR_ID);
    m_leftRearMotor = new VictorSP(Constants.LEFT_REAR_DRIVE_MOTOR_ID);
    m_rightRearMotor = new VictorSP(Constants.RIGHT_REAR_DRIVE_MOTOR_ID);

    // Reverse some of the motors if needed
    m_leftFrontMotor.setInverted(Constants.DRIVE_INVERT_LEFT);
    m_rightFrontMotor.setInverted(Constants.DRIVE_INVERT_RIGHT);
    m_leftRearMotor.setInverted(Constants.DRIVE_INVERT_LEFT);
    m_rightRearMotor.setInverted(Constants.DRIVE_INVERT_RIGHT);

    leftDriveEncoder = new Encoder(0, 1);
    rightDriveEncoder = new Encoder(2, 3);

    leftDriveEncoder.setDistancePerPulse(Constants.WHEEL_CIRCUMFERENCE / Constants.LEFT_ENCODER_COUNTS_PER_REV);
    leftDriveEncoder.setDistancePerPulse(Constants.WHEEL_CIRCUMFERENCE / Constants.RIGHT_ENCODER_COUNTS_PER_REV);

    zeroEncoders(); // Zero the encoders

    // Drive Scale Options //
    driveScaleChooser.setDefaultOption("100%", 1.0);
    driveScaleChooser.addOption("75%", 0.75);
    driveScaleChooser.addOption("50%", 0.5);
    driveScaleChooser.addOption("25%", 0.25);

    SmartDashboard.putData("Drivetrain Speed", driveScaleChooser);
  }

  /* Set power to the drivetrain motors */
  public void drive(double leftPercentPower, double rightPercentPower) {
    m_leftFrontMotor.set(leftPercentPower);
    m_leftRearMotor.set(leftPercentPower);
    m_rightFrontMotor.set(rightPercentPower);
    m_rightRearMotor.set(rightPercentPower);
  }
  public void stop() {
    drive(0, 0);
  }

  // NavX Gyroscope Methods //
  public void calibrateGyro() {
    navx.calibrate();
  }
  public void zeroGyro() {
    System.out.println("NavX Connected: " + navx.isConnected());
    navx.reset();
  }
  public double getYaw() {
    return navx.getYaw();
  }
  public double getPitch() {
    return navx.getPitch();
  }
  public double getRoll() {
    return navx.getRoll();
  }
  public double getAngle() {
    return navx.getAngle();
  }

  // Speed will be measured in meters/second
  public double getLeftSpeed() {
    return leftDriveEncoder.getRate();
  }
  public double getRightSpeed() {
    return rightDriveEncoder.getRate();
  }
  // Distance will be measured in meters
  public double getLeftDistance() {
    return leftDriveEncoder.getDistance();
  }
  public double getRightDistance() {
    return rightDriveEncoder.getDistance();
  }
  public void zeroEncoders() { // Reset the 'zero' positions to be the current encoder positions
		leftPositionZero = leftDriveEncoder.get();
    rightPositionZero = rightDriveEncoder.get();
	}
	public double getLeftEncoderPositionZero() { // Returns the current 'zero' position in raw encoder counts
		return leftPositionZero;
	}
  public double getRightEncoderPositionZero() { // Returns the current 'zero' position in raw encoder counts
		return rightPositionZero;
	}
  // These return values are measured in raw encoder counts
  public double getLeftRaw() {
    return leftDriveEncoder.get();
  }
  public double getRightRaw() {
    return rightDriveEncoder.get();
  }

  @Override
  public void periodic() {
    CURRENT_DRIVE_SCALE = driveScaleChooser.getSelected();
  }
}