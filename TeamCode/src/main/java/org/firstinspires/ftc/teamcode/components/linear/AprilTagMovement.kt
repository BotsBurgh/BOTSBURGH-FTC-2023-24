package org.firstinspires.ftc.teamcode.components.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.vision.AprilVision
import kotlin.math.abs

/**
 * A linear component for moving the robot based on april tags.
 *
 * Requires [AprilVision], Vision, and [TriWheels] APIs.
 */
object AprilTagMovement : LinearComponent {
    /** A multiplier of how much the robot should turn versus how much it needs to. */
    private const val TURN_GAIN: Double = 0.01

    /** The max power that the wheel can turn. */
    private const val TURN_MAX: Double = 0.25

    /** How close the robot needs to be to the desired turn to stop moving. */
    private const val TURN_ERROR: Double = 0.2

    override fun run(linearOpMode: LinearOpMode) {
        throw NotImplementedError("Do not run AprilTagMovement. Instead")
    }

    /**
     * Points the robot towards the given april tag.
     *
     * [tagId] is the ID of the april tag that the robot should point towards. This function returns
     * a boolean of whether it was successful rotating the robot. For instance, it will return false
     * if it cannot find the april tag specified.
     */
    fun pointTowards(linearOpMode: LinearOpMode, tagId: Int): Boolean {
        // Detect april tag, returning false if unsuccessful
        val aprilTag = AprilVision.detect(tagId) ?: return false

        // Create new telemetry section
        val telemetryLine = linearOpMode.telemetry.addLine("April Tag Movement")

        while (linearOpMode.opModeIsActive()) {
            // Check how much the robot needs to turn
            val headingError = aprilTag.ftcPose.bearing

            // Break out of the loop if robot turned enough
            if (abs(headingError) <= TURN_ERROR) {
                break
            }

            // Calculate the amount of power applied based on the heading error
            val wheelPower = Range.clip(headingError * TURN_GAIN, -TURN_MAX, TURN_MAX)

            // Rotate the robot with given power
            TriWheels.power(wheelPower)

            // Log heading and power
            telemetryLine.addData("Heading Error", headingError)
            telemetryLine.addData("Wheel Power", wheelPower)

            // Sleep for 0.01 seconds
            linearOpMode.sleep(10)
        }

        // Stop the wheels
        TriWheels.stop()

        // Remove telemetry section
        linearOpMode.telemetry.removeLine(telemetryLine)

        return true
    }
}
