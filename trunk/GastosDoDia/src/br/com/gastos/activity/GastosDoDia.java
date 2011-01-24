package br.com.gastos.activity;

import java.util.List;

import br.com.gastos.activity.Gasto.Gastos;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class GastosDoDia extends ListActivity {
	
	protected static final int INSERIR_EDITAR = 1;
	
	protected static final int BUSCAR = 2;
	
	public static RepositorioGasto repositorio;
	
	private List<Gasto> gastos;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		repositorio = new RepositorioGastoScript(this);
		atualizarLista();
	}

	protected void atualizarLista() {
	
		gastos = repositorio.listarGastosDoDia();
		setListAdapter(new GastoListAdapter(this, gastos));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
		menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case INSERIR_EDITAR:
				startActivityForResult(new Intent(this, EditarGasto.class), INSERIR_EDITAR);
				break;
			case BUSCAR:
				startActivity(new Intent(this, BuscarGasto.class));
				break;
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int posicao, long id) {
		super.onListItemClick(l, v, posicao, id);
		editarGasto(posicao);
	}

	protected void editarGasto(int posicao) {
		
		Gasto gasto = gastos.get(posicao);
		Intent it = new Intent(this, EditarGasto.class);
		it.putExtra(Gastos._ID, gasto.id);

		startActivityForResult(it, INSERIR_EDITAR);
	}

	@Override
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it);

		if (codigoRetorno == RESULT_OK) {
			atualizarLista();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		repositorio.fechar();
	}
}