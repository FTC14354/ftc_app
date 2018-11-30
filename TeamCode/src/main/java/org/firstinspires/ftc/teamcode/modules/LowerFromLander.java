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
    private final static double POWER_MAX = 0.7;

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
        moveMotorToPosition(liftMotor, encoder_ticks_top_released, Direction.FORWARD);
    }

    public void driveForward() {
        driveStyle.setDriveValues(POWER_MAX, POWER_MAX);
        sleep(1000);
        driveStyle.stop();

    }

    public void lowerArm() {
        moveMotorToPosition(liftMotor, encoder_ticks_top_released, Direction.REVERSE);


    }

    private void moveMotorToPosition(DcMotor motor, int position, Direction direction) {
        liftMotor.setDirection(direction);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(position);
        while (liftMotor.isBusy()) {

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
