package com.example.mueckenfang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Animation animationEinblenden;
	private Animation animationWackeln;
	private Button startButton;
	private Handler handler = new Handler();
	private static final int  RUNDEN_ANZEIGER =1 ;
	private static final int  GAME =2 ;
	private static final int  ERGEBNIS_ANZEIGER =3 ;
	private int runde;

	
	/*********************************************************************************** 
	 * protected void onCreate(Bundle savedInstanceState) {
	 * 
	 *  
	 ***********************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		animationEinblenden = AnimationUtils.loadAnimation(this, R.anim.einblenden);
		animationWackeln = AnimationUtils.loadAnimation(this, R.anim.wackeln);
		
		startButton = (Button) findViewById(R.id.button1);
		startButton.setOnClickListener(this);
		
		runde =0;
	}
	
	/*********************************************************************************** 
	 * public boolean onCreateOptionsMenu(Menu menu) {
	 * 
	 *  
	 ***********************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	/*********************************************************************************** 
	 * public void onClick(View v)
	 * 
	 * Wenn auf den Startknopf gedrueckt wird 
	 ***********************************************************************************/
	@Override
	public void onClick(View v)
	{
		runde++;
		
		Bundle dataBundle = new Bundle();

		dataBundle.putInt("runde", runde);
		dataBundle.putInt("kaefer", 10*runde);
		Intent i = new Intent(this, RundenAnzeiger.class);
		i.putExtras(dataBundle);
		
		startActivityForResult(i, RUNDEN_ANZEIGER);
	}
	
	/*********************************************************************************** 
	 * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 * 
	 * Bekommt das Ergebnis des Spiel zurueck 
	 ***********************************************************************************/	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch(requestCode){
			case  RUNDEN_ANZEIGER:
				Bundle dataBundle = new Bundle();
				dataBundle.putInt("runde", runde);
				dataBundle.putInt("kaefer", 10*runde);
				Intent i = new Intent(this, GameActivity.class);
				i.putExtras(dataBundle);
				startActivityForResult(i, GAME);
				break;
				
			case GAME:
				Bundle dataBundle2 = new Bundle();
				dataBundle2.putInt("runde", runde);
				dataBundle2.putInt("kaefer", 10*runde);
				Intent i2 = new Intent(this, ErgebnisAnzeigerActivity.class);
				i2.putExtras(dataBundle2);
				startActivityForResult(i2, ERGEBNIS_ANZEIGER);
				break;
		
		
		
		}
		
	}//onActivityResult
	
	
	/*********************************************************************************** 
	 * protected void onResume() {
	 * 
	 * Fuer die Animation wackeln des Startknopf 
	 ***********************************************************************************/	
	@Override
	protected void onResume() {
		super.onResume();
		View v = findViewById(R.id.wurzel);
		v.startAnimation(animationEinblenden);
		handler.postDelayed(new WackleButton(),1000*10);
	}
	

	
	/*********************************************************************************** 
	 * private class WackleButton implements Runnable {
	 * 
	 * Fuer die Animation wackeln des Startknopf 
	 ***********************************************************************************/
	private class WackleButton implements Runnable {

		private Button startButton;

		@Override
		public void run() {
			startButton = (Button) findViewById(R.id.button1);
			startButton.startAnimation(animationWackeln);
		}
	}//private class WackleButton implements Runnable {


}//public class MainActivity extends Activity implements OnClickListener {


