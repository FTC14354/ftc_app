//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//public class ServoIntake implements Intake {
//    private CRServo intakeServo;
//    private Servo elbowServo;
//    private Servo shoulderServo;
//    private final Integer ELBOW_IN = 0;
//    private final Integer ELBOW_OUT = 0;
//    private final Integer SHOULDER_IN = 0;
//    private final Integer SHOULDER_OUT = 0;
//    private final Integer SHOULDER_STOP = 0;
//    private final Integer ELBOW_STOP = 0;
//
//
//    public ServoIntake(HardwareMap hardwareMap) {
//        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
//        elbowServo = hardwareMap.get(Servo.class, "elbowServo");
//        shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
//    }
//
//    @Override
//    public void purge() {
//        intakeServo.setDirection(CRServo.Direction.FORWARD);
//    }
//
//    @Override
//    public void binge() {
//        intakeServo.setDirection(CRServo.Direction.REVERSE);
//    }
//
//    @Override
//    public void stop() {
//        intakeServo.setDirection(CRServo.Direction.FORWARD);
//        elbowServo.setPosition(ELBOW_STOP);
//        shoulderServo.setPosition(SHOULDER_STOP);
//    }
//
//    @Override
//    public void extend() {
//        elbowServo.setPosition(ELBOW_OUT);
//        shoulderServo.setPosition(SHOULDER_OUT);
//    }
//
//    @Override
//    public void retract() {
//        elbowServo.setPosition(ELBOW_IN);
//        shoulderServo.setPosition(SHOULDER_IN);
//
//    }
//}
