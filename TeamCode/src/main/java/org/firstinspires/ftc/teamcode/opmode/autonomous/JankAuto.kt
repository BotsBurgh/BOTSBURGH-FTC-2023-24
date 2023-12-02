package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.Reset

abstract class JankAuto : LinearOpMode() {
    abstract val redSide: Boolean

    override fun runOpMode() {
        Reset.init(this)

        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        if (this.redSide) {
            Encoders.spinTo(90.0)
        } else {
            Encoders.spinTo(-90.0)
        }

        Encoders.driveTo(Encoders.Direction.Blue, 24.0)
    }
}

@Autonomous(name = "JankAuto - Red")
class JankAutoRed : JankAuto() {
    override val redSide = true
}

@Autonomous(name = "JankAuto - Red")
class JankAutoBlue : JankAuto() {
    override val redSide = false
}
