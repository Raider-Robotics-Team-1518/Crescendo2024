// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;

import frc.robot.Constants;
import frc.robot.Utils;



public class FieldManipulationUnit extends SubsystemBase {
  /** Creates a new Shooter. */
  private CANSparkMax lead_shooter_motor;
  private CANSparkMax follow_shooter_motor;
  private CANSparkMax lead_intake_motor;
  private CANSparkMax follow_intake_motor;
  private CANSparkMax climb_motor;
  private CANSparkMax lead_arm_motor;
  private CANSparkMax follow_arm_motor;
  private boolean override_note_is_loaded = false;
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);


  public FieldManipulationUnit () {
    lead_shooter_motor = new CANSparkMax(Constants.LEAD_SHOOTER_MOTOR, MotorType.kBrushless);
    lead_shooter_motor.setInverted(true);
    lead_shooter_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);

    follow_shooter_motor = new CANSparkMax(Constants.FOLLOW_SHOOTER_MOTOR, MotorType.kBrushless);
    follow_shooter_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);

    lead_intake_motor = new CANSparkMax(Constants.LEAD_INTAKE_MOTOR, MotorType.kBrushless);
    follow_intake_motor = new CANSparkMax(Constants.FOLLOW_INTAKE_MOTOR, MotorType.kBrushless);

    lead_arm_motor = new CANSparkMax(Constants.LEAD_ARM_MOTOR, MotorType.kBrushless);
    follow_arm_motor = new CANSparkMax(Constants.FOLLOW_ARM_MOTOR, MotorType.kBrushless);

    climb_motor = new CANSparkMax(Constants.CLIMB_MOTOR, MotorType.kBrushless);

    follow_shooter_motor.follow(lead_shooter_motor);
    follow_intake_motor.follow(lead_intake_motor);
    follow_arm_motor.follow(lead_arm_motor);

  }

  public void setIntakeSpeed(double speed) {
    lead_intake_motor.set(speed);
  }

  public void stopIntake() {
    lead_intake_motor.set(0);
  }

  public void bumpIntake() {
    // run intake slowly to push Note into shooter
    lead_intake_motor.set(Constants.MotorSpeeds.intakeBumpSpeed);
    override_note_is_loaded = true;
    Timer.delay(Constants.Timings.resetColorSensorDelay);
    override_note_is_loaded = false;
  }

  public void setShooterSpeed(double speed) {
    lead_shooter_motor.set(speed);
  }

  public void stop_shooterCommand() {
    lead_shooter_motor.set(0);
  }

  public boolean isFinished() {
    return true;
  }

  public boolean isNoteLoaded() {
    Color detectedColor = m_colorSensor.getColor(); // returns a struct of doubles
    double r = detectedColor.red;
    double b = detectedColor.blue;
    double g = detectedColor.green;

    // calculate with Hue Saturation Value (HSV)
    float hue = Utils.getHue((float)r, (float)g, (float)b);
    SmartDashboard.putNumber("Hue", (double)hue);

    if (hue > Constants.ColorValues.orangeHueMin && hue < Constants.ColorValues.orageHueMax) {
      SmartDashboard.putBoolean("Note Loaded", true);
      return true;
    }
    else {
      SmartDashboard.putBoolean("Note Loaded", false);
      return false;
    }

    // alternatively, calculate using the RGB values
    // orange has lots of red, a fair bit of blue, and not much green
    // tune these amounts in the Constants file
/*    if (r > Constants.ColorValues.red && b > Constants.ColorValues.blue && g < Constants.ColorValues.green) {
      return true;
    }
*/
  }
  
  @Override
  public void periodic() {
    isNoteLoaded();

    // This method will be called once per scheduler run
    
    // COMMENTED OUT UNTIL WE INSTALL THE COLOR SENSOR
    // if (!override_note_is_loaded) {
    //   if (isNoteLoaded()) {
    //     stopIntake();;
    //   }
    // }
  }
}
