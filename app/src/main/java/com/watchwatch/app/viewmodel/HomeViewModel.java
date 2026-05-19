package com.watchwatch.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.watchwatch.app.model.Post;
import com.watchwatch.app.repository.PostRepository;
import com.watchwatch.app.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final PostRepository repository = new PostRepository();

    private final MutableLiveData<List<Post>> trendingPosts = new MutableLiveData<>();
    private final MutableLiveData<List<Post>> latestPosts   = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading        = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage      = new MutableLiveData<>();

    public HomeViewModel() { loadAll(); }

    public LiveData<List<Post>> getTrendingPosts() { return trendingPosts; }
    public LiveData<List<Post>> getLatestPosts()   { return latestPosts; }
    public LiveData<Boolean> getIsLoading()        { return isLoading; }
    public LiveData<String> getErrorMessage()      { return errorMessage; }

    public void refresh() { loadAll(); }

    private void loadAll() {
        isLoading.setValue(true);
        loadTrending();
        loadLatest();
    }

    private void loadTrending() {
        repository.getPostsByTag(Constants.TAG_TRENDING_ID, Constants.HOME_POSTS_LIMIT,
                new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null)
                    trendingPosts.postValue(response.body());
                isLoading.postValue(false);
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                errorMessage.postValue("Gagal memuat trending");
                isLoading.postValue(false);
            }
        });
    }

    private void loadLatest() {
        repository.getLatestPosts(Constants.HOME_POSTS_LIMIT + 1, 1,
                new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null)
                    latestPosts.postValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                errorMessage.postValue("Gagal memuat artikel terbaru");
            }
        });
    }
}
