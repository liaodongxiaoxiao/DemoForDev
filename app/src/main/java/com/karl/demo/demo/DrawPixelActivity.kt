package com.karl.demo.demo

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import com.coder.karl.mqtt.lib.MqttLib
import com.coder.karl.mqtt.lib.listener.MqttConnectListener
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityDemoPixelBinding
import com.karl.kotlin.extension.toast
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import permissions.dispatcher.NeedsPermission
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DrawPixelActivity :
    BaseActivity<ActivityDemoPixelBinding>(ActivityDemoPixelBinding::inflate) {

    private lateinit var pixelAdapter: PixelItemAdapter
    private lateinit var pixelAdapterBg: PixelItemAdapter
    private var currentColor: ColorEnvelope? = null
    private val CHANNEL_ID = "1988"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNotificationChannel()
        bindEvent()
        initAdapter()
        getMqttStatus()
    }

    private fun getMqttStatus() {
        if (MqttLib.isConnected()) {
            binding.tvMqtt.setText("当前已连接Mqtt服务")
            binding.tvMqtt.setOnClickListener(null)
        } else {
            binding.tvMqtt.setText("未连接Mqtt服务,请点击尝试连接")
            binding.tvMqtt.setOnClickListener {
                MqttLib.setupHost("192.168.1.121")
                MqttLib.addConnectionListener(object : MqttConnectListener {
                    override fun onConnected() {
                        Log.e("TAG", "onConnected")
                        getMqttStatus()
                    }

                    override fun onDisConnected(errorCode: Int, errorMessage: String?) {
                        Log.e("TAG", "onDisConnected")
                        getMqttStatus()
                    }

                })
                MqttLib.connect()
            }
        }
    }

    private fun bindEvent() {
        binding.apply {
            tvCurrent.setOnClickListener {
                selectColor()
            }

            btnClear.setOnClickListener {
                for (bean in pixelAdapter.getAll()) {
                    bean.selected = false
                }
                pixelAdapter.notifyDataSetChanged()
            }

            btnFill.setOnClickListener {
                if (currentColor == null) {
                    toast("请选择一种颜色")
                } else {
                    for (bean in pixelAdapter.getAll()) {
                        setColor(bean, pixelAdapter)
                    }
                    pixelAdapter.notifyDataSetChanged()
                }
            }

            btnSave.setOnClickListener {
                saveImage()
            }

            btnSaveImg.setOnClickListener {
                saveImageToLocal()
            }


        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImageToLocal() {
        try {
            viewConversionBitmap(binding.gv)?.let { bitmap2uri(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    /**
     * view转bitmap
     */
    private fun viewConversionBitmap(v: View): Bitmap? {
        val w: Int = v.width
        val h: Int = v.height
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        //c.drawColor(Color.WHITE)
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }

    private fun bitmap2uri(b: Bitmap) {
        val path = File(
            externalCacheDir?.path + File.separator + System.currentTimeMillis()
                .toString() + ".png"
        )
        Log.e("TAG", "path:${path}")
        try {
            val os: OutputStream = FileOutputStream(path)
            b.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.close()

            val intent = Intent()
            intent.action = Intent.ACTION_VIEW


            val uri: Uri = FileProvider.getUriForFile(
                this@DrawPixelActivity,
                this@DrawPixelActivity.getApplicationContext().getPackageName()
                    .toString() + ".provider",
                path
            )
            intent.setDataAndType(uri, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val pIntent = PendingIntent.getActivity(this@DrawPixelActivity, 0, intent, 0)

            val notification = NotificationCompat.Builder(this@DrawPixelActivity, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("保存成功")
                .setContentText("生成图片保存成功，点击查看")
                .setLargeIcon(b)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(b)
                        .bigLargeIcon(null)
                )
                .setContentIntent(pIntent)
                .build()

            NotificationManagerCompat.from(this@DrawPixelActivity).notify(123, notification)


        } catch (ignored: Exception) {
            Log.e("TAG", ignored.message, ignored)
        }

    }

    private fun saveImage() {
        //val sb = StringBuilder()
        val map: MutableMap<String, MutableList<Int>> = mutableMapOf()

        for (bean in pixelAdapter.getDrawItem()) {
            if (map.containsKey(bean.color.hex)) {
                map[bean.color.hex]?.add(bean.index)
            } else {
                val list: MutableList<Int> = mutableListOf()
                list.add(bean.index)
                map[bean.color.hex] = list
            }
        }

        var json: JsonObject
        val jsonArray = JsonArray()

        for (key in map.keys) {
            json = JsonObject()
            Log.e("TAG","key:${map[key]?.sorted()?.joinToString(separator = ",")}")
            json.addProperty(key, map[key]?.sorted()?.joinToString(separator = "-") {
                Integer.toHexString(it).uppercase()
            })
            jsonArray.add(json)
        }
        val result = JsonObject()
        result.addProperty("action", "show_pixel")
        result.add("data", jsonArray)

        binding.tvData.text = result.toString()
        if (MqttLib.isConnected()) {
            MqttLib.publish("/iot/action", result.toString())
        }
    }

    private fun encoderColor(r: Int, g: Int, b: Int): String {
        val result = b.toLong() or (g.toLong() shl 8) or (r.toLong() shl 16)
        return Integer.toHexString(result.toInt()).uppercase()
    }

    private fun selectColor() {
        ColorPickerDialog.Builder(this)
            .setTitle("ColorPicker Dialog")
            .setPreferenceName("MyColorPickerDialog")
            .setPositiveButton("确定",
                ColorEnvelopeListener { envelope, _ ->
                    currentColor = envelope
                    binding.tvCurrent.apply {
                        text = envelope.hexCode
                        setBackgroundColor(envelope.color)
                    }
                })
            .setNegativeButton(
                "取消"
            ) { dialogInterface, i -> dialogInterface.dismiss() }
            .attachAlphaSlideBar(true) // the default value is true.
            .attachBrightnessSlideBar(true) // the default value is true.
            .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
            .show()
    }

    private fun initAdapter() {
        var bgColor: String
        val items = buildList<PixelItemBean> {
            val lineResultTmp: MutableList<PixelItemBean> = mutableListOf()
            var lineTmp = 0
            for (i in 0..255) {
                val currentLine = i / 16 + 1
                bgColor = if (i % 2 == 1) {
                    ("#CCCCCC")
                } else {
                    ("#FFFFFF")
                }

                if (lineTmp != 0 && lineTmp != currentLine) {
                    if (currentLine % 2 == 0) {
                        lineResultTmp.reverse()
                    }
                    //println(lineTmp)
                    addAll(lineResultTmp)
                    lineResultTmp.clear()
                    lineResultTmp.add(
                        PixelItemBean(
                            i,
                            ColorBean("FFFFFF", "#FFFFFF", bgColor),
                            false
                        )
                    )
                    lineTmp = currentLine
                } else {
                    lineResultTmp.add(
                        PixelItemBean(
                            i,
                            ColorBean("FFFFFF", "#FFFFFF", bgColor),
                            false
                        )
                    )
                }
                if (lineTmp == 0) {
                    lineTmp = currentLine
                }

                //add(PixelItemBean(i, ColorBean("FFFFFF", "#FFFFFF", bgColor), false))
            }
            addAll(lineResultTmp)
        }
        //Log.e("TAG", "item size:${items.size}")
        pixelAdapter = PixelItemAdapter(false, items.toMutableList()) { position, bean ->
            if (currentColor != null) {
                setColor(bean, pixelAdapter)
                val bgBean = pixelAdapterBg.getItem(position) as PixelItemBean
                setColor(bgBean, pixelAdapterBg)
            } else {
                toast("请选择一种颜色")
            }
        }
        pixelAdapterBg = PixelItemAdapter(true, items.toMutableList()) { _, _ -> }
        binding.gv.adapter = pixelAdapterBg

        binding.gvImg.adapter = pixelAdapter
    }

    private fun setColor(bean: PixelItemBean, adapter: PixelItemAdapter) {
        bean.color.apply {
            currentColor!!.let { ev ->
                hexColor = "#${ev.hexCode}"
                hex = encoderColor(ev.argb[1], ev.argb[2], ev.argb[3])
            }
        }
        bean.selected = true
        adapter.notifyDataSetChanged()
    }
}

class PixelItemAdapter(
    private val isBg: Boolean = false,
    private val data: MutableList<PixelItemBean>,
    private val click: (Int, PixelItemBean) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val backView: View
        val holder: PixelItemViewHolder
        if (convertView == null && parent != null) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_pixel,
                parent,
                false
            )
            backView = view
            holder = PixelItemViewHolder(view)
            backView.tag = holder
        } else {
            backView = convertView!!
            holder = convertView.tag as PixelItemViewHolder
        }
        val entity = data[position]
        holder.root.setOnClickListener {
            click(position, entity)
        }
        holder.item.setText("${entity.index}")
        if (entity.selected) {
            holder.item.setBackgroundColor(Color.parseColor(entity.color.hexColor))
        } else {
            holder.item.setBackgroundColor(
                if (isBg) {
                    Color.TRANSPARENT
                } else {
                    Color.parseColor(entity.color.bgColor)
                }
            )
        }

        return backView
    }

    fun getAll(): MutableList<PixelItemBean> = data

    fun getDrawItem(): MutableList<PixelItemBean> = data.filter { it.selected }.toMutableList()

    inner class PixelItemViewHolder(view: View) {
        val root: View = view.findViewById(R.id.root)
        val item: TextView = view.findViewById(R.id.v_item)
    }

}

data class ColorBean(
    var hex: String,
    var hexColor: String,
    var bgColor: String
)

data class PixelItemBean(val index: Int, val color: ColorBean, var selected: Boolean)
