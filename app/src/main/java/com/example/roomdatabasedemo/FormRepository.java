package com.example.roomdatabasedemo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FormRepository {
    private Dao dao;
    private LiveData<List<DetailsModal>> allDetail;

    public FormRepository(Application application) {
        FormDatabase database = FormDatabase.getInstance(application);
        dao = database.dao();
        allDetail = dao.getAllDetail();
    }
    public void insert(DetailsModal model) {
        new InsertFormAsyncTask(dao).execute(model);
    }
    public void update(DetailsModal model) {
        new  UpdateFormAsyncTask(dao).execute(model);
    }
    public void delete(DetailsModal model) {
        new DeleteFormAsyncTask(dao).execute(model);
    }

    public void deleteAllDetail(){
        new DeleteAllFormDetailAsyncTask(dao).execute();
    }

    public LiveData<List<DetailsModal>> getAllDetail(){

        return allDetail;
    }
    public LiveData<List<DetailsModal>> searchDetails(String query) {
        return dao.searchDetails(query);
    }

//Async Task Method For Insert Form
    private static class InsertFormAsyncTask extends AsyncTask<DetailsModal, Void, Void>{

        private Dao dao;

        private InsertFormAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DetailsModal... model) {
            dao.insert(model[0]);
            return null;
        }
    }

//Async Task Method For Update Form
    private static class UpdateFormAsyncTask extends AsyncTask<DetailsModal, Void, Void>{

        private Dao dao;

        private UpdateFormAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DetailsModal... model) {
            dao.update(model[0]);
            return null;
        }
    }
//Async Task Method For Delete Form
    private static class DeleteFormAsyncTask extends AsyncTask<DetailsModal, Void, Void>{

        private Dao dao;

        private DeleteFormAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DetailsModal... model) {
            dao.delete(model[0]);
            return null;
        }
    }

//Async Task Method For Delete All Form

    private static class DeleteAllFormDetailAsyncTask extends AsyncTask<Void, Void, Void>{

        private Dao dao;

        public DeleteAllFormDetailAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllForms();
            return null;
        }
    }


}
