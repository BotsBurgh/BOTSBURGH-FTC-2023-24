package org.firstinspires.ftc.teamcode.api.vision

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.vision.VisionProcessor
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor

object AprilVision : API(), VisionAPI {
    // How external code accesses the april tag processor
    override val processor: VisionProcessor
        get() = this.aprilTag

    // How this file accesses the april tag processor
    private lateinit var aprilTag: AprilTagProcessor

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.aprilTag = AprilTagProcessor.Builder()
            // Configure april tag processor here
            .build()
    }
}
