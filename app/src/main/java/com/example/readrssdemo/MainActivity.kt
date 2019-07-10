package com.example.readrssdemo

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.w3c.dom.Element
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runOnUiThread {
            ReadDataTask().execute("https://vnexpress.net/rss/the-gioi.rss")
        }
    }

    inner class ReadDataTask: AsyncTask<String, Integer, String>() {
        override fun doInBackground(vararg params: String?): String? {
            return readDataFromUrl(params[0].toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var parser = XmlDOMParser()
            var document = parser.getDocument(result.toString())
            var nodeList = document?.getElementsByTagName("item")
            var title = ""
            var link = ""
            for (i in 0 until nodeList?.length!!) {
                var element = nodeList.item(i) as Element
                title += parser.getValue(element, "title")
                link = parser.getValue(element, "link")
                Log.d("MainActivity", link)
            }

        }

    }

    private fun readDataFromUrl(theUrl: String): String? {
        try {
            var content = StringBuilder()
            val url = URL(theUrl)
            val urlConnection = url.openConnection()
            var bufferedReader = BufferedReader(InputStreamReader(urlConnection.getInputStream()))

            var line: String? = bufferedReader.readLine()
            while(line != null) {
                content.append(line + "\n")
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
            return content.toString()
        } catch (e: Exception) {

        }

        return null
    }
}
