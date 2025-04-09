package org.mathieu.cleanrmapi.data.audio

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mathieu.cleanrmapi.common.SoundPlayer
import java.io.BufferedInputStream
import java.net.URL
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.LineEvent

class DesktopSoundPlayer : SoundPlayer {

    override fun play(url: String) {
        // Lancer dans un thread IO via coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Chargement et bufferisation du flux audio
                val audioStream = AudioSystem.getAudioInputStream(
                    BufferedInputStream(URL(url).openStream())
                )

                val clip = AudioSystem.getClip()
                clip.open(audioStream)

                // Ferme automatiquement la ressource après la lecture
                clip.addLineListener {
                    if (it.type == LineEvent.Type.STOP) {
                        clip.close()
                    }
                }

                clip.start()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
