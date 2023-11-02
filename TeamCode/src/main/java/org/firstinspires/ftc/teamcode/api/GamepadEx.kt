package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.api.GamepadEx.justPressed
import org.firstinspires.ftc.teamcode.api.GamepadEx.justReleased
import org.firstinspires.ftc.teamcode.api.GamepadEx.pressed
import org.firstinspires.ftc.teamcode.api.GamepadEx.update
import org.firstinspires.ftc.teamcode.utils.Resettable

/**
 * A [Gamepad] extension for more complex inputs.
 *
 * The biggest function of this API is checking whether an input was pressed or released this
 * update. See [pressed], [justPressed], and [justReleased].
 *
 * Beyond initialization, please call [update] every loop to use this API correctly.
 *
 * This class is inspired by the [`bevy_input` system](https://docs.rs/bevy_input/latest/bevy_input/struct.Input.html).
 */
object GamepadEx : API() {
    /** A list of all inputs being tracked by [GamepadEx]. */
    enum class Inputs {
        DPadUp {
            override fun pressed() = gamepad.dpad_up
        },
        DPadDown {
            override fun pressed() = gamepad.dpad_down
        },
        DPadLeft {
            override fun pressed() = gamepad.dpad_left
        },
        DPadRight {
            override fun pressed() = gamepad.dpad_right
        },
        RightBumper {
            override fun pressed() = gamepad.right_bumper
        };

        /** Returns true if button is pressed. */
        abstract fun pressed(): Boolean
    }

    // Internal reference to the current gamepad
    private val gamepad: Gamepad
        get() = this.opMode.gamepad1

    private val pressed: MutableSet<Inputs> by Resettable { mutableSetOf() }
    private val justPressed: MutableSet<Inputs> by Resettable { mutableSetOf() }
    private val justReleased: MutableSet<Inputs> by Resettable { mutableSetOf() }

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
