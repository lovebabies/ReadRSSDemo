package com.example.readrssdemo

import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException


class XmlDOMParser {
    fun getDocument(xml: String): Document? {
        var document: Document? = null
        var factory: DocumentBuilderFactory? = DocumentBuilderFactory.newInstance()
        try {
            var db = factory?.newDocumentBuilder()
            var inputSource = InputSource()
            inputSource.characterStream = StringReader(xml)
            inputSource.encoding = "UTF-8"
            document = db?.parse(inputSource)
        } catch (e: ParserConfigurationException) {
            Log.e("ERROR", e.message, e)
            return null
        } catch (e: SAXException) {
            Log.e("ERROR", e.message, e)
            return null
        } catch (e: IOException) {
            Log.e("ERROR", e.message, e)
            return null
        }
        return document
    }

    fun getValue(item: Element, name: String): String {
        var nodes = item.getElementsByTagName(name)
        return this.getTextNodeValue(nodes.item(0))
    }

    fun getTextNodeValue(element: Node?): String {
        var child: Node? = null
        if (element != null) {
            if (element.hasChildNodes()) {
                child = element.firstChild
                while (child != null) {
                    if (child.nodeType === Node.TEXT_NODE) {
                        return child.nodeValue
                    }
                    child = child.nextSibling
                }

            }
        }

        return ""
    }
}