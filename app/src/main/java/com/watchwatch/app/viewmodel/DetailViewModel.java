package com.watchwatch.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.watchwatch.app.model.Post;
import com.watchwatch.app.repository.PostRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {

    private final PostRepository repository = new PostRepository();
    private final MutableLiveData<Post> post       = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error    = new MutableLiveData<>();

    private boolean initialized = false;

    public LiveData<Post> getPost()           { return post; }
    public LiveData<Boolean> getIsLoading()   { return isLoading; }
    public LiveData<String> getError()        { return error; }

    public void init(int postId) {
        if (initialized) return;
        initialized = true;
        loadPost(postId);
    }

    private void loadPost(int postId) {
        isLoading.setValue(true);
        repository.getPostDetail(postId, new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    post.postValue(response.body());
                } else {
                    error.postValue("Artikel tidak ditemukan");
                }
                isLoading.postValue(false);
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                error.postValue("Gagal memuat artikel. Periksa koneksi internet.");
                isLoading.postValue(false);
            }
        });
    }
}
