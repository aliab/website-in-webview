package info.abdolahi.showtheweb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	// set your custom url here
	String url = "http://www.YOURCUSTOMURL.ir";

	// if you want to show progress bar on splash screen
	Boolean showProgressOnSplashScreen = true;

	WebView mWebView;
	ProgressBar prgs, splashPrgs;
	RelativeLayout splash, main_layout;

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);

		mWebView = (WebView) findViewById(R.id.wv);
		prgs = (ProgressBar) findViewById(R.id.progressBar);
		main_layout = (RelativeLayout) findViewById(R.id.main_layout);

		// splash screen View
		splashPrgs = (ProgressBar) findViewById(R.id.progressBarSplash);
		if (!showProgressOnSplashScreen)
			splashPrgs.setVisibility(View.GONE);
		splash = (RelativeLayout) findViewById(R.id.splash);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			// get status bar height to push webview below that
			int result = 0;
			int resourceId = getResources().getIdentifier("status_bar_height",
					"dimen", "android");
			if (resourceId > 0) {
				result = getResources().getDimensionPixelSize(resourceId);
			}

			// set top padding to status bar
			main_layout.setPadding(0, result, 0, 0);
		}

		mWebView.loadUrl(url);
		WebSettings webSettings = mWebView.getSettings();

		// control javaScript and add html5 features
		mWebView.setFocusable(true);
		mWebView.setFocusableInTouchMode(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.getSettings().setDatabaseEnabled(true);
		mWebView.getSettings().setDatabasePath(this.getFilesDir().getPath() + this.getPackageName() + "/databases/");

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
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				if (prgs.getVisibility() == View.VISIBLE)
					prgs.setVisibility(View.GONE);

				// check if splash is still there, get it away!
				if (splash.getVisibility() == View.VISIBLE)
					slideToTop(splash);
				// splash.setVisibility(View.GONE);

			}

		});

	}

	/**
	 * To animate view slide out from top to bottom
	 * 
	 * @param view
	 */
	void slideToTop(View view) {
		TranslateAnimation animate = new TranslateAnimation(0, 0, 0,
				view.getHeight());
		animate.setDuration(2000);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		view.setVisibility(View.GONE);
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
