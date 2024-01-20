// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class FieldManipulationUnit extends SubsystemBase {
  /** Creates a new Shooter. */
  private CANSparkMax lead_shooter_motor;
  private CANSparkMax follow_shooter_motor;
  private CANSparkMax lead_intake_motor;
  private CANSparkMax follow_intake_motor;
  private CANSparkMax climb_motor;
  private CANSparkMax elevation_motor;

  public FieldManipulationUnit () {
    lead_shooter_motor = new CANSparkMax(Constants.LEAD_SHOOTER_MOTOR, MotorType.kBrushless);
    follow_shooter_motor = new CANSparkMax(Constants.FOLLOW_SHOOTER_MOTOR, MotorType.kBrushless);
    lead_intake_motor = new CANSparkMax(Constants.LEAD_INTAKE_MOTOR, MotorType.kBrushless);
    follow_intake_motor = new CANSparkMax(Constants.FOLLOW_INTAKE_MOTOR, MotorType.kBrushless);

    //follow_shooter_motor.setInverted(true);
    //follow_intake_motor.setInverted(true);

    follow_shooter_motor.follow(lead_shooter_motor);
    follow_intake_motor.follow(lead_intake_motor);

  }

  public Command run_intakeCommand() {
    lead_intake_motor.set(0.5);
    return isFinished();
  }

  public void stop_intakeCommand() {
    lead_intake_motor.set(0);

  }

  public void run_shooterCommand() {
    lead_shooter_motor.set(0.5);

  }

  public void stop_shooterCommand() {
    lead_shooter_motor.set(0);
  }

  public boolean isFinished() {
    return true;
  }
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
