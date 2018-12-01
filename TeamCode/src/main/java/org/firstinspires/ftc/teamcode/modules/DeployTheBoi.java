package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class DeployTheBoi {
    private HardwareMap hardwareMap;
    private Servo deploymentServo = null;
    private File file;

    public DeployTheBoi(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        deploymentServo = hardwareMap.get(Servo.class, "boiDeployer");
        String filename = "sweepLog.log";
        file = AppUtil.getInstance().getSettingsFile(filename);
    }

    public void doTheThing() {
        deploymentServo.setPosition(.8);
    }

    public void stopDoingThing() {
        deploymentServo.setPosition(0);
    }

    public void sweep() {
        double pos = deploymentServo.getPosition() + .05;
        if (pos > 1) {
            pos=0;
        }

        deploymentServo.setPosition(pos);
    }

    public void logPosition() {
        ReadWriteFile.writeFile(file, "Arm position: " + Double.toString(deploymentServo.getPosition()) + "\n");
    }
}
