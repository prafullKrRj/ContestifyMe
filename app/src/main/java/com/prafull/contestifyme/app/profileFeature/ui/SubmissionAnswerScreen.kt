package com.prafull.contestifyme.app.profileFeature.ui

import android.annotation.SuppressLint
import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SubmissionAnswer(url: String) {
    AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
        WebView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams(measuredWidth, LayoutParams.MATCH_PARENT)
            )
            webViewClient = WebViewClient()
            loadUrl(url)
            settings.builtInZoomControls = true
            settings.cacheMode = android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK

        }
    }, update = {
        it.loadUrl(url)
        it.minimumHeight
        it.canGoBack()
    })
}
