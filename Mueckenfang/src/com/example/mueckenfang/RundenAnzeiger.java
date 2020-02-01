package com.example.mueckenfang;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RundenAnzeiger extends Activity implements OnClickListener {
	private Button startButton;
	

	/*********************************************************************************** 
	 * protected void onCreate(Bundle savedInstanceState) {
	 * 
	 *  
	 ***********************************************************************************/	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_runden_anzeiger);
		
		startButton = (Button) findViewById(R.id.button1);
		startButton.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras(); 
		System.out.println("Zeige runde an:");
		if(extras !=null)
		{
		    int runde = extras.getInt("runde");
		    int kaefer = extras.getInt("kaefer");

		    System.out.println("runde = " + runde + " kaefer = " + kaefer);
		    
			//Rundennummer malen
			TextView tvRunde = (TextView) findViewById(R.id.textViewRundenAnzeigerRunde);
			tvRunde.setText("Runde: "+Integer.toString(runde));
			
			//Anzahl getroffener Käfter Malen
			TextView tvHits = (TextView) findViewById(R.id.textViewErgebnisAnzeigerKaefer);
			tvHits.setText("Kaefer: "+Integer.toString(kaefer));
		}
	}//protected void onCreate(Bundle savedInstanceState) {

	

	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.runden_anzeiger, menu);
		return true;
	}

	

	/*********************************************************************************** 
	 * public void onClick(View v)
	 * 
	 *  Wenn auf den Startknopf gedrueckt wurde
	 ***********************************************************************************/	
	@Override
	public void onClick(View v)
	{
		finish();
	
	}
	
	
}//public class RundenAnzeiger extends Activity implements OnClickListener {
