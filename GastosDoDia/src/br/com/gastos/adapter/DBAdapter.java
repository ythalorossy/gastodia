package br.com.gastos.adapter;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public static final String KEY_ROWID = "id";
	public static final String KEY_DESCRICAO = "descricao";
	public static final String KEY_VALOR = "valor";
	public static final String KEY_DATA = "data";
	
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "gerenciador";
	private static final String DATABASE_TABLE = "gastos";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table gastos (" +
					"_id integer primary key autoincrement, " +
					"descricao text not null, " +
					"valor real not null, " + 
					"data numeric not null);";

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() 
    {
        DBHelper.close();
    }
    
    public long insertGastos(String descricao, float valor, Date data) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DESCRICAO, descricao);
        initialValues.put(KEY_VALOR, valor);
        initialValues.put(KEY_DATA, data.getTime());
        return db.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteGasto(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllGastos() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_DESCRICAO,
        		KEY_VALOR,
                KEY_DATA}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    public Cursor getGasto(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_DESCRICAO, 
                		KEY_VALOR,
                		KEY_DATA
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateGasto(long rowId, String descricao, float valor, Date data) 
    {
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRICAO, descricao);
        values.put(KEY_VALOR, valor);
        values.put(KEY_DATA, data.getTime());
        return db.update(DATABASE_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;
    }

	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Atualizando da versão " + oldVersion + " para " + newVersion + ", que destruirá todos os dados.");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
}
