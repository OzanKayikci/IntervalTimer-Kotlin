package com.example.intervaltimer.activities.timeInterval

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.intervaltimer.databinding.IntervalSettingsBinding


class TimeIntervalSettingsActivity : AppCompatActivity() {

    lateinit var binding: IntervalSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = IntervalSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onClickEvents()
    }

    private fun onClickEvents(){
        //set work time
        binding.btnMinusHours.setOnClickListener {
            decreaseValue(binding.etMinutes,binding.etHours)
        }
        binding.btnPlusHours.setOnClickListener {
            increaseValue(binding.etMinutes,binding.etHours)
        }

        //set rest time
        binding.btnMinusHours2.setOnClickListener {
            decreaseValue(binding.etMinutes2,binding.etHours2)
        }
        binding.btnPlusHours2.setOnClickListener {
            increaseValue(binding.etMinutes2,binding.etHours2)
        }

        //set set
        binding.setMinus.setOnClickListener {
            val value = binding.setText.text.toString().toInt()
            binding.setText.setText( if(value>0) (value-1).toString() else value.toString())
        }
        binding.setPlus.setOnClickListener {
            val value = binding.setText.text.toString().toInt()
            binding.setText.setText( (value+1).toString())
        }

        binding.startTimer.setOnClickListener {


            var intent = Intent(this,IntervalTimerActivity::class.java)
            intent.putExtra("hoursW", binding.etHours.text.toString())
            intent.putExtra("minutesW", binding.etMinutes.text.toString())
            intent.putExtra("hoursR", binding.etHours2.text.toString())
            intent.putExtra("minutesR", binding.etMinutes2.text.toString())
            intent.putExtra("sets",binding.setText.text.toString())
            startActivity(intent)
        }
    }
    private fun decreaseValue(editText: EditText, overFlowText:EditText) {
        val currentValue = editText.text.toString().toInt()
        val overFlowValue:Int = overFlowText.text.toString().toInt()

        if (currentValue > 0) {
            val newValue = if((currentValue - 1) <10 ) "0${currentValue-1}" else (currentValue-1).toString()
            editText.setText(newValue)
        }
       else {
            if(overFlowValue>0) {
                val newValue =
                    if ((overFlowValue - 1) < 10) "0${overFlowValue - 1}" else (overFlowValue - 1).toString()
                overFlowText.setText(newValue)
                editText.setText("59")
            }
            else {
                editText.setText("00")
                overFlowText.setText("00")
            }
        }
    }

    private fun increaseValue(editText: EditText, overFlowText:EditText) {
        val currentValue = editText.text.toString().toInt()
        val overFlowValue = overFlowText.text.toString().toInt()


        if (currentValue >= 59) {
            editText.setText("00")
            val newValue = if((overFlowValue + 1) <10 ) "0${overFlowValue+1}" else (overFlowValue+1).toString()
            overFlowText.setText(newValue)
        }
        else {
            val newValue = if((currentValue + 1) <10 ) "0${currentValue+1}" else (currentValue+1).toString()

            editText.setText(newValue)
        }
    }
}