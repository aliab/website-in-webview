package info.abdolahi.showtheweb;

/*
 * Copyright (C) 2015 Ali Abdolahi
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	// set your custom url here
	String url = "http://jameehonar.ir/";
	
	// show progress bar on splash screen
	Boolean showProgressOnSplashScreen = true;

	WebView mWebView;
	ProgressBar prgs, splashPrgs;
	RelativeLayout splash;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);

		mWebView = (WebView) findViewById(R.id.wv);
		prgs = (ProgressBar) findViewById(R.id.progressBar);
		
		// splash screen View
		splashPrgs = (ProgressBar) findViewById(R.id.progressBarSplash);
		if(!showProgressOnSplashScreen) splashPrgs.setVisibility(View.GONE);		
		splash = (RelativeLayout) findViewById(R.id.splash);
		
		mWebView.loadUrl(url);
		WebSettings webSettings = mWebView.getSettings();

		// control javaScript
		webSettings.setJavaScriptEnabled(true);

		// this force use chromeWebClient
		webSettings.setSupportMultipleWindows(true);

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (prgs.getVisibility() == View.GONE) {
					prgs.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				
				// check if splash is still there, get it away!
				if(splash.getVisibility() == View.VISIBLE) splash.setVisibility(View.GONE);
				
				if (prgs.getVisibility() == View.VISIBLE) prgs.setVisibility(View.GONE);
				

			}

		});

	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
