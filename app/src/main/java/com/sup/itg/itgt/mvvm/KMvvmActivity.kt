package com.sup.itg.itgt.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.sup.itg.base.ItgL
import com.sup.itg.itgt.R
import com.sup.itg.itgt.databinding.ActivityKmvvmBinding

class KMvvmActivity : AppCompatActivity() {

    var changeData:Button? = null
    var count:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kmvvm)
    }

}
