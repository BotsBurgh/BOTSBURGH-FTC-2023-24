package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor

/**
 * An API for working with April Tags.
 *
 * You must call [enable] before using this API. Access [detections] to find all april tags in view
 * of the camera.
 */
class AprilTags : API() {
    private lateinit var aprilTag: AprilTagProcessor
    private lateinit var visionPortal: VisionPortal

    /** Returns a list of april tags detected. */
    val detections: List<AprilTagDetection>
        get() = this.aprilTag.detections

    /**
     * Returns true if april tag scanning is enabled.
     *
     * @see enable
     * @see disable
     * */
    val isEnabled: Boolean
        get() = this.visionPortal.getProcessorEnabled(this.aprilTag)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.aprilTag = AprilTagProcessor.Builder()
            // April Tag settings and calibrations go here
            .build()

        this.visionPortal = VisionPortal.Builder()
            .setCamera(BuiltinCameraDirection.BACK)
            .addProcessor(this.aprilTag)
            .build()

        // Disable scanning by default
        this.disable()
    }

    /**
     * Enables scanning.
     *
     * @see disable
     */
    fun enable() {
        this.visionPortal.setProcessorEnabled(this.aprilTag, true)
    }

    /**
     * Disables scanning.
     *
     * When disabled, [detections] will not update with new data.
     */
    fun disable() {
        this.visionPortal.setProcessorEnabled(this.aprilTag, false)
    }
}