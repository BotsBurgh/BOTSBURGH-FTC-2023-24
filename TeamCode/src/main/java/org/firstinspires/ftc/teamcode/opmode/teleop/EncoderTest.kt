package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.api.Encoders
import org.firstinspires.ftc.teamcode.components.EncoderAutonomousTest


@Autonomous(name = "Encoder Test")
class EncoderTest: OpMode() {
    override fun init() {
        // Initializes an API, shared code used by components.
        Encoders.init(this)
    }

    override fun loop() {
        EncoderAutonomousTest.loop(this)
    }
}