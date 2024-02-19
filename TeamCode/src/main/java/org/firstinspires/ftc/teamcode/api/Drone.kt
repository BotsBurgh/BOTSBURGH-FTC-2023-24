package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

/**
 * An API to control the drone launcher.
 */
object Drone : API() {
    private lateinit var droneLeft: DcMotor
    private lateinit var droneRight: DcMotor

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.droneLeft = this.opMode.hardwareMap.get(DcMotor::class.java, "droneLeft")
        this.droneRight = this.opMode.hardwareMap.get(DcMotor::class.java, "droneRight")

        this.droneRight.direction = DcMotorSimple.Direction.REVERSE
    }

    fun spin() {
        this.droneLeft.power = 1.0
        this.droneRight.power = 1.0
    }

    fun stop() {
        this.droneLeft.power = 0.0
        this.droneRight.power = 0.0
    }
}
