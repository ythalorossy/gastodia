package br.com.gastos.activity;

import android.os.Bundle;

public class GastosActivity extends DefaultGastosActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.main);
    }
    
}