package br.com.gastos.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import br.com.gastos.adapter.DBAdapter;

/**
 * Listagem de todos os gastos efetuados;
 * @author yross
 *
 */
public class GastosListarActivity extends DefaultGastosListActtivity {

	private DBAdapter dbAdapter;
	
	public static final String ALTERAR = "Alterar";
	public static final String EXCLUIR = "Excluir";
	public static final String DESCRICAO = "Descrição";
	public static final String GASTOS = "Gastos...";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		dbAdapter = new DBAdapter(context);

		setContentView(R.layout.list_todos_gastos);

		Cursor cursor = dbAdapter.open().getAllGastos();

		// TODO:Verificar por que não consigo usar o layout list_item.xml.
		// Explicando: ao clicar na linha com o item, o metodo do listener
		// nao é invocado, pesquisando rapido na net vi um exemplo onde se usava
		// esse layout padrao do android.
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listGastos);
		
		// Conseguir passar mais informações para o List, desta vez estou passando um Cursor no ligar da Lsta de Strings
		// Dica: http://thinkandroid.wordpress.com/2010/01/09/simplecursoradapters-and-listviews/
		
		startManagingCursor(cursor);
        String[] columns = new String[] { DBAdapter.KEY_DESCRICAO, DBAdapter.KEY_VALOR, DBAdapter.KEY_DATA};
        int[] to = new int[] { R.id.item_list_gasto_descricao, R.id.item_list_gasto_valor, R.id.item_list_gasto_data};
        
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item_gasto, cursor, columns, to);
		
		setListAdapter(cursorAdapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {

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
