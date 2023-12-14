package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor

object LinearSlide : API() {

    private lateinit var hook: DcMotor
    private const val LINEAR_SLIDE_REDUCTION_SLOPE: Double = (0 - 0.8) / (4500 - 4250)

    private object linearSlideConfig {
        @JvmField
        var min = 0.0

        @JvmField
        var max = 4500
    }

    override fun init(opMode: OpMode) {

        super.init(opMode)

        this.hook = this.hardwareMap.get(DcMotor::class.java, "hook")
    }

    fun positionSlide(power: Double) {
        if (power > 0.0 && hook.currentPosition < linearSlideConfig.max) {
            if (hook.currentPosition > linearSlideConfig.max - 250) {
                hook.power =
                    LINEAR_SLIDE_REDUCTION_SLOPE * (hook.currentPosition - linearSlideConfig.max)
            } else {
                hook.power = power
            }
        } else if (power < 0.0 && hook.currentPosition > linearSlideConfig.min) {
            hook.power = power
        } else {
            hook.power = 0.0
        }
    }
}