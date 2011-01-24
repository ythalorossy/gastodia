package br.com.gastos.activity;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GastoListAdapter extends BaseAdapter {

	private Context context;
	private List<Gasto> lista;

	public GastoListAdapter(Context context, List<Gasto> lista) {
		this.context = context;
		this.lista = lista;
	}

	public int getCount() {
		return lista.size();
	}

	public Object getItem(int position) {
		return lista.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Gasto g = lista.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.gasto_linha_tabela, null);

		TextView nome = (TextView) view.findViewById(R.id.descricao);
		nome.setText(g.descricao);

		TextView placa = (TextView) view.findViewById(R.id.valor);
		placa.setText(String.valueOf(g.valor));

//		TextView dataCriacao = (TextView) view.findViewById(R.id.dataCriacao);
		
//		Calendar gc = GregorianCalendar.getInstance();
//		gc.setTimeInMillis(g.dataCriacao);
//		
//		int dia = gc.get(Calendar.DAY_OF_MONTH);
//		int mes = gc.get(Calendar.MONTH)+1;
//		int ano = gc.get(Calendar.YEAR);
//		
//		dataCriacao.setText( dia + "/" + mes + "/" + ano);

		return view;
	}
}