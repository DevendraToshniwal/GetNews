package com.me.devesh;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivitymain extends Activity{
	
	
	 RatingBar ratingbar1;  
	    Button button;  
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.settingactivitymain);  
	        addListenerOnButtonClick();  
	    }  
	  
	    public void addListenerOnButtonClick(){  
	        ratingbar1=(RatingBar)findViewById(R.id.ratingBar1);  
	        button=(Button)findViewById(R.id.button1);  
	        //Performing action on Button Click  
	        button.setOnClickListener(new OnClickListener(){  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                //Getting the rating and displaying it on the toast  
	                String rating=String.valueOf(ratingbar1.getRating());  
	                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();  
	            } 
	              
	        });  
	    }  

}
