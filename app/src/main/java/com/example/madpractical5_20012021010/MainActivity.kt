package com.example.madpractical5_20012021010

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.madpractical5_20012021010.databinding.ActivityMainBinding
import kotlinx.coroutines.NonCancellable.start


class MainActivity : AppCompatActivity() {
    private lateinit var player: MediaPlayer


    private lateinit var binding: ActivityMainBinding
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        fun play(){
            Intent(applicationContext,MyService::class.java).putExtra(MyService.DATA_KEY,MyService.DATA_VALUE).apply { startService(this) }
        }

        fun stop() {
            Intent(applicationContext, MyService::class.java).apply { stopService(this) }
        }

        binding.btnShuffle.setOnClickListener{
            stop()
            play()
        }

        binding.btnPrevious.setOnClickListener{
            play()
        }

        binding.btnPlay.setOnClickListener{

            if (!this::player.isInitialized){
                player=MediaPlayer.create(this,R.raw.song)
            }
            if(player.isPlaying){
                player.pause()
                binding.btnPlay.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24))

            }
            else{
                binding.btnPlay.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_24))
                player.start()
            }
        }

        binding.btnNext.setOnClickListener{

            play()
        }

        binding.btnStop.setOnClickListener{
            if (!this::player.isInitialized){
                player=MediaPlayer.create(this,R.raw.song)
            }
            player.pause()
            binding.btnPlay.setImageDrawable(getDrawable(R.drawable.ic_baseline_play_arrow_24))
        }

        binding.slider.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                player=MediaPlayer.create(applicationContext,R.raw.song)
                player.seekTo(progress)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                play()
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                stop()
            }
        })

    }
}