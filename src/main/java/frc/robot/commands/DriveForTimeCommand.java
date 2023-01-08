package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

/** Wait *******************************************************
 * Waits for a specified number of seconds. Useful for autonomous command groups! */
public class DriveForTimeCommand extends CommandBase {

    private DriveSubsystem m_drivetrainSubsystem;

	double duration;
	Timer timer = new Timer();
    double leftPct, rightPct;

	public DriveForTimeCommand(double leftPct, double rightPct, double time) {
		duration = time;
        m_drivetrainSubsystem = Robot.m_driveSubsystem;
        addRequirements(m_drivetrainSubsystem);
	}

	public void initialize() {
		timer.reset();
		timer.start();
        m_drivetrainSubsystem.drive(leftPct, rightPct);
	}
	
	public boolean isFinished() {
		return timer.get() >= duration;
	}

	public void end(boolean interrupted) {
		timer.reset();
	}
}