package br.com.gastos.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.gastos.activity.Gasto.Gastos;

/**
 * Activity que utiliza o TableLayout para editar o carro
 * 
 * @author rlecheta
 * 
 */
public class EditarGasto extends Activity {
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;

	// Campos texto
	private EditText campoDescricao;
	private EditText campoValor;
	private EditText campoDataCriacao;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);

		setContentView(R.layout.form_editar_gasto);

		campoDescricao = (EditText) findViewById(R.id.campoDescricao);
		campoValor = (EditText) findViewById(R.id.campoValor);
		campoDataCriacao = (EditText) findViewById(R.id.campoDataCriacao);

		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			
			id = extras.getLong(Gastos._ID);

			if (id != null) {
				// é uma edição, busca o carro...
				Gasto c = buscarGasto(id);
				campoDescricao.setText(c.descricao);
				campoValor.setText(String.valueOf(c.valor));
				campoDataCriacao.setText(convertTimestampString(c.dataCriacao));

			}
		}

		ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				// Fecha a tela
				finish();
			}
		});

		// Listener para salvar o carro
		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar();
			}
		});

		ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluir);

		if (id == null) {
			// Se id está nulo, não pode excluir
			btExcluir.setVisibility(View.INVISIBLE);
		} else {
			// Listener para excluir o carro
			btExcluir.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					excluir();
				}
			});
		}
	}

	private String convertTimestampString(long timestamp) {
		
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTimeInMillis(timestamp);
		
		int dia = gc.get(Calendar.DAY_OF_MONTH);
		int mes = gc.get(Calendar.MONTH)+1;
		int ano = gc.get(Calendar.YEAR);
		
		String data = ((dia < 10) ? "0"+dia : dia) + "/" + ((mes < 10)?"0"+mes:mes) + "/" + ano;
		return data;
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada na tela pendente
		setResult(RESULT_CANCELED);

		// Fecha a tela
		finish();
	}

	public void salvar() {

		int data = 0;
		try {
			data = Integer.parseInt(campoDataCriacao.getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}

		Gasto gasto = new Gasto();
		if (id != null) {
			// É uma atualização
			gasto.id = id;
		}
		gasto.descricao = campoDescricao.getText().toString();
		gasto.valor = Double.parseDouble(campoValor.getText().toString());
		gasto.dataCriacao = data;

		// Salvar
		salvarGasto(gasto);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		if (id != null) {
			excluirGasto(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar o carro pelo id
	protected Gasto buscarGasto(long id) {
		return GastosDoDia.repositorio.buscarGasto(id);
	}

	// Salvar o carro
	protected void salvarGasto(Gasto gasto) {
		GastosDoDia.repositorio.salvar(gasto);
	}

	// Excluir o carro
	protected void excluirGasto(long id) {
		GastosDoDia.repositorio.deletar(id);
	}
}
