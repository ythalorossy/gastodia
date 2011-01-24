package br.com.gastos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class TelaSplashScreen extends Activity implements Runnable {

	private final int DELAY = 3000;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(R.layout.layout_splash);
		
		Toast.makeText(TelaSplashScreen.this, "Aguarde o carregamento", Toast.LENGTH_LONG).show();
		
		Handler h = new Handler();
		h.postDelayed(this, DELAY);
	}
	
	@Override
	public void run() {
		//Abre tela principal
		startActivity(new Intent(TelaSplashScreen.this, GastosDoDia.class));
		finish();
	}

}
