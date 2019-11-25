package com.buck.internal

import com.buck.App

import java.util.*

object AppProperties {


    private const val PROPERTIES_NAME = "app.properties"

    private const val KEY_BASE_URL = "base.url"

    private val properties: Properties = Properties()

    fun initProperties() {
        properties.load(App.instance.assets.open(PROPERTIES_NAME))
    }

    var baseURL:String
        get() = properties.getProperty(KEY_BASE_URL, "http://192.168.124.142:8080/")
        set(value) {
            properties.setProperty(KEY_BASE_URL, value)
        }


//
//    fun getProperties(keyName: String, defaultValue: String): String {
//        Log.e("properties name", PROPERTIES_NAME)
//        val props = Properties()
//
//        var value: String = defaultValue
//
//        try {
//            //方法一：通过activity中的context攻取setting.properties的FileInputStream
//            //注意这地方的参数appConfig在eclipse中应该是appConfig.properties才对,但在studio中不用写后缀
//            //InputStream in = c.getAssets().open("appConfig.properties");
//
//            //props.load(context.getAssets().open(configName));
//
//            //方法二：通过class获取setting.properties的FileInputStream
//            //InputStream in = PropertiesUtill.class.getResourceAsStream("/assets/  setting.properties "));
//
//            // 方法三
//            props.load(App.instance.assets.open(PROPERTIES_NAME))
//            value = props.getProperty(keyName, defaultValue)
//
//        } catch (e1: Exception) {
//            e1.printStackTrace()
//        }
//        return value
//    }
//
//    //保存配置文件
//    fun setProperties(keyName: String, keyValue: String) {
//        val props = Properties()
//        try {
//            props.load(App.instance.openFileInput(PROPERTIES_NAME))
//            props.setProperty(keyName, keyValue)
//            // FileOutputStream out = context.getAssets().openFd(configPath).createOutputStream();
//            val out = App.instance.openFileOutput(PROPERTIES_NAME, Context.MODE_PRIVATE)
//            // FileOutputStream out = new FileOutputStream(configPath);
//            props.store(out, null)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.e("setPropertiesError", e.toString())
//        }
//
//    }
}