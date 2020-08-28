package com.example.youtubeplayer

//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
//import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "kSFJGEHDCrQ"
const val YOUTUBE_PLAYLIST = "jzbMxWvnlGw&list=PLK3AVFrbB4s5m5w_k6V6H67X4uGPuZwg6"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YouTubeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)
        val layout =  findViewById<ConstraintLayout>(R.id.activity_youtube)

        //val button1 = Button(this)
        //button1.layoutParams= ConstraintLayout.LayoutParams(600,180)
        //button1.text = "Button added"
        //layout.addView(button1)

        val playerView = YouTubePlayerView(this)
        playerView.layoutParams=ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY),this)

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: youTubePlayer is ${youTubePlayer?.javaClass}")
        Toast.makeText(this,"Initialized YouTube Player successfully", Toast.LENGTH_SHORT).show()

        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored){
            youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        }else{
            youTubePlayer?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE = 0
        if (youTubeInitializationResult?.isUserRecoverableError == true){
            youTubeInitializationResult?.getErrorDialog(this,REQUEST_CODE)?.show()
        } else {
            val errorMessage = "this was an error initializing the youtube player ($youTubeInitializationResult)"
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener{
        override fun onSeekTo(p0: Int) {
        }

        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity,"Video is playing as needed",Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity,"Video has stopped",Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity,"Video is paused as needed",Toast.LENGTH_SHORT).show()

        }
    }
    private val playerStateChangeListener= object : YouTubePlayer.PlayerStateChangeListener{
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity,"Advertisement started",Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity,"Video has started",Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {

        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity,"Video is Finished",Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {

        }
    }


}