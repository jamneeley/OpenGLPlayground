package com.example.jamesneeley.glproject35.util

import android.content.Context
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.RuntimeException
import java.lang.StringBuilder
import kotlin.contracts.contract

object TextResourceReader {

    fun readTextFromResource(contex: Context, id: Int): String {
        val body = StringBuilder()

        try {
            val inputStream = contex.resources.openRawResource(id)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)


            var line: String?
            do {
                line = bufferedReader.readLine()

                if (line == null) {
                    break
                }

                body.append(line)
                body.append("\n")
            } while (true)


        } catch (error: IOException) {
            throw RuntimeException("Error reading resource. Error: $error")
        } catch (nfe: Resources.NotFoundException) {
            throw RuntimeException("Error finding resource: Error: $nfe")
        }
        return body.toString()
    }
}