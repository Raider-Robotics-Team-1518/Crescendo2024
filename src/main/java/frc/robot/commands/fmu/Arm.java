package frc.robot.commands.fmu;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class Arm extends Command {
    private double power = Constants.MotorSpeeds.armPower;

    public Arm(double power) {
        this.power = power;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

}
