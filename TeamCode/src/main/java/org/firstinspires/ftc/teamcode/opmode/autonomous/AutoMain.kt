package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Hook
import org.firstinspires.ftc.teamcode.api.PixelPlacer
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.AprilMovement
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.api.vision.AprilVision
import org.firstinspires.ftc.teamcode.api.vision.AprilVision.optimizeForAprilTags
import org.firstinspires.ftc.teamcode.api.vision.CubeVision
import org.firstinspires.ftc.teamcode.api.vision.Vision
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.Team
import kotlin.math.PI

abstract class AutoMain : LinearOpMode() {
    abstract val team: Team

    /** The axis the robot should move along. */
    private val forward = Encoders.Direction.Red

    /** The length and width of a tile in inches. */
    private val tileSize = 24.0

    override fun runOpMode() {
        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Hook.init(this)
        PixelPlacer.init(this)

        AprilVision.init(this)
        AprilMovement.init(this)

        CubeVision.init(this, team)
        Vision.init(this, CubeVision, AprilVision)

        Telemetry.sayInitialized()

        while (opModeInInit()) {
            telemetry.addData("Cube", CubeVision.output)
            telemetry.update()

            sleep(100)
        }

        Telemetry.sayStarted()

        val cubePosition = CubeVision.output
        CubeVision.disable()

        sleep(RobotConfig.AutoMain.WAIT_TIME)

        when (cubePosition) {
            CubeVision.CubePlacement.Left -> {
                // Spin first, then drive to backdrop, the spin to face april tag
                Encoders.spinTo2(pickTeam(45.0, -45.0))
                Encoders.driveTo2(forward, 36.0)
                Encoders.spinTo2(pickTeam(30.0, -30.0))
            }
            CubeVision.CubePlacement.Center -> {
                // Drive to spike tile and turn
                Encoders.driveTo2(forward, tiles(1) + 6.0)
                Encoders.spinTo2(pickTeam(90.0, -90.0))

                // Drive forward a bit so april tags are visible
                Encoders.driveTo2(forward, tiles(0.7))
            }
            CubeVision.CubePlacement.Right -> {
                // Spin first, then drive to backdrop
                Encoders.spinTo2(pickTeam(65.0, -65.0))

                Encoders.driveTo2(forward, 24.0)
            }
        }

        // Move hook to upright position
        Hook.moveHook(0.5)
        sleep(400)
        Hook.stop()

        // Drive to april tag
        Vision.optimizeForAprilTags()
        AprilMovement.driveTo(cubePosition.toInt() + pickTeam(3, 0), 5.0)
        Vision.close()

        // Slam against backboard, getting as close as possible
        TriWheels.drive(PI / 2.0, 0.4)
        sleep(1000)
        TriWheels.stop()

        // Let robot come to a stop
        sleep(500)

        // Place pixel
        PixelPlacer.place()
        sleep(1000)
        PixelPlacer.reset()

        // Back up a bit
        Encoders.driveTo2(forward, tiles(-0.3))

        // Spin and park
        if (cubePosition == CubeVision.CubePlacement.Right) {
            Encoders.spinTo2(pickTeam(75.0, -75.0))
        } else {
            Encoders.spinTo2(pickTeam(90.0, -90.0))
        }

        Encoders.driveTo2(
            forward,
            when (cubePosition) {
                CubeVision.CubePlacement.Left -> tiles(-0.5)
                CubeVision.CubePlacement.Center -> tiles(1)
                CubeVision.CubePlacement.Right -> tiles(0.75)
            },
        )

        // Put hook back in resting position
        Hook.moveHook(-0.5)
        sleep(400)
        Hook.stop()
    }

    /** Converts an amount of tiles to inches. */
    private fun tiles(amount: Double) = amount * tileSize

    /** Converts an amount of tiles to inches. */
    private fun tiles(amount: Int) = tiles(amount.toDouble())

    /**
     * A utility that returns the value correlating to the configured team.
     *
     * It is a lot easier to understand by reading the code than explaining it here.
     */
    private fun <T> pickTeam(
        red: T,
        blue: T,
    ): T =
        when (team) {
            Team.Red -> red
            Team.Blue -> blue
        }
}

@Autonomous(name = "AutoMain - Red")
class AutoMainRed : AutoMain() {
    override val team = Team.Red
}

@Autonomous(name = "AutoMain - Blue")
class AutoMainBlue : AutoMain() {
    override val team = Team.Blue
}
