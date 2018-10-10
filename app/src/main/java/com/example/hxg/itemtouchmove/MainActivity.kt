package com.example.hxg.itemtouchmove

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import com.example.hxg.itemtouchmove.channel.ChannelActivity
import com.example.hxg.itemtouchmove.custom.GiftView
import com.example.hxg.itemtouchmove.drag.DragActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_drag).apply {
            setOnClickListener { startActivity(Intent(this@MainActivity, DragActivity::class.java)) }
        }

        findViewById<Button>(R.id.btn_channl).apply {
            setOnClickListener { startActivity(Intent(this@MainActivity, ChannelActivity::class.java)) }
        }

        val giftView = findViewById<GiftView>(R.id.gift_view)

        findViewById<ImageButton>(R.id.btn_like).apply {
            setOnClickListener{ giftView.addImageView() }
        }
    }
}
