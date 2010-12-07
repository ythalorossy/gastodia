package br.com.gastos.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class GastosActivity extends Activity {
	
	private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	context = this;
    	
    	super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.main);
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
    	
    	// TODO Auto-generated method stub
    	return super.onCreateOptionsMenu(menu);
    }
}