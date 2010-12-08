package br.com.gastos.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import br.com.gastos.adapter.DBAdapter;

public class GastosListarActivity extends DefaultGastosListActtivity {

	private DBAdapter dbAdapter;
	private ListView listAllGastos;
	
	public static final String ALTERAR = "Alterar";
	public static final String EXCLUIR = "Excluir";
	public static final String DESCRICAO = "Descrição";
	public static final String GASTOS = "Gastos...";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		dbAdapter = new DBAdapter(context);

		setContentView(R.layout.list_todos_gastos);

		dbAdapter.open();

		Cursor allGastos = dbAdapter.getAllGastos();
	
		List<String> listGastos = new ArrayList<String>();

		if (allGastos.moveToFirst()) {
			
			do {

				listGastos.add(allGastos.getString(1));

			} while (allGastos.moveToNext());
		}

		dbAdapter.close();

		listAllGastos = getListView();
		listAllGastos.setSoundEffectsEnabled(true);

		// TODO:Verificar por que não consigo usar o layout list_item.xml.
		// Explicando: ao clicar na linha com o item, o metodo do listener
		// nao é invocado, pesquisando rapido na net vi um exemplo onde se usava
		// esse layout padrao do android.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listGastos);

		listAllGastos.setAdapter(adapter);

		listAllGastos.setTextFilterEnabled(true);

		listAllGastos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,	long id) {
				
				final CharSequence[] items = { ALTERAR, EXCLUIR, DESCRICAO };

				AlertDialog.Builder builder = new AlertDialog.Builder(GastosListarActivity.this);

				builder.setTitle(GASTOS);
				
				builder.setItems(items, new DialogInterface.OnClickListener() {
				
					public void onClick(DialogInterface dialog, int item) {
						Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();							
					}
				});

				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
}
