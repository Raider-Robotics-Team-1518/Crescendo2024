// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Blinkies extends SubsystemBase {
  public LEDState lightState = LEDState.DEFAULT;
  public final DigitalOutput dOutput1 = new DigitalOutput(1);
  public final DigitalOutput dOutput2 = new DigitalOutput(2);
  public final DigitalOutput dOutput3 = new DigitalOutput(3);
  public final DigitalOutput dOutput4 = new DigitalOutput(4);

  public Blinkies() {
  }

  @Override
  public void periodic() {
    if(RobotState.isDisabled()) {
      setLEDState(LEDState.DEFAULT);
    } else {
      /*
       * Pseudo code:
       *    if note is loaded:
       *      show some solid color setLEDState(LEDState.ORANGE)
       *    else if stage april tag in view and within shooting distance (so ready to shoot)
       *      show some other color/pattern setLEDState(LEDState.GREEN)
       *    else if loading station april tag in view and distance is NNN
       *      show some other color/pattern  setLEDState(LEDState.PULSE_GREEN)
       *    else
       *      show alliance color
       */


       /*
      if(BallRejectSubsystem.getCurrentColorBall().equalsIgnoreCase("None")) {
      } else if(BallShooterSubsystem.shooterMotor.get() > 0.1d) {
        switch(RobotContainer.allianceColor.toString().toLowerCase()) {
          case "blue":
            setLEDState(LEDState.BLUE_WITH_RED_STRIPE);
            return;
          case "red":
            setLEDState(LEDState.RED_WITH_BLUE_STRIPE);
            return;
        }
      } else {
        switch(DriverStation.getAlliance().toString().toLowerCase()) {
          case "blue":
            setLEDState(LEDState.PULSE_BLUE);
            return;
          case "red":
            setLEDState(LEDState.PULSE_RED);
            return;
        }
      } 
      */
    }
  }

  public void setLEDState(LEDState state) {
    dOutput1.set(state.do1);
    dOutput2.set(state.do2);
    dOutput3.set(state.do3);
    this.lightState = state;
  }


  public enum LEDState {
      // the state of the DIO ports must match the code on the LED controller
      // which is looking for a pattern of highs & lows to determine the
      // color or pattern to show
      DEFAULT(false, true, true), //(false, false, false),
      BLUE(false, true, false),
      RED(true, false, false),
      PULSE_BLUE(false, false, true),
      PULSE_RED(true, true, false),
      RED_WITH_BLUE_STRIPE(false, true, true),
      BLUE_WITH_RED_STRIPE(true, false, true);
  
      private final boolean do1, do2, do3;
      private LEDState(boolean do1, boolean do2, boolean do3) {
        this.do1 = do1;
        this.do2 = do2;
        this.do3 = do3;
      }
  }

  }
