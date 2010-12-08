package br.com.gastos.activity;

import br.com.gastos.adapter.DBAdapter;
import br.com.gastos.ui.util.MenuApplication;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 * 
 * Default Activity, toda activity (não list) deve herdar essa classe,
 * fazendo uso da lógica criação do menu, possibilitando mostrar o menu em todas as 
 * páginas, adicionando logica necessaria.
 * 
 * Se preciso, basta adicionar interfaces de evento nessa classe, e utilizar os metodos necessarios
 * para poder manipula-los.
 * 
 * @author leonardo - leonardometalhead@gmail.com
 *
 */
public class DefaultGastosActivity extends Activity implements MenuApplication {

	protected Context context;
	protected DBAdapter dbAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
    	dbAdapter = new DBAdapter(context);

		super.onCreate(savedInstanceState);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = getMenuInflater();
    	menuInflater.inflate(R.menu.menu, menu);

    	MenuItem menuItemNovoGasto = menu.findItem(R.id.item01);
    	menuItemNovoGasto.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				Intent myIntent = new Intent(context, GastosNovoActivity.class);
                startActivityForResult(myIntent, 0);
				return false;
			}
		});
    	
    	MenuItem menuItemListarGasto = menu.findItem(R.id.item02);
    	menuItemListarGasto.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				Intent myIntent = new Intent(context, GastosListarActivity.class);
                startActivityForResult(myIntent, 0);
				return false;
			}
		});
    	
    	return super.onCreateOptionsMenu(menu);
    }
}
