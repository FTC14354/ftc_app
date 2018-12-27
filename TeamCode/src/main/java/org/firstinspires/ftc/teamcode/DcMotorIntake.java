package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DcMotorIntake implements Intake {
    private DcMotor intakeMotor = null;

    public DcMotorIntake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
    }

    @Override
    public void purge() {
        intakeMotor.setPower(0.5);
    }

    @Override
    public void binge() {
        intakeMotor.setPower(-0.5);
    }

    @Override
    public void stop() {
        intakeMotor.setPower(0);
    }

    @Override
    public void extend() {

    }

    @Override
    public void retract() {

    }

}
