package org.firstinspires.ftc.teamcode.opmode.teleop

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.Reset

@TeleOp(name = "Encoder Test")
class EncoderTest : LinearOpMode() {
    @Config
    object Test {
        @JvmField
        var DISTANCE: Double = 24.0
    }

    override fun runOpMode() {
        Reset.init(this)

        TriWheels.init(this)
        Encoders.init(this)

        waitForStart()

        Encoders.driveTo(Encoders.Direction.Blue, Test.DISTANCE)
    }
}
