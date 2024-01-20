package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

/**
 * An API for controlling the linear slide(s).
 */
object LinearSlide : API() {
    // Technically not the slides themselves, but instead a motor connected to both.
    // For abstraction's sake, it's fine to treat it as the slide itself.
    lateinit var slide: DcMotor
        private set

    private const val MAX_POS: Int = 3000
    private const val MIN_POS: Int = 100
    private const val SLOWDOWN_POS: Int = 2500

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.slide = this.opMode.hardwareMap.get(DcMotor::class.java, "slide")

        this.slide.direction = DcMotorSimple.Direction.REVERSE
        this.slide.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        this.resetEncoders()
    }

    /**
     * Sets the power of the linear slides.
     *
     * This function will automatically adjust the power to prevent the slide from moving past its
     * pre-configured minimum and maximums. It will also slow down the slide as it reaches its
     * maximum, but it will not slow down as it reaches its minimum.
     *
     * **Please take care not to move the linear slide down too quickly.**
     */
    fun power(power: Double) {
        this.slide.power =
            if (power > 0 && this.slide.currentPosition < this.MAX_POS) {
                // If going upward and current position is less than maximum.
                power *
                    if (this.slide.currentPosition > this.SLOWDOWN_POS) {
                        // Multiply power by a decimal between 0 and 1 based on how close the slide
                        // is to the max. This slows down the slide the closer it is to the maximum
                        // position, smoothing its motion. This only gets applied if the current
                        // position is between MAX_POS and SLOWDOWN_POS.
                        ((this.MAX_POS - this.slide.currentPosition) / (this.MAX_POS - this.SLOWDOWN_POS)).toDouble()
                    } else {
                        // Else use identity function, don't affect speed.
                        1.0
                    }
            } else if (power < 0 && this.slide.currentPosition > this.MIN_POS) {
                // If going downward and current position is greater than minimum.
                power
            } else {
                // Else default to no movement.
                0.0
            }
    }

    /**
     * Stops the linear slide from moving.
     *
     * The linear slide is configured to brake when stopped. This will hold it in place, though
     * results may vary depending on how much force is exerted on it.
     *
     * Prefer this function to `LinearSlide.power(0.0)`, since this bypasses some logic.
     */
    fun stop() {
        this.slide.power = 0.0
    }

    /**
     * Resets the value of the linear slide encoders.
     *
     * This is an unsafe function that should only be called when the slide is guaranteed to be in
     * resting position. Logic in [power] depends on the encoder position of 0 being resting
     * position. Failure to follow this may wear down the slide faster at the least, and snap the
     * bands at the worst.
     */
    private fun resetEncoders() {
        this.slide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        this.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }
}
