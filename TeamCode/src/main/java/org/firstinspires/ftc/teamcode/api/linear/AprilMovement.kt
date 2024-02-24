package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.vision.AprilVision
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * An API for driving related to april tags.
 *
 * Requires the [TriWheels] and [AprilVision] APIs.
 */
object AprilMovement : API() {
    override val isLinear = true

    // The max speed for individual components of the movement
    private const val MAX_RANGE = 0.3
    private const val MAX_HEADING = 0.5
    private const val MAX_STRAFE = 0.4

    /**
     * Drives in front of the given april tag [tagId], facing squarely in front of it
     * [desiredDistance] inches away.
     */
    fun driveTo(
        tagId: Int,
        desiredDistance: Double = 12.0,
    ) = with(linearOpMode) {
        // Initialize errors with a really large number so while loop runs at least once
        // These are placeholders that get overridden in the loop
        var rangeError = Double.MAX_VALUE
        var headingError = Double.MAX_VALUE
        var strafeError = Double.MAX_VALUE

        // Drive while opmode is active and target has not been reached
        while (
            opModeIsActive() &&
            (
                abs(rangeError) > RobotConfig.AprilMovement.RANGE_ERROR ||
                    abs(headingError) > RobotConfig.AprilMovement.HEADING_ERROR ||
                    abs(strafeError) > RobotConfig.AprilMovement.STRAFE_ERROR ||
                    abs(TriWheels.red.power) > 0.03 ||
                    abs(TriWheels.green.power) > 0.03 ||
                    abs(TriWheels.blue.power) > 0.03
            )
        ) {
            // Try scanning for the april tag
            val tag = AprilVision.detect(tagId)

            // If no tag of the expected ID is found
            if (tag == null) {
                // Stop moving, hoping that the tag will be scanned
                TriWheels.stop()

                with(telemetry) {
                    addData("Status", "Tag not found")
                    update()
                }

                sleep(100)

                continue
            }

            // Calculate how far we are from the target position
            rangeError = tag.ftcPose.range - desiredDistance
            headingError = tag.ftcPose.bearing
            strafeError = tag.ftcPose.yaw

            // Calculate the wheel power from the error
            val range =
                Range.clip(
                    rangeError * RobotConfig.AprilMovement.RANGE_GAIN,
                    -MAX_RANGE,
                    MAX_RANGE,
                )
            val heading =
                Range.clip(
                    headingError * RobotConfig.AprilMovement.HEADING_GAIN,
                    -MAX_HEADING,
                    MAX_HEADING,
                )
            val strafe =
                Range.clip(
                    strafeError * RobotConfig.AprilMovement.STRAFE_GAIN,
                    -MAX_STRAFE,
                    MAX_STRAFE,
                )

            // Treat range and strafe as an XY vector
            // Then convert to the polar coordinate system (angle, length)
            // This is the same math used to
            val radians = atan2(range, strafe)
            val magnitude = sqrt(range * range + strafe * strafe)

            // Set the power of the wheels
            TriWheels.driveWithRotation(radians, magnitude, heading)

            // Sleep for 0.1s before repeating again
            sleep(100)
        }

        // Stop the wheels from moving, the target has been reached!
        TriWheels.stop()
    }

    override fun dependencies() = setOf(TriWheels, AprilVision)
}
