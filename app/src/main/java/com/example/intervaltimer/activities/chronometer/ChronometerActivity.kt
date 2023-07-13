package com.example.intervaltimer.activities.chronometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import com.example.intervaltimer.R
import com.example.intervaltimer.databinding.ChronometerBinding

class ChronometerActivity : AppCompatActivity() {

    lateinit var binding: ChronometerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ChronometerBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        var stopTime:Long = 0

        binding.btnStart.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime()+stopTime
            binding.chronometer.start()
            binding.btnStart.visibility= View.GONE
            binding.btnPause.visibility = View.VISIBLE
            binding.imageView.setImageDrawable(getDrawable(R.drawable.pause))
        }
        binding.btnPause.setOnClickListener {
            stopTime = binding.chronometer.base-SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            binding.btnStart.visibility= View.VISIBLE
            binding.btnPause.visibility = View.GONE
            binding.imageView.setImageDrawable(getDrawable(R.drawable.start))
        }
        binding.btnReset.setOnClickListener {
            stopTime = 0
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.btnStart.visibility= View.VISIBLE
            binding.btnPause.visibility = View.GONE
            binding.imageView.setImageDrawable(getDrawable(R.drawable.start))
        }
    }
}