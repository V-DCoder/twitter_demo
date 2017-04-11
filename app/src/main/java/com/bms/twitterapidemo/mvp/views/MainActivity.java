package com.bms.twitterapidemo.mvp.views;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bms.twitterapidemo.R;
import com.bms.twitterapidemo.TwitterDemoApplication;
import com.bms.twitterapidemo.mvp.model.SearchResult;
import com.bms.twitterapidemo.mvp.presentors.MainActivityPresenter;
import com.bms.twitterapidemo.mvp.pv_interfaces.MainActivityPresentorCallback;
import com.bms.twitterapidemo.mvp.views.adapter.TwitListAdapter;
import com.bms.twitterapidemo.utils.Util;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity implements MainActivityPresentorCallback {

    @BindView(R.id.rv_twit_list)
    RecyclerView rvTwitList;
    @BindView(R.id.et_search_box)
    EditText etSearchBoxTitle;
    @BindView(R.id.tv_error_msg)
    TextView tvErrorMsg;
    @Inject
    MainActivityPresenter mPresenter;

    private SearchResult mSearchResult;
    private TwitListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        ((TwitterDemoApplication) getApplication()).getInjectorComponent().inject(this);
        mPresenter.startPresentor(this);

        init();


    }

    private void init() {

        mPresenter.display(mSearchResult);
        rvTwitList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rvTwitList.setLayoutManager(mLayoutManager);
        Util.showProgressBar(this, "Wait a Sec", "Shaking hand with twitter");
        mPresenter.getBearerToken();

    }

    @OnTextChanged(R.id.et_search_box)
    void onTextChange() {
        mPresenter.startRescheduleRunner();
    }


    @Override
    public void onAccesstokenSuccess() {
        Util.dismissProgressBar();
        tvErrorMsg.setVisibility(View.GONE);
        rvTwitList.setVisibility(View.VISIBLE);
        Toast.makeText(this, "You are good to go", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDataSuccess(SearchResult result) {
        mSearchResult = new SearchResult(result.getStatuseList());

        mPresenter.display(mSearchResult);
    }

    @Override
    public void onEmptyResult() {

    }

    @Override
    public void onResultReceived() {
        mAdapter = new TwitListAdapter(mSearchResult.getStatuseList(), this);
        rvTwitList.setAdapter(mAdapter);
    }

    @Override
    public void onResetDisplay() {
        if (mSearchResult != null) {
            mSearchResult.getStatuseList().clear();
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onError(String error) {
        tvErrorMsg.setText(error);
        tvErrorMsg.setVisibility(View.VISIBLE);
        rvTwitList.setVisibility(View.GONE);
    }

    @Override
    public void onWaitComplete() {
        hideKeybord();
        mPresenter.getData(etSearchBoxTitle.getText().toString());
    }

    private void hideKeybord() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        IBinder token = etSearchBoxTitle.getWindowToken();
        if (token != null)
            imm.hideSoftInputFromWindow(token, 0);
    }

    @Override
    public void showProgressDialog() {
        Util.showProgressBar(this, "Please wait", "Loading twits");
    }

    @Override
    public void hideProgressDialog() {
        Util.dismissProgressBar();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stopPresentor();
    }
}
