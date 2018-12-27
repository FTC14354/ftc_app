package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;

@TeleOp(name = "TESTTeleOpMode", group = "TeleOp opmode")
public class TestTelop extends OpMode {
    private static final double STEP_SIZE = 5;
    private Servo elbowServo;
    private Servo shoulderServo;

    @Override
    public void init() {
        elbowServo = hardwareMap.get(Servo.class, "elbowServo");
        shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
        composeTelemetry();
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
        if (gamepad2.a) {
            elbowServo.setPosition(elbowServo.getPosition() + STEP_SIZE);
        } else if (gamepad2.y) {
            elbowServo.setPosition(elbowServo.getPosition() - STEP_SIZE);

        } else if (gamepad2.x) {
            shoulderServo.setPosition(shoulderServo.getPosition() + STEP_SIZE);

        } else if (gamepad2.b) {
            shoulderServo.setPosition(shoulderServo.getPosition() + STEP_SIZE);
        }
        telemetry.update();
    }

}
