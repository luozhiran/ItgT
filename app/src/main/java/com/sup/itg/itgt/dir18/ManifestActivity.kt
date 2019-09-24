package com.sup.itg.itgt.dir18

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.TextView
import com.sup.itg.base.ItgHandlerActivity
import com.sup.itg.base.ItgL
import com.sup.itg.itgt.R

class ManifestActivity : ItgHandlerActivity() {
    var tvShow: TextView? = null

    override fun handleMessage(msg: Message?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_manifest
    }

    override fun initView() {
        tvShow = findViewById(R.id.show)
        tvShow?.setOnClickListener {
            manifestParse(applicationContext)
        }

    }

    override fun initData() {

    }


    fun manifestParse(context: Context) {
        var appInfo = context.applicationInfo
        var result = appInfo?.metaData ?: "empty"
        ItgL.e(result as String)
    }

}
