package com.android.phonegap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.IPlugin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewTest extends Activity implements CordovaInterface {
    private static CordovaWebView sGlobalCordovaWebView;
    private final ExecutorService mThreadPool = Executors.newCachedThreadPool();
//    private SherlockFragment mLatestFragment;
    private Bundle mSavedInstanceState;
    ProgressDialog myProgressDialog;
    
    String resultStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mSavedInstanceState = savedInstanceState;

//        setIntegerProperty("loadUrlTimeoutValue", 60000);
        setContentView(R.layout.main);

        sGlobalCordovaWebView = (CordovaWebView) findViewById(R.id.phoneGapView);
        
        sGlobalCordovaWebView.getSettings().setJavaScriptEnabled(true);
        
        sGlobalCordovaWebView.addJavascriptInterface(new MyJavaScriptInterface(this),
  	"PassValue");

        sGlobalCordovaWebView.addJavascriptInterface(new JavaScriptInterface(),
		"getValue");
        
        myProgressDialog = new ProgressDialog(WebViewTest.this);
		myProgressDialog.setMessage("Loading...");
		myProgressDialog.show();
		
		
		
        sGlobalCordovaWebView.loadUrl("file:///android_asset/www/index.html");
        
        sGlobalCordovaWebView.setWebViewClient(new MyWebClient());

//        startFragment("com.acme.view.ExampleScreenView");
    }
	public void cancelLoadUrl() {
		// TODO Auto-generated method stub
		
	}
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}
	public Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object onMessage(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setActivityResultCallback(IPlugin arg0) {
		// TODO Auto-generated method stub
		
	}
	public void startActivityForResult(IPlugin arg0, Intent arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	public ExecutorService getThreadPool()
	{
	    return mThreadPool;
	}
	
	
//--------------------------------------------
	
	final class JavaScriptInterface {
		JavaScriptInterface() {
		}

		public String getNameString() {

			return resultStr;
		}
	}
	
//-----------------------------------------------
		
	public class MyJavaScriptInterface {
		Context mContext;

		MyJavaScriptInterface(Context c) {
			mContext = c;
		}

		public void passVar(String str) {
			resultStr = str;

			Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
		}
	}
	
	class MyWebClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// Start PROGRESS DIALOG
				myProgressDialog.show();
				view.loadUrl(url);

			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			System.err.println("Page Finish Call");
			myProgressDialog.dismiss();
			// Toast.makeText(getApplicationContext(), "Page Finish Call",
			// Toast.LENGTH_SHORT).show();
		}
	}
}
