package com.karl.demo.demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.R
import com.karl.kotlin.extension.isEmpty
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.toast
import kotlinx.android.synthetic.main.activity_demo_mqtt.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

/**
 * MQTT demo
 * 参考：https://www.emqx.cn/blog/android-connects-mqtt-using-kotlin
 */
class MQTTDemoActivity : AppCompatActivity() {

    private lateinit var mqttClient: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_mqtt)

        btn_start.setOnClickListener {
            connect(this)
        }

        btn_subscribe.setOnClickListener {
            if (et_topic.isEmpty()) {
                toast("请输入要订阅的主题")
                return@setOnClickListener
            }

            subscribe(et_topic.text.toString())
        }
    }

    fun connect(context: Context) {
        val serverURI = "tcp://192.168.1.107:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client")
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                "Receive message: ${message.toString()} from topic: $topic".log()
            }

            override fun connectionLost(cause: Throwable?) {
                "Connection lost ${cause.toString()}".log()
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
        val options = MqttConnectOptions()
        try {
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    "Connection success".log()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    "Connection failure".log()
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    fun subscribe(topic: String, qos: Int = 1) {
        try {
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    "Subscribed to $topic".log()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    "Failed to subscribe $topic".log()
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic: String) {
        try {
            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    "Unsubscribed to $topic".log()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    "Failed to unsubscribe $topic".log()
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    "$msg published to $topic".log()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    "Failed to publish $msg to $topic".log()
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    "Disconnected".log()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    "Failed to disconnect".log()
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}