package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.GamepadEx
import org.firstinspires.ftc.teamcode.api.TeleOpState
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.components.DPadCommands
import org.firstinspires.ftc.teamcode.components.TeleOpMovement

@TeleOp(name = "TeleOpMain")
class TeleOpMain : OpMode() {
    // init will run once
    override fun init() {
        // APIs (code that can be used in components)

        // Triangle wheel controls
        TriWheels.init(this)

        // State machine that toggles on and off functionality
        TeleOpState.init(this)

        // Advanced gamepad inputs
        GamepadEx.init(this)
    }

    // loop will run repetitively overtime while the robot runs
    override fun loop() {
        // Components

        // Allows changing state through the DPad
        // This should be run before TeleOpMovement
        DPadCommands.loop(this)

        // Default movement controls
        TeleOpMovement.loop(this)
    }
}
