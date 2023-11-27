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
 */
fun getVisionPortalCamera(visionPortal: VisionPortal): OpenCvCamera? =
    // Return the camera if visionPortal is a VisionPortalImpl, else null
    (visionPortal as? VisionPortalImpl)?.camera
