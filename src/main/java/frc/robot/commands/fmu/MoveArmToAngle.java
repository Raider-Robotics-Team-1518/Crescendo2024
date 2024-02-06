package frc.robot.commands.fmu;

import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class MoveArmToAngle extends Command {
    private double powerUp = Constants.MotorSpeeds.armPowerUp;
    private double powerDn = Constants.MotorSpeeds.armPowerDn;
    private double current_angle = RobotContainer.fmu.get_arm_position();
    
    public MoveArmToAngle(double set_angle) {
        addRequirements(RobotContainer.fmu);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Check value of shoulder encoder
        // Move arm up or down to default speaker angle

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
