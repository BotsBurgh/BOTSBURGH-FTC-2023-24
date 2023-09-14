package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.Encoders
import kotlin.math.PI

object EncoderAutonomousTest: Component {
    override fun loop(opMode: OpMode){
        Encoders.moveDirection(PI, 24.0, 1.0)
        Encoders.moveDegree(180.0, 1.0)
        Encoders.moveDegree(180.0, -1.0)
        Encoders.moveDirection(PI, 24.0, -1.0)

    }
}