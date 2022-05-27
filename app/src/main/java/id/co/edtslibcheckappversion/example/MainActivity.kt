package id.co.edtslibcheckappversion.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.edtslibcheckappversion.CheckAppVersion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CheckAppVersion.check(this, "1.0.0")
    }
}