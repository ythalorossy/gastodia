package br.com.gastos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Buscar o Carro.
 * 
 * @author rlecheta
 * 
 */
public class BuscarGasto extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_buscar_gasto);

		ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscar);
		btBuscar.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada pendente na tela
		setResult(RESULT_CANCELED);

		// Fecha a tela
		finish();
	}

	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View view) {

		EditText descricao = (EditText) findViewById(R.id.campoDescricao);
		EditText valor = (EditText) findViewById(R.id.campoValor);
		EditText dataCriacao = (EditText) findViewById(R.id.campoDataCriacao);

		// Recupera o descricao do gasto
		String descricaoGasto = descricao.getText().toString();

		// Busca o gasto pela descricao
		Gasto gasto = buscarGasto(descricaoGasto);

		if (gasto != null) {
			// Atualiza os campos com o resultado
			valor.setText(String.valueOf(gasto.valor));
			dataCriacao.setText(String.valueOf(gasto.dataCriacao));
		} else {
			// Limpa os campos
			valor.setText("");
			dataCriacao.setText("");

			Toast.makeText(BuscarGasto.this, "Nenhum carro encontrado", Toast.LENGTH_SHORT).show();
		}
	}

	// Busca um carro pelo nome
	protected Gasto buscarGasto(String descricaoGasto) {
		Gasto gasto = GastosDoDia.repositorio.buscarGastoPorDescricao(descricaoGasto);
		return gasto;
	}
}
