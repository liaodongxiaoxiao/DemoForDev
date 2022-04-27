package com.coder.karl.mqtt.lib

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.coder.karl.mqtt.lib.listener.MqttActionListener
import com.coder.karl.mqtt.lib.listener.MqttConnectListener
import com.coder.karl.mqtt.lib.listener.OnArrivedMessageListener
import com.coder.karl.mqtt.lib.utils.LogUtils
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.nio.charset.Charset

@SuppressLint("StaticFieldLeak")
object MqttLib {
    private lateinit var mqttClient: MqttAndroidClient
    private var isConnected = false


    private var context: Context? = null
    private var hostAddress: String = "127.0.0.1:1883"
    private val connectedListeners: MutableList<MqttConnectListener> = mutableListOf()
    private val messageListener: MutableMap<String, OnArrivedMessageListener> = mutableMapOf()


    fun setContext(context: Context?) {
        this.context = context
        LogUtils.setShowLog(true)
        LogUtils.d("set context")
    }

    fun setupHost(hostAddress: String) {
        this.hostAddress = hostAddress
    }

    fun connect() {
        if (hostAddress.isEmpty()) {
            LogUtils.e("host is empty")
            toNotificationLostConnected(1, "host address is empty")
        } else if (!isConnected) {

            val serverURI = "tcp://${hostAddress}"
            mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client")
            mqttClient.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    LogUtils.d("Receive message: ${message.toString()} from topic: $topic")
                    if (topic.isNullOrEmpty()) {
                        Log.e("mqtt", "arrived a null topic info.")
                    } else {
                        val msg = if (message != null) {
                            String(message.payload, Charset.forName("utf-8"))
                        } else {
                            "message is null"
                        }
                        sendMessage(topic, msg)
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    LogUtils.d("Connection lost ${cause.toString()}")
                    toNotificationLostConnected(errorMessage = cause?.message)
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    //
                }
            })
            val options = MqttConnectOptions()
            try {
                mqttClient.connect(options, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        LogUtils.d("connect success.")
                        toNotificationConnected()
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        LogUtils.d("connect failure.", exception)
                        toNotificationLostConnected(errorMessage = exception?.message)
                    }
                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }


    }

    fun isConnected() = isConnected

    fun disconnect() {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    LogUtils.d("Disconnected")
                    toNotificationLostConnected(1, "disconnect success")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    LogUtils.d("Failed to disconnect")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String, qos: Int = 1, actionListener: MqttActionListener? = null) {
        try {
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    LogUtils.d("Subscribed to $topic")
                    actionListener?.onSuccess(topic)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    LogUtils.d("Failed to subscribe $topic")
                    actionListener?.onFailure(exception)
                }
            })
        } catch (e: MqttException) {
            LogUtils.d("[$topic] subscribe error.", e)
            actionListener?.onFailure(e)
        }
    }

    fun unsubscribe(topic: String) {
        try {
            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    LogUtils.d("Unsubscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    LogUtils.d("Failed to unsubscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 0, retained: Boolean = false) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    LogUtils.d("$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    LogUtils.d("Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun addConnectionListener(listener: MqttConnectListener) {
        connectedListeners.add(listener)
    }

    fun removeConnectionListener(listener: MqttConnectListener) {
        connectedListeners.remove(listener)
    }

    private fun sendMessage(topic: String, message: String) {
        messageListener[topic]?.messageArrived(message)
    }

    private fun toNotificationLostConnected(errorCode: Int = -100, errorMessage: String?) {
        isConnected = false
        for (listener in connectedListeners) {
            listener.onDisConnected(errorCode, errorMessage)
        }
    }

    private fun toNotificationConnected() {
        isConnected = true
        for (listener in connectedListeners) {
            listener.onConnected()
        }
    }

    fun setArrivedMessageListener(topic: String, listener: OnArrivedMessageListener) {
        messageListener[topic] = listener
    }
}
