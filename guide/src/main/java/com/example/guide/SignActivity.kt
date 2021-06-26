package com.example.guide

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption

class SignActivity : AppCompatActivity() {
    lateinit var locationClient:LocationClient
    private val REQUEST_CODE_ACCESS_COARSE_LOCATION = 1
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var errorCode: Int = 0
    var time1:Int = 0
    var time2:Int = 0

    lateinit var number:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        number = this.intent.getStringExtra("number")!!
        Toast.makeText(this,"number:$number",Toast.LENGTH_SHORT).show()

        val button1:Button = findViewById(R.id.signButton)
        val button2:Button = findViewById(R.id.signButton2)
        val spinner1 = findViewById<Spinner>(R.id.spinnerSign1)
        val spinner2 = findViewById<Spinner>(R.id.spinnerSign2)
        val switch = findViewById<Switch>(R.id.switchTimeSign)
        val timeLayout:LinearLayout = findViewById(R.id.signTimeSetting)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                timeLayout.visibility = View.VISIBLE
            }else{
                timeLayout.visibility = View.GONE
            }
        }

        val dataset1 = ArrayList<Int>()
        val dataset2 = ArrayList<Int>()
        for (i in 0..59){
            dataset1.add(i)
            dataset2.add(i)
        }
        val adapt1 = ArrayAdapter<Int>(this,R.layout.support_simple_spinner_dropdown_item,dataset1)
        val adapt2 = ArrayAdapter<Int>(this,R.layout.support_simple_spinner_dropdown_item,dataset2)
        spinner1.adapter = adapt1
        spinner2.adapter = adapt2
        spinner1.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                time1 = dataset1[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        spinner2.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                time2 = dataset2[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        locationClient = LocationClient(applicationContext)
        locationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(p0: BDLocation?) {
                latitude = p0!!.getLatitude() //获取纬度信息
                longitude = p0!!.getLongitude() //获取经度信息
                errorCode = p0!!.getLocType() //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                if (switch.isChecked)
                    Toast.makeText(applicationContext, "经纬度： $latitude,$longitude 返回码: $errorCode 时间 $time1 分 $time2 秒 ",Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(applicationContext, "经纬度： $latitude,$longitude 返回码: $errorCode",Toast.LENGTH_LONG).show()
                locationClient.stop()
            }
        })
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Device_Sensors
        option.isOpenGps = true
        option.enableSimulateGps = true
        option.isIgnoreCacheException = true
        locationClient.locOption = option

        button1.setOnClickListener {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_ACCESS_COARSE_LOCATION
            )
            locationClient.start()
            finish()
        }
        button2.setOnClickListener {
            finish()
        }
    }
}