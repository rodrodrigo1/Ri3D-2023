// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.ExtenderSubsystem;

public class MoveExtenderCommand extends CommandBase {
  /** Creates a new MoveExtenderCommand. */
  private ExtenderSubsystem m_ExtenderSubsystem;
  private double powerPct;

  public MoveExtenderCommand(double powerPct) {
    // Use addRequirements() here to declare subsystem dependencies. 
    this.powerPct = powerPct;
    addRequirements(Robot.m_extenderSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ExtenderSubsystem.setPower(powerPct);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ExtenderSubsystem.setPower(0.0);
  }
}