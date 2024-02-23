package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

/** An API for controlling the hook, used to hang the robot. */
object Hook : API() {
    lateinit var hook: DcMotor
        private set

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.hook = this.opMode.hardwareMap.get(DcMotor::class.java, "hook")

        hook.direction = DcMotorSimple.Direction.REVERSE
        hook.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        hook.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun moveHook(power: Double) {
        // TODO: Add limits based on encoders
        hook.power = power
    }

    fun stop() {
        hook.power = 0.0
    }
}
