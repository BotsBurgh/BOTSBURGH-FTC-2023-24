package org.firstinspires.ftc.teamcode.api.linear

import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.MotorController
import org.firstinspires.ftc.teamcode.utils.RobotConfig

/** An API similar to [Encoders] but it allows traveling in any direction. */
object Omnicoders : API() {
    override val isLinear = true

    override fun dependencies() = setOf(TriWheels)

    fun driveTo(
        radians: Double,
        inches: Double,
    ) {
        TriWheels.stopAndResetMotors()

        // Convert from inches to ticks.
        val magnitude = inches * RobotConfig.Omnicoders.TICKS_PER_INCH

        // Calculate the distance each wheel needs to travel.
        val (redTarget, greenTarget, blueTarget) = TriWheels.wheelRatio(radians, magnitude)

        // Create the motor controllers for each wheel.
        val (redController, greenController, blueController) =
            Triple(
                MotorController(redTarget.toInt(), RobotConfig.Omnicoders.PID, TriWheels.red),
                MotorController(greenTarget.toInt(), RobotConfig.Omnicoders.PID, TriWheels.green),
                MotorController(blueTarget.toInt(), RobotConfig.Omnicoders.PID, TriWheels.blue),
            )

        try {
            // Repeat while `opModeIsActive()` and at least one controller is running.
            while (!(redController.isDone() && greenController.isDone() && blueController.isDone()) && linearOpMode.opModeIsActive()) {
                redController.update()
                greenController.update()
                blueController.update()

                with(linearOpMode.telemetry) {
                    addData("Status", "Omnicoder Driving")

                    // TODO: Log position, target, and power of each motor.

                    update()
                }
            }
        } finally {
            TriWheels.stopAndResetMotors()
        }
    }
}
