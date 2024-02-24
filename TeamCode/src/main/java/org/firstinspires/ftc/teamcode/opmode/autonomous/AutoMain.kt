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

        // Drive to spike tile and turn
        Encoders.driveTo2(forward, tiles(1) + 6.0)
        Encoders.spinTo2(pickTeam(90.0, -90.0))

        // Move hook to upright position
        Hook.moveHook(0.5)
        sleep(400)
        Hook.stop()

        // Drive forward a bit so april tags are visible
        Encoders.driveTo2(forward, tiles(0.7))

        // Drive to april tag
        Vision.optimizeForAprilTags()
        AprilMovement.driveTo(cubePosition.toInt() + pickTeam(3, 0), 4.0)
        Vision.close()

        // Slam against backboard, getting as close as possible
        TriWheels.drive(PI / 2.0, 0.4)
        sleep(500)
        TriWheels.stop()

        // Place pixel
        PixelPlacer.place()
        sleep(1000)
        PixelPlacer.reset()

        // Back up a bit
        Encoders.driveTo2(forward, tiles(-0.3))

        // Spin and park
        Encoders.spinTo2(pickTeam(90.0, -90.0))
        Encoders.driveTo2(
            forward,
            tiles(1) +
                when (cubePosition) {
                    CubeVision.CubePlacement.Left -> 6.0
                    CubeVision.CubePlacement.Center -> 0.0
                    CubeVision.CubePlacement.Right -> -6.0
                },
        )
        // Encoders.driveTo2(pickTeam(Encoders.Direction.Blue, Encoders.Direction.Green), tiles(0.3))

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
