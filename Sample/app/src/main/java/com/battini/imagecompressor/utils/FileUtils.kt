package com.battini.imagecompressor.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object FileUtils {

    fun copyFileToDeviceSampleFolder(context: Context, file: File, cb: ((File) -> Unit)? = null) {
        val root = context.getExternalFilesDir(DIRECTORY_PICTURES).toString()
        val myDir = File("$root/Sample")
        myDir.mkdirs()
        val f = File(myDir, file.name)
        if (f.exists())
            f.delete()
        try {
            val source = FileInputStream(file).channel
            val out = FileOutputStream(f).channel
            out.transferFrom(source, 0, source.size())
            source.close()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)))
        cb?.invoke(f)
    }
}

fun File.getSizeInKB(): String {
    return (this.length() / (1024)).toString().plus("KB")
}

fun Context.getPicturesFile(fileName: String, subDirectory: String = ""): File {
    return File(this.cacheDir.absolutePath.plus("/$subDirectory"), fileName)
}