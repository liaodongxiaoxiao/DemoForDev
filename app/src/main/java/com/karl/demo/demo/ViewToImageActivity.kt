package com.karl.demo.demo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.R
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.view.View
import com.karl.kotlin.extension.toast
import com.zhy.http.okhttp.utils.L
import kotlinx.android.synthetic.main.activity_demo_view_to_image.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception


class ViewToImageActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_view_to_image)

        btn_save.setOnClickListener {
            if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                EasyPermissions.requestPermissions(
                    this,
                    "",
                    2001,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } else {
                viewConversionBitmap(v1)?.let { bitmap2uri(it) }
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

    private fun bitmap2uri(b: Bitmap){ //c.getCacheDir()
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

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 2001) {
            viewConversionBitmap(v1)?.let { bitmap2uri(it) }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        toast("没有权限保存图片")
    }
}