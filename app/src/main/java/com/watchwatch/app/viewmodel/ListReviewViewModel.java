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

public class ListReviewViewModel extends ViewModel {

    private final PostRepository repository = new PostRepository();
    private final MutableLiveData<List<Post>> posts     = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading    = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage  = new MutableLiveData<>();

    private int categoryId;
    private boolean initialized = false;

    public LiveData<List<Post>> getPosts()      { return posts; }
    public LiveData<Boolean> getIsLoading()     { return isLoading; }
    public LiveData<String> getErrorMessage()   { return errorMessage; }

    public void init(int categoryId) {
        if (initialized) return;
        this.categoryId = categoryId;
        this.initialized = true;
        loadPosts();
    }

    public void refresh() { loadPosts(); }

    private void loadPosts() {
        isLoading.setValue(true);

        Callback<List<Post>> callback = new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null)
                    posts.postValue(response.body());
                isLoading.postValue(false);
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                errorMessage.postValue("Gagal memuat artikel");
                isLoading.postValue(false);
            }
        };

        if (categoryId == 0) {
            // categoryId = 0 artinya "Semua Artikel" (dari tombol Lihat Semua di Home)
            // Fetch semua post tanpa filter kategori
            repository.getLatestPosts(Constants.POSTS_PER_PAGE, 1, callback);
        } else {
            // Fetch post berdasarkan kategori yang dipilih
            repository.getPostsByCategory(categoryId, Constants.POSTS_PER_PAGE, 1, callback);
        }
    }
}
