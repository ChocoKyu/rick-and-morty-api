package org.mathieu.cleanrmapi.audio

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.common.SoundPlayer

class AndroidSoundPlayer(private val context: Context) : SoundPlayer {

    override fun play(url: String) {
        val mediaPlayer = MediaPlayer().apply {
            println("MediaPlayer PLAY")

            // Définit la source audio depuis l'URL
            setDataSource(url)

            // Démarre la lecture une fois que le son est prêt
            setOnPreparedListener { it.start() }

            setOnErrorListener { mp, what, extra ->
                println("MediaPlayer error: what=$what extra=$extra")
                true
            }

            // Préparation asynchrone pour éviter de bloquer le thread principal
            prepareAsync()
        }
    }
}
