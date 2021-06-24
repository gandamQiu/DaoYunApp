package com.example.guide

import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption

class StudentSignActivity : AppCompatActivity() {
    lateinit var locationClient: LocationClient
    private val REQUEST_CODE_ACCESS_COARSE_LOCATION = 1
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var errorCode: Int = 0
    lateinit var number:String
    lateinit var studentNumber:String

    lateinit var textView: TextView
    lateinit var imageButton: ImageButton
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_sign)
        number = this.intent.getStringExtra("number")!!
        studentNumber = this.intent.getStringExtra("studentNumber")!!

        textView = findViewById(R.id.studentSignTimeText)
        imageButton = findViewById(R.id.studentSignReturnButton)
        imageButton.setOnClickListener {
            finish()
        }

        locationClient = LocationClient(applicationContext)
        locationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(p0: BDLocation?) {
                latitude = p0!!.getLatitude() //获取纬度信息
                longitude = p0!!.getLongitude() //获取经度信息
                errorCode = p0!!.getLocType() //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                Toast.makeText(applicationContext, "经纬度： $latitude,$longitude 返回码: $errorCode", Toast.LENGTH_LONG).show()
                locationClient.stop()
            }
        })
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Device_Sensors
        option.isOpenGps = true
        option.enableSimulateGps = true
        option.isIgnoreCacheException = true
        locationClient.locOption = option

        button = findViewById(R.id.studentSignButton)
        button.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_ACCESS_COARSE_LOCATION
            )
            locationClient.start()
            finish()
        }
    }
}