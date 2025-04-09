package org.mathieu.cleanrmapi.audio

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.common.SoundPlayer

class AndroidSoundPlayer(private val context: Context) : SoundPlayer {

    override fun play(url: String) {
        val mediaPlayer = MediaPlayer().apply {
            println("MediaPlayer PLAY")
            setDataSource(url)
            setOnPreparedListener { it.start() }
            setOnErrorListener { mp, what, extra ->
                println("MediaPlayer error: what=$what extra=$extra")
                true
            }
            prepareAsync()
        }
    }
}
