package com.example.jamesneeley.glproject5.util

import android.content.Context
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.RuntimeException
import java.lang.StringBuilder

object TextResourceReader {

    fun readResourceFromID(context: Context, id: Int): String {
        val body = StringBuilder()

        try {
            val inputString = context.resources.openRawResource(id)
            val inputStreamReader = InputStreamReader(inputString)
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
            throw RuntimeException("problem reading resource")
        } catch (nfe: Resources.NotFoundException) {
            throw RuntimeException("Resource not found")
        }
        return body.toString()
    }
}