package me.aflak.thecodinglove.ui.main.view;


import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
        presenter.onCreate();
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
    public void showPost(Post post) {
        Glide.with(this).load(post.getLink()).into(imageView);
        description.setText(post.getDescription());
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
