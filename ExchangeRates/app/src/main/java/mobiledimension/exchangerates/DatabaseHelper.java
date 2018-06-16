package mobiledimension.exchangerates;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static mobiledimension.exchangerates.MainMenu.LOG_TAG;

/**
 * Created by Tural on 23.12.2017.
 */

class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper(Context context, String Path) {
        super(context, Path + "/"
                + "ExchangeRatesDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase ExchangeRatesDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        ExchangeRatesDatabase.execSQL("create table ExchangeRatesTable ("
                + "date int,"
                + "currency text,"
                + "json text," +
                "PRIMARY KEY(date, currency)" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

