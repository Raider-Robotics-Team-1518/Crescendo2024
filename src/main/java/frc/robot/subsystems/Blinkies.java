// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimeLight;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;

public class Blinkies extends SubsystemBase {
  public LEDState lightState = LEDState.DEFAULT;
  public final DigitalOutput dOutput1 = new DigitalOutput(1);
  public final DigitalOutput dOutput2 = new DigitalOutput(2);
  public final DigitalOutput dOutput3 = new DigitalOutput(3);
  public final DigitalOutput dOutput4 = new DigitalOutput(4);
  private LimelightTarget_Fiducial llAprilTag = new LimelightHelpers.LimelightTarget_Fiducial();
  private LimeLight limelight = new LimeLight();

  public Blinkies() {
  }

  @Override
  public void periodic() {
    if(RobotState.isDisabled()) {
      setLEDState(LEDState.DEFAULT);
    } else {
      int targetSpeakerAprilTag = 0;
      int targetAmpAprilTag = 0;
      int targetSourceAprilTag = 0;
      String allianceColor = DriverStation.getAlliance().toString().toLowerCase();
      switch(allianceColor) {
        case "blue":
          targetSpeakerAprilTag = Constants.AprilTagIds.blueSpeakerLeft;
          targetAmpAprilTag = Constants.AprilTagIds.blueAmp;
          targetSourceAprilTag = Constants.AprilTagIds.blueSourceLeft;
          setLEDState(LEDState.BLUE);
          break;
        case "red":
          targetSpeakerAprilTag = Constants.AprilTagIds.redSpeakerLeft;
          targetAmpAprilTag = Constants.AprilTagIds.redAmp;
          targetSourceAprilTag = Constants.AprilTagIds.redSourceLeft;
          setLEDState(LEDState.RED);
          break;
      }

      int fID = (int) llAprilTag.fiducialID;
      if (fID > 0) {
        if (fID == targetSpeakerAprilTag && limelight.getDistanceToTarget(fID) < Constants.FieldPositions.maxDistanceToSpeaker) {
          setLEDState(LEDState.GREEN);
        } else if (fID == targetAmpAprilTag && limelight.getDistanceToTarget(fID) < Constants.FieldPositions.maxDistanceToAmp) {
          setLEDState(LEDState.GREEN);
        } else if (fID == targetSourceAprilTag && limelight.getDistanceToTarget(fID) < Constants.FieldPositions.maxDistanceToSource) {
          setLEDState(LEDState.YELLOW);
        }
      }
      /*
      } else if (note is loaded) {
        setLEDState(LEDState.ORANGE)
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
      OFF(false, false, false), // 0
      RED(true, false, false), // 1
      BLUE(false, true, false), // 2
      GREEN(true, true, false), // 3
      ORANGE(false, false, true), // 4
      YELLOW(true, false, true), // 5
      PURPLE(false, true, true), // 6
      DEFAULT(true, true, true); // 7

  
      private final boolean do1, do2, do3;
      private LEDState(boolean do1, boolean do2, boolean do3) {
        this.do1 = do1;
        this.do2 = do2;
        this.do3 = do3;
      }
  }

  }
