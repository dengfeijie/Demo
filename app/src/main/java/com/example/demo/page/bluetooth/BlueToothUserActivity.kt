package com.example.demo.page.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.adapter.BlueListAdapter
import com.example.demo.page.base.FrameActivity
import kotlinx.android.synthetic.main.activity_blue_tooth.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * 蓝牙用户端
 */
class BlueToothUserActivity : FrameActivity() {

    /**
     * 蓝牙UUID
     */
//    val SPP_UUID = UUID.fromString("00001105-0000-1000-8000-00805f9B34FB")

    companion object {
        val SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    private var isRegister = false
    private val MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 60000
    private val GPS_REQUEST_CODE = 4000
    private lateinit var booth: HashMap<String, BluetoothDevice>
    private val REQUEST_CODE = 1
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val mBlueList: MutableList<BluetoothDevice> = ArrayList()
    private lateinit var listAdapter: BlueListAdapter
    private lateinit var currentDevice: BluetoothDevice


    override fun initData() {
        super.initData()

        //6.0以上请求定位权限
        if (Build.VERSION.SDK_INT >= 23) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
            }


            //是否已经开启了定位
            if (!isLocationEnabled()) {
                openGPSSettings()
            }
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_blue_tooth
    }


    override fun initListener() {
        super.initListener()

        sb_button_2.setOnCheckedChangeListener { _, _ ->
            ll_dialog.visibility = View.VISIBLE
            initBluetooth()
        }

        btn_send_message.setOnClickListener {
            if (socket != null) {
                val message = if (TextUtils.isEmpty(et_message.text.toString())) {
                    "空消息欧~~"
                } else {
                    et_message.text.toString()
                }
                sendDataToPairedDevice(message, currentDevice)
                et_message.setText("")
            }
        }

    }


    /**
     * 请求结果
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "蓝牙已打开", Toast.LENGTH_SHORT).show()
            val devices = getConnectionList()
            val stringBuffer = StringBuffer()
            for (device in devices) {
                stringBuffer.append("${device.name}\n")
            }
            findBluetoothDevice()
        } else if (requestCode == GPS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "定位已开启", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * 获取已经配对的蓝牙列表
     */
    private fun getConnectionList(): ArrayList<BluetoothDevice> {
        val list = arrayListOf<BluetoothDevice>()
        val paredDevice = bluetoothAdapter.bondedDevices
        if (paredDevice != null && paredDevice.size > 0) {
            for (bd in paredDevice) {
                list.add(bd)
            }
        }
        return list
    }


    /**
     * 搜索附近的蓝牙
     */
    private fun findBluetoothDevice() {
        bluetoothAdapter.startDiscovery()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isRegister) {
            unregisterReceiver(receiver)
        }
    }


    /**
     * 初始化蓝牙
     */
    private fun initBluetooth() {
        // 判断是否打开蓝牙
        if (!bluetoothAdapter.isEnabled) {
            //弹出对话框提示用户是后打开
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, REQUEST_CODE)
        } else {
            // 不做提示，强行打开
            bluetoothAdapter.enable()
        }
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        booth = HashMap()
        registerBrodcast()
        startScanBluth()
    }


    /**
     * 注册广播
     */
    private fun registerBrodcast() {
        // 搜索完成的广播
        val filter = IntentFilter(ACTION_DISCOVERY_FINISHED)
        filter.addAction(ACTION_FOUND)
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        filter.addAction(ACTION_CONNECTION_STATE_CHANGED)
        // 注册广播
        registerReceiver(receiver, filter)
        isRegister = true
    }


    /**
     * 开始扫描蓝牙
     */
    private fun startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }
        // 开始搜索
        bluetoothAdapter.startDiscovery()
        Toast.makeText(this, "正在搜索，请稍后！", Toast.LENGTH_SHORT).show()
    }


    /**
     * 广播接收器
     */
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // 收到的广播类型
            val action = intent.action
            val bondedDevices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
            for (device in bondedDevices) {
                if (!TextUtils.isEmpty(device.name)) {
                    booth.remove(device.name)
                    booth[device.name] = device
                }
            }


            when (action) {
                ACTION_FOUND -> {
                    // 从intent中获取设备
                    // 发现设备的广播
                    val device =
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    // 是否没配对
                    if (device.bondState != BluetoothDevice.BOND_BONDED) {
                        if (device.bondState != BluetoothDevice.BOND_BONDED) {
                            if (!TextUtils.isEmpty(device.name)) {
                                booth.remove(device.name)
                                booth.put(device.name, device)
                            }
                        }
                    }
                }

                // 搜索完成
                ACTION_DISCOVERY_FINISHED -> {
                    for (key in booth.keys) {
                        if (!mBlueList.contains(booth[key])) {
                            mBlueList.add(booth[key]!!)
                        }
                    }
                    showBluetoothPop(mBlueList)
                    bluetoothAdapter.cancelDiscovery()
                    Toast.makeText(context, "搜索完成", Toast.LENGTH_SHORT).show()
                    ll_dialog.visibility = View.GONE
                }

                //配对成功
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    Toast.makeText(context, "配对成功", Toast.LENGTH_SHORT).show()
                }

                //连接成功
                ACTION_CONNECTION_STATE_CHANGED -> {
                    Toast.makeText(context, "连接成功", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    /**
     * 搜索成功后并显示并注册监听
     *
     */
    private fun showBluetoothPop(list: MutableList<BluetoothDevice>) {
        rv_recycler.layoutManager = LinearLayoutManager(this)
        listAdapter = BlueListAdapter(this, list)
        rv_recycler.adapter = listAdapter

        listAdapter.setOnSelectedListener(object : BlueListAdapter.OnSelectListener {
            override fun onSelected(position: Int) {
//                device.getBondState() == BluetoothDevice.BOND_NONE
                val device = mBlueList[position]
                if (device.bondState == BluetoothDevice.BOND_NONE) {
                    //未配对
                    //如果想要取消已经配对的设备，只需要将creatBond改为removeBond
                    val method = BluetoothDevice::class.java.getMethod("createBond")
                    method.invoke(device) as Boolean
                } else {
                    //todo 连接
                    checkBluetoothPermission(device)
                }
            }
        })
    }


    private var socket: BluetoothSocket? = null
    fun connect(device: BluetoothDevice) {
        currentDevice = device
        Thread {
            val sdk = Build.VERSION.SDK_INT
            socket = if (sdk >= 10) {
                device.createInsecureRfcommSocketToServiceRecord(SPP_UUID)
            } else {
                device.createRfcommSocketToServiceRecord(SPP_UUID)
            }

            if (bluetoothAdapter.isDiscovering) {
                bluetoothAdapter.cancelDiscovery()
            }

            try {
                socket?.connect()
                Log.e("TAG", "正在准备连接")
            } catch (e: IOException) {
                Log.e("ERROR1", e.message)
                try {
                    Log.e("", "trying fallback...")
                    socket = device.javaClass.getMethod("createRfcommSocket",
                        *arrayOf<Class<*>?>(
                            Int::class.javaPrimitiveType))
                        .invoke(device, 1) as (BluetoothSocket)
                    socket?.connect();

                    Log.e("TAG", "正在准备连接")
                } catch (e2: Exception) {
                    Log.e("ERROR2", "无法连接蓝牙")
                }
            }

        }.start()
    }


    /**
     * 发送信息到已连接的蓝牙设备
     */
    private fun sendDataToPairedDevice(message: String, device: BluetoothDevice) {
        val toSend = message.toByteArray()
        try {
            socket?.outputStream?.write(toSend)
        } catch (e: IOException) {
            Log.e("TAG", "Exception during write", e)
        }
    }


    /**
     * 检查是否开启了定位权限
     */

    fun checkBluetoothPermission(device: BluetoothDevice) {
        if (Build.VERSION.SDK_INT >= 23) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
            } else {
                //具有权限
                connect(device)
            }
        } else {
            //系统不高于6.0直接执行
            connect(device)
        }
    }


    /**
     * 申请权限回调
     */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意权限
                Toast.makeText(this, "权限获取成功,请再次点击连接", Toast.LENGTH_SHORT).show()
            } else {
                // 权限拒绝
                // 下面的方法最好写一个跳转，可以直接跳转到权限设置页面，方便用户
                Toast.makeText(this, "需同意所需要的权限", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     * 检查是否有开启定位
     */
    private fun isLocationEnabled(): Boolean {
        var locationMode: Int
        var locationProviders: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode =
                    Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace();
                return false
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    /**
     * 跳转GPS设置
     */
    private fun openGPSSettings() {
        //没有打开则弹出对话框
        AlertDialog.Builder(this)
            .setTitle("开启定位")
            .setMessage("需要开启定位才能搜索附近的蓝牙")
            // 拒绝, 退出应用
            .setNegativeButton(
                "取消") { _, _ -> finish() }

            .setPositiveButton("去开启") { _, _ ->
                //跳转GPS设置界面
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, GPS_REQUEST_CODE);
            }
            .setCancelable(false)
            .show();
    }

}
