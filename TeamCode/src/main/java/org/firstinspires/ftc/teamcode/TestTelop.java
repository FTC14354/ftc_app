package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;

@TeleOp(name = "TESTTeleOpMode", group = "TeleOp opmode")
public class TestTelop extends OpMode {
    private static final double STEP_SIZE = 0.02;
    private static final double SHOULDER_STEP_SIZE = .001;
    private Servo wristServo;
    private Servo elbowServo;
    private Servo shoulderServo;
    private final double DRIVE_SERVO_MAX = .950;
    private final double DRIVE_SERVO_MIN = .032;

    @Override
    public void init() {
        shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
        wristServo = hardwareMap.get(Servo.class, "wristServo");
        elbowServo = hardwareMap.get(Servo.class, "elbowServo");
        composeTelemetry();
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
        telemetry.addLine()
                .addData("shoulder", new Func<Double>() {
                    @Override
                    public Double value() {
                        return shoulderServo.getPosition();
                    }
                });
    }

    @Override
    public void loop() {
        double currentElbowPosition = elbowServo.getPosition();
        double currentShoulderPosition = shoulderServo.getPosition();

//        if (gamepad2.right_stick_y > .1 ) {
//            shoulderMotor.setPower(-.5);
//            elbowServo.setPosition(currentElbowPosition - STEP_SIZE);
//
//        }else if (gamepad2.right_stick_y < -.1 && currentElbowPosition - STEP_SIZE < 1 ) {
//            shoulderMotor.setPower(.5);
//            elbowServo.setPosition(currentElbowPosition + STEP_SIZE);
//        }
        if (gamepad2.right_stick_y > .1 && currentElbowPosition + STEP_SIZE < DRIVE_SERVO_MAX) {
            elbowServo.setPosition(currentElbowPosition + STEP_SIZE);

        } else if (gamepad2.left_stick_y > .1 && currentShoulderPosition + SHOULDER_STEP_SIZE < DRIVE_SERVO_MAX) {
            shoulderServo.setPosition(currentShoulderPosition + SHOULDER_STEP_SIZE);

        } else if (gamepad2.right_stick_y < -.1 && currentElbowPosition - STEP_SIZE > DRIVE_SERVO_MIN) {
            elbowServo.setPosition(currentElbowPosition - STEP_SIZE);

        } else if (gamepad2.left_stick_y < -.1 && currentShoulderPosition - SHOULDER_STEP_SIZE > DRIVE_SERVO_MIN) {
            shoulderServo.setPosition(currentShoulderPosition - SHOULDER_STEP_SIZE);

        }


//        }else if (gamepad2.right_stick_y < -.1 && currentElbowPosition - STEP_SIZE < 1 ) {
//            shoulderMotor.setPower(.5);
//            elbowServo.setPosition(currentElbowPosition + STEP_SIZE);
//        }

//        double currentWristPosition = wristServo.getPosition();
//        double currentElbowPosition = elbowServo.getPosition();
//
//        if (gamepad2.a && currentWristPosition + STEP_SIZE < 1) {
//            wristServo.setPosition(currentWristPosition + STEP_SIZE);
//        } else if (gamepad2.y && currentWristPosition - STEP_SIZE > 0) {
//            wristServo.setPosition(currentWristPosition - STEP_SIZE);
//
//        } else if (gamepad2.x && currentElbowPosition + STEP_SIZE < 1) {
//            elbowServo.setPosition(currentElbowPosition + STEP_SIZE);
//
//        } else if (gamepad2.b && currentElbowPosition - STEP_SIZE > 0) {
//            elbowServo.setPosition(currentElbowPosition - STEP_SIZE);
//        }
//        telemetry.update();

    }
}