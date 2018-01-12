package com.cherny.clipnote.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.cherny.clipnote.R
import com.cherny.clipnote.SplashActivity
import com.cherny.clipnote.entity.Configuration
import com.cherny.clipnote.service.RemoteStore
import kotlinx.android.synthetic.main.activity_set_host.*

interface SetHostCallback {
    fun onResponse(code:Int)
}

class SetHostActivity : AppCompatActivity(),SetHostCallback {

    private lateinit var host:String

    override fun onResponse(code:Int) {

        if (code != 1)
        {
            set_host_resurt.visibility = View.VISIBLE
            return
        }
        val config = Configuration()
        config.HOST = this.host
        Config.saveConfiguration(this,config)

        val intent = Intent()
        intent.setClass(this,SplashActivity::class.java)
        startActivity(intent)
        this.finish()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_host)

        set_host_test.setOnClickListener({
            this.host = set_host_input.text.trim().toString()
            RemoteStore.ping(this.host,this)
        })

        set_host_input.onFocusChangeListener = View.OnFocusChangeListener { _, p1 ->
            if (p1) {
                set_host_resurt.visibility = View.GONE
            }
        }
    }
}
