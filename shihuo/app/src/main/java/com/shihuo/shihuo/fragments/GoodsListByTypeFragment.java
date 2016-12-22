package com.shihuo.shihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.GoodsDetailActivity;
import com.shihuo.shihuo.Activities.ShopHomeActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.CustomAutolabelHeaderView;
import com.shihuo.shihuo.Views.loadmore.LoadMoreContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreGridViewContainer;
import com.shihuo.shihuo.Views.loadmore.LoadMoreHandler;
import com.shihuo.shihuo.application.AppShareUitl;
import com.shihuo.shihuo.models.GoodsModel;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.LoginModel;
import com.shihuo.shihuo.models.StoreDetailModel;
import com.shihuo.shihuo.network.NetWorkHelper;
import com.shihuo.shihuo.network.ShiHuoResponse;
import com.shihuo.shihuo.network.ShihuoStringCallback;
import com.shihuo.shihuo.util.AppUtils;
import com.shihuo.shihuo.util.aliyun.AliyunHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by cm_qiujiaheng on 2016/12/10.
 * 商品列表分类界面
 */

public class GoodsListByTypeFragment extends Fragment {


    public static final String KEY_GOODSTYPE = "goodsType";
    @BindView(R.id.load_more_grid_view)
    GridViewWithHeaderAndFooter loadMoreGridView;
    @BindView(R.id.load_more_grid_view_container)
    LoadMoreGridViewContainer loadMoreGridViewContainer;
    @BindView(R.id.load_more_grid_view_ptr_frame)
    PtrClassicFrameLayout loadMoreGridViewPtrFrame;


    private String goodsType;

    public static GoodsListByTypeFragment newInstance(int goodsType) {
        Bundle args = new Bundle();
        args.putInt(KEY_GOODSTYPE, goodsType);
        GoodsListByTypeFragment fragment = new GoodsListByTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<GoodsModel> goods = new ArrayList<>();
    //当前一级分类下的二级分类列表
    private ArrayList<GoodsTypeModel> secondGoodsTypeModel = new ArrayList<>();
    //推荐的店铺列表
    private ArrayList<StoreDetailModel> stores = new ArrayList<>();
    private BaseAdapter mAdapter;

    private String mCurrentsysSecondTypeId = "0";//当前选中的二级分类
    private String mCurrentsysStore = "";//当前选中推荐店铺
    private String mCurrentOrderType = "1";//当前的排序类型    //orderType:1 价格 2 销量
    private String mCurrentDescribe = "asc";//当前的排序方法 describe: asc 降序 desc 升序

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsType = String.valueOf(getArguments().getInt(KEY_GOODSTYPE, 0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_shophome_goods_list, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initViews() {
        loadMoreGridViewPtrFrame.setLoadingMinTime(1000);
        loadMoreGridViewPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData("1");
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, loadMoreGridView, header);
            }
        });

        loadMoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsModel goodsModel = (GoodsModel) parent.getItemAtPosition(position);
                GoodsDetailActivity.start(getContext(), goodsModel.goodsId);
            }
        });
        loadMoreGridViewContainer.setAutoLoadMore(false);
        loadMoreGridViewContainer.useDefaultFooter();
//        mAdapter = new ShopHomeGoodsListAdapter();
//        loadMoreGridView.setAdapter(mAdapter);

        loadMoreGridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {


            }
        });
        loadMoreGridViewPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreGridViewPtrFrame.autoRefresh();
            }
        }, 100);
    }

    private void loadMoreData() {

    }

    private void refreshData(final String pageNum) {
        getSystypeInfo(pageNum);
    }

    private void getSystypeInfo(final String pageNum) {
        //获取系统一级分类对应的相关信息
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_SYSTYPEINFO))
                .addParams("typeId", goodsType)
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {

                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {

                                JSONObject jsonObject = new JSONObject(response.data);
                                JSONArray jsonArray = jsonObject.getJSONArray("shSysGoodsSecondTypeList");
                                secondGoodsTypeModel.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsTypeModel goodsTypeModel = GoodsTypeModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    secondGoodsTypeModel.add(goodsTypeModel);
                                }
                                //解析推荐的店铺信息
                                jsonArray = jsonObject.getJSONArray("shStores");
                                stores.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    StoreDetailModel storeDetailModel = StoreDetailModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    stores.add(storeDetailModel);
                                }
                                addHeaderView();

                                getGoodsList(pageNum, mCurrentsysSecondTypeId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        AppUtils.showToast(getContext(), "获取二级系统分类出错");
                    }
                });
    }

    private void addHeaderView() {
        if (mAdapter == null) {
            CustomAutolabelHeaderView customAutolabelHeaderView = new CustomAutolabelHeaderView(getContext());
            customAutolabelHeaderView.addAutoLabels(secondGoodsTypeModel, stores);
            loadMoreGridView.addHeaderView(customAutolabelHeaderView);
            mAdapter = new ShopHomeGoodsListAdapter();
            loadMoreGridView.setAdapter(mAdapter);
        }
    }

    private void getGoodsList(String pageNum, String sysSecondTypeId) {
        //本店商品列表
        OkHttpUtils
                .get()
                .url(NetWorkHelper.getApiUrl(NetWorkHelper.API_GET_GOODS_LIST_BY_SYSTYPE))
                .addParams("sysTypeId", goodsType)
                .addParams("storeId", mCurrentsysStore)
                .addParams("pageNum", pageNum)
                .addParams("sysTypeSecondId", sysSecondTypeId)
                .addParams("orderType", mCurrentOrderType)//orderType:1 价格 2 销量
                .addParams("describe", mCurrentDescribe)//describe: asc 降序 desc 升序
                .build()
                .execute(new ShihuoStringCallback() {
                    @Override
                    public void onResponse(ShiHuoResponse response, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        if (response.code == ShiHuoResponse.SUCCESS) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.data).getJSONObject("page");
                                JSONArray jsonArray = jsonObject.getJSONArray("resultList");
                                goods.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GoodsModel goodsTypeModel = GoodsModel.parseJsonStr(jsonArray.getJSONObject(i));
                                    goods.add(goodsTypeModel);
                                }
                                mAdapter.notifyDataSetChanged();
                                loadMoreGridViewContainer.setAutoLoadMore(true);
                                loadMoreGridViewContainer.loadMoreFinish(goods.isEmpty(), true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), response.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadMoreGridViewPtrFrame.refreshComplete();
                        AppUtils.showToast(getContext(), "获取商品列表出错");
                    }
                });
    }


    public class ShopHomeGoodsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goods.size();
        }

        @Override
        public Object getItem(int position) {
            return goods.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_goods_shop_home, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final GoodsModel goodsModel = (GoodsModel) getItem(position);
            viewHolder.imageView.setImageURI(AppUtils.parse(AliyunHelper.getFullPathByName(goodsModel.picUrl)));//0018ae25-cefa-4260-8f4f-926920c3aa1f.jpeg
            viewHolder.goodsTitle.setText(goodsModel.goodsName);
            viewHolder.goodsNewPrice.setText(String.format("￥%1$s", goodsModel.curPrice));
            viewHolder.goodsOriginPrice.setText(String.format("￥%1$s", goodsModel.prePrice));
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.imageView)
            SimpleDraweeView imageView;
            @BindView(R.id.goods_title)
            TextView goodsTitle;
            @BindView(R.id.goods_new_price)
            TextView goodsNewPrice;
            @BindView(R.id.goods_origin_price)
            TextView goodsOriginPrice;
            @BindView(R.id.detail_layout)
            LinearLayout detailLayout;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
