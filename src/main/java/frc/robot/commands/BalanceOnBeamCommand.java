// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

public class BalanceOnBeamCommand extends CommandBase {

  DriveSubsystem m_DriveSubsystem;
  double error;
  
  /** Creates a new BalanceOnBeam. */
  public BalanceOnBeamCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_DriveSubsystem = Robot.m_driveSubsystem;
    addRequirements(m_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Double currentAngle = m_DriveSubsystem.getPitch(); /*-1 * Robot.controller.getRawAxis(Constants.LEFT_VERTICAL_JOYSTICK_AXIS) * 45*/;
    Double error = Constants.BEAM_BALANCED_ANGLE_DEGREES - currentAngle;
    Double drivePower = Math.min(Constants.BEAM_BALANACED_DRIVE_KP * error, 1);
    System.out.println("Current Angle: " + error);
    System.out.println("Error " + error);
    System.out.println("Value: " + drivePower);
    m_DriveSubsystem.drive(drivePower, drivePower);
    System.out.println(
      "Left: " + m_DriveSubsystem.getLeftPct() + " Right: " + m_DriveSubsystem.getLeftPct()
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_DriveSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(error) < 1;
  }
}