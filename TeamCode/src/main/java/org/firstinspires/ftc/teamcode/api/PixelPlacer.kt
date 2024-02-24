package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.utils.RobotConfig

object PixelPlacer : API() {
    private lateinit var pixelPlacer: Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.pixelPlacer = this.opMode.hardwareMap.get(Servo::class.java, "pixelPlacer")

        this.reset()
    }

    fun place() {
        pixelPlacer.position = RobotConfig.PixelPlacer.PLACE_POSITION
    }

    fun reset() {
        pixelPlacer.position = RobotConfig.PixelPlacer.DEFAULT_POSITION
    }
}
