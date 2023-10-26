package org.firstinspires.ftc.teamcode.components.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.DemoQuadWheels

/**
 * A demo linear component that spins the robot for 2 seconds, then stops.
 *
 * It requires the [DemoQuadWheels] API.
 */
object DemoSpinLinear : LinearComponent {
    override fun run(opMode: LinearOpMode) {
        DemoQuadWheels.drive(1.0, -1.0, 1.0, -1.0)

        opMode.sleep(2 * 1000)

        DemoQuadWheels.drive(0.0)
    }
}
