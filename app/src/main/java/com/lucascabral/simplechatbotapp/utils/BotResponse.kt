package com.lucascabral.simplechatbotapp.utils

import com.lucascabral.simplechatbotapp.utils.Constants.OPEN_GOOGLE
import com.lucascabral.simplechatbotapp.utils.Constants.OPEN_SEARCH
import java.lang.Exception
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object BotResponse {

    fun basicResponse(_message: String): String {
        val random = (0..2).random()
        val message = _message.lowercase(Locale.getDefault())

        return when {

            message.contains("oi") || message.contains("ola")
                    || message.contains("olá") || message.contains("hello") -> {
                when (random) {
                    0 -> "Hello \uD83D\uDE04"
                    1 -> "Oi \uD83D\uDE03"
                    2 -> "Olá \uD83D\uDE09"
                    else -> "error \uD83D\uDE28"
                }
            }

            message.contains("como vai") || message.contains("tudo bem")
                    && message.contains("?") -> {
                when (random) {
                    0 -> "Eu estou bem e você? \uD83E\uDD37\uD83C\uDFFD\u200D♀️"
                    1 -> "Eu estou com fome \uD83D\uDE16"
                    2 -> "Muito bem! e com você? \uD83E\uDD37\uD83C\uDFFD\u200D♀️"
                    else -> "error \uD83D\uDE28"
                }
            }

            message.contains("estou bem") || message.contains("tudo bem") -> {
                when (random) {
                    0 -> "Que bom! \uD83D\uDE06"
                    1 -> "Legal! \uD83D\uDE04"
                    2 -> "Fico feliz \uD83D\uDE09"
                    else -> "error"
                }
            }

            message.contains("qual") && message.contains("linguagem")
                    && message.contains("favorita") -> {
                when (random) {
                    0 -> "Kotlin \uD83E\uDD70"
                    1 -> "Python \uD83D\uDE04"
                    2 -> "Assembly  \uD83E\uDD13"
                    else -> "error"
                }
            }

            message.contains("jogar") || message.contains("novamente")
                    && message.contains("moeda") -> {
                val random = (0..1).random()
                val result = if (random == 0) "cara" else "coroa"
                "Eu joguei uma moeda e caiu $result"
            }

            message.contains("calcule") -> {
                val equation: String? = message.substringAfterLast("calcule")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    answer.toString()
                } catch (e: Exception) {
                    "Sorry, I can't solve that! \uD83E\uDD7A"
                }
            }

            message.contains("horas") && message.contains("?") -> {
                Time.timeStamp()
            }

            message.contains("data de hoje") && message.contains("?") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val date = sdf.format(Date(timeStamp.time))
                date.toString()
            }

            message.contains("abrir") || message.contains("abra")
                    || message.contains("va para") || message.contains("vá para")
                    && message.contains("google") -> {
                OPEN_GOOGLE
            }

            message.contains("pesquise") || message.contains("pesquisar") -> {
                OPEN_SEARCH
            }

            else -> {
                when (random) {
                    0 -> "Eu não entendi \uD83E\uDD7A"
                    1 -> "Poderia perguntar novamente? \uD83E\uDD7A"
                    2 -> "Tenta me perguntar algo diferente! \uD83E\uDD7A"
                    else -> "error \uD83D\uDE28"
                }
            }
        }
    }
}