package org.firstinspires.ftc.teamcode.api.vision

import android.graphics.Canvas
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.api.vision.CubeVision.placement
import org.firstinspires.ftc.vision.VisionProcessor
import org.opencv.core.Mat
import kotlin.concurrent.Volatile

/**
 * A [VisionAPI] that scans for the game piece in its 3 possible positions.
 *
 * Note that this is only meant to run in the beginning of the autonomous phase. Afterwards, it can
 * be disabled with [Vision.disable].
 *
 * @see placement
 */
object CubeVision : API(), VisionAPI {
    override val processor: VisionProcessor
        get() = this.cubeProcessor

    /**
     * Returns where the cube is currently placed.
     *
     * @see CubePlacement
     */
    val placement: CubePlacement
        get() = this.cubeProcessor.placement

    private lateinit var cubeProcessor: CubeProcessor

    override fun init(opMode: OpMode) {
        super.init(opMode)
        this.cubeProcessor = CubeProcessor()
    }

    /**
     * Represents the position where the cube is placed, relative to the camera of the robot.
     */
    enum class CubePlacement {
        Left,
        Center,
        Right,
    }

    /**
     * A custom processor for the vision portal that scans for the cube.
     */
    private class CubeProcessor : VisionProcessor {
        // Make reads and writes synchronized, avoiding data races.
        @Volatile
        var placement = CubePlacement.Center

        // Initialization code
        override fun init(width: Int, height: Int, calibration: CameraCalibration?) {}

        // Actual processing of OpenCV frame
        override fun processFrame(frame: Mat, captureTimeNanos: Long): Any? {
            // This returned value is the userContext in [onDrawFrame]
            return null
        }

        // Allows drawing shapes on stream video
        override fun onDrawFrame(
            canvas: Canvas,
            onscreenWidth: Int,
            onscreenHeight: Int,
            scaleBmpPxToCanvasPx: Float,
            scaleCanvasDensity: Float,
            userContext: Any?
        ) {
        }
    }
}
