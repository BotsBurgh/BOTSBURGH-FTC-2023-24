package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.utils.RobotConfig

/**
 * An API for controlling the linearSlide.
 */
object LinearSlide : API() {
    private lateinit var hook: DcMotor
    private const val REDUCTION_SLOPE: Double = (0 - 0.8) / (4500 - 4250)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.hook = this.hardwareMap.get(DcMotor::class.java, "hook")
    }

    /**
     * The movement of the linearSlide
     */
    fun positionSlide(power: Double) {
        // checks if the power is positive
        if (power > 0.0 && hook.currentPosition < RobotConfig.LinearSlide.MAX) {
            // when close to the max of linear slide slow down using reduction slope
            if (hook.currentPosition > RobotConfig.LinearSlide.MAX - 250) {
                hook.power =
                    REDUCTION_SLOPE * (hook.currentPosition - RobotConfig.LinearSlide.MAX)
            } else {
                hook.power = power
            }
            // checks if the power is negative
        } else if (power < 0.0 && hook.currentPosition > RobotConfig.LinearSlide.MIN) {
            hook.power = power
            // sets power to stop if the power is zero
        } else {
            hook.power = 0.0
        }
    }
}
