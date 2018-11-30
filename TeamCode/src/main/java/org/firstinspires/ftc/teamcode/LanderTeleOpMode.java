package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "LanderTeleOpMode", group = "TeleOp opmode")
@Disabled
public class LanderTeleOpMode extends OpMode
{
    private DcMotor liftMotor = null;

    @Override
    public void init(){
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            liftMotor.setPower(0.5);
        } else if (gamepad1.y) {
            liftMotor.setPower(-0.5);
        } else {
            liftMotor.setPower(0);
        }

    }
}
