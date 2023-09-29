package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.api.GamepadEx.justPressed
import org.firstinspires.ftc.teamcode.api.GamepadEx.justReleased
import org.firstinspires.ftc.teamcode.api.GamepadEx.pressed
import org.firstinspires.ftc.teamcode.api.GamepadEx.update

/**
 * A [Gamepad] extension for more complex inputs.
 *
 * The biggest function of this API is checking whether an input was pressed or released this
 * update. See [pressed], [justPressed], and [justReleased].
 *
 * Beyond initialization, please call [update] every loop to use this API correctly.
 */
object GamepadEx : API() {
    /** A list of all inputs being tracked by [GamepadEx]. */
    enum class Inputs {
        DPadUp {
            override fun pressed() = GamepadEx.gamepad.dpad_up
        },
        DPadDown {
            override fun pressed() = GamepadEx.gamepad.dpad_down
        },
        DPadLeft {
            override fun pressed() = GamepadEx.gamepad.dpad_left
        },
        DPadRight {
            override fun pressed() = GamepadEx.gamepad.dpad_right
        };

        /** Returns true if button is pressed. */
        abstract fun pressed(): Boolean
    }

    // Internal reference to the current gamepad
    private val gamepad: Gamepad
        get() = this.opMode.gamepad1

    private val pressed = mutableSetOf<Inputs>()
    private val justPressed = mutableSetOf<Inputs>()
    private val justReleased = mutableSetOf<Inputs>()

    /** Returns true if input it pressed. */
    fun pressed(input: Inputs): Boolean = input in this.pressed

    /** Returns true if input was pressed this update. */
    fun justPressed(input: Inputs): Boolean = input in this.justPressed

    /** Returns true if input was released this update. */
    fun justReleased(input: Inputs): Boolean = input in this.justReleased

    /** Updates [pressed], [justPressed], and [justReleased] to match current input. */
    fun update() {
        // Reset justPressed and justReleased
        this.clear()

        // Check every possible input if it is pressed or not
        for (input in Inputs.values()) {
            if (input.pressed()) {
                // Add to pressed set, which returns true if it did not exist
                if (this.pressed.add(input)) {
                    this.justPressed.add(input)
                }
            } else {
                // Remove from pressed set, which returns true if it existed
                if (this.pressed.remove(input)) {
                    this.justReleased.add(input)
                }
            }
        }
    }

    /** Removes all values from [justPressed] and [justReleased]. */
    private fun clear() {
        this.justPressed.clear()
        this.justReleased.clear()
    }
}
