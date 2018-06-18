package me.aflak.thecodinglove.ui.main.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import me.aflak.thecodinglove.R;
import me.aflak.thecodinglove.entitiy.Post;
import me.aflak.thecodinglove.ui.main.interactor.MainInteractor;
import me.aflak.thecodinglove.ui.main.view.MainView;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private MainInteractor interactor;

    public MainPresenterImpl(MainView view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate(Bundle bundle) {
        view.showProgress();

        if(bundle==null) {
            nextPost();
        }
        else{
            interactor.restoreInstanceState(bundle);
            interactor.currentPost(new MainInteractor.PostCallback() {
                @Override
                public void onPost(Post post) {
                    view.showPost(post, requestListener);
                }

                @Override
                public void onError(String error) {
                    view.toast(error);
                }
            });
        }
    }

    @Override
    public void onSaveInstance(Bundle bundle) {
        interactor.saveInstanceState(bundle);
    }

    @Override
    public void previousPost() {
        view.showProgress();
        interactor.previousPost(new MainInteractor.PostCallback() {
            @Override
            public void onPost(Post post) {
                view.showPost(post, requestListener);
            }

            @Override
            public void onError(String error) {
                view.hideProgress();
                view.toast(error);
            }
        });
    }

    @Override
    public void nextPost() {
        view.showProgress();
        interactor.nextPost(new MainInteractor.PostCallback() {
            @Override
            public void onPost(Post post) {
                view.showPost(post, requestListener);
            }

            @Override
            public void onError(String error) {
                view.hideProgress();
                view.toast(error);
            }
        });
    }

    @Override
    public void menuGithubRepoClicked(Context context) {
        String url = context.getResources().getString(R.string.github_repo_link);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    @Override
    public void onImageLongClick(final Context context) {
        final Post post = interactor.getCurrentPost();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = Glide.with(context)
                            .downloadOnly()
                            .load(post.getLink())
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();

                    File dst = new File(file.getParent(), file.getName()+".gif");
                    copy(file, dst);

                    Log.d(getClass().getSimpleName(), file.getParent());
                    Log.d("LIST", Arrays.toString(new File(file.getParent()).list()));

                    Log.d("PRESENTER", file.getAbsolutePath());
                    Log.d("PRESENTER", dst.getAbsolutePath());

                    Uri uri = FileProvider.getUriForFile(context, context.getPackageName(), dst);

                    Intent intent = ShareCompat.IntentBuilder.from((Activity) context)
                            .setType("image/gif")
                            .setText("<lol>"+post.getDescription()+"</lol>")
                            .setStream(uri)
                            .setChooserTitle(R.string.main_share_image_text)
                            .createChooserIntent()
                            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    context.startActivity(intent);
                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                    Log.e(getClass().getSimpleName(), e.getMessage());
                }
            }
        }).start();
    }

    private RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            view.hideProgress();
            view.toast("Could not load gif...");
            nextPost();
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            view.hideProgress();
            return false;
        }
    };

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }
}
