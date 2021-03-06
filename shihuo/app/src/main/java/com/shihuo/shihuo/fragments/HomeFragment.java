
package com.shihuo.shihuo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.shihuo.shihuo.Activities.LoginActivity;
import com.shihuo.shihuo.Activities.MessageCenterActivity;
import com.shihuo.shihuo.Activities.QRCodeActivity;
import com.shihuo.shihuo.Activities.SearchActivity;
import com.shihuo.shihuo.Activities.ShoppingCarListActivity;
import com.shihuo.shihuo.Adapters.HomeAdapter;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.ShoppingCarView;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.BaseGoodsListModel;
import com.shihuo.shihuo.models.BaseGoodsModel;
import com.shihuo.shihuo.models.HomeModel;
import com.shihuo.shihuo.models.SysTypeModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiahengqiu on 2016/10/23. 首页
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener {

    @BindView(R.id.tv_search)
    TextView tv_search;

    @BindView(R.id.view_shoppingCar)
    ShoppingCarView mShoppingCarView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshRecyclerView mSwipeRefresh;

    private Context mContext;

    private HomeAdapter mAdapter;

    private List<HomeModel> mList;

    private int page = 1;

    private boolean isScrollTop = true;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_activity, null);
        ButterKnife.bind(this, view);
        initView();
        requestSysData();
        return view;
    }

    private void initView() {
        mContext = getActivity();
        mList = new ArrayList<>();
        AppUtils.initSwipeRefresh(mContext, mSwipeRefresh);
        mSwipeRefresh.setOnListLoadListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
        mAdapter = new HomeAdapter(getActivity(), mList);
        mSwipeRefresh.setAdapter(mAdapter);
//        mSwipeRefresh.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY > 0){
//                    isScrollTop = false;
//                }
//            }
//        });

        mShoppingCarView.setOnClickListener(new ShoppingCarView.OnViewClickListener() {
            @Override
            public void onShoppingCarListener() {
                if (AppShareUitl.isLogin(getContext())) {
                    ShoppingCarListActivity.start(getActivity());
                } else {
                    LoginActivity.start(getContext());
                }
            }

            @Override
            public void onBackTopListener() {
                mSwipeRefresh.getScrollView().smoothScrollToPosition(0);
//                if(!isScrollTop){
//                    mSwipeRefresh.getScrollView().smoothScrollToPosition(0);
//                    isScrollTop = true;
//                }
            }
        });
    }

    /**
     * 请求首页商圈，商品分类，广告
     */
    private void requestSysData() {
        final GsonRequest<SysTypeModel> request = new GsonRequest<>(
                NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SYS_TYPE), SysTypeModel.class,
                AppUtils.getOAuthMap(getActivity()), new Response.Listener<SysTypeModel>() {
            @Override
            public void onResponse(SysTypeModel response) {
                if (response != null) {
                    //保存商圈和商品系统分类
                    AppShareUitl.saveSysGoodsType(getContext(),
                            response.data.shSysGoodsTypeList);
                    AppShareUitl.saveSysCircleType(getContext(),
                            response.data.shSysStoreCircleList);
                    // 设置商品分类
                    mList.clear();
                    HomeModel model = new HomeModel();
                    model.typeData = response;
                    mList.add(model);
                    mAdapter.bindData(mList);
                    mSwipeRefresh.setRefreshing(false);
                    requestHotGoods(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeRefresh.setRefreshing(false);
            }
        });
        addRequest(request);
    }

    /**
     * 请求热销商品
     */
    private void requestHotGoods(final boolean isLoadMore) {
        if (isLoadMore) {
            page++;
        } else {
            page = 1;
        }
        String url = NetWorkHelper.API_GET_HOT_GOODS + "?pageNum=" + page;
        final GsonRequest<BaseGoodsListModel> request = new GsonRequest<>(
                NetWorkHelper.getApiUrl(url), BaseGoodsListModel.class,
                AppUtils.getOAuthMap(getActivity()), new Response.Listener<BaseGoodsListModel>() {
            @Override
            public void onResponse(BaseGoodsListModel response) {
                if (response != null && response.data != null && response.data.page != null) {
                    // 设置热销商品
                    if (!response.data.page.resultList.isEmpty()) {
                        int temp = response.data.page.resultList.size() / 2;
                        if (response.data.page.resultList.size() % 2 != 0) {
                            temp = temp + 1;
                        }
                        for (int i = 0; i < temp; i++) {
                            HomeModel model = new HomeModel();
                            model.item_type = HomeModel.ITEM_TYPE_GOODS;
                            BaseGoodsModel baseGoodsModel = new BaseGoodsModel();
                            baseGoodsModel.goodsLeftModel = response.data.page.resultList
                                    .get(2 * i);
                            Log.d(TAG, "onResponse: left= " + baseGoodsModel.goodsLeftModel.toString());
                            if (2 * i + 1 < response.data.page.resultList.size()) {
                                baseGoodsModel.goodsRightModel = response.data.page.resultList
                                        .get(2 * i + 1);
                                Log.d(TAG, "onResponse: left= " + baseGoodsModel.goodsRightModel.toString());

                            }
                            model.baseGoodsModel = baseGoodsModel;
                            mList.add(model);
                        }
                    }
                    if (isLoadMore) {
                        mSwipeRefresh.setLoading(false);
                    } else {
                        mSwipeRefresh.setRefreshing(false);
                    }
                }
                mAdapter.bindData(mList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isLoadMore) {
                    mSwipeRefresh.setLoading(false);
                } else {
                    page = 0;
                    mSwipeRefresh.setRefreshing(false);
                }
                mAdapter.bindData(mList);
                mSwipeRefresh.setRefreshing(false);
            }
        });
        addRequest(request);
    }

    @Override
    public void onRefresh() {
        requestSysData();
    }

    @Override
    public void onListLoad() {
        requestHotGoods(true);
    }

    @OnClick({
            R.id.btn_msg, R.id.btn_more, R.id.tv_search
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_msg:
                MessageCenterActivity.startMessageCenterActivity(getContext());
//                NotifyListActivity.start(getContext());
                break;
            case R.id.tv_search:
                SearchActivity.start(getContext());
                break;
            case R.id.btn_more:
                QRCodeActivity.start(getActivity());
                break;
        }
    }

}
