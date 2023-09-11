package com.nuhin13.parent_android_native

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.ByteArrayOutputStream

object Util {
    fun print(message: String?, tag: String?) {
        Log.e(tag ?: "Error", message ?: "No Message")
    }

    fun getImageUri(inContext: Context, inImage: Bitmap, name: String): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            name,
            null
        )
        return Uri.parse(path)
    }

    fun getRealPathFromURI(contentURI: Uri, context: Context): String? {
        val filePath: String?
        val cursor = context.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            filePath = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            filePath = cursor.getString(idx)
            cursor.close()
        }
        return filePath
    }
}