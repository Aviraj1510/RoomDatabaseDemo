package com.example.roomdatabasedemo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DetailsModal.class}, version = 5)
public abstract class FormDatabase extends RoomDatabase {

    private static FormDatabase instance;
    public abstract Dao dao();

    public static synchronized FormDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FormDatabase.class, "form_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        PopulateDbAsyncTask(FormDatabase db) {
            dao = db.dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
