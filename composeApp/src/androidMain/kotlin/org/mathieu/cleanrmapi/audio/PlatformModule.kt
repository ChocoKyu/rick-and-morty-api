package org.mathieu.cleanrmapi.audio

import android.content.Context
import org.koin.dsl.module
import org.mathieu.cleanrmapi.common.SoundPlayer

// Fournit le SoundPlayer spécifique à Android à Koin
fun platformModule(context: Context) = module {
    single<SoundPlayer> { AndroidSoundPlayer(context) }
}
