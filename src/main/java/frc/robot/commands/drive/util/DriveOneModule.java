/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController.Axis;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.base.SwerveDrive.SwerveModNum;

/**
 * A command to test one swerve module at a time, it takes that module 
 * as a constructor arguement. This command is designed to test 
 * a single module, while all other module are stopped. 
 */
public class DriveOneModule extends Command {
  private int moduleNum;
  private double rotatePos = 0.0;
  /**
   * Creates a new DriveOneModule.
   */
  public DriveOneModule(int moduleNumber) {
    //use addRequirements() and pull the subSystem object from RobotContainer
    addRequirements(RobotContainer.swerveDrive);
    //assigns the passed module number to the field of similar name
    moduleNum = moduleNumber;
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //we are driving one module, stop all other modules so they don't keep running
    RobotContainer.swerveDrive.stopAllModules();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //check to see if there is a new angle input from the DPad
    if(Robot.robotContainer.getDriverDPad() != -1){
      rotatePos = Math.toRadians(Robot.robotContainer.getDriverDPad());
      rotatePos = (rotatePos - Math.PI);
    }

    double[] modAngles = RobotContainer.swerveDrive.getAllAbsModuleAngles();
    double[] modRelEnc = RobotContainer.swerveDrive.getAllModuleRelEnc();
    SmartDashboard.putNumber("frontLeftAbsAngle", modAngles[SwerveModNum.frontLeft.getNumber()]);
    SmartDashboard.putNumber("frontLeftRelEnc", modRelEnc[SwerveModNum.frontLeft.getNumber()]);
    SmartDashboard.putNumber("frontRightAbsAngle", modAngles[SwerveModNum.frontRight.getNumber()]);
    SmartDashboard.putNumber("frontRightRelEnc", modRelEnc[SwerveModNum.frontRight.getNumber()]);
    SmartDashboard.putNumber("rearLeftAbsAngle", modAngles[SwerveModNum.rearLeft.getNumber()]);
    SmartDashboard.putNumber("rearLeftRelEnc", modRelEnc[SwerveModNum.rearLeft.getNumber()]);
    SmartDashboard.putNumber("rearRightAbsAngle", modAngles[SwerveModNum.rearRight.getNumber()]);
    SmartDashboard.putNumber("rearRightRelEnc", modRelEnc[SwerveModNum.rearRight.getNumber()]);
    
    double[] moduleVelocities = RobotContainer.swerveDrive.getAllModuleVelocity();
    SmartDashboard.putNumber("frontLeftVelocity", moduleVelocities[SwerveModNum.frontLeft.getNumber()]);
    SmartDashboard.putNumber("frontRightVelocity", moduleVelocities[SwerveModNum.frontRight.getNumber()]);
    SmartDashboard.putNumber("rearLeftVelocity", moduleVelocities[SwerveModNum.rearLeft.getNumber()]);
    SmartDashboard.putNumber("rearRightVelocity", moduleVelocities[SwerveModNum.rearRight.getNumber()]);
    
    double[] moduleDistances = RobotContainer.swerveDrive.getAllModuleDistance();
    //Push distances pulled from the modules' drive motors
    SmartDashboard.putNumber("frontLeftDistance", moduleDistances[SwerveModNum.frontLeft.getNumber()]);
    SmartDashboard.putNumber("frontRightDistance", moduleDistances[SwerveModNum.frontRight.getNumber()]);
    SmartDashboard.putNumber("rearLeftDistance", moduleDistances[SwerveModNum.rearLeft.getNumber()]);
    SmartDashboard.putNumber("rearRightDistance", moduleDistances[SwerveModNum.rearRight.getNumber()]);
    
    //Set the one module we are working with to an angle and a speed
    RobotContainer.swerveDrive.driveOneModule(
      moduleNum, Robot.robotContainer.getDriverAxis(Axis.kLeftY), rotatePos, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //before any other command is run, stop all modules, for safety 
    RobotContainer.swerveDrive.stopAllModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
