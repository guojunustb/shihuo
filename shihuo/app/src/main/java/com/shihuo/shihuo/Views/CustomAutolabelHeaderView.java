package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelStoreUi;
import com.shihuo.shihuo.Views.autolabel.CustomAutoLabelTypeUi;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.StoreDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiahengqiu on 2016/10/23.
 * 便民服务、微视频的headerview
 */

public class CustomAutolabelHeaderView extends LinearLayout {


    @BindView(R.id.label_view_syssecond)
    CustomAutoLabelTypeUi labelViewSyssecond;
    @BindView(R.id.label_view)
    CustomAutoLabelStoreUi labelView;
    @BindView(R.id.goods_layout)
    LinearLayout goodsLayout;
    @BindView(R.id.store_layout)
    LinearLayout storeLayout;
    @BindView(R.id.view_banner)
    BannerView viewBanner;

    private LabelChangeListener labelChangeListener;

    public interface LabelChangeListener {
        void onTypeLabelChanged(GoodsTypeModel goodsTypeModel);

        void onStoreLabelChanged(StoreDetailModel storeDetailModel);
    }

    public CustomAutolabelHeaderView(Context context, LabelChangeListener labelChangeListener) {
        super(context);
        this.labelChangeListener = labelChangeListener;
        initView();
    }

    public CustomAutolabelHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomAutolabelHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_autolabel_header_view, null);
        ButterKnife.bind(this, view);
        addView(view);
        labelViewSyssecond.setLabelClickListner(new CustomAutoLabelTypeUi.LabelClickListner() {
            @Override
            public void onLabelClick(GoodsTypeModel goodsTypeModel) {
                if (labelChangeListener != null) {
                    labelChangeListener.onTypeLabelChanged(goodsTypeModel);
                }
            }
        });

        labelView.setLabelClickListner(new CustomAutoLabelStoreUi.LabelClickListner() {
            @Override
            public void onLabelClick(StoreDetailModel storeDetailModel) {
                if (labelChangeListener != null) {
                    labelChangeListener.onStoreLabelChanged(storeDetailModel);
                }
            }
        });

    }

    public void addAutoLabels(ArrayList<GoodsTypeModel> goodsTypeModels, ArrayList<StoreDetailModel> storeDetailModels, List<GoodsTypeModel> shAdvertisingList) {
        if (goodsTypeModels.isEmpty()) {
            goodsLayout.setVisibility(GONE);
        } else {
            for (GoodsTypeModel goodsTypeModel : goodsTypeModels) {
                labelViewSyssecond.addLabel(goodsTypeModel);
            }
        }

        if (storeDetailModels.isEmpty()) {
            storeLayout.setVisibility(GONE);
        } else {
            for (StoreDetailModel storeDetailModel : storeDetailModels) {
                labelView.addLabel(storeDetailModel);
            }
        }

        // 设置banner
        viewBanner.setData(shAdvertisingList);
    }


//    public void setListeners(AutoLabelUI.OnLabelsCompletedListener onLabelsCompletedListener,
//                             AutoLabelUI.OnRemoveLabelListener onRemoveLabelListener,
//                             AutoLabelUI.OnLabelsEmptyListener onLabelsEmptyListener,
//                             AutoLabelUI.OnLabelClickListener onLabelClickListener) {
//        labelView.setOnLabelsCompletedListener(onLabelsCompletedListener);
//
//        labelView.setOnRemoveLabelListener(onRemoveLabelListener);
//
//        labelView.setOnLabelsEmptyListener(onLabelsEmptyListener);
//
//        labelView.setOnLabelClickListener(onLabelClickListener);
//
//    }


}
