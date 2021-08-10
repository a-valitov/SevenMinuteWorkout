package com.avalitov.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

lateinit var etMetricUnitHeight: EditText
lateinit var etMetricUnitWeight: EditText

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        etMetricUnitHeight = findViewById(R.id.et_metric_unit_height)
        etMetricUnitWeight = findViewById(R.id.et_metric_unit_weight)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Calculate BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btn_calculate_units.setOnClickListener {
            if(validateMetricUnits()){
                val heightValue : Float = etMetricUnitHeight.text.toString().toFloat() / 100    // cm -> m
                val weightValue : Float = etMetricUnitWeight.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)
                displayBMIResult(bmi)
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateMetricUnits(): Boolean {

        if(et_metric_unit_height.text.toString().isNullOrEmpty() || et_metric_unit_weight.text.toString().isNullOrEmpty()) {
            return false
        }

        if(et_metric_unit_height.text.toString().toInt() == 0) {
            return false
        }

        return true
    }

    private fun displayBMIResult(bmi : Float){
        val bmiLabel : String
        val bmiDescription : String

        when{
            (bmi <= 15f) -> {bmiLabel = "Very severely underweight";
                bmiDescription="Oops! You really need to take care of yourself! Eat more!"}
            ((15f < bmi) && (bmi <= 16f)) -> {bmiLabel = "Severely underweight";
                bmiDescription="Oops! You really need to take care of yourself! Eat more!"}
            ((16f < bmi) && (bmi <= 18.5f)) -> {bmiLabel = "Underweight";
                bmiDescription="Oops! You really need to take care of yourself! Eat more!"}
            ((18.5f < bmi) && (bmi <= 25f)) -> {bmiLabel = "Normal";
                bmiDescription="Congratulations! You are in a good shape!"}
            ((25f < bmi) && (bmi <= 30f)) -> {bmiLabel = "Overweight";
                bmiDescription="Oops! You really need to take care of yourself! Workout!"}
            ((30f < bmi) && (bmi <= 35f)) -> {bmiLabel = "Obese Class I (Moderately obese)";
                bmiDescription="Oops! You really need to take care of yourself! Workout!"}
            ((35f < bmi) && (bmi <= 40f)) -> {bmiLabel = "Obese Class II (Severely obese)";
                bmiDescription="Oops! You really need to take care of yourself! Workout!"}
            (bmi > 40f) -> {bmiLabel = "Godzilla";
                bmiDescription="Dude. No, really, dude."}
            else -> {bmiLabel = "Something went wrong.";
                bmiDescription="We couldn't calculate your BMI. Please check the inputs."}
        }

        tv_your_BMI.visibility = View.VISIBLE
        tv_BMI_value.visibility = View.VISIBLE
        tv_BMI_type.visibility = View.VISIBLE
        tv_BMI_description.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tv_BMI_value.text = bmiValue
        tv_BMI_type.text = bmiLabel
        tv_BMI_description.text = bmiDescription

    }


}