package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveSubsystem;

/** Wait *******************************************************
 * Waits for a specified number of seconds. Useful for autonomous command groups! */
public class TimedGyroDriveStraightCommand extends CommandBase {

  private DriveSubsystem m_drivetrainSubsystem;

	double duration; // how long we want to move forward
	Timer timer = new Timer();
    double startAngle;
    double driveRate;

	public TimedGyroDriveStraightCommand(double time, double driveRate) {
	  duration = time;
      this.driveRate = driveRate;
      m_drivetrainSubsystem = Robot.m_driveSubsystem;
      addRequirements(m_drivetrainSubsystem);
	}

	public void initialize() {
	  timer.reset();
  	  timer.start();
      startAngle = m_drivetrainSubsystem.getAngle();
	}

    public void execute() {
      double error = startAngle - m_drivetrainSubsystem.getAngle(); // Correction needed for robot angle (our starting angle, since we would like to drive straight)
      double value1 = Math.min(driveRate + Constants.GYRO_KP * error, 1); // plus or minus Constants.GYRO_KP * error, meant for error correction
      double value2 = Math.min(driveRate - Constants.GYRO_KP * error, 1);
      m_drivetrainSubsystem.drive(value1, value2); // write percent values to motors
	}
	
	// returns true when the command ends
	public boolean isFinished() {
		return timer.get() >= duration;
	}

	public void end(boolean interrupted) {
		timer.reset();
        System.out.println("ENDED");
	}
}