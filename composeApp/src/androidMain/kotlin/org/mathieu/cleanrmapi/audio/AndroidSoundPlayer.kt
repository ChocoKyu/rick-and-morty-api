package org.mathieu.cleanrmapi.audio

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.common.SoundPlayer
import java.io.IOException

class AndroidSoundPlayer(private val context: Context) : SoundPlayer {

    override fun play(url: String) {
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.setOnPreparedListener {
                it.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}