package org.dantelab.kotlinweakreferences;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Api.ApiHandler<Api.Comment> {

    Api api = new Api();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComments();
    }


    void loadComments() {
        api.getComments(new Api.ApiHandler<Api.Comment>() {
            @Override
            public void onResult(@Nullable List<Api.Comment> comments, @Nullable Exception exception) {
                if (comments != null) {
                    updateUI(comments);
                } else {
                    displayError(exception);
                }
            }
        });
    }

    void loadCommentsWithWeakReferenceToThis() {
        final WeakReference<MainActivity> weakThis = new WeakReference<>(this);
        api.getComments(new Api.ApiHandler<Api.Comment>() {
            @Override
            public void onResult(@Nullable List<Api.Comment> comments, @Nullable Exception exception) {
                MainActivity strongThis = weakThis.get();
                if (strongThis != null)
                    if (comments != null)
                        strongThis.updateUI(comments);
                     else
                        strongThis.displayError(exception);
            }
        });
    }

    void loadCommentsWithWeakApi() {
        api.getCommentsWeak(this);
    }

    @Override
    public void onResult(@Nullable List<Api.Comment> comments, @Nullable Exception exception) {
        if (comments != null)
            updateUI(comments);
        else
            displayError(exception);
    }

    void updateUI(List<Api.Comment> comments) {}

    void displayError(Exception error) {}

    public void onClick(View view) {
        loadCommentsWithWeakApi();
    }
}
