package com.rathodhub.study;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import androidx.webkit.WebViewAssetLoader;
import java.io.ByteArrayInputStream;

public class MainActivity extends Activity {
 private WebView webView;
 private static final String HOST = "appassets.androidplatform.net";
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  webView = new WebView(this);
  WebSettings s = webView.getSettings();
  s.setJavaScriptEnabled(true);
  s.setDomStorageEnabled(true);
  s.setAllowFileAccess(false);
  s.setAllowContentAccess(false);
  s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
  s.setMediaPlaybackRequiresUserGesture(true);
  s.setSupportMultipleWindows(false);
  WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
  final WebViewAssetLoader loader = new WebViewAssetLoader.Builder().addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this)).build();
  webView.setWebViewClient(new WebViewClient() {
   @Override public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
    Uri u=request.getUrl();
    if ("https".equals(u.getScheme()) && HOST.equals(u.getHost())) {
     WebResourceResponse r=loader.shouldInterceptRequest(u);
     if(r!=null)return r;
    }
    // This preview is local-only. Do not silently load third-party scripts or content.
    return new WebResourceResponse("text/plain", "UTF-8", 403, "Blocked", java.util.Collections.emptyMap(), new ByteArrayInputStream(new byte[0]));
   }
   @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
    Uri u = request.getUrl();
    if ("https".equals(u.getScheme()) && HOST.equals(u.getHost()) && u.getPath()!=null && u.getPath().startsWith("/assets/")) return false;
    if ("https".equals(u.getScheme())) {
     try { startActivity(new Intent(Intent.ACTION_VIEW, u).addCategory(Intent.CATEGORY_BROWSABLE)); } catch (ActivityNotFoundException ignored) { }
    }
    return true;
   }
  });
  webView.setWebChromeClient(new WebChromeClient());
  setContentView(webView);
  if(state==null || webView.restoreState(state)==null) webView.loadUrl("https://"+HOST+"/assets/index.html");
 }
 @Override public void onBackPressed() {
  webView.evaluateJavascript("document.querySelector('dialog[open]') !== null", result -> {
   if("true".equals(result)) webView.evaluateJavascript("document.querySelector('dialog[open]').close()", null);
   else if(webView.canGoBack()) webView.goBack();
   else finish();
  });
 }
 @Override protected void onSaveInstanceState(Bundle state) { webView.saveState(state); super.onSaveInstanceState(state); }
 @Override protected void onDestroy() { if(webView!=null){ webView.stopLoading(); webView.destroy(); } super.onDestroy(); }
}
