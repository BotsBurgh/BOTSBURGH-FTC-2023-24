package org.firstinspires.ftc.teamcode.components

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.components.Telemetry.init
import org.firstinspires.ftc.teamcode.components.Telemetry.loop

/**
 * A simple component that logs the status on the driver station.
 *
 * Both [init] and [loop] should be run last, because they flush all updates to the driver station.
 * [init] is also explicitly there to say that everything has been initialized.
 *
 * [init] can also work during autonomous, but [loop] can only be run during teleop.
 */
object Telemetry : Component {
    override fun init(opMode: OpMode) {
        opMode.telemetry = MultipleTelemetry(opMode.telemetry, FtcDashboard.getInstance().telemetry)

        opMode.telemetry.addData("Status", "Initialized")
        opMode.telemetry.update()
    }

    override fun loop(opMode: OpMode) {
        opMode.telemetry.addData("Status", "Running")
        opMode.telemetry.update()
    }
}
