package com.lh.frameproj.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.frameproj.library.util.ToastUtil;
import com.lh.frameproj.R;
import com.lh.frameproj.bean.TerminiEntity;
import com.lh.frameproj.ui.BaseActivity;
import com.lh.frameproj.ui.location.ChooseLocationActivity;

import butterknife.BindView;

import static com.lh.frameproj.Constants.REQUEST_CHOOSE_LOCATION_CODE;
import static com.lh.frameproj.Constants.RESULT_CHOOSE_LOCATION_CODE;
import static com.lh.frameproj.Constants.RESULT_DELIVERY_INFO_CODE;
import static com.lh.frameproj.R.id.textAddress;
import static com.lh.frameproj.R.id.textContactPhone;
import static com.lh.frameproj.R.id.textDetailsAddress;
import static com.lh.frameproj.R.id.textPersonalName;

/**
 * Created by we-win on 2017/7/27.
 * 发货位置信息
 */

public class DeliveryInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.image_back)
    ImageView mImageBack;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.image_handle)
    ImageView mImageHandle;
    @BindView(R.id.text_handle)
    TextView mTextHandle;
    @BindView(R.id.relative_layout)
    RelativeLayout mRelativeLayout;
    @BindView(textAddress)
    EditText mTextAddress;
    @BindView(R.id.myListView)
    ListView mMyListView;
    @BindView(textDetailsAddress)
    EditText mTextDetailsAddress;
    @BindView(textPersonalName)
    EditText mTextPersonalName;
    @BindView(textContactPhone)
    EditText mTextContactPhone;
    @BindView(R.id.textOk)
    TextView mTextOk;

    private TerminiEntity mTerminiEntity;
    private int mPosition;

    @Override
    public int initContentView() {
        return R.layout.activity_delivery_info;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        getData();
        mImageBack.setImageResource(R.drawable.btn_back);
        mImageBack.setVisibility(View.VISIBLE);
        mImageBack.setOnClickListener(this);
        mTextTitle.setText(getString(R.string.ship_info));
        mTextHandle.setText(getString(R.string.switch_earth_address));
        mTextHandle.setTextSize(14);
        mTextHandle.setTextColor(getResources().getColor(R.color.z5b5b5b));
        mTextHandle.setClickable(true);
        mTextHandle.setOnClickListener(this);
        mTextHandle.setVisibility(View.VISIBLE);
    }

    /**
     * 页面传值
     */
    private void getData() {
        mTerminiEntity = (TerminiEntity) getIntent().getExtras().getSerializable("termini_info");
        mPosition = getIntent().getExtras().getInt("position");
        if (mTerminiEntity != null) {
            mTextAddress.setText(mTerminiEntity.getAddressName());
            mTextDetailsAddress.setText(mTerminiEntity.getAddressDescribeName());
            mTextPersonalName.setText(mTerminiEntity.getReceiverName());
            mTextContactPhone.setText(mTerminiEntity.getReceiverPhone());
            setLen(mTextAddress);
            setLen(mTextDetailsAddress);
            setLen(mTextPersonalName);
            setLen(mTextContactPhone);
            if (mPosition == 0) {
                mTextAddress.setHint(getResources().getString(R.string.input_begin_d));
                mTextPersonalName.setHint(getResources().getString(R.string.personal_name));
            } else {
                mTextAddress.setHint(getResources().getString(R.string.input_target_d));
                mTextPersonalName.setHint(getResources().getString(R.string.personal_shou_name));
            }
        }

    }

    private void setLen(EditText edit) {
        int len = edit.getText().toString().trim().length();
        if (len > 0) {
            edit.setSelection(len);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                if (mTerminiEntity != null) {
                    // 当前页面有内容修改，需要弹出提示
                    if (!mTerminiEntity.getAddressName().equals(mTextAddress.getText().toString().trim())
                            || !mTerminiEntity.getAddressDescribeName().equals(mTextDetailsAddress.getText().toString().trim())
                            || !mTerminiEntity.getReceiverName().equals(mTextPersonalName.getText().toString().trim())
                            || !mTerminiEntity.getReceiverPhone().equals(mTextContactPhone.getText().toString().trim()) ) {
                        isFinish();
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
                break;

            case R.id.text_handle:
                Intent localIntent = new Intent(DeliveryInfoActivity.this, ChooseLocationActivity.class);
                //如果之前有定位需要先定位在之前的位置上,暂时不做吧
                startActivityForResult(localIntent, REQUEST_CHOOSE_LOCATION_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_LOCATION_CODE && resultCode == RESULT_CHOOSE_LOCATION_CODE) {
            String name = data.getStringExtra("name");
            String addr = data.getStringExtra("addr");
            mTextAddress.setText(name);
            mTextDetailsAddress.setText(addr);
        }
    }

    private void isFinish() {

        new MaterialDialog.Builder(DeliveryInfoActivity.this)
                .title("提示")
                .content("是否放弃已输入的信息？")
                .positiveText("放弃")
                .negativeText("保留")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();

    }

    /**
     * 确认
     *
     * @param view
     */
    public void ok(View view) {

        String name = mTextAddress.getText().toString().trim();
        String detailName = mTextDetailsAddress.getText().toString().trim();
        String peopleName = mTextPersonalName.getText().toString().trim();
        String phone = mTextContactPhone.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            if (mPosition == 0){
               ToastUtil.showToast("请填写始发站地址");
            }else{
                ToastUtil.showToast("请填写目的地地址");
            }
        }else{

            if (TextUtils.isEmpty(peopleName)){
                if (mPosition == 0){
                    ToastUtil.showToast("请填写发货人姓名");
                }else{
                    ToastUtil.showToast("请填写收货人姓名");
                }

            }else{
                if (TextUtils.isEmpty(phone)){
                    ToastUtil.showToast("请填写联系电话");
                }else{
                    if (phone.length() == 11){
                        mTerminiEntity.setAddressName(name);
                        mTerminiEntity.setAddressDescribeName(detailName);
                        mTerminiEntity.setReceiverName(peopleName);
                        mTerminiEntity.setReceiverPhone(phone);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("terminiEntity",mTerminiEntity);
                        bundle.putInt("position",mPosition);
                        intent.putExtras(bundle);
                        setResult(RESULT_DELIVERY_INFO_CODE, intent);
                        finish();
                    }else{
                        ToastUtil.showToast("请填写11位长度的联系电话");
                    }

                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!mTerminiEntity.getAddressName().equals(mTextAddress.getText().toString().trim())
                || !mTerminiEntity.getAddressDescribeName().equals(mTextDetailsAddress.getText().toString().trim())
                || !mTerminiEntity.getReceiverName().equals(mTextPersonalName.getText().toString().trim())
                || !mTerminiEntity.getReceiverPhone().equals(mTextContactPhone.getText().toString().trim()) ) {
            isFinish();
        } else {
            finish();
        }
    }
}