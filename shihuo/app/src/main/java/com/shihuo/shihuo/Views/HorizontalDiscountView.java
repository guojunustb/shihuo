
package com.shihuo.shihuo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shihuo.shihuo.Activities.HomeDiscountListActivity;
import com.shihuo.shihuo.R;
import com.shihuo.shihuo.application.Contants;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 折扣优惠区二view
 */
public class HorizontalDiscountView extends LinearLayout {

    @BindView(R.id.layout_container)
    LinearLayout mLayoutContainer;

    private Context context;

    private List<GoodsTypeModel> mDatas;

    public HorizontalDiscountView(Context context) {
        super(context);
        initViews(context);
    }

    public HorizontalDiscountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public HorizontalDiscountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;
        mDatas = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_horizontal_tag, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    /**
     * 绑定数据
     *
     * @param list
     */
    public void setData(List<GoodsTypeModel> list) {
        if (list.isEmpty())
            return;
        mDatas.addAll(list);
        mLayoutContainer.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            final GoodsTypeModel model = list.get(i);
            View viewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.view_horscroll_discount_item, null);
            SimpleDraweeView imageView = (SimpleDraweeView)viewItem.findViewById(R.id.image);
            LinearLayout.LayoutParams params = new LayoutParams(AppUtils.dip2px(context, 120),
                    AppUtils.dip2px(context, 60));
            params.setMargins(AppUtils.dip2px(context, 5), 0, 0, 0);
            imageView.setLayoutParams(params);
            if (model != null) {
                imageView.setImageURI(AppUtils.parse(Contants.IMAGE_URL + model.discountPicUrl));
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeDiscountListActivity.start(context, model);
//                        AppUtils.showToast(getContext(), model.discountName);
                    }
                });

            }
            mLayoutContainer.addView(viewItem);
        }
    }

}
