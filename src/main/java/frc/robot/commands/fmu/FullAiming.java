package frc.robot.commands.fmu;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class FullAiming extends Command {
    private double powerUp = Constants.MotorSpeeds.armPowerUp;
    private double powerSteer = 0.0d;
    private boolean isTargetVisible = RobotContainer.limeLight1.isTargetVisible();
    private int targetID = (int) RobotContainer.llHelpers.getFiducialID("limelight");
    // private double powerDn = Constants.MotorSpeeds.armPowerDn;
    private double current_angle = RobotContainer.fmu.get_arm_position();
    private double set_angle = current_angle;
    private int horizOffset = (int) RobotContainer.limeLight1.getTargetOffsetHorizontal();
    
    public FullAiming() {
        addRequirements(RobotContainer.fmu);
        //addRequirements(RobotContainer.swerveDrive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Check to see if LimeLight has acquired target lock
        if (isTargetVisible) { // April Tag Visible
            set_angle = RobotContainer.limeLight1.getOptimalArmAngle(RobotContainer.limeLight1.getDistanceToTarget(targetID));
            horizOffset = (int) RobotContainer.limeLight1.getTargetOffsetHorizontal();
        } else {
            set_angle = RobotContainer.optimalArmAngle;
            horizOffset = 0;
        }
        // Check value of shoulder encoder
        current_angle = RobotContainer.fmu.get_arm_position();
        // Calculate power curve proportional
        powerUp = Math.abs(this.set_angle - current_angle) / 100;
        powerSteer = 0.2d; // 0.5 - Math.pow((horizOffset / 27), 2);
        // Move arm up or down to default speaker angle
        if (Math.abs(this.set_angle - current_angle) > Constants.Tolerances.armAimingTolerance) {
            double v_sign = Math.signum(this.set_angle - current_angle);
            RobotContainer.fmu.move_arm(v_sign * (powerUp + 0.25d));
        } else {
            RobotContainer.fmu.stop_arm();
        }
        // Rotate Robot to center on April Tag
        if (Math.abs(horizOffset) > 3 ) { // Constants.Tolerances.armAimingTolerance) {
            double h_sign = Math.signum(0 - horizOffset);
            RobotContainer.swerveDrive.driveRobotCentric(0, 0, h_sign * (powerSteer + 0.0d), false, true);  // h_sign * (powerSteer + 0.2d)
        } else {
            RobotContainer.swerveDrive.stopAllModules();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //RobotContainer.fmu.stop_arm();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        return false;
    }

}