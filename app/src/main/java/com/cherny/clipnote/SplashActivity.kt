package com.cherny.clipnote

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cherny.clipnote.entity.Configuration
import com.cherny.clipnote.notelist.MainListActivity
import com.cherny.clipnote.service.RequestURL
import com.cherny.clipnote.setting.Config
import com.cherny.clipnote.setting.SetHostActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var config:Configuration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.supportActionBar?.hide()

        if (this.hasConfig()) {
            val intent = Intent()
            intent.setClass(this, MainListActivity::class.java)
            startActivity(intent)
            RequestURL.HOST = this.config.HOST
            this.finish()
        } else {
            val intent = Intent()
            intent.setClass(this,SetHostActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun hasConfig() :Boolean {
        this.config = Config.getConfiguration(this) ?: return false
        return true
    }
}
