package com.rula.svacy

import android.app.Activity
import android.webkit.WebView
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import android.os.Build
import android.webkit.WebViewClient
import android.annotation.TargetApi
import android.view.View
import android.webkit.WebResourceRequest
import android.widget.*
import java.util.ArrayList

class WebActivity : Activity() {
    var searchedit: EditText? = null
    var closebut: ImageView? = null
    var urledittext: EditText = findViewById(R.id.urledittext)
    var webView: WebView = findViewById(R.id.webView)
    var pointsMenu = ArrayList<String>()
    var listView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        searchedit = findViewById(R.id.searchedit)
        closebut = findViewById(R.id.closebut)
        listView = findViewById(R.id.listView)
        webView.getSettings().javaScriptEnabled = true
        webView.setWebViewClient(MyWebViewClient())
        val url = intent.data
        webView.loadUrl(url.toString())
        urledittext.setText(url.toString())
    }

    fun toSetting(view: View?) {
        setContentView(R.layout.settings)
        pointsMenu.add("Аккаунт")
        pointsMenu.add("Персонализация")
        pointsMenu.add("История")
        pointsMenu.add("Настройки страниц")
        pointsMenu.add("Дополнительно")
        val arrayAdapter = ArrayAdapter(this@WebActivity,
                android.R.layout.simple_list_item_1, pointsMenu)
        listView!!.adapter = arrayAdapter
        listView!!.onItemClickListener = OnItemClickListener { adapterView: AdapterView<*>?, view1: View?, i: Int, l: Long ->
            when (i) {
                0 -> setContentView(R.layout.account)
                1 -> setContentView(R.layout.personalisation)
                2 -> setContentView(R.layout.history)
                3 -> setContentView(R.layout.pagesettings)
                4 -> setContentView(R.layout.advanced)
            }
        }
    }

    fun toSearch(view: View?) {
        if (searchedit!!.visibility != View.VISIBLE) {
            searchedit!!.visibility = View.VISIBLE
            closebut!!.visibility = View.VISIBLE
            urledittext!!.visibility = View.GONE
        } else {
            webView!!.loadUrl("https://www.google.com/search?q=" + searchedit!!.text)
        }
    }

    fun close(view: View?) {
        searchedit!!.visibility = View.GONE
        closebut!!.visibility = View.GONE
        urledittext!!.visibility = View.VISIBLE
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun smline(view: View?) {
        if (urledittext!!.isSingleLine || searchedit!!.isSingleLine) {
            urledittext!!.isSingleLine = false
            searchedit!!.isSingleLine = false
        } else {
            urledittext!!.isSingleLine = true
            searchedit!!.isSingleLine = true
        }
    }

    private inner class MyWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            urledittext!!.setText(request.url.toString())
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            urledittext!!.setText(url)
            return true
        }
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }
}