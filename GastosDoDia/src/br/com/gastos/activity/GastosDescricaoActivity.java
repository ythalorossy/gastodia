package br.com.gastos.activity;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.gastos.adapter.DBAdapter;
import br.com.gastos.ui.util.Constants;

public class GastosDescricaoActivity extends DefaultGastosActivity  {

	private DBAdapter dbAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.desc_gastos);

    	dbAdapter = new DBAdapter(context);
    	
   		Bundle extras = getIntent().getExtras();
   		final long _id = extras.getLong(Constants.CAMPO_ID);
   		final String descricao = extras.getString(Constants.CAMPO_DESCRICAO);
   		final String valor = String.valueOf(extras.getFloat(Constants.CAMPO_VALOR));
   		
    	((TextView)findViewById(R.id.descricao_text_descricao)).setText(descricao);
    	((TextView)findViewById(R.id.descricao_text_valor)).setText(valor);
    	
   	 	Button botaoAlterar = (Button)findViewById(R.id.descricao_btn_alterar);
   		botaoAlterar.setOnClickListener(new OnClickListener() {
   			public void onClick(View v) {
   				dbAdapter.open();
   				dbAdapter.updateGasto(_id,
   						((TextView)findViewById(R.id.descricao_text_descricao)).getText().toString().trim(), 
   						Float.parseFloat(((TextView)findViewById(R.id.descricao_text_valor)).getText().toString()), 
   						new Date());
   				dbAdapter.close();
   				Toast.makeText(context, "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
   				setResult(RESULT_OK);
   				startActivity(new Intent(context, GastosListarActivity.class));
   				
			}

   		});

	}

}
