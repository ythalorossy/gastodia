package br.com.gastos.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.gastos.adapter.DBAdapter;

public class GastosListarActivity extends Activity {

	private Context context;
	private DBAdapter dbAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	   	context = this;
    	dbAdapter = new DBAdapter(context);
    	
    	setContentView(R.layout.list_todos_gastos);
    	
    	
    	dbAdapter.open();
    		Cursor allGastos = dbAdapter.getAllGastos();
    		List<String> listGastos = new ArrayList<String>();
    		
    		if(allGastos.moveToFirst()) {
    			do {
					listGastos.add(allGastos.getString(1));
				} while (allGastos.moveToNext());
    		}
    	dbAdapter.close();
	
    	ListView listAllGastos = (ListView) findViewById(R.id.listTodosGastos);
    	listAllGastos.setSoundEffectsEnabled(true);
    	listAllGastos.setAdapter(new ArrayAdapter<String>(context, R.layout.list_item, listGastos));
//    	listAllGastos.setAdapter(new CursorAdapter(context, allGastos) {
//			
//			@Override
//			public View newView(Context context, Cursor cursor, ViewGroup parent) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public void bindView(View view, Context context, Cursor cursor) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
    	
    	listAllGastos.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
