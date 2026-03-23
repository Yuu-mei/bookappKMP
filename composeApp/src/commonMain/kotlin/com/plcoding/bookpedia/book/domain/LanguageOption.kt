package com.plcoding.bookpedia.book.domain

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.lang_arabic
import cmp_bookpedia.composeapp.generated.resources.lang_chinese
import cmp_bookpedia.composeapp.generated.resources.lang_czech
import cmp_bookpedia.composeapp.generated.resources.lang_danish
import cmp_bookpedia.composeapp.generated.resources.lang_dutch
import cmp_bookpedia.composeapp.generated.resources.lang_english
import cmp_bookpedia.composeapp.generated.resources.lang_finnish
import cmp_bookpedia.composeapp.generated.resources.lang_french
import cmp_bookpedia.composeapp.generated.resources.lang_german
import cmp_bookpedia.composeapp.generated.resources.lang_greek
import cmp_bookpedia.composeapp.generated.resources.lang_hebrew
import cmp_bookpedia.composeapp.generated.resources.lang_hindi
import cmp_bookpedia.composeapp.generated.resources.lang_hungarian
import cmp_bookpedia.composeapp.generated.resources.lang_italian
import cmp_bookpedia.composeapp.generated.resources.lang_japanese
import cmp_bookpedia.composeapp.generated.resources.lang_korean
import cmp_bookpedia.composeapp.generated.resources.lang_norwegian
import cmp_bookpedia.composeapp.generated.resources.lang_polish
import cmp_bookpedia.composeapp.generated.resources.lang_portuguese
import cmp_bookpedia.composeapp.generated.resources.lang_russian
import cmp_bookpedia.composeapp.generated.resources.lang_spanish
import cmp_bookpedia.composeapp.generated.resources.lang_swedish
import cmp_bookpedia.composeapp.generated.resources.lang_turkish
import org.jetbrains.compose.resources.StringResource

data class LanguageOption(
    val name: StringResource,
    val code: String
)

val supportedLanguages = listOf<LanguageOption>(
    LanguageOption(Res.string.lang_english, "eng"),
    LanguageOption(Res.string.lang_spanish, "spa"),
    LanguageOption(Res.string.lang_french, "fra"),
    LanguageOption(Res.string.lang_german, "deu"),
    LanguageOption(Res.string.lang_italian, "ita"),
    LanguageOption(Res.string.lang_portuguese, "por"),
    LanguageOption(Res.string.lang_russian, "rus"),
    LanguageOption(Res.string.lang_chinese, "zho"),
    LanguageOption(Res.string.lang_japanese, "jpn"),
    LanguageOption(Res.string.lang_arabic, "ara"),
    LanguageOption(Res.string.lang_hindi, "hin"),
    LanguageOption(Res.string.lang_dutch, "nld"),
    LanguageOption(Res.string.lang_swedish, "swe"),
    LanguageOption(Res.string.lang_norwegian, "nor"),
    LanguageOption(Res.string.lang_danish, "dan"),
    LanguageOption(Res.string.lang_finnish, "fin"),
    LanguageOption(Res.string.lang_greek, "ell"),
    LanguageOption(Res.string.lang_turkish, "tur"),
    LanguageOption(Res.string.lang_polish, "pol"),
    LanguageOption(Res.string.lang_czech, "ces"),
    LanguageOption(Res.string.lang_hungarian, "hun"),
    LanguageOption(Res.string.lang_hebrew, "heb"),
    LanguageOption(Res.string.lang_korean, "kor")
)
