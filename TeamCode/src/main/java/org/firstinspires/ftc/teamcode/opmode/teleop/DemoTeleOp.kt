package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.DemoQuadWheels
import org.firstinspires.ftc.teamcode.components.DemoForward

@TeleOp(name = "Demo")
class DemoTeleOp : OpMode() {
    // Run once, when "Init" is pressed on driver hub.
    override fun init() {
        // Initializes an API, shared code used by components.
        DemoQuadWheels.init(this)
    }

    // Run repeatedly while the robot is running.
    override fun loop() {
        // Runs the component.
        DemoForward.loop(this)
    }
}
