package com.example.phkim.mycleanmvp.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.phkim.mycleanmvp.ApiCallback;
import com.example.phkim.mycleanmvp.UseCase;
import com.example.phkim.mycleanmvp.UseCaseHandler;
import com.example.phkim.mycleanmvp.main.adapter.ImageAdapter;
import com.example.phkim.mycleanmvp.main.domain.model.SearchData;
import com.example.phkim.mycleanmvp.main.domain.usecase.GetSearch;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pyeonghokim on 2017. 5. 7..
 */

public class SearchPresenter implements SearchContract.Presenter {

    private final SearchContract.View mView;
    private final GetSearch mGetSearch;
    private final UseCaseHandler mUseCaseHandler;

    public SearchPresenter(@NonNull UseCaseHandler useCaseHandler,
                          @NonNull SearchContract.View view, @NonNull GetSearch getSearch) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mView = checkNotNull(view, "view cannot be null!");
        mGetSearch = checkNotNull(getSearch, "getTask cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void inputTextSearch(@NonNull String s) {
        Log.d("search",s);
        GetSearch.RequestValues req = new GetSearch.RequestValues(s);
        mUseCaseHandler.execute(mGetSearch, req,
                new UseCase.UseCaseCallback<GetSearch.ResponseValue>() {
                    @Override
                    public void onSuccess(GetSearch.ResponseValue response) {
                        Log.d("search","onSuccess");
                        mView.showSearch(response.getSearch());
                    }

                    @Override
                    public void onError() {
                        Log.d("search","onError");
                        mView.showLoadingSearchError();
                    }
                });


    }

    @Override
    public void onItemClick(ImageAdapter adapter,int position) {
        if(mView.isActive()){
            SearchData sd = adapter.getItem(position);
            if(sd==null){
                return;
            }
            String link = sd.getOriginalUrl();
            mView.onMoveLink(link);
        }
    }

    @Override
    public void clickedRxCallBack(@NonNull String query) {
        GetSearch.RequestValues req = new GetSearch.RequestValues(query);
        mGetSearch.executeUseCaseRx(req,
                new ApiCallback<List<SearchData>>() {
                    @Override
                    public void onSuccess(List<SearchData> model) {
                        Log.d("search","onSuccess");
                        if(mView.isActive()){
                            mView.showSearch(model);
                        }

                    }

                    @Override
                    public void onFailure(Integer code, String msg) {
                        if(mView.isActive()){
                            mView.showLoadingSearchError();
                        }
                    }

                    @Override
                    public void onFinish() {
                        Log.d("search","onFinish");
                    }
                });
    }
}
