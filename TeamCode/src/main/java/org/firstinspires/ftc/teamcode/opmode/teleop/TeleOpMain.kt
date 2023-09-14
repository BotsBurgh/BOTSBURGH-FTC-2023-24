package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.components.TeleOpMovement

@TeleOp(name = "TeleOpMain")
class TeleOpMain : OpMode() {
    // init will run once
    override fun init() {
        // the api (code that can be used in components)
        // initialization of motors
        TriWheels.init(this)
    }
    // loop will run repetitively overtime while the robot runs
    override fun loop() {
        // the component
        TeleOpMovement.init(this)
    }
}