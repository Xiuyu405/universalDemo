package com.example.crashapp.util;


import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.example.crashapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class NewImageLoader {

    private NewImageLoader() {
        throw new IllegalStateException("no instance");
    }

    private static <T> void checkNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }

    /**
     * 检查传入的url或者数组中第一个url是不是为空
     */
    @SuppressWarnings("uncheck")
    public static boolean isUrlsEmpty(String... urls) {
        return urls == null || urls.length == 0 || urls[0] == null || urls[0].trim().isEmpty();
    }

    @SuppressWarnings("uncheck")
    public static String checkAndHandleUrl(String... urls) {
        if (isUrlsEmpty(urls)) {
            return "empty_url";
        }
        return urls[0];
    }

    public static Picasso getInstance() {
        return Picasso.get();
    }

    public static void load(String path, @DrawableRes int placeholderResId,
                            @NonNull ImageView target) {
        Picasso.get()
                .load(checkAndHandleUrl(path))
                .placeholder(placeholderResId)
                .error(placeholderResId)
                .into(target);
    }

    /**
     * 默认图
     */
    public static void loadCenterCrop(String path,
                                      @NonNull ImageView target) {
        loadCenterCrop(path, R.drawable.ic_launcher_background, target);
    }

    public static void loadCenterCrop(String path,
                                      @DrawableRes int placeholderResId, @NonNull ImageView target) {
        loadCenterCrop(path, placeholderResId, placeholderResId, target);
    }

    public static void loadCenterCrop(String path,
                                      @DrawableRes int placeholderResId, @DrawableRes int errorResId, @NonNull ImageView target) {
        Picasso.get()
                .load(checkAndHandleUrl(path))
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop()
                .fit()
                //.tag(context)
                .into(target);
    }

    public static void loadCenterCrop(@NonNull Context context, @NonNull int resourceId,
                                      @DrawableRes int placeholderResId, @DrawableRes int errorResId, @NonNull ImageView target) {
        if (resourceId == 0) {
            target.setImageDrawable(context.getResources().getDrawable(placeholderResId));
        } else {
            Picasso.get()
                    .load(resourceId)
                    .placeholder(placeholderResId)
                    .error(errorResId)
                    .centerCrop()
                    .fit()
                    .into(target);
        }
    }

    public static void loadCenterCrop(@NonNull Context context, File file,
                                      @DrawableRes int placeholderResId, @DrawableRes int errorResId, @NonNull ImageView target) {
        if (file == null) {
            target.setImageDrawable(context.getResources().getDrawable(placeholderResId));
        } else {
            Picasso.get()
                    .load(file)
                    .placeholder(placeholderResId)
                    .error(errorResId)
                    .centerCrop()
                    .fit()
                    .into(target);
        }
    }

    //圆形
    public static void loadCRoundCrop(String path, @NonNull ImageView target) {
        loadCRoundCrop(path, R.mipmap.ic_123, R.mipmap.ic_launcher, target);
    }

    public static void loadCRoundCrop(String path,
                                      @DrawableRes int placeholderResId, @DrawableRes int errorResId, @NonNull ImageView target) {
        Picasso.get()
                .load(checkAndHandleUrl(path))
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop()
                .fit()
                .transform(new CircleTransform())
                //.tag(context)
                .into(target);
    }

    //圆角
    public static void loadCArcCrop(String path, @NonNull ImageView target) {
        loadCArcCrop(path, R.mipmap.ic_123, R.mipmap.ic_launcher, target);
    }

    public static void loadCArcCrop(String path,
                                    @DrawableRes int placeholderResId, @DrawableRes int errorResId, @NonNull ImageView target) {
        Picasso.get()
                .load(checkAndHandleUrl(path))
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop()
                .fit()
                .transform(new CircleCornerForm())
                //.tag(context)
                .into(target);
    }


    //本地加载
    public static void picassoFile(String file, ImageView imageView) {
        Picasso.get().load(new File(file)).into(imageView);
    }

}



