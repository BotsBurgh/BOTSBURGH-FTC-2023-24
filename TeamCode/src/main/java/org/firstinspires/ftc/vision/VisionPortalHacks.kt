package org.firstinspires.ftc.vision

import org.openftc.easyopencv.OpenCvCamera

/**
 * Returns the (possibly null) current camera of the given [VisionPortal].
 *
 * This is in the `org.firstinspires.ftc.vision` package so it can access the `protected`
 * [VisionPortalImpl.camera] property.
 *
 * # Example
 *
 * Stream the vision portal's camera to FTC Dashboard.
 *
 * ```
 * val camera = getVisionPortalCamera(Vision.portal)!!
 * FtcDashboard.getInstance().startCameraStream(camera, 0.0)
 * ```
 *
 * # Developer Note
 *
 * This is only possible because the [VisionPortalImpl.camera] property is `protected`, not
 * `private`. In Java, `protected` items are visible to classes in the same package. This function
 * is in the [org.firstinspires.ftc.vision] package so that it can read and export that property.
 *
 * This is technically bad practice and could break between minor updates to FtcRobotController.
 *
 * This was originally written to support streaming the camera to FTC Dashboard without writing a
 * custom vision processor that just copied the pixels over. For a real-life example, look at the
 * `AutoMain` opmode.
 */
fun getVisionPortalCamera(visionPortal: VisionPortal): OpenCvCamera? =
    // Return the camera if visionPortal is a VisionPortalImpl, else null
    (visionPortal as? VisionPortalImpl)?.camera
