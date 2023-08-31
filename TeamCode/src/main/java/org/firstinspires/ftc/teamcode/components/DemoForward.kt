package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.DemoQuadWheels

// Components are pieces of code that make the robot do something.
// This one makes the robot go forward when A is pressed.
// Note how it is an object and inherits `Component`.
object DemoForward : Component {
    override fun loop(opMode: OpMode) {
        if (opMode.gamepad1.a) {
            DemoQuadWheels.drive(1.0)
        } else {
            DemoQuadWheels.drive(0.0)
        }
    }
}
