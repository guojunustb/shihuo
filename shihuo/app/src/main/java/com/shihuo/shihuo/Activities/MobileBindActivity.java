
package com.shihuo.shihuo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;
import com.shihuo.shihuo.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cm_qiujiaheng on 2016/11/4. 绑定手机界面
 */

public class MobileBindActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.edit_mobile_number)
    EditText editMobileNumber;

    @BindView(R.id.edit_verify)
    EditText editVerify;

    @BindView(R.id.imag_left)
    ImageView imagLeft;

    @BindView(R.id.btn_verify)
    TextView btn_verify;

    @BindView(R.id.phone_number_prefix)
    TextView phone_number_prefix;

    private TimeCount mTimer;

    public static void start(Context context) {
        Intent intent = new Intent(context, MobileBindActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.fullScreenColor(this);
        setContentView(R.layout.layout_mobile_bind);
        ButterKnife.bind(this);
        initViews();
        mTimer = new TimeCount(60000, 1000);
    }

    public void initViews() {
        title.setText(R.string.change_mobile);
        imagLeft.setVisibility(View.VISIBLE);
        phone_number_prefix.setText(getResources().getString(R.string.prefix_old_mobile_bind));
    }

    @OnClick({
            R.id.imag_left, R.id.btn_commit, R.id.btn_verify
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_left:
                finish();
                break;
            case R.id.btn_commit:
                break;

            case R.id.btn_verify:
                mTimer.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.cancel();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btn_verify.setText(getResources().getString(R.string.get_verify));
            btn_verify.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btn_verify.setClickable(false);
            btn_verify.setText(millisUntilFinished / 1000 + getResources().getString(R.string.phone_code));
        }
    }
}
