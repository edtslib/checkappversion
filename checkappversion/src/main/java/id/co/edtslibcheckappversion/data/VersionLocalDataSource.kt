package id.co.edtslibcheckappversion.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VersionLocalDataSource(sharedPreferences: SharedPreferences): LocalDataSource<CacheData>(sharedPreferences) {
    override fun getKeyName(): String = "version"
    override fun getValue(json: String): CacheData = Gson().fromJson(json, object : TypeToken<CacheData>() {}.type)
}