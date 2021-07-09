package com.avalitov.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*

lateinit var llRestView : LinearLayout
lateinit var llExerciseView : LinearLayout
lateinit var toolbarExerciseActivity : androidx.appcompat.widget.Toolbar
lateinit var tvTimerRest : TextView
lateinit var tvTimerExercise : TextView

class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        llRestView = findViewById(R.id.llRestView)
        llExerciseView = findViewById(R.id.llExerciseView)
        toolbarExerciseActivity = findViewById(R.id.toolbar_exercise_activity)
        tvTimerRest = findViewById(R.id.tv_timer_rest)
        tvTimerExercise = findViewById(R.id.tv_timer_exercise)

        setSupportActionBar(toolbarExerciseActivity)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        toolbarExerciseActivity.setNavigationOnClickListener{
            onBackPressed()
        }

        setupRestView()
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    private fun setRestProgressBar(){
        var progressBarRest : ProgressBar = findViewById(R.id.progressBar_rest)
        progressBarRest.progress = restProgress

        restTimer = object: CountDownTimer((Constants.REST_TIME * 1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBarRest.progress = Constants.REST_TIME - restProgress
                tvTimerRest.text = (Constants.REST_TIME - restProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise.",
                    Toast.LENGTH_SHORT
                ).show()

                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        var progressBarExercise : ProgressBar = findViewById(R.id.progressBar_exercise)
        progressBarExercise.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer((Constants.EXERCISE_TIME * 1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = Constants.EXERCISE_TIME - exerciseProgress
                tvTimerExercise.text = (Constants.EXERCISE_TIME - exerciseProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "The exercise has finished.",
                    Toast.LENGTH_SHORT
                ).show()

                setupRestView()
            }
        }.start()
    }

    private fun setupRestView(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        llExerciseView.visibility = View.INVISIBLE
        llRestView.visibility = View.VISIBLE
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        llRestView.visibility = View.INVISIBLE
        llExerciseView.visibility = View.VISIBLE
        setExerciseProgressBar()
    }
}




