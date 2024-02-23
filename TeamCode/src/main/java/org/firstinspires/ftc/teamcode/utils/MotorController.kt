package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class MotorControllerGroup(
    target: Int,
    vararg motors: DcMotor,
) {
    private val controllers = motors.map { MotorController(target, it) }

    fun update() {
        for (controller in controllers) {
            controller.update()
        }
    }

    fun isDone() = controllers.all { it.isDone() }
}

class MotorController(
    private val target: Int,
    private val motor: DcMotor,
) {
    private val runtime = ElapsedTime()

    private var previousTime = 0.0
    private var previousError = Int.MAX_VALUE

    private var i = 0.0

    fun update() {
        val currentTime = runtime.milliseconds()
        val currentError = target - motor.currentPosition

        val p = RobotConfig.MotorController.pid.p * currentError

        i += RobotConfig.MotorController.pid.i * currentError * (currentTime - previousTime)

        // Clamp between [-MAX_I, MAX_I]
        i = max(min(i, RobotConfig.MotorController.iLimit), -RobotConfig.MotorController.iLimit)

        val d = RobotConfig.MotorController.pid.d * (currentError - previousError) / (currentTime - previousTime)

        motor.power = max(min(p + i + d, RobotConfig.MotorController.powerLimit), -RobotConfig.MotorController.powerLimit)

        previousTime = currentTime
        previousError = currentError
    }

    fun isDone() = abs(previousError) < 5
}
