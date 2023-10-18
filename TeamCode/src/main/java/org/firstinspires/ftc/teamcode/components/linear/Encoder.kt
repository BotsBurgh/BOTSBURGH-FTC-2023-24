package org.firstinspires.ftc.teamcode.api


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.components.linear.LinearComponent
import kotlin.math.PI

object Encoder : LinearComponent {
    private lateinit var wheel1: DcMotor
    private lateinit var wheel2: DcMotor
    private lateinit var wheel3: DcMotor

    private const val tickPerInch: Double = 39.0
    private const val tickPerDegree: Double = 6.5

    private var wheelFinalDistance1: Double = 0.0
    private var wheelFinalDistance2: Double = 0.0
    private var wheelFinalDistance3: Double = 0.0

    private var tick: Double = 0.0

    override fun run(linearOpMode: LinearOpMode) {
        moveDirection(linearOpMode, PI / 4, 24.0, 0.25)
        moveDegree(linearOpMode, 90.0, -0.25)
        moveDegree(linearOpMode, 90.0, -0.25)
        moveDirection(linearOpMode, PI / 4, 24.0, 0.25)
        moveDegree(linearOpMode, 90.0, -0.25)
        moveDirection(linearOpMode, PI / 4, 22.0, 0.25)
        moveDegree(linearOpMode, 90.0, -0.25)
        moveDirection(linearOpMode, PI / 4, 22.0, 0.25)
        moveDegree(linearOpMode, 90.0, 0.25)
        moveDirection(linearOpMode, PI / 4, 58.0, 0.25)
        moveDegree(linearOpMode, 180.0, -0.25)
    }

    /**
     * Converts inputted inches into ticks
     */
    private fun inchToTick(inches: Double) {
        tick = tickPerInch * inches
    }

    /**
     * Converts inputted degrees into ticks
     */
    private fun degreeToTick(degree: Double) {
        tick = tickPerDegree * degree
    }

    /**
     * Moves the robot using encoders.
     * The three valid fronts are PI (camera), 7 * PI / 4 (Main Slide), and PI / 4 (Secondary Slide)
     */
    fun moveDirection(linearOpMode: LinearOpMode, front: Double, inches: Double, power: Double) {
        wheelDefine(front)
        TriWheels.stopAndResetEncoders()

        if (power > 0) {
            inchToTick(inches)
            wheelFinalDistance1 = wheel1.currentPosition - tick
            wheelFinalDistance2 = wheel2.currentPosition - tick
        } else if (power < 0) {
            inchToTick(inches)
            wheelFinalDistance1 = wheel1.currentPosition + tick
            wheelFinalDistance2 = wheel2.currentPosition + tick
        }

        wheelFinalDistance3 = 0.0

        wheel1.targetPosition = wheelFinalDistance1.toInt()
        wheel2.targetPosition = wheelFinalDistance2.toInt()
        wheel3.targetPosition = wheelFinalDistance3.toInt()

        if (power > 0) {
            while (wheel1.currentPosition >= wheelFinalDistance1 || wheel2.currentPosition >= wheelFinalDistance2 /*&& linearOpMode.opModeIsActive()*/) {

                /*linearOpMode.telemetry.addData("Wheel One Current", wheel1.currentPosition)
                linearOpMode.telemetry.addData("Wheel One Final", wheelFinalDistance1)
                linearOpMode.telemetry.addData("Wheel Two Current", wheel2.currentPosition)
                linearOpMode.telemetry.addData("Wheel Two Final", wheelFinalDistance2)
                linearOpMode.telemetry.addData("Wheel Three Current", wheel3.currentPosition)
                linearOpMode.telemetry.addData("Wheel Three Final", wheelFinalDistance3)
                linearOpMode.telemetry.update()*/
                if (wheel1.currentPosition >= wheelFinalDistance1) {
                    wheel1.power = -power
                }
                if (wheel2.currentPosition >= wheelFinalDistance2) {
                    wheel2.power = -power
                }

            }

            TriWheels.stopAndResetEncoders()

        } else if (power < 0) {
            while (wheel1.currentPosition >= wheelFinalDistance1 || wheel2.currentPosition >= wheelFinalDistance2 /*&& linearOpMode.opModeIsActive()*/) {
                /*linearOpMode.telemetry.addData("Wheel One Current", wheel1.currentPosition)
                linearOpMode.telemetry.addData("Wheel One Final", wheelFinalDistance1)
                linearOpMode.telemetry.addData("Wheel Two Current", wheel2.currentPosition)
                linearOpMode.telemetry.addData("Wheel Two Final", wheelFinalDistance2)
                linearOpMode.telemetry.addData("Wheel Three Current", wheel3.currentPosition)
                linearOpMode.telemetry.addData("Wheel Three Final", wheelFinalDistance3)
                linearOpMode.telemetry.update()*/

                if (wheel1.currentPosition >= wheelFinalDistance1) {
                    wheel1.power = power
                }

                if (wheel2.currentPosition >= wheelFinalDistance2) {
                    wheel2.power = power
                }

            }

            TriWheels.stopAndResetEncoders()


        }


    }

    fun moveDegree(linearOpMode: LinearOpMode, degrees: Double, power: Double) {
        wheel1 = TriWheels.red
        wheel2 = TriWheels.blue
        wheel3 = TriWheels.green
        wheel1.direction = DcMotorSimple.Direction.FORWARD
        wheel2.direction = DcMotorSimple.Direction.FORWARD
        wheel3.direction = DcMotorSimple.Direction.FORWARD
        //TODO("Add Sleep Support")
        linearOpMode.sleep(1000)

        degreeToTick(degrees)

        if (power > 0) {
            wheelFinalDistance1 = wheel1.currentPosition + tick
            wheelFinalDistance2 = wheel2.currentPosition + tick
            wheelFinalDistance3 = wheel3.currentPosition + tick

            while (wheel1.currentPosition < wheelFinalDistance1 /*&& linearOpMode.opModeIsActive()*/) {
                TriWheels.power(power)
            }

            TriWheels.stop()
            linearOpMode.sleep(1000)

            /*while (wheel1.currentPosition > wheelFinalDistance1 /*&& linearOpMode.opModeIsActive()*/) {
                TriWheels.power(-0.1)
            }

            TriWheels.stop()
            linearOpMode.sleep(1000)
*/
        } else if (power < 0) {
            wheelFinalDistance1 = wheel1.currentPosition - tick
            wheelFinalDistance2 = wheel2.currentPosition - tick
            wheelFinalDistance3 = wheel3.currentPosition - tick

            while (wheel1.currentPosition >= wheelFinalDistance1 /*&& linearOpMode.opModeIsActive()*/) {
                TriWheels.power(power)
            }

            TriWheels.stop()
            linearOpMode.sleep(1000)

            /*          while (wheel1.currentPosition <= wheelFinalDistance1 && linearOpMode.opModeIsActive()) {
                          TriWheels.power(0.1)
                      }
                      TriWheels.stop()
                      linearOpMode.sleep(1000)
                  */
        }
    }

    private fun wheelDefine(front: Double) {
        when (front) {
            PI -> {
                //Camera
                wheel1 = TriWheels.blue
                wheel2 = TriWheels.green
                wheel3 = TriWheels.red
                wheel1.direction = DcMotorSimple.Direction.REVERSE
                wheel1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
                wheel2.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
                wheel3.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

            }

            7 * PI / 4 -> {
                //Main Slide
                wheel1 = TriWheels.green
                wheel2 = TriWheels.red
                wheel3 = TriWheels.blue
                wheel1.direction = DcMotorSimple.Direction.REVERSE
                wheel1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
                wheel2.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
                wheel3.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

            }

            PI / 4 -> {
                //Secondary Slide
                wheel1 = TriWheels.red
                wheel2 = TriWheels.blue
                wheel3 = TriWheels.green
                wheel1.direction = DcMotorSimple.Direction.REVERSE
                wheel1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
                wheel2.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
                wheel3.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

            }
        }
    }
}