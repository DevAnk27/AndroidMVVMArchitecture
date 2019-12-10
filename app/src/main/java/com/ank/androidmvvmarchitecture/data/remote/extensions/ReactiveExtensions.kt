package com.ank.androidmvvmarchitecture.data.remote.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.ank.androidmvvmarchitecture.data.remote.networking.Scheduler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


/**
 * Extension function to subscribe on the background thread and observe on the main thread for a Completable
 * */
fun Completable.performOnBackOutOnMain(scheduler: Scheduler): Completable {
    return this.subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread  for a Flowable
 * */
fun <T> Flowable<T>.performOnBackOutOnMain(scheduler: Scheduler): Flowable<T> {
    return this.subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a Observable
 * */
fun <T> Observable<T>.performOnBackOutOnMain(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
}

/**
 * Extension function to add a Disposable to a CompositeDisposable
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

/**
 * Extension function to subscribe on the background thread for a Flowable
 * */
fun <T> Flowable<T>.performOnBack(scheduler: Scheduler): Flowable<T> {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Completable
 * */
fun Completable.performOnBack(scheduler: Scheduler): Completable {
    return this.subscribeOn(scheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Observable
 * */
fun <T> Observable<T>.performOnBack(scheduler: Scheduler): Observable<T> {
    return this.subscribeOn(scheduler.io())
}


/*fun ImageView.loadUrl(context: Context?, url: String) {
    if (context != null) {
        Glide
                .with(context)
                .load(url)
                .apply(RequestOptions()
                        .placeholder(R.drawable.img_placeholder)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(this)
    } else {
        this.setImageResource(R.drawable.img_placeholder)
    }
}

fun ImageView.loadUrlWithCallback(context: Context?, url: String, ivPlaceHolder: ImageView) {
    if(context != null) {
        Glide.with(context)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        ivPlaceHolder.setImageResource(R.drawable.img_placeholder)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        ivPlaceHolder.visibility = View.GONE
                        return false
                    }
                })
                .into(this)
    } else {
        this.visibility = View.GONE
        ivPlaceHolder.setImageResource(R.drawable.img_placeholder)
        ivPlaceHolder.visibility = View.VISIBLE
    }
}*/

fun ImageView.loadGIF(context: Context?, id: Int) {
    if (context != null) {
        Glide
                .with(context)
                .load(id)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(this)
    }
}