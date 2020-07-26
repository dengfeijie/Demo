package com.example.demo.page

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.model.LatLng
import com.example.demo.R
import com.example.demo.page.base.FrameActivity
import kotlinx.android.synthetic.main.activity_location.*


class LocationActivity : FrameActivity(), LocationSource, AMapLocationListener {


    private lateinit var mLocationClient: AMapLocationClient
    private lateinit var mLocationClientOption: AMapLocationClientOption


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mv_map.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mv_map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mv_map.onResume()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mv_map.onSaveInstanceState(outState)
    }

    override fun initData() {

        super.initData()
        mLocationClient = AMapLocationClient(application)
        mLocationClientOption = AMapLocationClientOption()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {//未开启定位权限
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                200)
        }

        val setting = mv_map.map.uiSettings
        mv_map.map.setLocationSource(this)
        mv_map.map.isMyLocationEnabled = true
        setting.isMyLocationButtonEnabled = true
        mLocationClient.startLocation()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_location
    }

    override fun initListener() {
        super.initListener()

        btn_location.setOnClickListener {
            mLocationClient.startLocation()
        }

//        Log.e("TAG", SHA1.sHA1(this))

        mLocationClient.setLocationListener(this)
//        mLocationClient.setLocationListener {
//            if (it != null && it.errorCode == 0) {
//                Log.e("TAG",
//                    it.country + it.province + it.city + it.district + it.street + it.aoiName)
//                tv_location.text =
//                    it.country + it.province + it.city + it.district + it.street + it.aoiName
//            }
//
//            mv_map.map.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(aMapLocation.getLatitude(),
//                aMapLocation.getLongitude())))
//        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            200 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "定位已经开启", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    override fun deactivate() {

    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
    }

    override fun onLocationChanged(p0: AMapLocation?) {
        if (p0 != null && p0.errorCode == 0) {
            Log.e("TAG",
                p0.country + p0.province + p0.city + p0.district + p0.street + p0.aoiName)
            tv_location.text =
                p0.country + p0.province + p0.city + p0.district + p0.street + p0.aoiName
        }

        //设置缩放级别
        mv_map.map.moveCamera(CameraUpdateFactory.zoomTo(17f))
        mv_map.map.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(p0?.latitude!!,
            p0.longitude)))
    }


}
