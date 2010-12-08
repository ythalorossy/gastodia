package br.com.gastos.activity;

import java.util.Date;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GastosNovoActivity extends DefaultGastosActivity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
    	super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.form_novo_gasto);

        Button btnSalvarGasto = (Button) findViewById(R.id.ButtonSalvarGasto);
        btnSalvarGasto.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EditText descricao = (EditText) findViewById(R.id.EditTextDescricao);
				EditText valor = (EditText) findViewById(R.id.EditTextValor);
				float valorAsFloat = Float.parseFloat(valor.getText().toString());

				dbAdapter.open();
				
				try {
					long id = dbAdapter.insertGastos(descricao.getText().toString(), valorAsFloat, new Date());
					Toast.makeText(context, "Inserido Com Sucesso: " + id, Toast.LENGTH_SHORT).show();
				} catch (SQLException exception) {
					Toast.makeText(context, "Erro na inserção: " + exception.getMessage(), Toast.LENGTH_LONG).show();
					descricao.setText(exception.getMessage());
				}
				
				dbAdapter.close();
				
				dbAdapter.open();
				Cursor allGastos = dbAdapter.getAllGastos();
				if (allGastos.moveToFirst()) {
					do {
						String des = allGastos.getString(1);
						Float val = allGastos.getFloat(2);
						System.out.println(des + val);
					} while (allGastos.moveToNext());
				}
				dbAdapter.close();
				
			}
		});
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.menu, menu);

    	MenuItem menuItemNovoGasto = menu.findItem(R.id.item01);
    	menuItemNovoGasto.setVisible(false);
    	
    	MenuItem menuItemListarGasto = menu.findItem(R.id.item02);
    	menuItemListarGasto.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				Intent myIntent = new Intent(context, GastosListarActivity.class);
                startActivityForResult(myIntent, 0);
				return false;
			}
		});
    	
    	return true;
    }

}
