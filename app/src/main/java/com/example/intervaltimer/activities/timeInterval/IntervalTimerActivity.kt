package com.example.intervaltimer.activities.timeInterval

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.intervaltimer.R
import com.example.intervaltimer.databinding.IntervalTimerScreenBinding
import java.util.Locale
import java.util.concurrent.TimeUnit


class IntervalTimerActivity : AppCompatActivity() {

    private lateinit var binding: IntervalTimerScreenBinding

    lateinit var timer: CountDownTimer
    var globalisWork = true
     var remainingSets:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = IntervalTimerScreenBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)
        var bundle: Bundle = intent.extras!!
        var hoursW: String? = bundle.getString("hoursW")
        var minutesW: String? = bundle.getString("minutesW")
        var hoursR: String? = bundle.getString("hoursR")
        var minutesR: String? = bundle.getString("minutesR")
        var sets: String? = bundle.getString("sets")

        remainingSets  = sets.toString().toInt()
        timer(hoursW!!, minutesW!!, hoursR!!, minutesR!!, sets!!, globalisWork)
        clickEvents(hoursW!!, minutesW!!, hoursR!!, minutesR!!, sets!!)
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    private fun timer(
        hoursW: String,
        minutesW: String,
        hoursR: String,
        minutesR: String,
        sets: String,
        isWork: Boolean
    ) {
        var hours: String?
        var minutes: String?

        val greenColor: Int = ContextCompat.getColor(this, R.color.green)
        binding.remainingSet.text = if (remainingSets!!.toInt() > 0) remainingSets!!.toString() else "Last Set"
        //set end time in milisecond
        if (isWork) {
            hours = hoursW
            minutes = minutesW
            binding.setName.text = "WORK"
            val color = ContextCompat.getColor(this, R.color.blue)
            binding.setName.setTextColor(color)
            println("work" + hours + minutes + "work")

        } else {
            hours = hoursR
            minutes = minutesR
            binding.setName.text = "REST"
            val color = ContextCompat.getColor(this, R.color.orange2)
            binding.setName.setTextColor(color)
            println("rest" + hours + minutes + "rest")

        }
        val duration = (hours.toInt() * 60 + minutes.toString().toInt())*60

        timer = object : CountDownTimer((duration * 1000).toLong(), 1000) {
            override fun onTick(remaining: Long) {
                var time: String = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(remaining),
                    TimeUnit.MILLISECONDS.toMinutes(remaining) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(remaining)
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(remaining) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            remaining
                        )
                    ),
                    Locale.getDefault()
                )
                var hourMinSec: List<String> = time.split(":");
                binding.hour.text = hourMinSec[0]
                binding.minute.text = hourMinSec[1]
                binding.second.text = hourMinSec[2]
            }

            override fun onFinish() {
                goNext(hoursW!!, minutesW!!, hoursR!!, minutesR!!, sets!!, isWork)
            }

        }

    }

    fun goNext(
        hoursW: String,
        minutesW: String,
        hoursR: String,
        minutesR: String,
        sets: String,
        isWork: Boolean
    ) {
        remainingSets=
            if (!isWork || sets.toInt() == 0) remainingSets!!.toInt() - 1 else remainingSets!!.toInt()
        if (remainingSets!! >= 0) {
            globalisWork = !isWork
            timer(hoursW!!, minutesW!!, hoursR!!, minutesR!!, remainingSets.toString()!!, !isWork)
            onStart()

        } else {
            val greenColor: Int = ContextCompat.getColor(this, R.color.green)
            binding.setName.setTextColor(greenColor)
            binding.setName.text = "DONE"
            onStop()
        }
    }

    fun goBack(
        hoursW: String,
        minutesW: String,
        hoursR: String,
        minutesR: String,
        sets: String,
        isWork: Boolean
    ) {
        println("remaining"+ remainingSets!!)
        remainingSets = if(remainingSets!!<0)  0 else remainingSets
         remainingSets =
            if (isWork && remainingSets!! < sets.toString().toInt() ) remainingSets!!.toInt() + 1 else remainingSets!!.toInt()

        globalisWork = !isWork
        timer(hoursW!!, minutesW!!, hoursR!!, minutesR!!, remainingSets.toString()!!, !isWork)
        onStart()

    }

    fun clickEvents(
        hoursW: String,
        minutesW: String,
        hoursR: String,
        minutesR: String,
        sets: String,

    ) {
        binding.nextSession.setOnClickListener {
            onStop()
            goNext(hoursW!!, minutesW!!, hoursR!!, minutesR!!, sets!!, globalisWork)
        }
        binding.previousSession.setOnClickListener {
            onStop()
            goBack(hoursW!!, minutesW!!, hoursR!!, minutesR!!, sets!!,globalisWork)
        }
    }
}