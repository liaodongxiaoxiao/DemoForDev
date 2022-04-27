package com.karl.demo.demo

import android.os.Bundle
import com.coder.karl.mqtt.lib.MqttLib
import com.coder.karl.mqtt.lib.listener.MqttActionListener
import com.coder.karl.mqtt.lib.listener.MqttConnectListener
import com.coder.karl.mqtt.lib.listener.OnArrivedMessageListener
import com.google.gson.JsonObject
import com.karl.demo.BaseActivity
import com.karl.demo.databinding.ActivityDemoMqttBinding
import com.karl.kotlin.extension.isEmpty
import com.karl.kotlin.extension.toast

/**
 * MQTT demo
 * 参考：https://www.emqx.cn/blog/android-connects-mqtt-using-kotlin
 */
class MQTTDemoActivity : BaseActivity<ActivityDemoMqttBinding>(ActivityDemoMqttBinding::inflate) {

    private val defaultTopic = "/iot/action"
    private var host = "192.168.1.121:1883"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MqttLib.setupHost("192.168.1.121:1883")
        MqttLib.addConnectionListener(object : MqttConnectListener {
            override fun onConnected() {
                toast("connected")
                toLogin()
            }

            override fun onDisConnected(errorCode: Int, errorMessage: String?) {
                toast("disConnected")
            }

        })
        MqttLib.connect()
        binding.apply {
            etPublishTopic.setText("online")
            val json = JsonObject()
            json.addProperty("id","app001")
            json.addProperty("name","Android-app")
            json.addProperty("type",1)
            json.addProperty("status",1)
            etPublishMsg.setText(json.toString())
            btnPublish.setOnClickListener {
                MqttLib.publish(etPublishTopic.text.toString(),etPublishMsg.text.toString())
            }
            etTopic.setText(defaultTopic)
            btnStart.setOnClickListener {
                //connect(this@MQTTDemoActivity)
            }

            etHost.setText(host)

            btnSubscribe.setOnClickListener {
                if (etTopic.isEmpty()) {
                    toast("请输入要订阅的主题")
                    return@setOnClickListener
                }

                MqttLib.subscribe(
                    etTopic.text.toString(),
                    actionListener = object : MqttActionListener {
                        override fun onSuccess(topic: String) {
                            toast("$topic 订阅成功...")
                        }

                        override fun onFailure(exception: Throwable?) {
                            toast(" 订阅失败：${exception?.message}")
                        }

                    })
                MqttLib.setArrivedMessageListener(etTopic.text.toString(),
                    object : OnArrivedMessageListener {
                        override fun messageArrived(message: String?) {
                            //Log.e(, "message:${message}")
                            tvRev.append(message)
                            tvRev.append("\n")
                        }
                    })
            }
        }
    }

    private fun toLogin() {
        MqttLib.publish("/online", "{'id':'app001','name':'Android-app','type':1,'status':1}")
    }

}