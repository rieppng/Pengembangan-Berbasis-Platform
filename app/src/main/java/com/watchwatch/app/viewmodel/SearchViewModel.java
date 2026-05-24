package com.watchwatch.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.watchwatch.app.model.Post;
import com.watchwatch.app.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private static final int SEARCH_LIMIT = 15;

    private final PostRepository repository = new PostRepository();
    private final MutableLiveData<List<Post>> results  = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading   = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isEmpty     = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> showHint    = new MutableLiveData<>(true);

    public LiveData<List<Post>> getResults()  { return results; }
    public LiveData<Boolean> getIsLoading()   { return isLoading; }
    public LiveData<Boolean> getIsEmpty()     { return isEmpty; }
    public LiveData<Boolean> getShowHint()    { return showHint; }

    public void search(String query) {
        showHint.setValue(false);
        isLoading.setValue(true);
        isEmpty.setValue(false);

        repository.searchPosts(query, SEARCH_LIMIT, new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    results.postValue(response.body());
                    isEmpty.postValue(response.body().isEmpty());
                }
                isLoading.postValue(false);
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                isLoading.postValue(false);
                isEmpty.postValue(true);
            }
        });
    }

    public void clearResults() {
        results.setValue(new ArrayList<>());
        isEmpty.setValue(false);
        showHint.setValue(true);
    }
}
