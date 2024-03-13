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
import org.firstinspires.ftc.teamcode.utils.auto.Position
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.auto.Park
import org.firstinspires.ftc.teamcode.utils.auto.Team
import org.firstinspires.ftc.teamcode.utils.auto.Truss
import kotlin.math.PI

abstract class AutoMain : LinearOpMode() {
    abstract val team: Team
    private var position = Position.Back
    private var park = Park.Right
    private var truss = Truss.Big

    /** The axis the robot should move along. */
    private val forward = Encoders.Direction.Red
    private val left = Encoders.Direction.Left
    private val right = Encoders.Direction.Right

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
            telemetry.addLine("Front Stage: A / Back Stage :B")
            telemetry.addLine("Park Left: Left Dpad / Park Center: Up Dpad / Park Right: Right Dpad")
            telemetry.addLine("Truss: X / Gate: Y")
            telemetry.addData("Position", position)
            telemetry.addData("Park", park)
            telemetry.addData("Truss", truss)


            //change starting position using a/b
            if (gamepad1.a) {
                position = Position.Front
            } else if (gamepad1.b) {
                position = Position.Back
            }

            //change parking position with dpad
            if (gamepad1.dpad_left) {
                park = Park.Left
            } else if (gamepad1.dpad_up) {
                park = Park.Center
            } else if (gamepad1.dpad_right) {
                park = Park.Right
            }

            //change truss to drive through with x/y
            if (gamepad1.x) {
                truss = Truss.Small
            } else if (gamepad1.y) {
                truss = Truss.Big
            }

            telemetry.update()

            sleep(100)
        }

        Telemetry.sayStarted()

        val cubePosition = CubeVision.output
        CubeVision.disable()

        sleep(RobotConfig.AutoMain.WAIT_TIME)

        // Drive to spike tile and position in-front of back drop
        when (cubePosition) {
            CubeVision.CubePlacement.Left -> {
                when (position) {
                    Position.Back -> {
                        when (team) {
                            Team.Red -> {
                                //Back Stage Red Left
                                Hook.moveHook(0.5)
                                sleep(400)
                                Hook.stop()
                                Encoders.driveTo2(forward, tiles(1) + 4.0)
                                //turn towards spike
                                Encoders.spinTo2(pickTeam(-90.0, 90.0))
                                Encoders.driveTo(forward, 10.0)
                                Hook.moveHook(-0.5)
                                sleep(400)
                                Hook.stop()
                                //back up and face backdrop
                                Encoders.driveTo2(forward, tiles(-1))
                                sleep(250)
                                Encoders.spinTo(180.0)
                            }

                            Team.Blue -> {
                                //Back Stage Blue Left
                                Encoders.driveTo2(forward, tiles(1) + 4.0)
                                //turn towards spike
                                Encoders.spinTo( -90.0)
                                Encoders.driveTo(forward, 10.0)
                                Encoders.driveTo(forward, -3.0)
                                Encoders.strafeTo(left, 10.0)
                                Encoders.driveTo2(forward, tiles(1))
                                Encoders.strafeTo(right, 10.0)
                            }
                        }
                    }

                    Position.Front -> {
                        when (team) {
                            Team.Red -> {
                                //Front Stage Red Left
                                Encoders.driveTo2(forward, tiles(1) + 8.0)
                                //turn towards pike
                                Encoders.spinTo2(pickTeam(-95.0, 95.0))
                                Encoders.driveTo(forward, 8.0)
                                Hook.moveHook(-0.5)
                                sleep(400)
                                Hook.stop()
                                //back up and face backdrop
                                Encoders.driveTo(forward, -tiles(3))
                                sleep(250)
                                Encoders.spinTo2(180.0)
                            }

                            Team.Blue -> {
                                //Front Stage Blue Left
                            }
                        }
                    }
                }
            }

            CubeVision.CubePlacement.Center -> {

                Encoders.spinTo(pickTeam(10.0, -10.0))
                Encoders.driveTo(forward, tiles(1.75))
                Encoders.driveTo(forward, -2.0)
                sleep(250)
                Encoders.spinTo(pickTeam(90.0, -90.0))

                when (position) {
                    Position.Back -> {
                        //Back Stage movement
                        Encoders.driveTo(forward, tiles(1))
                    }

                    Position.Front -> {
                        //Front Stage movement
                        Encoders.driveTo(forward, tiles(3))
                    }
                }

            }

            CubeVision.CubePlacement.Right -> {
                when (position) {
                    Position.Back -> {
                        when (team) {
                            Team.Red -> {
                                //Back Stage Right Red
                            }

                            Team.Blue -> {
                                //Back Stage Right Blue
                            }
                        }
                    }

                    Position.Front -> {
                        when (team) {
                            Team.Red -> {
                                //Front Stage Right Red
                                Encoders.driveTo(forward, 2.0)
                                sleep(250)
                                Encoders.strafeTo(pickTeam(right, left), tiles(0.5))
                                sleep(250)
                                //drive towards spike
                                Encoders.driveTo(forward, tiles(1))
                                Encoders.driveTo(forward, -2.0)
                                //strafe towards backdrop
                                Encoders.strafeTo(pickTeam(right, left), tiles(0.5))
                                //turn towards backdrop
                                Encoders.spinTo(pickTeam(90.0, -90.0))
                            }

                            Team.Blue -> {
                                //Front Stage Right Blue
                                Encoders.driveTo2(forward, tiles(1) + 8.0)
                                //turn towards pike
                                Encoders.spinTo2(pickTeam(-95.0, 95.0))
                                Encoders.driveTo(forward, 8.0)
                                Hook.moveHook(-0.5)
                                sleep(400)
                                Hook.stop()
                                //back up and face backdrop
                                Encoders.driveTo(forward, -tiles(3))
                                sleep(250)
                                Encoders.spinTo2(180.0)
                            }
                        }
                    }
                }





                Encoders.driveTo2(forward, tiles(1) + 8.0)
                //turn towards spike
                Encoders.spinTo2(pickTeam(-95.0, 95.0))
                Encoders.driveTo(forward, 8.0)
                Hook.moveHook(-0.5)
                sleep(400)
                Hook.stop()
                //back up and face backdrop
                Encoders.driveTo(forward, -tiles(3))
                sleep(250)
                Encoders.spinTo2(180.0)


            }
        }



        Hook.moveHook(0.5)
        sleep(400)
        Hook.stop()

        // Drive to april tag
        Vision.optimizeForAprilTags()
        AprilMovement.driveTo(cubePosition.toInt() + pickTeam(3, 0), 5.0)
        Vision.close()

        // Slam against backboard, getting as close as possible
        TriWheels.drive(PI / 2.0, 0.4)
        sleep(500)
        TriWheels.stop()

        Encoders.strafeTo(right, 1.0)

        // Place pixel
        PixelPlacer.place()
        sleep(1000)
        PixelPlacer.reset()

        // Back up a bit
        Encoders.driveTo(forward, tiles(-0.1))
        sleep(250)

        // Spin and park
        Encoders.strafeTo(Encoders.Direction.Right, 24.0)
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

    private fun <P> pickPosition(
        front: P,
        back: P,
    ): P =
        when (position) {
            Position.Front -> front
            Position.Back -> back
        }

    private fun <L> pickPark(
        left: L,
        center: L,
        right: L,
    ): L =
        when (park) {
            Park.Left -> left
            Park.Center -> center
            Park.Right -> right
        }

    private fun <R> pickTruss(
        big: R,
        small: R,
    ): R =
        when (truss) {
            Truss.Big -> big
            Truss.Small -> small
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
