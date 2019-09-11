package com.example.jamesneeley.glproject3.util

import android.content.Context
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.RuntimeException

object TextResourceReader {
    fun readTextFileFromResource(context: Context, resourceID: Int): String {
        val body = StringBuilder()

        try {
            val inputStream = context.resources.openRawResource(resourceID)
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
            throw RuntimeException("Could not open resource: $resourceID: $error")
        } catch (nfError: Resources.NotFoundException) {
            throw RuntimeException("Resource not found: $resourceID, $nfError")

        }
        return body.toString()
    }
}
