package me.aflak.thecodinglove.ui.main.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aflak.thecodinglove.MyApp;
import me.aflak.thecodinglove.R;
import me.aflak.thecodinglove.entitiy.Post;
import me.aflak.thecodinglove.ui.main.data.DaggerMainComponent;
import me.aflak.thecodinglove.ui.main.data.MainModule;
import me.aflak.thecodinglove.ui.main.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.activity_main_imageview) ImageView imageView;
    @BindView(R.id.activity_main_progress) ProgressBar progressBar;
    @BindView(R.id.activity_main_description) TextView description;

    @Inject MainPresenter presenter;
    @Inject Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .appModule(MyApp.app.appModule())
                .mainModule(new MainModule(this))
                .build().inject(this);

        init();
        presenter.onCreate(savedInstanceState);
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            TextView tv = new TextView(getApplicationContext());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(lp);
            tv.setText(R.string.app_name);
            tv.setTextSize(20);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTypeface(typeface);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(tv);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_github:
                presenter.menuGithubRepoClicked(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstance(outState);
    }

    @OnClick(R.id.activity_main_next)
    public void onNextClicked(){
        presenter.nextPost();
    }

    @OnClick(R.id.activity_main_previous)
    public void onPreviousClicked(){
        presenter.previousPost();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPost(Post post, RequestListener<Drawable> listener) {
        Glide.with(this).load(post.getLink()).listener(listener).into(imageView);
        description.setText(post.getDescription());
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
