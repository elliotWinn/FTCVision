//package org.firstinspires.ftc.teamcode;
//
//import android.util.Size;
//import org.firstinspires.ftc.vision.apriltag.AprilTagMetadata;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import java.util.List;
//import org.firstinspires.ftc.robotcore.external.JavaUtil;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.Position;
//import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
//import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//@TeleOp(name = "FollowCamera")
//public class FollowCamera extends LinearOpMode {
//
//    private DcMotor frontLeft;
//    private DcMotor backLeft;
//    private DcMotor frontRight;
//    private DcMotor backRight;
//
//    List<AprilTagDetection> myAprilTagDetections;
//    AprilTagProcessor myAprilTagProcessor;
//    AprilTagDetection myAprilTagDetection;
//
//    @Override
//    public void runOpMode() {
//        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
//        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
//        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
//        backRight = hardwareMap.get(DcMotor.class, "backRight");
//
//        // Initialize AprilTag before waitForStart.
//        initAprilTag();
//        telemetry.update();
//        waitForStart();
//
//        if (opModeIsActive()) {
//            while (opModeIsActive()) {
//                telemetryAprilTag();
//                telemetry.update();
//                sleep(20);
//            }
//        }
//    }
//
//    private void followAprilTag(
//            int targetId,
//            double targetX, double targetY) {
//        double errorX;
//        double errorY;
//        int gain;
//
//        myAprilTagDetections = myAprilTagProcessor.getDetections();
//        errorX = 0;
//        errorY = 0;
//        gain = 2;
//        for (AprilTagDetection myAprilTagDetection_item : myAprilTagDetections) {
//            myAprilTagDetection = myAprilTagDetection_item;
//            if (myAprilTagDetection.id == targetId) {
//                errorX = targetX - myAprilTagDetection.center.x;
//                errorY = targetY - myAprilTagDetection.center.y;
//            }
//        }
//        drive((errorY / -800) * gain, 0, (errorX / -1280) * gain);
//    }
//
//    /**
//     * Initialize AprilTag Detection.
//     */
//    private void initAprilTag() {
//            AprilTagProcessor.Builder myAprilTagProcessorBuilder;
//        VisionPortal.Builder myVisionPortalBuilder;
//        AprilTagLibrary myAprilTagLibrary;
//        VisionPortal myVisionPortal;
//
//        AprilTagMetadata tag39 = new AprilTagMetadata(39, "Tag39", 140.0, DistanceUnit.MM);
//        AprilTagLibrary tagLibrary = new AprilTagLibrary.Builder().addTag(tag39).build();
//
//        myAprilTagProcessorBuilder = new AprilTagProcessor.Builder();
//        myAprilTagProcessorBuilder.setDrawAxes(true);
//        myAprilTagProcessorBuilder.setDrawTagOutline(true);
//        myAprilTagProcessorBuilder.setDrawTagID(true);
//        myAprilTagProcessorBuilder.setDrawCubeProjection(true);
//        myAprilTagProcessorBuilder.setCameraPose(new Position(DistanceUnit.MM, 100, 140, 0, System.nanoTime()), new YawPitchRollAngles(AngleUnit.DEGREES, 0, -90, 0, System.nanoTime()));
//        myAprilTagProcessorBuilder.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11);
//        myAprilTagProcessorBuilder.setOutputUnits(DistanceUnit.MM, AngleUnit.DEGREES);
//        myAprilTagProcessorBuilder.setLensIntrinsics(908.758, 908.758, 696.345, 376.979);
//        myAprilTagProcessorBuilder.setTagLibrary(tagLibrary);
//        myAprilTagProcessor = myAprilTagProcessorBuilder.build();
//
//        myVisionPortalBuilder = new VisionPortal.Builder();
//        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
//        myVisionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.MJPEG);
//        myVisionPortalBuilder.setCameraResolution(new Size(1280, 800));
//        myVisionPortalBuilder.enableLiveView(true);
//        myVisionPortalBuilder.addProcessor(myAprilTagProcessor);
//        myVisionPortal = myVisionPortalBuilder.build();
//    }
//
//    /**
//     * Describe this function...
//     */
//    private void drive(double forward, double turn, double strafe) {
//        telemetry.addData("forward: ", forward);
//        telemetry.addData("turn: ", turn);
//        telemetry.addData("strafe", strafe);
//        telemetry.addLine("");
//        frontLeft.setPower(-(forward + turn + strafe));
//        backLeft.setPower(-((forward + turn) - strafe));
//        frontRight.setPower((forward - turn) - strafe);
//        backRight.setPower((forward - turn) + strafe);
//    }
//
//    /**
//     * Display info (using telemetry) for a recognized AprilTag.
//     */
//    private void telemetryAprilTag() {
//        // Get a list of AprilTag detections.
//        myAprilTagDetections = myAprilTagProcessor.getDetections();
//        telemetry.addData("# AprilTags Detected", JavaUtil.listLength(myAprilTagDetections));
//        // Iterate through list and call a function to display info for each recognized AprilTag.
//        for (AprilTagDetection myAprilTagDetection_item2 : myAprilTagDetections) {
//            myAprilTagDetection = myAprilTagDetection_item2;
//            // Display info about the detection.
//            telemetry.addLine("");
//            if (myAprilTagDetection.metadata != null) {
//                telemetry.addLine("==== (ID " + myAprilTagDetection.id + ") " + myAprilTagDetection.metadata.name);
//                telemetry.addLine("XYZ " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.x, 6, 1) + " " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.y, 6, 1) + " " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.z, 6, 1) + "  (mm)");
//                telemetry.addLine("PRY " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.pitch, 6, 1) + " " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.roll, 6, 1) + " " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.yaw, 6, 1) + "  (deg)");
//                telemetry.addLine("RBE " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.range, 6, 1) + " " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.bearing, 6, 1) + " " + JavaUtil.formatNumber(myAprilTagDetection.ftcPose.elevation, 6, 1) + "  (mm, deg, deg)");
//            } else {
//                telemetry.addLine("==== (ID " + myAprilTagDetection.id + ") Unknown");
//                telemetry.addLine("Center " + JavaUtil.formatNumber(myAprilTagDetection.center.x, 6, 0) + "" + JavaUtil.formatNumber(myAprilTagDetection.center.y, 6, 0) + " (pixels)");
//            }
//        }
//        telemetry.addLine("");
//        telemetry.addLine("key:");
//        telemetry.addLine("XYZ = X (Right), Y (Forward), Z (Up) dist.");
//        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
//        telemetry.addLine("RBE = Range, Bearing & Elevation");
//    }
//}
