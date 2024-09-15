package com.prafull.contestifyme.features.problemsFeature.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ProblemScreen(problemId: String) {
    val contestId = problemId.split("-")[0]
    val index = problemId.split("-")[1]
    WebViewComposable(url = "https://codeforces.com/problemset/problem/$contestId/$index")
}


@Composable
fun WebViewComposable(url: String) {
    var zoomLevel = 100
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
    Button(onClick = {
        zoomLevel += 10
        if (zoomLevel > 150) zoomLevel = 50
    }) {
        Text(text = "Zoom")
    }
}