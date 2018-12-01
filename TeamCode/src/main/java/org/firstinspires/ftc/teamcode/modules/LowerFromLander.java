package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.DriveStyle;

import static java.lang.Thread.sleep;


public class LowerFromLander {
    private HardwareMap hardwareMap;
    private DcMotor liftMotor = null;
    private DriveStyle driveStyle = null;
    private final static int encoder_ticks_top_released = 18985;
    private final static int encoder_ticks_lowered = 10000;
    private final static double POWER_MAX = 0.5;

    public LowerFromLander(HardwareMap hardwareMap, DriveStyle drivestyle) {
        this.hardwareMap = hardwareMap;
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        this.driveStyle = drivestyle;
    }

    public void landRobot() {
        lowerRobot();
        driveForward();
        lowerArm();
    }

    public void lowerRobot() {
        moveMotorToPosition(encoder_ticks_top_released);
    }

    public void driveForward() {
        driveStyle.setDriveValues(POWER_MAX, POWER_MAX);
        sleep(500);
        driveStyle.stop();

    }

    public void lowerArm() {
        moveMotorToPosition(encoder_ticks_lowered);
    }

    private void moveMotorToPosition(int position) {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(position);
        liftMotor.setPower(.5);

        while (liftMotor.isBusy()) {
            sleep(500);
        }
        liftMotor.setPower(0);
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
