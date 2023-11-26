package org.firstinspires.ftc.teamcode.components.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.vision.AprilVision
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

object DepositPixelBackdrop : LinearComponent {
    private const val TAG_ID = 1
    private const val DESIRED_DISTANCE = 12.0

    private const val MAX_AUTO_SPEED = 0.3
    private const val MAX_AUTO_STRAFE = 0.4
    private const val MAX_AUTO_TURN = 0.5

    override fun run(linearOpMode: LinearOpMode) = with(linearOpMode) {
        while (opModeIsActive()) {
            val tag = AprilVision.detect(TAG_ID)

            // Sleep for 0.1 seconds if no tag is found
            if (tag == null) {
                // TriWheels.stop()

                telemetry.addData("Status", "Tag not found")
                telemetry.update()

                sleep(100)
                continue
            }

            // Figure out how far we are from where we want to be
            val rangeError = tag.ftcPose.range - DESIRED_DISTANCE
            val headingError = tag.ftcPose.bearing
            val yawError = tag.ftcPose.yaw

            // Calculate how fast motors need to move
            val drive = Range.clip(
                rangeError * RobotConfig.DepositPixelBackdrop.SPEED_GAIN,
                -MAX_AUTO_SPEED,
                MAX_AUTO_SPEED
            )
            val turn = Range.clip(
                headingError * RobotConfig.DepositPixelBackdrop.TURN_GAIN,
                -MAX_AUTO_TURN,
                MAX_AUTO_TURN
            )
            val strafe = Range.clip(
                yawError * RobotConfig.DepositPixelBackdrop.STRAFE_GAIN,
                -MAX_AUTO_STRAFE,
                MAX_AUTO_STRAFE
            )

            // Log values
            with(telemetry) {
                addData("Status", "Tag found with id ${tag.id}")
                addData("Drive", drive)
                addData("Turn", turn)
                addData("Strafe", strafe)
                update()
            }

            val radians = atan2(drive, strafe) - (PI / 3.0)
            val magnitude = sqrt(drive * drive + strafe * strafe)

            TriWheels.driveWithRotation(radians, magnitude, turn)

            sleep(100)
        }
    }
}