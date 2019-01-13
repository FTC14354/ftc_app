package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.modules.DeployTheBoi;

@TeleOp(name = "PrimaryTeleOpMode", group = "TeleOp opmode")
public class Teleop extends OpMode {
    static final double LIFT_MAX_POWER = 0.8;
    static final double INCREMENT = 0.05;     // amount to ramp motor each CYCLE_MS cycle
    double leftRampedPower = 0;
    double rightRampedPower = 0;
    boolean rampUp = false;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor liftMotor = null;
    private DriveStyle driveStyle = null;
    private boolean isLiftRunning = false;
    private Intake intake;
    private DeployTheBoi boiDeployer;
    private static final double STEP_SIZE = 0.02;
    //    private static final double SHOULDER_STEP_SIZE = .01;
    private Servo wristServo;
    private Servo elbowServo;
    //    private Servo shoulderServo;
    private final double DRIVE_SERVO_MAX = .963;
    private final double DRIVE_SERVO_MIN = .045;
    private DcMotor shoulderMotor;


    @Override
    public void init() {
        boiDeployer = new DeployTheBoi(hardwareMap);
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoulderMotor = hardwareMap.get(DcMotor.class, "shoulderMotor");
        shoulderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wristServo = hardwareMap.get(Servo.class, "wristServo");
        elbowServo = hardwareMap.get(Servo.class, "elbowServo");
        composeTelemetry();
        shoulderMotor = hardwareMap.dcMotor.get("shoulderMotor");
        intake = new DcMotorIntake(hardwareMap);
    }

    private void composeTelemetry() {
        telemetry.addLine()
                .addData("wrist", new Func<Double>() {
                    @Override
                    public Double value() {
                        return wristServo.getPosition();
                    }
                });
        telemetry.addLine()
                .addData("elbow", new Func<Double>() {
                    @Override
                    public Double value() {
                        return elbowServo.getPosition();
                    }
                });


        telemetry.addData("Status", "Initialized");

        driveStyle = new FourWheelDriveStyle(hardwareMap, telemetry,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive", );


    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void init_loop() {
        telemetry.addData("status", "loop test... waiting for start");
    }

    @Override
    public void loop() {

        if (gamepad1.y) {
            liftMotor.setPower(-LIFT_MAX_POWER);
            isLiftRunning = true;
        } else if (gamepad1.a) {
            liftMotor.setPower(LIFT_MAX_POWER);
            isLiftRunning = true;
        } else {
            liftMotor.setPower(0);
            if (isLiftRunning) {
                telemetry.addData("LiftEncoderValue; ", liftMotor.getCurrentPosition());
                telemetry.update();
                isLiftRunning = false;
            }
        }
        double currentElbowPosition = elbowServo.getPosition();
//        double currentShoulderPosition = shoulderServo.getPosition();
        double currentShoulderPosition = shoulderMotor.getCurrentPosition();

        if (gamepad1.right_stick_y > .2 && currentElbowPosition - STEP_SIZE > DRIVE_SERVO_MIN) {
            elbowServo.setPosition(currentElbowPosition - STEP_SIZE);
        } else if (gamepad1.right_stick_y < -.2 && currentElbowPosition + STEP_SIZE < DRIVE_SERVO_MAX) {
            elbowServo.setPosition(currentElbowPosition + STEP_SIZE);
        }
//
        if (gamepad1.left_stick_y < -.5) {
            shoulderMotor.setPower(.4);
        }
//

        else if (gamepad1.left_stick_y > .5) {
            shoulderMotor.setPower(-.4);

        } else {
            shoulderMotor.setPower(0);
        }


        double leftPower;
        double rightPower;

        double drive = -gamepad2.left_stick_y;
        double turn = -gamepad2.right_stick_x;
        leftPower = Range.clip(drive + turn, -1.0, 1.0);
        rightPower = Range.clip(drive - turn, -1.0, 1.0);


        if (gamepad2.left_trigger > 0.1) {
            rampUp = true;
        } else {
            rampUp = false;
        }
        if (rampUp = true) {
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
        } else {
            leftRampedPower = leftPower;
            rightRampedPower = rightPower;
        }
        telemetry.addData("Shoulder", (shoulderMotor.getCurrentPosition()));
        telemetry.addData("Right Motor Power", "%5.2f", rightRampedPower);
        telemetry.addData("Left Motor Power", "%5.2f", leftRampedPower);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Encoders", ((FourWheelDriveStyle) driveStyle).getaencoderValues());
        telemetry.addData("lift Motor", liftMotor.getCurrentPosition());
        telemetry.update();

        driveStyle.setDriveValues(leftRampedPower, rightRampedPower);

        if (gamepad1.left_bumper) {
            intake.retract();

        } else if (gamepad2.right_bumper) {
            intake.extend();

        } else {
            intake.stop();

        }

        if (gamepad1.b) {
            intake.purge();

        } else if (gamepad1.x) {
            intake.binge();

        } else {
            intake.stop();

        }

//        if (gamepad1.left_bumper)
//
//        {
//            boiDeployer.sweep();
//            if (gamepad1.right_bumper) {
//                boiDeployer.logPosition();
//            } else {
//                boiDeployer.stopDoingThing();
//            }
//        }
    }

    @Override
    public void stop() {
        driveStyle.setDriveValues(0, 0);
        intake.stop();
        liftMotor.setPower(0);
    }


}