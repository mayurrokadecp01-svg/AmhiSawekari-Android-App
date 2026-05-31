package com.rgi.amhisevikari.utils

import android.icu.text.Transliterator
import android.os.Build

object TransliterationUtils {
    
    fun transliterateMarathiToEnglish(marathiText: String): String {
        if (marathiText.isEmpty()) return ""
        
        return try {
            val baseTransliteration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // "Devanagari-Latin" converts the script. 
                // "nfd; [:nonspacing mark:] remove; nfc" removes accents/diacritics.
                val transliterator = Transliterator.getInstance("Devanagari-Latin; nfd; [:nonspacing mark:] remove; nfc")
                transliterator.transliterate(marathiText)
            } else {
                marathiText
            }
            
            var result = baseTransliteration.toLowerCase()
            
            val colloquialVariants = mapOf(
                "ganapati" to "ganapati ganpati ganesh",
                "datta" to "datta datt",
                "viththala" to "viththala vitthal vithal",
                "sankara" to "sankara shankar",
                "siva" to "siva shiv",
                "krsna" to "krsna krishna",
                "svami" to "svami swami",
                "samartha" to "samartha samarth",
                "maharaja" to "maharaja maharaj"
            )
            
            for ((strict, variants) in colloquialVariants) {
                result = result.replace(strict, variants)
            }
            
            result
        } catch (e: Exception) {
            marathiText
        }
    }
}
