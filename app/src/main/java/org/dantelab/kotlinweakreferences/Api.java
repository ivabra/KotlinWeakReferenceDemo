package org.dantelab.kotlinweakreferences;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanbrazhnikov on 07/04/2017.
 */

class Api {
    void getComments(final ApiHandler<Comment> handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.onResult(new ArrayList<Comment>(), null);
            }
        }).start();
    }

    void getCommentsWeak(ApiHandler<Comment> handler) {
        final WeakReference<ApiHandler<Comment>> weakHandler = new WeakReference<>(handler);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ApiHandler<Comment> strongHandler = weakHandler.get();
                if (strongHandler != null) {
                    strongHandler.onResult(new ArrayList<Comment>(), null);
                }
            }
        }).start();
    }

    static class Comment {}

    interface ApiHandler<T> {
        void onResult(@Nullable List<Comment> comments, @Nullable Exception exception);
    }
}
