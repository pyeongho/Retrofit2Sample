package com.example.phkim.mycleanmvp.data.source;

import android.support.annotation.NonNull;

import com.example.phkim.mycleanmvp.ApiCallback;
import com.example.phkim.mycleanmvp.data.source.daum.SearchApi;
import com.example.phkim.mycleanmvp.data.source.daum.search.ImageItem;
import com.example.phkim.mycleanmvp.data.source.daum.search.SearchChannel;
import com.example.phkim.mycleanmvp.main.domain.model.SearchData;

import java.util.ArrayList;
import java.util.List;



import io.reactivex.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableFromArray;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


import static com.google.common.base.Preconditions.checkNotNull;
import static io.reactivex.Observable.empty;
import static io.reactivex.Observable.just;

/**
 * Created by pyeonghokim on 2017. 5. 7..
 */

public class SearchRepositoryDaum implements SearchDataSource {
    // DI Object
    private SearchApi mSearchApi;


    private int pageCount = 1;
    private static SearchRepositoryDaum INSTANCE;
    public static SearchRepositoryDaum getInstance(@NonNull SearchApi searchApi) {
        if (INSTANCE == null) {
            INSTANCE = new SearchRepositoryDaum(searchApi);
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.

    private SearchRepositoryDaum(@NonNull SearchApi searchApi){
        mSearchApi = checkNotNull(searchApi);
    }


    // remove warning  Observable.fromArray -> fromArray(jsu add @safeVarArgs
    @Override
    public void getSearch(@NonNull String query,final @NonNull LoadSearchCallback callback) {
        mSearchApi.searchText(query, pageCount)
                .filter(channel -> channel != null && channel.getChannel() != null)
                .map(SearchChannel::getChannel)
                .filter(result -> result != null && result.getResult() > 0)
                .flatMap(imageResult -> fromArray(imageResult.getItem()))
                .flatMap(imageItems -> {
                     List<SearchData>  ls = new ArrayList<>();
                    for(ImageItem item : imageItems){
                        SearchData sd = new SearchData(item.getTitle(),item.getImage(),item.getLink());
                        ls.add(sd);
                    }
                    return fromArray(ls);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<SearchData>>() {
                    @Override
                    public void onNext(List<SearchData> value) {
                        callback.onSearchLoaded(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @SafeVarargs
    private static  <T> Observable<T> fromArray(final T... items) {
        ObjectHelper.requireNonNull(items, "items is null");
        if (items.length == 0) {
            return empty();
        } else
        if (items.length == 1) {
            return just(items[0]);
        }
        return RxJavaPlugins.onAssembly(new ObservableFromArray<>(items));
    }

    @Override
    public void getSearch(@NonNull String query, @NonNull ApiCallback<List<SearchData>> callback) {
        mSearchApi.searchText(query, pageCount)
                .subscribeOn(Schedulers.io())
                .filter(channel -> channel != null && channel.getChannel() != null)
                .map(SearchChannel::getChannel)
                .filter(result -> result != null && result.getResult() > 0)
                .flatMap(imageResult -> fromArray(imageResult.getItem()))
                .flatMap(imageItems -> {
                    List<SearchData>  ls = new ArrayList<>();
                    for(ImageItem item : imageItems){
                        SearchData sd = new SearchData(item.getTitle(),item.getImage(),item.getLink());
                        ls.add(sd);
                    }
                    return fromArray(ls);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(callback);
    }

//    private void initSubscription(@NonNull PublishSubject<String> searchSubject) {
//        if(searchSubscription == null){
//            searchSubscription = searchSubject
//                    .onBackpressureBuffer()
//                    .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
//                    .observeOn(Schedulers.io())
//                    .subscribe(text -> {
//                       // loadSearchResult(text);
//
//                    }, Throwable::printStackTrace);
//        }
//
//    }


}
