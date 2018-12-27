package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;

@TeleOp(name = "TESTTeleOpMode", group = "TeleOp opmode")
public class TestTelop extends OpMode {
    private static final double STEP_SIZE = 0.02;
    private Servo elbowServo;
    private Servo shoulderServo;

    @Override
    public void init() {
        elbowServo = hardwareMap.get(Servo.class, "elbowServo");
        shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
        composeTelemetry();
        elbowServo.setPosition(0);
        shoulderServo.setPosition(0);
    }

    private void composeTelemetry() {
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

        if (gamepad2.a && currentElbowPosition + STEP_SIZE < 1) {
            elbowServo.setPosition(currentElbowPosition + STEP_SIZE);
        } else if (gamepad2.y && currentElbowPosition - STEP_SIZE > 0) {
            elbowServo.setPosition(currentElbowPosition - STEP_SIZE);

        } else if (gamepad2.x && currentShoulderPosition + STEP_SIZE < 1) {
            shoulderServo.setPosition(currentShoulderPosition + STEP_SIZE);

        } else if (gamepad2.b && currentShoulderPosition - STEP_SIZE > 0) {
            shoulderServo.setPosition(currentShoulderPosition - STEP_SIZE);
        }
        telemetry.update();
    }

}
