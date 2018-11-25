package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class DeployTheBoi {
    private HardwareMap hardwareMap;
    private Servo deploymentServo = null;
    public DeployTheBoi (HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        deploymentServo = hardwareMap.get(Servo.class, "boiDeployer");
    }
    public void doTheThing (){
//        for(double pos = 0; pos <=1; pos+=.01) {
            deploymentServo.setPosition(.2);
  //      }
    }
    public void stopDoingThing (){
        deploymentServo.setPosition(.5);
    }
}
