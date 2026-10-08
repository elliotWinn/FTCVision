package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class JosephineDrive extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
                drive(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);

            }
        }
    }

    private void drive(double forward, double strafe, double rotation) {
        frontLeft.setPower((forward + rotation) - strafe);
        frontRight.setPower((forward - rotation) + strafe);
        backRight.setPower((forward - rotation) - strafe);
        backLeft.setPower((forward + rotation) + strafe);
    }

}
