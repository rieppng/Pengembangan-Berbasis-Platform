package com.watchwatch.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.watchwatch.app.R;
import com.watchwatch.app.database.BookmarkEntity;
import com.watchwatch.app.databinding.ActivityDetailBinding;
import com.watchwatch.app.model.Post;
import com.watchwatch.app.utils.Constants;
import com.watchwatch.app.utils.DateUtils;
import com.watchwatch.app.viewmodel.BookmarkViewModel;
import com.watchwatch.app.viewmodel.DetailViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private DetailViewModel viewModel;
    private BookmarkViewModel bookmarkViewModel;

    private Post currentPost;
    private boolean isBookmarked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int postId = getIntent().getIntExtra(Constants.EXTRA_POST_ID, -1);
        String postTitle = getIntent().getStringExtra(Constants.EXTRA_POST_TITLE);

        setupToolbar(postTitle);
        setupViewModels(postId);
        observeData(postId);
    }

    private void setupToolbar(String title) {
        binding.toolbar.setTitle(title != null ? title : "Detail Artikel");
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.toolbar.inflateMenu(R.menu.menu_detail);
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_bookmark) {
                toggleBookmark();
                return true;
            }
            return false;
        });
    }

    private void setupViewModels(int postId) {
        if (postId == -1) {
            Toast.makeText(this, "Artikel tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        viewModel.init(postId);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
    }

    private void observeData(int postId) {
        viewModel.getIsLoading().observe(this, isLoading ->
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.getPost().observe(this, post -> {
            currentPost = post;
            displayPost(post);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty())
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });

        bookmarkViewModel.isBookmarked(postId).observe(this, count -> {
            isBookmarked = count != null && count > 0;
            updateBookmarkIcon();
        });
    }

    private void displayPost(Post post) {
        Glide.with(this)
                .load(post.getFeaturedImageUrl())
                .centerCrop()
                .into(binding.ivFeatured);

        binding.tvTitle.setText(post.getTitleRendered());
        binding.toolbar.setTitle("");
        binding.tvCategory.setText(post.getCategoryName());
        binding.tvDate.setText(DateUtils.formatDate(post.getDate()));

        String rawHtml = post.getContentRendered();
        String cleanHtml = rawHtml
                .replaceAll("<img[^>]*>", "")
                .replaceAll("(?s)<!--.*?-->", "")
                .replaceAll("(?s)<figure[^>]*>.*?</figure>", "");

        binding.tvContent.setText(
                Html.fromHtml(cleanHtml, Html.FROM_HTML_MODE_COMPACT));

        extractAndShowShopeeButton(rawHtml);
    }

    private void extractAndShowShopeeButton(String htmlContent) {
        try {
            Document doc = Jsoup.parse(htmlContent);
            Element shopeeEl = doc.select("a:containsOwn(Link Shopee)").first();
            if (shopeeEl != null) {
                String url = shopeeEl.attr("href");
                binding.btnShopee.setVisibility(View.VISIBLE);
                binding.btnShopee.setOnClickListener(v ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
            } else {
                binding.btnShopee.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            binding.btnShopee.setVisibility(View.GONE);
        }
    }

    private void updateBookmarkIcon() {
        MenuItem item = binding.toolbar.getMenu().findItem(R.id.action_bookmark);
        if (item == null) return;
        item.setIcon(isBookmarked
                ? R.drawable.ic_bookmark
                : R.drawable.ic_bookmark_outline);
    }

    /** Simpan atau hapus bookmark saat ikon diklik */
    private void toggleBookmark() {
        if (currentPost == null) return;

        if (isBookmarked) {
            bookmarkViewModel.removeBookmark(currentPost.getId());
            Toast.makeText(this, "Bookmark dihapus", Toast.LENGTH_SHORT).show();
        } else {
            BookmarkEntity entity = new BookmarkEntity();
            entity.id           = currentPost.getId();
            entity.title        = currentPost.getTitleRendered();
            entity.imageUrl     = currentPost.getFeaturedImageUrl();
            entity.categoryName = currentPost.getCategoryName();
            entity.date         = currentPost.getDate();
            entity.savedAt      = System.currentTimeMillis();

            bookmarkViewModel.addBookmark(entity);
            Toast.makeText(this, "Artikel disimpan ke Bookmark", Toast.LENGTH_SHORT).show();
        }
        // isBookmarked akan otomatis terupdate via LiveData observer di atas
    }
}
