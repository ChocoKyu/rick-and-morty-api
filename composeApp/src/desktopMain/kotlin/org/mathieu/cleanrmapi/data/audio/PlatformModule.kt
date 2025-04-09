package org.mathieu.cleanrmapi.data.audio


import org.koin.dsl.module
import org.mathieu.cleanrmapi.common.SoundPlayer

// Fournit le SoundPlayer spécifique au Desktop à Koin
fun platformModule() = module {
    single<SoundPlayer> { DesktopSoundPlayer() }
}