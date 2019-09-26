package com.sup.itg.itgt.java.struct.linkStruct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sup.itg.base.ItgL
import com.sup.itg.itgt.R
import com.sup.itg.itgt.databinding.ActivityBaseLinkBinding

class BaseLinkActivity : AppCompatActivity(), View.OnClickListener {


    var mBind: ActivityBaseLinkBinding? = null
    var mHead: BaseLinkData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_base_link)
        mBind.let {
            it?.createLink?.setOnClickListener(this)
            it?.print?.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.createLink -> {
                createLikn()
            }
            R.id.print -> {
                var next = mHead;
                while (next != null) {
                    ItgL.e(next.content)
                    next = next.next
                }
            }
        }
    }


    fun createLikn() {
        if (mHead == null) {
            mHead = BaseLinkData()
            mHead?.content = "第0个"
            mHead?.index = 0
            var next: BaseLinkData? = null
            var pre = mHead
            for (i in 1..11) {
                next = BaseLinkData()
                next.content = "第${i}个"
                next.index = i
                pre?.next = next
                pre = next
            }
        } else {
            Toast.makeText(this, "链表已经存在", Toast.LENGTH_SHORT)
        }

    }
}
