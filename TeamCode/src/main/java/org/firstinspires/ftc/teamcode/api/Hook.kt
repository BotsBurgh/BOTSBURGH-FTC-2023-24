package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor

/** An API for controlling the hook, used to hang the robot. */
object Hook : API() {
    private lateinit var hanger: DcMotor

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.hanger = this.opMode.hardwareMap.get(DcMotor::class.java, "hanger")

        hanger.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun moveHook(power: Double) {
        // TODO: Add limits based on encoders
        hanger.power = power
    }

    fun stop() {
        hanger.power = 0.0
    }
}
