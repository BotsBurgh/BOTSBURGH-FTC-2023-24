package org.firstinspires.ftc.teamcode.components.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.vision.AprilVision
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

object DepositPixelBackdrop : LinearComponent {
    private const val DESIRED_DISTANCE = 12.0

    private const val MAX_AUTO_SPEED = 0.3
    private const val MAX_AUTO_STRAFE = 0.4
    private const val MAX_AUTO_TURN = 0.5

    fun run(linearOpMode: LinearOpMode, tagID: Int) = with(linearOpMode) {
        var rangeError = 9999.0
        var headingError = 9999.0
        var yawError = 9999.0

        while ((
                    abs(rangeError) > RobotConfig.DepositPixelBackdrop.RANGE_ERROR ||
                            abs(headingError) > RobotConfig.DepositPixelBackdrop.HEADING_YAW_ERROR ||
                            abs(yawError) > RobotConfig.DepositPixelBackdrop.HEADING_YAW_ERROR) &&
            opModeIsActive()
        ) {
            val tag = AprilVision.detect(tagID)

            // Sleep for 0.1 seconds if no tag is found
            if (tag == null) {
                TriWheels.stop()

                telemetry.addData("Status", "Tag not found")
                telemetry.update()

                sleep(100)
                continue
            }

            // Figure out how far we are from where we want to be
            rangeError = tag.ftcPose.range - DESIRED_DISTANCE
            headingError = tag.ftcPose.bearing
            yawError = tag.ftcPose.yaw

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
                addData("Range", rangeError)
                addData("Heading", headingError)
                addData("Yaw", yawError)
                update()
            }

            val radians = atan2(drive, strafe)
            val magnitude = sqrt(drive * drive + strafe * strafe)

            TriWheels.driveWithRotation(radians, magnitude, turn)

            sleep(100)
        }

        TriWheels.stop()
    }

    override fun run(linearOpMode: LinearOpMode) {
        throw RuntimeException("Please call DepositPixelBackdrop.run with a tag ID.")
    }
}
