package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.RobotConfig

@Autonomous(name = "Auto Forward")
class AutoForward : LinearOpMode() {
    override fun runOpMode() {
        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        Encoders.driveTo(
            when (RobotConfig.model) {
                RobotConfig.Model.RobotA -> Encoders.Direction.Blue
                RobotConfig.Model.RobotB -> Encoders.Direction.Red
            },
            20.0,
        )
    }
}
