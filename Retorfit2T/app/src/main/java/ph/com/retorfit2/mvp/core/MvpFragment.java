package ph.com.retorfit2.mvp.core;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


public abstract class MvpFragment<P extends BasePresenter> extends Fragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
