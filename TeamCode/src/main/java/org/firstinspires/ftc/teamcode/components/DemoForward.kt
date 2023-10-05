package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.DemoQuadWheels

/**
 * A component that makes the robot go forward when A is pressed.
 *
 * It requires the [DemoQuadWheels] API.
 */
object DemoForward : Component {
    override fun loop(opMode: OpMode) {
        if (opMode.gamepad1.a) {
            DemoQuadWheels.drive(1.0)
        } else {
            DemoQuadWheels.drive(0.0)
        }
    }
}
