package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.abs
import kotlin.math.min

/**
 * An API for manipulating wheels using encoders.
 *
 * This helps when writing autonomous that needs to move a specific distance, or turn a specific
 * amount.
 *
 * Requires the [TriWheels] API.
 *
 * @see driveTo
 * @see spinTo
 */
object Encoders : LinearAPI() {
    private fun inchesToTick(inches: Double): Int =
        (RobotConfig.Encoders.TICKS_PER_INCH * inches).toInt()

    private fun degreesToTick(degrees: Double): Int =
        (RobotConfig.Encoders.TICKS_PER_DEGREE * degrees).toInt()

    /**
     * Drives the robot in a given [direction] a certain amount of [inches].
     *
     * This function will take full control of the robot, and has a few side-effects. All wheel
     * motors will be reset with [TriWheels.stopAndResetMotors]. Furthermore, this function will not
     * return until the robot has finished moving.
     *
     * Due to current restrictions, [inches] cannot be a negative number.
     */
    fun driveTo(direction: Direction, inches: Double) {
        TriWheels.stopAndResetMotors()

        val (left, right, _) = this.defineWheels(direction)
        val ticks = this.inchesToTick(inches)

        val leftTarget = left.currentPosition + ticks
        val rightTarget = right.currentPosition + ticks

        // Set left direction to reverse, undoing it at the end even if an exception is thrown
        try {
            right.direction = DcMotorSimple.Direction.REVERSE

            val runtime = ElapsedTime()

            while (
                abs(left.currentPosition - leftTarget) > RobotConfig.Encoders.ENCODER_ERROR &&
                abs(right.currentPosition - rightTarget) > RobotConfig.Encoders.ENCODER_ERROR &&
                linearOpMode.opModeIsActive()
            ) {
                // Accelerate the longer the robot has been running
                val timeSpeed = runtime.seconds() * RobotConfig.Encoders.TIME_GAIN

                left.power =
                    min(
                        min(
                            abs(left.currentPosition - leftTarget) * RobotConfig.Encoders.ENCODER_GAIN,
                            timeSpeed
                        ), RobotConfig.Encoders.MAX_DRIVE_SPEED
                    )
                right.power =
                    min(
                        min(
                            abs(right.currentPosition - rightTarget) * RobotConfig.Encoders.ENCODER_GAIN,
                            timeSpeed
                        ), RobotConfig.Encoders.MAX_DRIVE_SPEED
                    )

                with(linearOpMode.telemetry) {
                    addData("Status", "Encoder Driving")

                    addData("Left Power", left.power)
                    addData("Right Power", right.power)

                    addData("Left Target", leftTarget)
                    addData("Right Target", rightTarget)

                    addData("Left Current", left.currentPosition)
                    addData("Right Current", right.currentPosition)

                    update()
                }
            }
        } finally {
            // This reset encoders but also changes the right motor direction back to forward
            TriWheels.stopAndResetMotors()
        }
    }

    /**
     * Spins the robot a certain number of [degrees].
     *
     * Like [driveTo], this function will not return until the robot has finished moving. It will
     * also reset all wheel motors' configuration, including rotation and encoders.
     *
     * Unlike [driveTo], [degrees] can be a negative or positive number.
     */
    fun spinTo(degrees: Double) {
        TriWheels.stopAndResetMotors()

        val ticks = this.degreesToTick(degrees)

        TriWheels.red.mode = DcMotor.RunMode.RUN_TO_POSITION
        TriWheels.green.mode = DcMotor.RunMode.RUN_TO_POSITION
        TriWheels.blue.mode = DcMotor.RunMode.RUN_TO_POSITION

        TriWheels.red.targetPosition = ticks
        TriWheels.green.targetPosition = ticks
        TriWheels.blue.targetPosition = ticks

        try {
            val runtime = ElapsedTime()

            while (
                TriWheels.red.isBusy &&
                TriWheels.green.isBusy &&
                TriWheels.blue.isBusy &&
                linearOpMode.opModeIsActive()
            ) {
                // Accelerate the longer the robot has been running
                val timeSpeed = runtime.seconds() * RobotConfig.Encoders.TIME_GAIN

                TriWheels.rotate(
                    min(
                        min(
                            abs(TriWheels.red.currentPosition - TriWheels.red.targetPosition) * RobotConfig.Encoders.ENCODER_GAIN,
                            timeSpeed,
                        ),
                        RobotConfig.Encoders.MAX_SPIN_SPEED
                    )
                )

                with(linearOpMode.telemetry) {
                    addData("Status", "Encoder Rotating")

                    addData(
                        "Power",
                        Triple(TriWheels.red.power, TriWheels.green.power, TriWheels.blue.power)
                    )
                    addData(
                        "Current",
                        Triple(
                            TriWheels.red.currentPosition,
                            TriWheels.green.currentPosition,
                            TriWheels.blue.currentPosition
                        )
                    )
                    addData("Target", ticks)

                    update()
                }
            }
        } finally {
            TriWheels.stopAndResetMotors()
        }
    }

    /**
     * A function used by [driveTo] to figure out which two wheels are in the front, and which is
     * behind.
     *
     * The returned [Triple] is in the format `Triple<Left, Right, Back>`.
     */
    private fun defineWheels(direction: Direction): Triple<DcMotor, DcMotor, DcMotor> =
        when (direction) {
            Direction.Red -> Triple(TriWheels.blue, TriWheels.green, TriWheels.red)
            Direction.Green -> Triple(TriWheels.red, TriWheels.blue, TriWheels.green)
            Direction.Blue -> Triple(TriWheels.green, TriWheels.red, TriWheels.blue)
        }

    /**
     * An enum representing which axis a robot will drive along in [driveTo].
     *
     * The colors correspond to the wheel names.
     */
    enum class Direction {
        Red,
        Green,
        Blue,
    }
}