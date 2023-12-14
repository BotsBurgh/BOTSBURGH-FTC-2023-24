package org.firstinspires.ftc.teamcode.api

import androidx.annotation.ColorInt
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Blinker

/**
 * An API for manipulating the light on the control and expansion hubs.
 */
object StatusLed : API() {
    private lateinit var blinkers: List<Blinker>

    override fun init(opMode: OpMode) {
        super.init(opMode)
        this.blinkers = this.opMode.hardwareMap.getAll(LynxModule::class.java)
    }

    /**
     * Sets the LED to a single color.
     *
     * ```
     * // Makes the color yellow
     * StatusLed.set(0xffff00)
     *
     * // Makes the color blue
     * StatusLed.set(0x0000ff)
     * ```
     */
    fun set(@ColorInt constant: Int) {
        this.blinkers.forEach { it.setConstant(this.overwriteAlpha(constant)) }
    }

    /** Overwrites the alpha property of a [ColorInt] to always be `0xff`. */
    private fun overwriteAlpha(@ColorInt color: Int) = color or (0xff000000u).toInt()
}
