package org.firstinspires.ftc.teamcode.api.vision

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.vision.VisionProcessor
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor

/**
 * An API for detecting april tags.
 *
 * This requires the [Vision] API.
 *
 * # Developer Note
 *
 * See the [Understanding AprilTag Detection Values](https://ftc-docs.firstinspires.org/en/latest/apriltag/understanding_apriltag_detection_values/understanding-apriltag-detection-values.html)
 * article, specifically figure 2, to understand the reasoning behind this code.
 */
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

    /** Gets a list of all april tags currently detected. */
    fun detections(): List<AprilTagDetection> = this.aprilTag.detections

    /** Returns the detection data of a specific tag id, or null if not found. */
    fun detect(id: Int): AprilTagDetection? = this.detections().firstOrNull { it.id == id }
}
