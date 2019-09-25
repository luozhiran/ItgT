package com.sup.itg.itgt.java.struct.linkStruct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sup.itg.itgt.R
import com.sup.itg.itgt.databinding.ActivityBaseLinkBinding

class BaseLinkActivity : AppCompatActivity() {
    var mBind: ActivityBaseLinkBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_base_link)

    }
}
