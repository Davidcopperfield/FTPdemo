package com.example.ftpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import android.os.Bundle;  
import android.os.StrictMode;  
import android.app.Activity;  
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;  
import android.view.Menu;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.Toast;  


public class FTPMainActivity extends Activity {
    //…µ±∆Buttons  
    private Button buttonUpLoad = null;  
    private Button buttonDownLoad = null;  
      
    //FTPπ§æﬂ¿‡  
    private FTPUtils ftpUtils = null;  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ftpmain);
	}
}
