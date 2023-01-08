// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TurnToAprilTagCommand extends CommandBase {

  DriveSubsystem m_DriveSubsystem;
  VisionSubsystem m_VisionSubsystem;
  double targetAngle;
  double kp;
  double error;

  /** Creates a new GyroTurnToAngle. */
  public TurnToAprilTagCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_DriveSubsystem = Robot.m_driveSubsystem;
    m_VisionSubsystem = Robot.m_visionSubsystem;
    kp = Constants.GYRO_KP*2.5;
    addRequirements(m_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_VisionSubsystem.getHasTarget()) {
      error = m_VisionSubsystem.getBestTarget().getYaw();
      double value = -Math.min(error*kp, 1);

      m_DriveSubsystem.drive(-value, value);
    } else {
      m_DriveSubsystem.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      System.out.println("ENDED");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    //return Math.abs(error) < 1;
  }
}