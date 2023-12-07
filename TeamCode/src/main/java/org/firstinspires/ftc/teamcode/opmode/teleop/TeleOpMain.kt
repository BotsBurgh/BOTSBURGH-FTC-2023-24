package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.GamepadEx
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.components.TeleOpMovement
import org.firstinspires.ftc.teamcode.utils.Reset

@TeleOp(name = "TeleOpMain")
class TeleOpMain : OpMode() {
    // init will run once
    override fun init() {
        // Resets property states of the robot
        Reset.init(this)

        // Setup special telemetry
        Telemetry.init(this)

        // Triangle wheel controls
        TriWheels.init(this)

        // Advanced gamepad inputs
        GamepadEx.init(this)

        // Log that we are initialized
        Telemetry.sayInitialized()
    }

    // loop will run repetitively overtime while the robot runs
    override fun loop() {
        // Movement controls
        TeleOpMovement.loop(this)

        // Log that we are running
        Telemetry.sayRunning()
    }
}
