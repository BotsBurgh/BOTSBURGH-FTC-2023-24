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
    fun set(@ColorInt constant: Int) =
        this.blinkers.forEach { it.setConstant(constant) }

    /**
     * Sets the LED to a pattern.
     *
     * [pattern] should be a sequence of [Blinker.Step]s, preferably created using [listOf].
     *
     * ```
     * // Cycle through red, green, then blue every second.
     * StatusLed.set(listOf(
     *     Step(0xff0000, 1, TimeUnit.SECONDS),
     *     Step(0x00ff00, 1, TimeUnit.SECONDS),
     *     Step(0x0000ff, 1, TimeUnit.SECONDS),
     * ))
     * ```
     */
    fun set(pattern: Collection<Blinker.Step>) = this.blinkers.forEach { it.pattern = pattern }

    /**
     * Turns the light off.
     *
     * This is equivalent to setting the color to black, or `0x000000`.
     */
    fun turnOff() = this.blinkers.forEach { it.stopBlinking() }

    /**
     * Resets the current pattern to the default one.
     *
     * It is recommended to call this at the end of an opmode.
     */
    fun reset() = this.blinkers.forEach {
        // SAFETY: Blinker is guaranteed to be LynxModule because [init] requests only LynxModules
        // from the hardware map.
        it.pattern = LynxModule.blinkerPolicy.getIdlePattern(it as LynxModule)
    }
}
