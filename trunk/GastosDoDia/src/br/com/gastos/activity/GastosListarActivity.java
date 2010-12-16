package br.com.gastos.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import br.com.gastos.adapter.DBAdapter;
import br.com.gastos.ui.util.Constants;

/**
 * Listagem de todos os gastos efetuados;
 * @author yross
 *
 */
public class GastosListarActivity extends DefaultGastosListActtivity {

	private DBAdapter dbAdapter;
	
	enum Opcoes {
		ALTERAR("Alterar"), EXCLUIR("Excluir");
		private String descricao;
		private Opcoes(String descricao) {this.descricao = descricao;}
		public String toString() {return this.descricao;}
	};
	
	public static final String ALTERAR = "Alterar";
	public static final String EXCLUIR = "Excluir";
	public static final String DESCRICAO = "Descrição";
	public static final String GASTOS = "Gastos...";
	
	private ListView listView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		dbAdapter = new DBAdapter(context);

		setContentView(R.layout.list_todos_gastos);

		Cursor cursor = dbAdapter.open().getAllGastos();

		// Conseguir passar mais informações para o List, desta vez estou passando um Cursor no ligar da Lsta de Strings
		// Dica: http://thinkandroid.wordpress.com/2010/01/09/simplecursoradapters-and-listviews/
		
		startManagingCursor(cursor);
		
        /*String[] columns = new String[] { DBAdapter.KEY_DESCRICAO, DBAdapter.KEY_VALOR, DBAdapter.KEY_DATA};
        int[] to = new int[] { R.id.item_list_gasto_descricao, R.id.item_list_gasto_valor, R.id.item_list_gasto_data};*/
        
		String[] columns = new String[] { DBAdapter.KEY_DESCRICAO};
		int[] to = new int[] { R.id.item_list_gasto_descricao};
		
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
													this, 
													R.layout.list_item_gasto, 
													cursor, 
													columns, 
													to);
		
		setListAdapter(cursorAdapter);

		listView = getListView();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				final SimpleCursorAdapter adapter = (SimpleCursorAdapter) arg0.getAdapter();
				
				final CharSequence[] items = { Opcoes.ALTERAR.toString(), Opcoes.EXCLUIR.toString()};
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);

				builder.setTitle(GASTOS);
				
				builder.setItems(items, new DialogInterface.OnClickListener() {
				
					public void onClick(DialogInterface dialog, int item) {
						
						Opcoes opc = Opcoes.values()[item];
						
						switch (opc) {
						case ALTERAR:
						
							Intent intentDescricao = new Intent(context, GastosDescricaoActivity.class);
							
							long _id = adapter.getCursor().getLong(0);
							String descricao = adapter.getCursor().getString(1);
							float valor = adapter.getCursor().getFloat(2);
							
							intentDescricao.putExtra(Constants.CAMPO_ID, _id);
							intentDescricao.putExtra(Constants.CAMPO_DESCRICAO, descricao);
							intentDescricao.putExtra(Constants.CAMPO_VALOR, valor);
							
							startActivity(intentDescricao);
							
//							Toast.makeText(context, "Alterar", Toast.LENGTH_SHORT).show();
							break;

						case EXCLUIR:
							
							Toast.makeText(context, "Alterar", Toast.LENGTH_SHORT).show();
							break;
						
//						case 2:
//
//							Intent myIntent = new Intent(context, GastosDescricaoActivity.class);
//							
//							TextView txt = (TextView)findViewById(R.id.item_list_gasto_descricao);
//							myIntent.putExtra(Constants.CAMPO_DESCRICAO, txt.getText());
//							
//							startActivity(myIntent);
//							
//							break;
						default:
							break;
						}
					}
				});

				builder.create().show();
				
				return false;
			}
		});
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,	long id) {
				
				SimpleCursorAdapter adapter = (SimpleCursorAdapter) a.getAdapter();
				
				String descricao = adapter.getCursor().getString(1);
				float valor = adapter.getCursor().getFloat(2);
//				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);

				builder.setTitle(GASTOS);
				builder.setMessage(String.format("Descricao: %s \n Valor: R$%.2f", descricao, valor));
				
//				builder.setItems(items, new DialogInterface.OnClickListener() {
//				
//					public void onClick(DialogInterface dialog, int item) {
//						
//						switch (item) {
//						case 0:
//							// TODO: implementar logica necessaria
//							break;
//						case 1:
//							// TODO: implementar logica necessaria
//							break;
//						case 2:
//
//							Intent myIntent = new Intent(context, GastosDescricaoActivity.class);
//							
//							TextView txt = (TextView)findViewById(R.id.item_list_gasto_descricao);
//							myIntent.putExtra(Constants.CAMPO_DESCRICAO, txt.getText());
//							
//							startActivity(myIntent);
//							
//							break;
//						default:
//							break;
//						}
//					}
//				});

				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
	
}
