package com.example.mueckenfang;

import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements Runnable,OnClickListener {
	  
	private static final int INTERVALL = 100;
	private static final int ZEITSCHEIBEN = 600;
	private Random zufallsgenerator = new Random();
	private Handler handler = new Handler();
	private FrameLayout spielbereich;
	private int runde;
	private int punkte;
	private int zu_fangende_kaefer;
	private int gefangenekaefer;
	private int zeit;
	private float massstab;
	private static final long HOECHSTALTER_MS = 2000;
	private static final String HIMMELSRICHTUNGEN[][] = { 
		  {"nw","n","no"},
		  {"w", "", "o"},
		  {"sw", "s", "so"} };
	

	/*********************************************************************************** 
	 * protected void onCreate(Bundle savedInstanceState) {
	 * 
	 * Called when the activity is first created. 
	 ***********************************************************************************/
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		spielbereich = (FrameLayout) findViewById(R.id.spielbereich);
		massstab = getResources().getDisplayMetrics().density;
		
		
		Bundle extras = getIntent().getExtras(); 
		System.out.println("Starte Runde:");
		if(extras !=null)
		{
		    runde = extras.getInt("runde");
		    zu_fangende_kaefer = extras.getInt("kaefer");

		    System.out.println("runde = " + runde + " kaefer = " + zu_fangende_kaefer);
		}
		
		punkte = 0;
		starteRunde();
	}
	
    
	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	@Override
	public void run() {
		zeitHerunterzaehlen();
	}
    
	/*********************************************************************************** 
	 * private void zeitHerunterzaehlen() {
	 * 
	 *  Haupthandler des Spiels
	 ***********************************************************************************/	
	private void zeitHerunterzaehlen() {
		zeit = zeit - 1;
		
		float zufallszahl = zufallsgenerator.nextFloat();
		double wahrscheinlichkeit = zu_fangende_kaefer * 1.5 / ZEITSCHEIBEN;
		if (wahrscheinlichkeit >1 ) {
			einekaeferAnzeigen();
			if (zufallszahl < wahrscheinlichkeit - 1) {
				einekaeferAnzeigen();
			}
		} else {
			if (zufallszahl < wahrscheinlichkeit) {
				einekaeferAnzeigen();
			}
		}
		kaeferVerschwinden();
		kaeferBewegen();
		bildschirmAktualisieren();
		if(!pruefeSpielende()) {
      		if(!pruefeRundenende()) {
      			handler.postDelayed(this, INTERVALL); //handler "run" starten
      		}
   		}
	}

	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	private void starteRunde() {
		gefangenekaefer = 0;
		zeit = ZEITSCHEIBEN;
		int id = getResources().getIdentifier("hintergrund"+Integer.toString(runde), "drawable", this.getPackageName());
		if(id>0) {
			LinearLayout l = (LinearLayout) findViewById(R.id.hintergrund);
			l.setBackgroundResource(id);
		}
		bildschirmAktualisieren();
        handler.postDelayed(this,INTERVALL); //handler "run" starten
	}
	
	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/		
	private boolean pruefeRundenende(){
		if(zeit==0){
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("result","gewonnen");
			 setResult(RESULT_OK,returnIntent);     
			 finish();
			 
			

			return true;
		}
		if(gefangenekaefer >= zu_fangende_kaefer){
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("result","gewonnen");
			 setResult(RESULT_OK,returnIntent);     
			 finish();
			
			return true;
		}
		return false;
	}
	
	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	private boolean pruefeSpielende()	{
		if(zeit == 0 && gefangenekaefer < zu_fangende_kaefer){
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("result","verloren");
			 setResult(RESULT_OK,returnIntent);     
			 finish();
			
			return true;
		}
		return false;
	}

	
	/*********************************************************************************** 
	 * private void gameOver()
	 * 
	 *  
	 ***********************************************************************************/	
	private void gameOver() {
		Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.gameover);
		dialog.show();
	}


	
	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	private void kaeferBewegen(){
		int nummer=0;
		
		while(nummer<spielbereich.getChildCount()){
			ImageView kaefer = (ImageView) spielbereich.getChildAt(nummer++);
			int vx = (Integer) kaefer.getTag(R.id.vx);
			int vy = (Integer) kaefer.getTag(R.id.vy);
			
			FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) kaefer.getLayoutParams();
			params.leftMargin += vx*runde;
			params.topMargin += vy*runde;
			kaefer.setLayoutParams(params);
		}
	}
	
	/*********************************************************************************** 
	 * private void bildschirmAktualisieren()	
	 * 
	 *  
	 ***********************************************************************************/	
	private void bildschirmAktualisieren()	{
		//Anzahl Punkte malen
		TextView tvPunkte = (TextView) findViewById(R.id.points);
		tvPunkte.setText(Integer.toString(punkte));
		
		//Rundennummer malen
		TextView tvRunde = (TextView) findViewById(R.id.round);
		tvRunde.setText(Integer.toString(runde));
		
		//Anzahl getroffener Käfter Malen
		TextView tvHits = (TextView) findViewById(R.id.hits);
		tvHits.setText(Integer.toString(gefangenekaefer));
		
		//verbleibende Zeit schreiben
		TextView tvTime = (TextView) findViewById(R.id.time);
		tvTime.setText(Integer.toString(zeit));
		
		//Balken mit Anzahl treffern
		FrameLayout flTreffer = (FrameLayout) findViewById(R.id.bar_hits);
		LayoutParams lpTreffer = flTreffer.getLayoutParams();
		lpTreffer.width = Math.round( massstab * 300 * Math.min( gefangenekaefer,zu_fangende_kaefer) / zu_fangende_kaefer );
		
		//Balken mit Zeit
		FrameLayout flZeit = (FrameLayout) findViewById(R.id.bar_time);
		LayoutParams lpZeit = flZeit.getLayoutParams();
		lpZeit.width = Math.round( massstab * zeit * 300 / ZEITSCHEIBEN );
	}
	
	/*********************************************************************************** 
	 * private void einekaeferAnzeigen()
	 * 
	 *  Zeichnet einen neuen Kaefer
	 ***********************************************************************************/	
	private void einekaeferAnzeigen()
	{
		int breite = spielbereich.getWidth();
		int hoehe = spielbereich.getHeight();
		
		int kaefer_breite = (int) Math.round(massstab*50);
		int kaefer_hoehe = (int) (Math.round(massstab)*42);
		
		int links = zufallsgenerator.nextInt(breite-kaefer_breite);
		int oben = zufallsgenerator.nextInt(hoehe-kaefer_hoehe);
		
		ImageView kaefer = new ImageView(this);
		kaefer.setImageResource(R.drawable.kaefer);
		
		kaefer.setOnClickListener(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(kaefer_breite,kaefer_hoehe);
		params.leftMargin = links;
		params.topMargin = oben;
		params.gravity = Gravity.TOP + Gravity.LEFT;
		
		spielbereich.addView(kaefer,params);
		kaefer.setTag(R.id.geburtsdatum, new Date());
		
		
		//Geschwindigkeit bestimmen und Anhaengen
		int vx,vy;
		do{
			vx= zufallsgenerator.nextInt(3)-1;
			vy= zufallsgenerator.nextInt(3)-1;
		}while (vx==0 && vy ==0);
		
		setzeBild(kaefer, vx, vy);
		vx= (int) Math.round(massstab*vx);
		vy= (int) Math.round(massstab*vy);
		kaefer.setTag(R.id.vx, new Integer(vx));
		kaefer.setTag(R.id.vy, new Integer(vy));
	}

	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	private void setzeBild(ImageView kaefer, int vx, int vy) {		
		kaefer.setImageResource( getResources().getIdentifier("kaefer_"+HIMMELSRICHTUNGEN[vy+1][vx+1], "drawable", this.getPackageName() ) );		
	}
	
	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	private void kaeferVerschwinden()	{
		int nummer=0;
		
		while(nummer<spielbereich.getChildCount()){
			ImageView kaefer = (ImageView) spielbereich.getChildAt(nummer);
			
			Date geburtsdatum = (Date) kaefer.getTag(R.id.geburtsdatum);
			long alter = (new Date()).getTime()- geburtsdatum.getTime();
			
			if(alter>HOECHSTALTER_MS){
				spielbereich.removeView(kaefer);
			}
			else{
				nummer++;
			}
		}
	}

	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	@Override
	public void onClick(View kaefer) {
		if(kaefer.getAnimation()==null){ //wenn eine Animation laeuft dann zählt es nicht
			gefangenekaefer++;
			punkte += 100;
			bildschirmAktualisieren();
			//spielbereich.removeView(kaefer);
			Animation animationTreffer = AnimationUtils.loadAnimation(this, R.anim.treffer);
			kaefer.startAnimation(animationTreffer);
			animationTreffer.setAnimationListener(new kaeferAnimationListener(kaefer));
		}
	}


	
	/*********************************************************************************** 
	 * 
	 * 
	 *  
	 ***********************************************************************************/	
	private class kaeferAnimationListener implements AnimationListener
	{
		private View kaefer;
		
		public kaeferAnimationListener(View m){
			kaefer = m;
		}
		
		@Override
		public void onAnimationEnd(Animation animation){
			handler.post(
					new Runnable(){
						@Override
						public void run(){
								spielbereich.removeView(kaefer);
						}
					}//new Runnable(){
			);//handler.post
		}
		
		@Override
		public void onAnimationRepeat(Animation animation){}

		@Override
		public void onAnimationStart(Animation animation){}

	}//private class kaeferAnimationListener implements AnimationListener
	
	
}//public class GameActivity extends Activity {
