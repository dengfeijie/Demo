package com.example.demo.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.util.*

/**
 * 蓝牙工具类 2020/5/18
 * 功能：搜索蓝牙设备
 * 连接蓝牙串口
 * 发送串口数据
 * 接收串口数据
 *
 * @author gtf35 gtf@gtf35.top
 */
internal class BLESPPUtils
/**
 * 构造蓝牙工具
 *
 * @param context           上下文
 * @param onBluetoothAction 蓝牙状态改变回调
 */(
    private val mContext: Context,
    private val mOnBluetoothAction: OnBluetoothAction?,
    private val mBluetoothAdapter: BluetoothAdapter
) {
    private val mConnectTask: ConnectTask? = ConnectTask()

    /**
     * 搜索到新设备广播广播接收器
     */
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                mOnBluetoothAction?.onFoundDevice(device)
            }
        }
    }

    /**
     * 搜索结束广播接收器
     */
    private val mFinishFoundReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                mOnBluetoothAction?.onFinishFoundDevice()
            }
        }
    }

    /**
     * 连接任务
     */
    private class ConnectTask : AsyncTask<String?, Array<Byte?>?, Void?>() {
        private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        var bluetoothSocket: BluetoothSocket? = null
        var romoteDevice: BluetoothDevice? = null
        var onBluetoothAction: OnBluetoothAction? = null
        var isRunning = false
        var stopString = "\r\n"
         override fun doInBackground(vararg params: String?): Void? {
            // 记录标志位，开始运行
            isRunning = true

            // 尝试获取 bluetoothSocket
            try {
//                val SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                val SPP_UUID = UUID.fromString("00001105-0000-1000-8000-00805f9B34FB")
                romoteDevice = bluetoothAdapter.getRemoteDevice(params[0])
                bluetoothSocket = romoteDevice?.createRfcommSocketToServiceRecord(SPP_UUID)
            } catch (e: Exception) {
                logD("获取Socket失败")
                isRunning = false
                e.printStackTrace()
                return null
            }

            // 检查有没有获取到
            if (bluetoothSocket == null) {
                onBluetoothAction!!.onConnectFailed("连接失败:获取Socket失败")
                isRunning = false
                return null
            }

            // 尝试连接
            try {
                // 等待连接，会阻塞线程
                bluetoothSocket!!.connect()
                logD("连接成功")
                onBluetoothAction!!.onConnectSuccess(romoteDevice)
            } catch (connectException: Exception) {
                connectException.printStackTrace()
                logD("连接失败:" + connectException.message)
                Looper.prepare()
                onBluetoothAction!!.onConnectFailed("连接失败:" + connectException.message)
                Log.e("TAG","连接失败:" + connectException.message)
                Looper.loop()
                return null
            }

            // 开始监听数据接收
            try {
                val inputStream = bluetoothSocket!!.inputStream
                var result = ByteArray(0)
                while (isRunning) {
                    logD("looping")
                    val buffer = ByteArray(256)
                    // 等待有数据
                    while (inputStream.available() == 0 && isRunning) {
                        if (System.currentTimeMillis() < 0) break
                    }
                    while (isRunning) {
                        try {
                            val num = inputStream.read(buffer)
                            val temp = ByteArray(result.size + num)
                            System.arraycopy(result, 0, temp, 0, result.size)
                            System.arraycopy(buffer, 0, temp, result.size, num)
                            result = temp
                            if (inputStream.available() == 0) break
                        } catch (e: Exception) {
                            e.printStackTrace()
                            onBluetoothAction!!.onConnectFailed("接收数据单次失败：" + e.message)
                            break
                        }
                    }
                    try {
                        // 返回数据
                        logD("当前累计收到的数据=>" + byte2Hex(result))
                        val stopFlag = stopString.toByteArray()
                        val stopFlagSize = stopFlag.size
                        var shouldCallOnReceiveBytes = false
                        logD("标志位为：" + byte2Hex(stopFlag))
                        for (i in stopFlagSize - 1 downTo 0) {
                            val indexInResult = result.size - (stopFlagSize - i)
                            if (indexInResult >= result.size || indexInResult < 0) {
                                shouldCallOnReceiveBytes = false
                                logD("收到的数据比停止字符串短")
                                break
                            }
                            shouldCallOnReceiveBytes = if (stopFlag[i] == result[indexInResult]) {
                                logD("发现" + byte2Hex(stopFlag[i]) + "等于" + byte2Hex(
                                    result[indexInResult]))
                                true
                            } else {
                                logD("发现" + byte2Hex(stopFlag[i]) + "不等于" + byte2Hex(
                                    result[indexInResult]))
                                false
                            }
                        }
                        if (shouldCallOnReceiveBytes) {
                            onBluetoothAction!!.onReceiveBytes(result)
                            // 清空
                            result = ByteArray(0)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        onBluetoothAction!!.onConnectFailed("验证收到数据结束标志出错：" + e.message)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onBluetoothAction!!.onConnectFailed("接收数据失败：" + e.message)
            }
            return null
        }

        override fun onCancelled() {
            try {
                logD("AsyncTask 开始释放资源")
                isRunning = false
                if (bluetoothSocket != null) {
                    bluetoothSocket!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /**
         * 发送
         *
         * @param msg 内容
         */
        fun send(msg: ByteArray?) {
            try {
                bluetoothSocket!!.outputStream.write(msg)
                onBluetoothAction!!.onSendBytes(msg)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 设置停止标志位字符串
     *
     * @param stopString 停止位字符串
     */
    fun setStopString(stopString: String) {
        mConnectTask!!.stopString = stopString
    }

    /**
     * 蓝牙活动回调
     */
    interface OnBluetoothAction {
        /**
         * 当发现新设备
         *
         * @param device 设备
         */
        fun onFoundDevice(device: BluetoothDevice?)

        /**
         * 当连接成功
         */
        fun onConnectSuccess(device: BluetoothDevice?)

        /**
         * 当连接失败
         *
         * @param msg 失败信息
         */
        fun onConnectFailed(msg: String?)

        /**
         * 当接收到 byte 数组
         *
         * @param bytes 内容
         */
        fun onReceiveBytes(bytes: ByteArray?)

        /**
         * 当调用接口发送了 byte 数组
         *
         * @param bytes 内容
         */
        fun onSendBytes(bytes: ByteArray?)

        /**
         * 当结束搜索设备
         */
        fun onFinishFoundDevice()
    }

    /**
     * 初始化
     */
    fun onCreate() {
        val foundFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        mContext.registerReceiver(mReceiver, foundFilter)
        val finishFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        mContext.registerReceiver(mFinishFoundReceiver, finishFilter)
    }

    /**
     * 销毁，释放资源
     */
    fun onDestroy() {
        try {
            logD("onDestroy，开始释放资源")
            mConnectTask!!.isRunning = false
            mConnectTask.cancel(true)
            mContext.unregisterReceiver(mReceiver)
            mContext.unregisterReceiver(mFinishFoundReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 开始搜索
     */
    fun startDiscovery() {
        if (mBluetoothAdapter.isDiscovering) mBluetoothAdapter.cancelDiscovery()
        mBluetoothAdapter.startDiscovery()
    }

    /**
     * 使用搜索到的数据连接
     *
     * @param device 设备
     */
    fun connect(device: BluetoothDevice) {
        mBluetoothAdapter.cancelDiscovery()
        connect(device.address)
    }

    /**
     * 使用Mac地址来连接
     *
     * @param deviceMac 要连接的设备的 MAC
     */
    private fun connect(deviceMac: String) {
        if (mConnectTask!!.status == AsyncTask.Status.RUNNING && mConnectTask.isRunning) {
            mOnBluetoothAction?.onConnectFailed("有正在连接的任务")
            return
        }
        mConnectTask.onBluetoothAction = mOnBluetoothAction
        try {
            mConnectTask.execute(deviceMac)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 发送 byte 数组到串口
     *
     * @param bytes 要发送的数据
     */
    fun send(bytes: ByteArray?) {
        mConnectTask?.send(bytes)
    }

    /**
     * 获取用户是否打开了蓝牙
     */
    val isBluetoothEnable: Boolean
        get() = mBluetoothAdapter.isEnabled

    /**
     * 开启蓝牙
     */
    fun enableBluetooth() {
        mBluetoothAdapter.enable()
    }

    companion object {
        private var mEnableLogOut = false

        /**
         * 字节转换为 16 进制字符串
         *
         * @param b 字节
         * @return Hex 字符串
         */
        private fun byte2Hex(b: Byte): String {
            var hex = StringBuilder(Integer.toHexString(b.toInt()))
            if (hex.length > 2) {
                hex = StringBuilder(hex.substring(hex.length - 2))
            }
            while (hex.length < 2) {
                hex.insert(0, "0")
            }
            return hex.toString()
        }

        /**
         * 字节数组转换为 16 进制字符串
         *
         * @param bytes 字节数组
         * @return Hex 字符串
         */
        private fun byte2Hex(bytes: ByteArray): String {
            val formatter = Formatter()
            for (b in bytes) {
                formatter.format("%02x", b)
            }
            val hash = formatter.toString()
            formatter.close()
            return hash
        }

        /**
         * 启用日志输出
         */
        fun setEnableLogOut() {
            mEnableLogOut = true
        }

        /**
         * 打印日志
         */
        private fun logD(msg: String) {
            if (mEnableLogOut) Log.d("BLEUTILS", msg)
        }
    }
}