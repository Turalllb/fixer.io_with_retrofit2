package mobiledimension.exchangerates;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static mobiledimension.exchangerates.MainMenu.LOG_TAG;

/**
 * Created by Tural on 23.12.2017.
 */

class DatabaseManagement {
    private SQLiteDatabase exchangeRatesDatabase;

    DatabaseManagement(SQLiteDatabase sqLiteDatabase) {
        exchangeRatesDatabase = sqLiteDatabase;
    }

    void setDataBase(String strDate, String currency, String json) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd"); //проверить не парсит ли формат в котором дата находится изначально
            Date date = format.parse(strDate);
            long unixTimeDate = date.getTime();
            Log.d(LOG_TAG, unixTimeDate + "");
            // подготовим данные для вставки в виде пар: наименование столбца -
            // значение
            cv.put("date", unixTimeDate);
            cv.put("currency", currency);
            cv.put("json", json);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "--- Insert in ExchangeRatesDatabase: ---");
        // вставляем запись и получаем ее ID
        long rowID = exchangeRatesDatabase.insert("ExchangeRatesTable", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);

        // делаем запрос всех данных из таблицы ExchangeRatesDatabase, получаем Cursor
        Cursor c = exchangeRatesDatabase.query("ExchangeRatesTable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idDate = c.getColumnIndex("date");
            int currencyColIndex = c.getColumnIndex("currency");
            int jsonColIndex = c.getColumnIndex("json");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "date = " + c.getLong(idDate) +
                                ", currency = " + c.getString(currencyColIndex) +
                                ", json = " + c.getString(jsonColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }


    String getStrPostModel(String strDate, String currency) {

        long unixTimeDate;
        String postModel = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");
            Date date = format.parse(strDate);
            unixTimeDate = date.getTime();

            Cursor c = exchangeRatesDatabase.query("ExchangeRatesTable",
                    new String[]{"json"},
                    "date = ? AND currency = ?",
                    new String[]{Long.toString(unixTimeDate), currency},
                    null, null, null);

            postModel = (c != null && c.moveToFirst()) ? c.getString(c.getColumnIndex("json")) : null;
            if (c != null) c.close();
        } catch (ParseException e) { //ловим не только ParseException, но и исключение, которое выбросится,
            e.printStackTrace();     // если в момент выполнения программы, удалить базу данных с
        }
        return postModel;
    }

}
 