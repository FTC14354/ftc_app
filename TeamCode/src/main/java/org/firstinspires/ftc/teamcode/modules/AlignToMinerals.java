package org.firstinspires.ftc.teamcode.modules;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FourWheelDriveStyle;

public class AlignToMinerals {
    private FourWheelDriveStyle driving;
    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private GoldAlignDetector detector;

    public void nameThing() {
        detector = new GoldAlignDetector();
        detector.enable();
        driving = new FourWheelDriveStyle(hardwareMap,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive");



    }

    public void otherNameThing() {
        telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("X Pos", detector.getXPosition());
    }
}