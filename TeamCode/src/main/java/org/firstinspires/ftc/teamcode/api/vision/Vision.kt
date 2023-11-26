package org.firstinspires.ftc.teamcode.api.vision

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.vision.VisionPortal

object Vision : API() {
    lateinit var portal: VisionPortal
        private set

    /**
     * Initializes the Vision Portal with a list of processors / APIs.
     *
     * You must create at least one vision API in order to use the portal.
     *
     * # Example
     *
     * ```
     * Vision.init(this, AprilVision, TFlowVision)
     * ```
     */
    fun init(opMode: OpMode, vararg visionAPIs: VisionAPI) {
        super.init(opMode)

        val webcam = opMode.hardwareMap.get(WebcamName::class.java, "Webcam 1")

        // Configure the builder with settings
        val builder = VisionPortal.Builder()
            .setCamera(webcam)
            .enableLiveView(true)

        // Add all of the processors
        for (visionAPI in visionAPIs) {
            builder.addProcessor(visionAPI.processor)
        }

        // Build the portal
        this.portal = builder.build()

        // Use a different sleep function depending on whether its a linear opmode or not.
        val sleep = if (opMode is LinearOpMode) {
            opMode::sleep
        } else {
            this::opModeSleep
        }

        // Wait until the camera has started.
        while (this.portal.cameraState != VisionPortal.CameraState.STREAMING) {
            opMode.telemetry.addData("Camera", this.portal.cameraState)
            opMode.telemetry.update()

            sleep(50)
        }
    }

    // Vision must be initialized with at least one VisionAPI
    override fun init(opMode: OpMode) {
        throw RuntimeException("Please initialize Vision with at least one VisionAPI.")
    }

    private fun opModeSleep(ms: Long) {
        try {
            Thread.sleep(ms)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}
