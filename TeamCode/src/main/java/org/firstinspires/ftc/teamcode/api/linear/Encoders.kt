package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.api.TriWheels
import kotlin.math.abs

/**
 * Requires the [TriWheels] API.
 */
object Encoders : LinearAPI() {
    private const val TICKS_PER_INCH: Double = 39.0
    private const val TICKS_PER_DEGREE: Double = 6.5

    private const val ENCODER_GAIN: Double = 0.0004
    private const val ENCODER_ERROR: Int = 10

    private fun inchesToTick(inches: Double): Int = (this.TICKS_PER_INCH * inches).toInt()
    private fun degreesToTick(degrees: Double): Int = (this.TICKS_PER_DEGREE * degrees).toInt()

    fun driveTo(direction: Direction, inches: Double) {
        TriWheels.stopAndResetEncoders()

        val (left, right, _) = this.defineWheels(direction)
        val ticks = this.inchesToTick(inches)

        val leftTarget = left.currentPosition + ticks
        val rightTarget = right.currentPosition + ticks

        // Set left direction to reverse, undoing it at the end even if an exception is thrown
        try {
            right.direction = DcMotorSimple.Direction.REVERSE

            while (
                abs(left.currentPosition - leftTarget) > this.ENCODER_ERROR &&
                abs(right.currentPosition - rightTarget) > this.ENCODER_ERROR &&
                linearOpMode.opModeIsActive()
            ) {
                left.power = abs(left.currentPosition - leftTarget) * this.ENCODER_GAIN
                right.power = abs(right.currentPosition - rightTarget) * this.ENCODER_GAIN

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
            right.direction = DcMotorSimple.Direction.FORWARD
            TriWheels.stopAndResetEncoders()
        }
    }

    fun spinTo(degrees: Double) {
        TriWheels.stopAndResetEncoders()

        val ticks = this.degreesToTick(degrees)

        TriWheels.red.mode = DcMotor.RunMode.RUN_TO_POSITION
        TriWheels.green.mode = DcMotor.RunMode.RUN_TO_POSITION
        TriWheels.blue.mode = DcMotor.RunMode.RUN_TO_POSITION

        TriWheels.red.targetPosition = ticks
        TriWheels.green.targetPosition = ticks
        TriWheels.blue.targetPosition = ticks

        while (
            TriWheels.red.isBusy &&
            TriWheels.green.isBusy &&
            TriWheels.blue.isBusy &&
            linearOpMode.opModeIsActive()
        ) {
            TriWheels.rotate(abs(TriWheels.red.currentPosition - TriWheels.red.targetPosition) * this.ENCODER_GAIN)

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

        TriWheels.stopAndResetEncoders()
    }

    private fun defineWheels(direction: Direction): Triple<DcMotor, DcMotor, DcMotor> =
        when (direction) {
            Direction.Red -> Triple(TriWheels.blue, TriWheels.green, TriWheels.red)
            Direction.Green -> Triple(TriWheels.red, TriWheels.blue, TriWheels.green)
            Direction.Blue -> Triple(TriWheels.green, TriWheels.red, TriWheels.blue)
        }

    enum class Direction {
        Red,
        Green,
        Blue,
    }
}
