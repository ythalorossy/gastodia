package br.com.gastos.activity;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Gasto {

	public static String[] colunas = new String[] { Gastos._ID, Gastos.DESCRICAO, Gastos.VALOR, Gastos.DATA_CRIACAO};

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "br.com.gastos.provider.gasto";

	public long id;
	public String descricao;
	public double valor;
	public long dataCriacao;

	public Gasto() {
	}

	public Gasto(String descricao, double valor, long dataCriacao) {
		super();
		this.descricao = descricao;
		this.valor = valor;
		this.dataCriacao = dataCriacao;
	}

	public Gasto(long id, String descricao, double valor, int dataCriacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
	 * Android
	 */
	public static final class Gastos implements BaseColumns {
	
		// Não pode instanciar esta Classe
		private Gastos() {
		}
	
		// content://br.com.gastos.provider.gasto/gastos
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/gastos");
	
		// Mime Type para todos os gastos
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.gastos";
	
		// Mime Type para um único gasto
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.gastos";
	
		// Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		public static final String DESCRICAO = "descricao";
		public static final String VALOR = "valor";
		public static final String DATA_CRIACAO = "data_criacao";
	
		// Método que constrói uma Uri para um Gasto específico, com o seu id
		// A Uri é no formato "content://br.com.gastos.provider.gasto/gastos/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /gastos
			Uri uriGasto = ContentUris.withAppendedId(Gastos.CONTENT_URI, id);
			return uriGasto;
		}
	}

	@Override
	public String toString() {
		return "Descricao: " + descricao + ", Valor: " + valor + ", Data Criacao: " + dataCriacao;
	}
}
