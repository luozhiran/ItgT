package com.sup.itg.itgt.java.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sup.itg.itgt.R

class KMvvmActivity : AppCompatActivity() {

    var changeData:Button? = null
    var count:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kmvvm)
    }

}
