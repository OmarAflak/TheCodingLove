package me.aflak.thecodinglove.ui.main.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build().inject(this);

        presenter.onCreate();
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
