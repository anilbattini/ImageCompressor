package com.battini.example

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

object ImageCompressor {
    fun compressBitmap(file: File, actualBitmap: Bitmap, cb: ((File) -> Unit)? = null) {
        val sampleHeight = if (actualBitmap.width > actualBitmap.height) 600 else 800
        val sampleWidth = if (actualBitmap.width > actualBitmap.height) 800 else 600
        val bitmap = updateDecodeBounds(file, sampleWidth, sampleHeight)
        val fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut)
        fOut.flush() // Not really required
        fOut.close() // do not forget to close the stream
        bitmap.recycle() //recycle the bitmap
        cb?.let { cb(file) }
    }

    private fun updateDecodeBounds(imageFile: File, sampleWidth: Int, sampleHeight: Int): Bitmap {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, this)
            inSampleSize = getInSampleSize(this, sampleWidth, sampleHeight)
            inJustDecodeBounds = false
            BitmapFactory.decodeFile(imageFile.absolutePath, this)
        }
    }

    private fun getInSampleSize(options: BitmapFactory.Options, sampleWidth: Int, sampleHeight: Int): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        val sampleSize = 2
        if (height > sampleHeight || width > sampleWidth) {
            while (height / (sampleSize * inSampleSize) >= sampleHeight
                    && width / (sampleSize * inSampleSize) >= sampleWidth) {
                inSampleSize *= sampleSize
            }
        }
        return inSampleSize
    }
}
