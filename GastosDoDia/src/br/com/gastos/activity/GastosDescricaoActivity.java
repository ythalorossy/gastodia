package br.com.gastos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import br.com.gastos.ui.util.Constants;

public class GastosDescricaoActivity extends DefaultGastosActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.desc_gastos);
    	
    	TextView txtDescricao = (TextView)findViewById(R.id.txtDescricao);
    	
    	Intent parentIntent = getIntent();
    	
    	if (parentIntent != null) {    		
    		
    		Bundle extras = parentIntent.getExtras();

    		if (extras != null) {
    			
    			txtDescricao.setText(extras.getString(Constants.CAMPO_DESCRICAO));
    		}
    	} 	
	}
}
