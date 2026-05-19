package com.watchwatch.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.watchwatch.app.model.Category;
import com.watchwatch.app.repository.PostRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {

    private final PostRepository repository = new PostRepository();
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public CategoryViewModel() {
        loadCategories();
    }

    public LiveData<List<Category>> getCategories() { return categories; }
    public LiveData<Boolean> getIsLoading()         { return isLoading; }

    private void loadCategories() {
        isLoading.setValue(true);
        repository.getCategories(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories.postValue(response.body());
                }
                isLoading.postValue(false);
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                isLoading.postValue(false);
            }
        });
    }
}
