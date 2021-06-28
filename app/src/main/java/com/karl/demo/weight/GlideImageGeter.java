/*
package com.karl.demo.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.ftms.superapp.common.glide.GlideApp;
import com.ftms.superapp.common.glide.GlideRequest;
import com.ftms.superapp.common.glide.GlideRequests;
import com.ftms.superapp.common.utils.RoundCorner;
import com.ftms.superapp.community.R;
import com.karl.demo.R;

import java.util.HashSet;

*/
/**
 * description: TODO
 *
 * @author autoai
 * @version V1.0
 * Copyright (C), 2020, NavInfo 四维图新
 * @date 2021/5/19 2:39 PM
 *//*

public class GlideImageGeter implements Html.ImageGetter{
    private HashSet<Target> targets;
    private HashSet<GifDrawable> gifDrawables;
    private final Context mContext;
    private final TextView mTextView;

    public  void recycle() {
        targets.clear();
        for (GifDrawable gifDrawable : gifDrawables) {
            gifDrawable.setCallback(null);
            gifDrawable.recycle();
        }
        gifDrawables.clear();
    }

    public GlideImageGeter(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
        mTextView.setTag(R.id.img_tag, this);
    }

    @SuppressLint("CheckResult")
    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        final Target target;

        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners((int)mContext.getResources().getDimension(R.dimen.dp_12));
        MultiTransformation multiTransformation = new MultiTransformation(new CenterCrop(),roundedCorners);
        RequestOptions options = new RequestOptions();
        options.transform(multiTransformation);
        options.override(setWigth(), setHeigh());
        options.placeholder(R.drawable.common_imageview_net_error);
        options.error(R.drawable.common_imageview_net_error);
//        options.diskCacheStrategy(DiskCacheStrategy.NONE);//不做磁盘缓存
//        options.skipMemoryCache(false);//不做内存缓存
//        options.centerCrop();

        if(isGif(url)){
            GlideRequest<GifDrawable> loadgif;
            loadgif = GlideApp.with(mContext).asGif().load(url).apply(options);
            target = new GifTarget(urlDrawable);
            targets.add(target);
            loadgif.into(target);
        }else {
            GlideRequest<Bitmap> load;
            load = GlideApp.with(mContext).asBitmap().load(url).apply(options);
            target = new BitmapTarget(urlDrawable);
            targets.add(target);
            load.into(target);
        }

        return urlDrawable;
    }

    private static boolean isGif(String path) {
        int index = path.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase().equals(path.substring(index + 1).toUpperCase());
    }

    private class GifTarget extends SimpleTarget<GifDrawable> {
        private final UrlDrawable urlDrawable;
        public GifTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }

        @Override
        public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
            int w = MeasureUtil.getScreenSize(mContext).x;
            int hh=resource.getIntrinsicHeight();
            int ww=resource.getIntrinsicWidth() ;
            int high = hh * (w - 50)/ww;

            Rect rect = new Rect(0,0,setWigth(),setHeigh());
            resource.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(resource);
            gifDrawables.add(resource);
            resource.setCallback(mTextView);
            resource.start();
            resource.setLoopCount(GifDrawable.LOOP_FOREVER);
            mTextView.setText(mTextView.getText());
//            mTextView.invalidate();
        }
    }

    private class BitmapTarget extends SimpleTarget<Bitmap> {
        private final UrlDrawable urlDrawable;

        public BitmapTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }
        @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
            Drawable drawable = new BitmapDrawable(mContext.getResources(), resource);
            int w = MeasureUtil.getScreenSize(mContext).x;
            int hh = drawable.getIntrinsicHeight();
            int ww = drawable.getIntrinsicWidth() ;
            int high = hh*(w-40)/ww;
            Rect rect = new Rect(0,0,setWigth(),setHeigh());
            drawable.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(drawable);
            mTextView.setText(mTextView.getText());
//            mTextView.invalidate();
        }
    }

    private int setWigth() {
        return (int) mContext.getResources().getDimension(R.dimen.dp_330);
    }

    private int setHeigh() {
        return (int) mContext.getResources().getDimension(R.dimen.dp_200);
    }
}
*/
