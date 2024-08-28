package com.example.roomdatabasedemo.modal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomdatabasedemo.Dao.FormRepository;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    private FormRepository repository;
    private LiveData<List<DetailsModal>> allForm;

    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new FormRepository(application);
        allForm = repository.getAllDetail();

    }

    public void insert(DetailsModal modal){

        repository.insert(modal);
    }
    public void update(DetailsModal modal){
        repository.update(modal);
    }
    public void Delete(DetailsModal modal){
        repository.delete(modal);
    }
    public void deleteAllForm(DetailsModal modal){
        repository.deleteAllDetail();
    }

    public LiveData<List<DetailsModal>>getAllForm(){
        return allForm;
    }
    public LiveData<List<DetailsModal>> searchDetails(String query) {
        return repository.searchDetails(query);
    }


}
