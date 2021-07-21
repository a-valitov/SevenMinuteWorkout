package com.avalitov.a7minuteworkout

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import org.w3c.dom.Text
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

lateinit var llRestView : LinearLayout
lateinit var llExerciseView : LinearLayout
lateinit var toolbarExerciseActivity : androidx.appcompat.widget.Toolbar
lateinit var tvTimerRest : TextView
lateinit var tvTimerExercise : TextView
lateinit var ivImage : ImageView
lateinit var tvExerciseName : TextView
lateinit var tvUpcomingExercise : TextView

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null // variable for TextToSpeech
    private var player: MediaPlayer? = null // variable for Media Player

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    //for TextToSpeech
    override fun onInit(status: Int) {
        //check if TTS works
        if(status == TextToSpeech.SUCCESS) {
            //set US English as a language for TTS
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported.")
                Toast.makeText(this, "The language specified is not supported", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.e("TTS", "Initialization failed.")
            Toast.makeText(this, "Initialization failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        llRestView = findViewById(R.id.llRestView)
        llExerciseView = findViewById(R.id.llExerciseView)
        toolbarExerciseActivity = findViewById(R.id.toolbar_exercise_activity)
        tvTimerRest = findViewById(R.id.tv_timer_rest)
        tvTimerExercise = findViewById(R.id.tv_timer_exercise)
        ivImage = findViewById(R.id.iv_image)
        tvExerciseName = findViewById(R.id.tv_exercise_name)
        tvUpcomingExercise = findViewById(R.id.tv_upcoming_exercise)

        setSupportActionBar(toolbarExerciseActivity)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        toolbarExerciseActivity.setNavigationOnClickListener{
            onBackPressed()
        }

        // Initialize the TextToSpeech
        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()

        setupRestView()
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null) {
            player!!.stop()
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
                currentExercisePosition++
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
                if(currentExercisePosition < exerciseList!!.size - 1) {
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity,
                    "Congratulations! You have completed your 7 minutes workout!",
                    Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }.start()
    }

    private fun setupRestView(){

        try{
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        llExerciseView.visibility = View.GONE
        llRestView.visibility = View.VISIBLE
        setRestProgressBar()
        tvUpcomingExercise.text = exerciseList!![currentExercisePosition + 1].getName()
        speakOut("Get ready for:" + tvUpcomingExercise.text)
        //TODO: this fucktard doesn't speak the first exercise
    }

    private fun setupExerciseView(){
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        speakOut((exerciseList!![currentExercisePosition].getName()))

        setExerciseProgressBar()

        //showing the name and image of a new exercise
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImageNumber())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

}




