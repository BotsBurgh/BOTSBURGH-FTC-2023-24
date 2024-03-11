@file:Suppress("DEPRECATION")

package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Box
import org.firstinspires.ftc.teamcode.api.LinearSlide
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.auto.Team

abstract class JankAuto : LinearOpMode() {
    abstract val team: Team

    private val forward = Encoders.Direction.Blue

    final override fun runOpMode() {
        Telemetry.init(this)

        TriWheels.init(this)
        Encoders.init(this)

        LinearSlide.init(this)
        Box.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        // Move in front of backdrop
        Encoders.driveTo(forward, tiles(1))
        Encoders.spinTo(pickTeam(90.0, -90.0))
        Encoders.driveTo(forward, tiles(1.5))

        sleep(1000)

        // Drop off pre-loaded pixels
        Box.pickUpBox()

        while (LinearSlide.slide.currentPosition < 2000) {
            LinearSlide.power(0.6)
            sleep(50)
        }

        LinearSlide.stop()
        Box.gripIn()

        sleep(1000)

        while (LinearSlide.slide.currentPosition > 50) {
            LinearSlide.power(-0.4)
            sleep(50)
        }

        LinearSlide.stop()

        sleep(1000)

        // Park in right side
        Encoders.spinTo(pickTeam(90.0, -90.0))
        Encoders.driveTo(forward, tiles(1))
        Encoders.spinTo(pickTeam(-90.0, 90.0))
        Encoders.driveTo(forward, tiles(0.5))

        with(telemetry) {
            addData("Status", "Finished")
            update()
        }
    }

    private fun tiles(tiles: Int) = tiles(tiles.toDouble())

    private fun tiles(tiles: Double) = tiles * 24.0

    private fun <T> pickTeam(
        red: T,
        blue: T,
    ): T =
        when (team) {
            Team.Red -> red
            Team.Blue -> blue
        }
}

@Autonomous(name = "JankAuto - Red", group = "Jank")
class JankAutoRed : JankAuto() {
    override val team = Team.Red
}

@Autonomous(name = "JankAuto - Blue", group = "Jank")
class JankAutoBlue : JankAuto() {
    override val team = Team.Red
}
