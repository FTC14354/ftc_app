package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "PrimaryTeleOpMode", group = "TeleOp opmode")
public class Teleop extends OpMode {

    static final double INCREMENT = 0.01;     // amount to ramp motor each CYCLE_MS cycle
    double leftRampedPower = 0;
    double rightRampedPower = 0;
    boolean rampUp = true;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor liftMotor = null;
    private DriveStyle driveStyle = null;
    private DcMotor intakeMotor = null;
    private boolean isLiftRunning = false;

    @Override
    public void init() {
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        telemetry.addData("Status", "Initialized");

        driveStyle = new FourWheelDriveStyle(hardwareMap,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            liftMotor.setPower(-0.5);
            isLiftRunning = true;
        } else if (gamepad1.y) {
            liftMotor.setPower(0.5);
            isLiftRunning = true;
        } else {
            liftMotor.setPower(0);
            if (isLiftRunning){
                telemetry.addData("LiftEncoderValue; ", liftMotor.getCurrentPosition());
                telemetry.update();
                isLiftRunning = false;
            }

        }


        double leftPower;
        double rightPower;

        double drive = -gamepad2.left_stick_y;
        double turn = -gamepad2.right_stick_x;
        leftPower = Range.clip(drive + turn, -1.0, 1.0);
        rightPower = Range.clip(drive - turn, -1.0, 1.0);

        if (rampUp) {
            if (leftRampedPower < leftPower) {
                leftRampedPower += INCREMENT;
            } else if (leftRampedPower > leftPower) {
                leftRampedPower -= INCREMENT;
            }
            if (rightRampedPower < rightPower) {
                rightRampedPower += INCREMENT;
            } else if (rightRampedPower > rightPower) {
                rightRampedPower -= INCREMENT;
            }
        }
//        telemetry.addData("Right Motor Power", "%5.2f", rightRampedPower);
//        telemetry.addData(">", "Press Stop to end test.");
//        telemetry.update();
//        telemetry.addData("Left Motor Power", "%5.2f", leftRampedPower);
//        telemetry.addData("Status", "Run Time: " + runtime.toString());
//        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
//
//        telemetry.addData(">", "Press Start to run Motors.");
//        telemetry.update();

            driveStyle.setDriveValues(leftRampedPower, rightRampedPower);

            if (gamepad1.b) {
                intakeMotor.setPower(0.5);
            } else if (gamepad1.x) {
                intakeMotor.setPower(-0.5);
            } else {
                intakeMotor.setPower(0);
            }
        }

    @Override
    public void stop() {
        driveStyle.setDriveValues(0, 0);
        intakeMotor.setPower(0);
        liftMotor.setPower(0);

    }

}