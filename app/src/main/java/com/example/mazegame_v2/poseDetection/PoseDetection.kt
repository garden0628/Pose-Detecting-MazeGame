package com.example.mazegame_v2.poseDetection

import android.graphics.Bitmap
import com.example.mazegame_v2.ml.LiteModelMovenetSingleposeLightningTfliteFloat164
import com.example.mazegame_v2.models.PoseDetectModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class PoseDetection() {
    val imageProcessor = ImageProcessor.Builder().add(ResizeOp(192, 192, ResizeOp.ResizeMethod.BILINEAR)).build()
    var poseModel: PoseDetectModel = PoseDetectModel()

    fun poseDetection(bitmap: Bitmap, model: LiteModelMovenetSingleposeLightningTfliteFloat164) {
        var tensorImage = TensorImage(DataType.UINT8)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 192, 192, 3), DataType.UINT8)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
        println(outputFeature0)

        poseModel.detectPose(outputFeature0, bitmap)
    }
}