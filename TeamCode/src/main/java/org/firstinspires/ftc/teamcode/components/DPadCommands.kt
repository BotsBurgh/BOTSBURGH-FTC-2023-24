package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.GamepadEx
import org.firstinspires.ftc.teamcode.api.TeleOpState

/**
 * A component that changes the state of the robot based on DPad inputs.
 *
 * This requires the [GamepadEx] and [TeleOpState] APIs.
 *
 * Pressing DPad down will reset the group selection. There are no other groups yet.
 */
object DPadCommands : Component {
    private enum class CommandGroup {}

    private var selected_group: CommandGroup? = null

    override fun loop(opMode: OpMode) {
        GamepadEx.update()

        // Reset selection on DPad down
        if (GamepadEx.justPressed(GamepadEx.Inputs.DPadDown)) {
            this.selected_group = null
        }

        // Log selected group
        opMode.telemetry.addData("Command Group", this.selected_group)
    }
}
