package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagMetadata;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "FollowCameraNew")
public class FollowCameraNew extends LinearOpMode {
    //TODO: define motors, sensors, processors here
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        initVision();

        double forward = 0;
        double turn = 0;
        double strafe = 0;

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here
                forward = -gamepad1.left_stick_y;
                turn = gamepad1.right_stick_x;
                strafe = gamepad1.left_stick_x;

                drive(forward, turn, strafe);

                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
                sleep(20);
            }
        }
    }

    private void drive(double forward, double turn, double strafe) {
        this.frontLeft.setPower(-(forward + turn + strafe));
        this.backLeft.setPower(-((forward + turn) - strafe));
        this.frontRight.setPower((forward - turn) - strafe);
        this.backRight.setPower((forward - turn) + strafe);
    }

    private void initVision() {
        AprilTagMetadata tag39 = new AprilTagMetadata(39, "Tag39", 140, DistanceUnit.MM);
        AprilTagLibrary aprilTagLibrary = AprilTagGameDatabase.getCenterStageTagLibrary();

        this.aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.MM, AngleUnit.DEGREES)
                .setCameraPose(
                        new Position(DistanceUnit.MM, 100, 140, 0, System.nanoTime()),
                        new YawPitchRollAngles(AngleUnit.DEGREES, 0, -90, 0, System.nanoTime())
                        )
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(aprilTagLibrary)
                .build();

        this.visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .addProcessor(this.aprilTagProcessor)
                .build();
    }

    private List<AprilTagDetection> handleAprilTagDetections() {
        ArrayList<AprilTagDetection> aprilTagDetections = this.aprilTagProcessor.getDetections();
        for (AprilTagDetection detection : aprilTagDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                // single tag
                AprilTagSingleDetection singleDetection = (AprilTagSingleDetection) detection;
                telemetry.addData("Single: ", singleDetection);
            } else {
                // tag cluster
                AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;
                telemetry.addData("Cluster: ", clusterDetection.metadata.name);
            }
        }
        return aprilTagDetections;
    }

}