package br.com.gastos.activity;

import android.content.Context;

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
public class RepositorioGastoScript extends RepositorioGasto {

	// Script para fazer drop na tabela
	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS gastos";

	// Cria a tabela com o "_id" sequencial
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
		"CREATE TABLE 'gastos' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'descricao' TEXT check(typeof('descricao') = 'text'), 'valor' DOUBLE, 'data_criacao' DATETIME);"};

	// Nome do banco
	private static final String NOME_BANCO = "repositorio_gastos";

	// Controle de versão
	private static final int VERSAO_BANCO = 1;

	// Nome da tabela
	public static final String TABELA_GASTO = "gastos";

	// Classe utilitária para abrir, criar, e atualizar o banco de dados
	private SQLiteHelper dbHelper;

	// Cria o banco de dados com um script SQL
	public RepositorioGastoScript(Context ctx) {
		// Criar utilizando um script SQL
		dbHelper = new SQLiteHelper(ctx, RepositorioGastoScript.NOME_BANCO, RepositorioGastoScript.VERSAO_BANCO,
				RepositorioGastoScript.SCRIPT_DATABASE_CREATE, RepositorioGastoScript.SCRIPT_DATABASE_DELETE);

		// abre o banco no modo escrita para poder alterar também
		db = dbHelper.getWritableDatabase();
	}

	// Fecha o banco
	@Override
	public void fechar() {
		super.fechar();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
