// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.fmu;

import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class Shooter extends Command {
  /** Creates a new Shooter. */
  private double speed = Constants.MotorSpeeds.intakeSpeed;
  private double angle = 76.0d;

  public Shooter(double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // to prevent the arm from sagging/moving during shooting, we grab its
    // starting angle, then drive to that angle
    this.angle = RobotContainer.fmu.get_arm_position();
    RobotContainer.fmu.setIntakeSpeed(-0.20d);
    Timer.delay(0.05d);
    RobotContainer.fmu.setShooterSpeed(speed);
    Timer.delay(0.5d);
    RobotContainer.fmu.bumpIntake();

  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /*
     * Because this is run every time in the command loop, we can't do the 
     * back up to shoot here.
     */
    // move arm to the target angle, or keep it there if it has drifted
    new MoveArmToAngle(this.angle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.fmu.setIntakeSpeed(0);
    RobotContainer.fmu.setShooterSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
