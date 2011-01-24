package br.com.gastos.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import br.com.gastos.activity.Gasto.Gastos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * <pre>
 * Repositório para carros que utiliza o SQLite internamente
 * 
 * Para visualizar o banco pelo adb shell:
 * 
 * &gt;&gt; sqlite3 /data/data/br.livro.android.exemplos.banco/databases/BancoCarro
 * 
 * &gt;&gt; Mais info dos comandos em: http://www.sqlite.org/sqlite.html
 * 
 * &gt;&gt; .exit para sair
 * 
 * </pre>
 * 
 * @author rlecheta
 * 
 */
public class RepositorioGasto {
	private static final String CATEGORIA = "gastos";

	// Nome do banco
	private static final String NOME_BANCO = "repositorio";
	// Nome da tabela
	public static final String NOME_TABELA = "gastos";

	protected SQLiteDatabase db;

	public RepositorioGasto(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected RepositorioGasto() {
		// Apenas para criar uma subclasse...
	}

	// Salva o carro, insere um novo ou atualiza
	public long salvar(Gasto gasto) {
		
		long id = gasto.id;

		if (id != 0) {
			atualizar(gasto);
		} else {
			// Insere novo
			id = inserir(gasto);
		}

		return id;
	}

	// Insere um novo gasto
	public long inserir(Gasto gasto) {
		
		ContentValues values = new ContentValues();
		values.put(Gastos.DESCRICAO, gasto.descricao);
		values.put(Gastos.VALOR, gasto.valor);
		values.put(Gastos.DATA_CRIACAO, GregorianCalendar.getInstance().getTimeInMillis());

		long id = inserir(values);
		
		return id;
	}

	// Insere um novo gasto
	public long inserir(ContentValues valores) {
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	// Atualiza o gasto no banco. O id do gasto é utilizado.
	public int atualizar(Gasto gasto) {
		ContentValues values = new ContentValues();
		values.put(Gastos.DESCRICAO, gasto.descricao);
		values.put(Gastos.VALOR, gasto.valor);
		values.put(Gastos.DATA_CRIACAO, gasto.dataCriacao);

		String _id = String.valueOf(gasto.id);

		String where = Gastos._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = atualizar(values, where, whereArgs);

		return count;
	}

	// Atualiza o gasto com os valores abaixo
	// A cláusula where é utilizada para identificar o gasto a ser atualizado
	public int atualizar(ContentValues valores, String where, String[] whereArgs) {
		int count = db.update(NOME_TABELA, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta o gasto com o id fornecido
	public int deletar(long id) {
		String where = Gastos._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = deletar(where, whereArgs);

		return count;
	}

	// Deleta o gasto com os argumentos fornecidos
	public int deletar(String where, String[] whereArgs) {
		int count = db.delete(NOME_TABELA, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		return count;
	}

	// Busca o gasto pelo id
	public Gasto buscarGasto(long id) {
		// select * from gasto where _id=?
		Cursor c = db.query(true, NOME_TABELA, Gasto.colunas, Gastos._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			Gasto gasto = new Gasto();

			// Lê os dados
			gasto.id = c.getLong(0);
			gasto.descricao = c.getString(1);
			gasto.valor = c.getDouble(2);
			gasto.dataCriacao = c.getLong(3);

			return gasto;
		}

		return null;
	}

	// Retorna um cursor com todos os gastos
	public Cursor getCursor() {
		try {
			// select * from gastos
			return db.query(NOME_TABELA, Gasto.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os carros: " + e.toString());
			return null;
		}
	}

	// Retorna uma lista com todos os gastos
	public List<Gasto> listarGastos() {
		
		Cursor c = getCursor();

		List<Gasto> gastos = new ArrayList<Gasto>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Gastos._ID);
			int idxDescricao = c.getColumnIndex(Gastos.DESCRICAO);
			int idxValor = c.getColumnIndex(Gastos.VALOR);
			int idxDataCriacao = c.getColumnIndex(Gastos.DATA_CRIACAO);

			do {
				Gasto gasto = new Gasto();
				gastos.add(gasto);

				// recupera os atributos de gasto
				gasto.id = c.getLong(idxId);
				gasto.descricao = c.getString(idxDescricao);
				gasto.valor = c.getDouble(idxValor);
				gasto.dataCriacao = c.getLong(idxDataCriacao);

			} while (c.moveToNext());
		}

		return gastos;
	}
	
	// Retorna uma lista com todos os gastos do dia.
	public List<Gasto> listarGastosDoDia() {
		
		List<Gasto> gastos = new ArrayList<Gasto>();
		
		Calendar dataAtual = GregorianCalendar.getInstance(new Locale("pt", "BR"));
		dataAtual.set(Calendar.HOUR_OF_DAY, 0);
		dataAtual.set(Calendar.MINUTE, 0);
		dataAtual.set(Calendar.SECOND, 0);
		
		final String inicioDia = String.valueOf(dataAtual.getTimeInMillis());
		
		dataAtual.set(Calendar.HOUR_OF_DAY, 23);
		dataAtual.set(Calendar.MINUTE, 59);
		dataAtual.set(Calendar.SECOND, 59);

		final String fimDia = String.valueOf(dataAtual.getTimeInMillis());
		
		String where = Gastos.DATA_CRIACAO + " between ? and ?";

		String[] argumentos = new String[]{inicioDia, fimDia};
		
		String orderBy = Gastos.DATA_CRIACAO + " DESC";
		
		try {
			
			Cursor c = db.query(NOME_TABELA, Gasto.colunas, where, argumentos, null, null, orderBy, null);
			
			if (c.moveToFirst()) {
	
				// Recupera os índices das colunas
				int idxId = c.getColumnIndex(Gastos._ID);
				int idxDescricao = c.getColumnIndex(Gastos.DESCRICAO);
				int idxValor = c.getColumnIndex(Gastos.VALOR);
				int idxDataCriacao = c.getColumnIndex(Gastos.DATA_CRIACAO);
	
				do {
					Gasto gasto = new Gasto();
					gastos.add(gasto);
	
					// recupera os atributos de gasto
					gasto.id = c.getLong(idxId);
					gasto.descricao = c.getString(idxDescricao);
					gasto.valor = c.getDouble(idxValor);
					gasto.dataCriacao = c.getLong(idxDataCriacao);
	
				} while (c.moveToNext());
			}
	
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os gastos: " + e.toString());
			return null;
		}

		
		return gastos;
	}

	// Busca o gasto pelo nome "select * from gasto where descricao=?"
	public Gasto buscarGastoPorDescricao(String descricao) {
		Gasto gasto = null;

		try {
			// Idem a: SELECT _id,descrica,valor,data_criacao from GASTO where descricao = ?
			Cursor c = db.query(NOME_TABELA, Gasto.colunas, Gastos.DESCRICAO + "='" + descricao + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				gasto = new Gasto();

				// utiliza os métodos getLong(), getString(), getLong(), etc para recuperar os valores
				gasto.id = c.getLong(0);
				gasto.descricao = c.getString(1);
				gasto.valor = c.getDouble(2);
				gasto.dataCriacao = c.getLong(3);
			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o gasto pela descricao: " + e.toString());
			return null;
		}

		return gasto;
	}

	// Busca um gasto utilizando as configurações definidas no
	// SQLiteQueryBuilder
	// Utilizado pelo Content Provider de gasto
	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}

	// Fecha o banco
	public void fechar() {
		// fecha o banco de dados
		if (db != null) {
			db.close();
		}
	}
}
