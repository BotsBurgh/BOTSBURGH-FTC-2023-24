package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
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
    companion object {
        private val pid = PIDCoefficients(0.0, 0.0, 0.0)
        private const val POWER_LIMIT = 0.5
        private const val I_LIMIT = 1.0
    }

    private val runtime = ElapsedTime()

    private var previousTime = 0.0
    private var previousError = Int.MAX_VALUE

    private var i = 0.0

    fun update() {
        val currentTime = runtime.milliseconds()
        val currentError = target - motor.currentPosition

        val p = pid.p * currentError

        i += pid.i * currentError * (currentTime - previousTime)

        // Clamp between [-MAX_I, MAX_I]
        i = max(min(i, I_LIMIT), -I_LIMIT)

        val d = pid.d * (currentError - previousError) / (currentTime - previousTime)

        motor.power = max(min(p + i + d, POWER_LIMIT), -POWER_LIMIT)

        previousTime = currentTime
        previousError = currentError
    }

    fun isDone() = previousError == 0
}
