// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class AutoDriveTime extends Command {
  /** Creates a new AutoDriveTime. */
  private Timer timer = new Timer();
  private double delayTime = 0;
  private boolean isDone = false;
  
  public AutoDriveTime(double seconds) {
    addRequirements(RobotContainer.swerveDrive);
    delayTime = seconds;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.swerveDrive.driveRobotCentric(0.35,0,0,false,false);
    
    if(timer.hasElapsed(delayTime)){
      RobotContainer.swerveDrive.driveRobotCentric(0,0,0,false,false);
      isDone = true;
    }
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // I think we don't want to stop the intake here so that it will
    // run until we call the AutoStopIntake command
    // RobotContainer.fmu.setIntakeSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}