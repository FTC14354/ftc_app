package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public  class Teleop1 extends OpMode {
    private DcMotor frontleft, frontright, backleft, backright;



    @Override
    public void init() {

            frontleft.setPower(0);
            frontright.setPower(0);
            backright.setPower(0);
            backleft.setPower(0);
        }


    @Override
    public void loop() {
        if (gamepad1.left_stick_y > .1) {
            frontleft.setPower(.6);
            frontright.setPower(.6);
            backright.setPower(.6);
            backleft.setPower(.6);
        }
        if (gamepad1.left_stick_y < -.1) {
            frontleft.setPower(-.6);
            frontright.setPower(-.6);
            backright.setPower(-.6);
            backleft.setPower(-.6);
        }
        if (gamepad1.left_stick_x > .1) {
            frontleft.setPower(.6);
            frontright.setPower(-.6);
            backright.setPower(.6);
            backleft.setPower(-.6);
        }
        if (gamepad1.left_stick_x < -.1) {
            frontleft.setPower(-.6);
            frontright.setPower(.6);
            backleft.setPower(.6);
            backright.setPower(-.6);
        }
        if (gamepad1.left_stick_x > .1 && gamepad1.left_stick_y > .1) {
            frontleft.setPower(.6);
            backright.setPower(.6);
        }
        if (gamepad1.left_stick_x < -.1 && gamepad1.left_stick_y > .1) {
            frontright.setPower(.6);
            backleft.setPower(.6);
        }
        if (gamepad1.left_stick_x < -.1 && gamepad1.left_stick_y < -.1) {
            frontleft.setPower(-.6);
            backright.setPower(-.6);
        }
        if (gamepad1.left_stick_x > .1 && gamepad1.left_stick_y < -.1){
            frontright.setPower (-.6);
            backleft.setPower (-.6);
        }
        if (gamepad1.right_stick_x > .1){
            frontleft.setPower (.6);
            frontright.setPower (-.6);
            backleft.setPower (.6);
            backright.setPower (-.6);
        }
        if (gamepad1.right_stick_x < -.1){
            frontleft.setPower (-.6);
            frontright.setPower (.6);
            backleft.setPower (-.6);
            backright.setPower (.6);
        }
    }
}