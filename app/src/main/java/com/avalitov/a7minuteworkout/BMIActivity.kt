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

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView : String = METRIC_UNITS_VIEW

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

            if(currentVisibleView == METRIC_UNITS_VIEW) {
                if(validateMetricUnits()){
                    val heightValueM : Float = etMetricUnitHeight.text.toString().toFloat() / 100    // cm -> m
                    val weightValueKg : Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValueKg / (heightValueM * heightValueM)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT).show()
                }
            } else {
                if(validateUSUnits()){
                    val heightValueIn : Float = (et_us_unit_height_feet.text.toString().toFloat() * 12) + (et_us_unit_height_inches.text.toString().toFloat())
                    val weightValueLbs : Float = et_us_unit_weight.text.toString().toFloat()

                    val bmi = 703 * (weightValueLbs / (heightValueIn * heightValueIn))
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT).show()
                }
            }

        }

        makeVisibleMetricUnitsView()

        rg_units.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.rb_metric_units){
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
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

    private fun validateUSUnits(): Boolean {

        if(et_us_unit_height_feet.text.toString().isNullOrEmpty()
                || et_us_unit_height_inches.text.toString().isNullOrEmpty()
                || et_us_unit_weight.text.toString().isNullOrEmpty()) {
            return false
        }

        if((et_us_unit_height_feet.text.toString().toInt() == 0) &&
                (et_us_unit_height_inches.text.toString().toInt() == 0)) {
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

        ll_display_BMI_result.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tv_BMI_value.text = bmiValue
        tv_BMI_type.text = bmiLabel
        tv_BMI_description.text = bmiDescription

    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW

        til_metric_unit_weight.visibility = View.VISIBLE
        til_metric_unit_height.visibility = View.VISIBLE

        et_metric_unit_height.text!!.clear()
        et_metric_unit_weight.text!!.clear()

        til_us_unit_weight.visibility = View.GONE
        ll_us_units_height.visibility = View.GONE

        ll_display_BMI_result.visibility = View.GONE
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW

        til_metric_unit_weight.visibility = View.GONE
        til_metric_unit_height.visibility = View.GONE

        et_us_unit_weight.text!!.clear()
        et_us_unit_height_feet.text!!.clear()
        et_us_unit_height_inches.text!!.clear()

        til_us_unit_weight.visibility = View.VISIBLE
        ll_us_units_height.visibility = View.VISIBLE

        ll_display_BMI_result.visibility = View.GONE
    }


}