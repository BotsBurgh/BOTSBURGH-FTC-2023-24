package org.firstinspires.ftc.teamcode.api.vision

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.opModeSleep
import org.firstinspires.ftc.vision.VisionPortal

/**
 * The main vision API that loads [VisionAPI]s.
 *
 * This is similar to the [VisionPortal], but it takes [VisionAPI]s instead.
 *
 * ```
 * // Initialize VisionAPIs first
 * AprilVision.init(this)
 *
 * // Then initialize Vision
 * Vision.init(this, AprilVision)
 * ```
 */
object Vision : API() {
    lateinit var portal: VisionPortal
        private set

    /**
     * Initializes the Vision Portal with a list of processors / APIs.
     *
     * You must create at least one vision API in order to use the portal. This function will not
     * return until the camera finishes turning on.
     *
     * # Example
     *
     * ```
     * Vision.init(this, AprilVision, TFlowVision)
     * ```
     */
    fun init(
        opMode: OpMode,
        vararg visionAPIs: VisionAPI,
    ) {
        super.init(opMode)

        val webcam = opMode.hardwareMap.get(WebcamName::class.java, "Webcam 1")

        // Configure the builder with settings
        val builder =
            VisionPortal.Builder()
                .setCamera(webcam)
                // Enable live view when debug is enabled
                .enableLiveView(RobotConfig.debug)

        // Add all of the processors
        for (visionAPI in visionAPIs) {
            builder.addProcessor(visionAPI.processor)
        }

        // Build the portal
        this.portal = builder.build()

        // Use a different sleep function depending on whether its a linear opmode or not.
        val sleep = opModeSleep(opMode)

        // Wait until the camera has started.
        while (this.portal.cameraState != VisionPortal.CameraState.STREAMING) {
            opMode.telemetry.addData("Camera", this.portal.cameraState)
            opMode.telemetry.update()

            sleep(50)
        }

        opMode.telemetry.addData("Camera", this.portal.cameraState)
        opMode.telemetry.update()
    }

    @Deprecated(
        message = "Please initialize Vision with at least one VisionAPI.",
        replaceWith = ReplaceWith("Vision.init(this, visionAPI, ...)"),
        level = DeprecationLevel.ERROR,
    )
    override fun init(opMode: OpMode) {
        throw RuntimeException("Please initialize Vision with at least one VisionAPI.")
    }
}
