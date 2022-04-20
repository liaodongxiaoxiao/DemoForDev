package com.karl.demo.demo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityDemoViewToImageBinding
import com.zhy.http.okhttp.utils.L
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ViewToImageActivity :
    BaseActivity<ActivityDemoViewToImageBinding>(ActivityDemoViewToImageBinding::inflate),
    ActivityCompat.OnRequestPermissionsResultCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_demo_view_to_image)

        binding.btnSave.setOnClickListener {
            ViewToImagePermissionHelper.getInstance(this).execute {
                Log.e("TAG", "on click ... ")
                try {
                    viewConversionBitmap(binding.v1)?.let { bitmap2uri(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * view转bitmap
     */
    fun viewConversionBitmap(v: View): Bitmap? {
        val w: Int = v.getWidth()
        val h: Int = v.getHeight()
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        c.drawColor(Color.WHITE)
        /** 如果不设置canvas画布为白色，则生成透明  */
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }

    private fun bitmap2uri(b: Bitmap) { //c.getCacheDir()
        //   /Android/data/你的报名/cache/1600739295328.jpg
        val path = File(
            getExternalCacheDir()?.path + File.separator + System.currentTimeMillis()
                .toString() + ".jpg"
        )
        L.e(
            "getAbsolutePath===" + path.getAbsolutePath()
                .toString() + "     ===getAbsolutePath===" + path.getParent()
        )
        try {
            val os: OutputStream = FileOutputStream(path)
            b.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.close()

        } catch (ignored: Exception) {
        }

    }

    /*override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 2001) {
            viewConversionBitmap(v1)?.let { bitmap2uri(it) }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        toast("没有权限保存图片")
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("TAG", "call.....")
        ViewToImagePermissionHelper.getInstance(this).reCallMethod(requestCode)

    }
}