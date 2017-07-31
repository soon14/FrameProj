package com.xjgj.mall.ui.fragment3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.frameproj.library.util.imageloader.ImageLoaderUtil;
import com.android.frameproj.library.util.log.Logger;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xjgj.mall.R;
import com.xjgj.mall.bean.HomepageEntity;
import com.xjgj.mall.components.storage.UserStorage;
import com.xjgj.mall.ui.BaseFragment;
import com.xjgj.mall.ui.businesslicence.BusinessLicenceActivity;
import com.xjgj.mall.ui.certification.CertificationActivity;
import com.xjgj.mall.ui.main.MainComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xjgj.mall.Constants.REQUEST_BUSINESS_LICENCE_CODE;
import static com.xjgj.mall.Constants.REQUEST_CERTIFICATION_CODE;

/**
 * Created by WE-WIN-027 on 2016/9/27.
 *
 * @des ${TODO}
 */
public class Fragment3 extends BaseFragment implements Fragment3Contract.View {

    @BindView(R.id.roundImageView)
    ImageView mRoundImageView;

    @Inject
    Fragment3Presenter mPresenter;
    @Inject
    UserStorage mUserStorage;
    @BindView(R.id.textName)
    TextView mTextName;
    @BindView(R.id.imageSex)
    ImageView mImageSex;
    @BindView(R.id.textPhone)
    TextView mTextPhone;
    @BindView(R.id.textShopName)
    TextView mTextShopName;
    @BindView(R.id.textShopAddress)
    TextView mTextShopAddress;
    @BindView(R.id.relativePersonal)
    RelativeLayout mRelativePersonal;
    @BindView(R.id.text_y_y_z_z)
    TextView mTextYYZZ;
    @BindView(R.id.text_yyzz_state)
    TextView mTextYyzzState;
    @BindView(R.id.relativeGoYyzz)
    LinearLayout mRelativeGoYyzz;
    @BindView(R.id.text_s_m_r_z)
    TextView mTextSMRZ;
    @BindView(R.id.text_smrz_state)
    TextView mTextSmrzState;
    @BindView(R.id.relativeGoSmrr)
    LinearLayout mRelativeGoSmrr;
    @BindView(R.id.btn_exit)
    Button mBtnExit;
    /**
     * 选择图片返回的图片信息
     */
    private List<LocalMedia> selectList = new ArrayList<>();

    public static BaseFragment newInstance() {
        Fragment3 fragment3 = new Fragment3();
        return fragment3;
    }

    @Override
    public void initInjector() {
        getComponent(MainComponent.class).inject(this);
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_3;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {
        showContent(true);
        mPresenter.attachView(this);
        mPresenter.onLoadHomepageInfo();
    }

    @Override
    public void initData() {

    }

    /**
     * 退出登录
     */
    @OnClick(R.id.btn_exit)
    public void exit_sys() {
        new MaterialDialog.Builder(getActivity())
                .title("退出登录")
                .content("是否确认退出登录？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mUserStorage.logout();
                        getActivity().finish();
                    }
                })
                .show();
    }

    /**
     * 选择头像
     */
    @OnClick(R.id.roundImageView)
    public void mRoundImageView() {
        PictureSelector.create(Fragment3.this)
                .openGallery(PictureMimeType.ofImage()) //图片
                .theme(R.style.picture_default_style) // 主题样式
                .maxSelectNum(1) // 最大图片选择数量
                .minSelectNum(1) // 最小选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .isGif(true)// 是否显示gif图片 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        LocalMedia localMedia = selectList.get(0);
                        ImageLoaderUtil.getInstance().loadImage(localMedia.getPath(), mRoundImageView);

                        Logger.i("localMedia.getPath() = " + localMedia.getPath());
                        Logger.i("localMedia.getCompressPath() = " + localMedia.getCompressPath());
                    }
                    break;
            }
        }
    }

    /**
     * 营业执照
     */
    @OnClick(R.id.relativeGoYyzz)
    public void mRelativeGoYyzz() {
        Intent intent = new Intent(getActivity(), BusinessLicenceActivity.class);
        startActivityForResult(intent,REQUEST_BUSINESS_LICENCE_CODE);
    }

    /**
     * 实名认证
     */
    @OnClick(R.id.relativeGoSmrr)
    public void mRelativeGoSmrr(){
        Intent intent = new Intent(getActivity(), CertificationActivity.class);
        startActivityForResult(intent,REQUEST_CERTIFICATION_CODE);
    }


    @Override
    public void onLoadHomepageInfoCompleted(HomepageEntity homepageEntity) {
        ImageLoaderUtil.getInstance().loadImage(homepageEntity.getAvatarUrl(), mRoundImageView);
        mTextName.setText(homepageEntity.getRealName());
        mTextPhone.setText(homepageEntity.getMobile());
        if (homepageEntity.getSex() == 1) {
            mImageSex.setImageResource(R.drawable.icon_boy);
        } else {
            mImageSex.setImageResource(R.drawable.icon_girl);
        }
        showState(homepageEntity.getFlgAuthBusiness(), mTextYyzzState);
        showState(homepageEntity.getFlgAuthRealName(), mTextSmrzState);
    }

    @Override
    public void onError(Throwable throwable) {
        loadError(throwable);
    }

    private void showState(int userType, TextView textView) {
        if (userType == 0) {
            textView.setText("未认证");
            textView.setTextColor(getResources().getColor(R.color.red));
            textView.setBackgroundResource(R.drawable.rectangle_card_unchecked_corner_bg);
        } else if (userType == 1) {
            textView.setText("待审核");
            textView.setTextColor(getResources().getColor(R.color.red));
            textView.setBackgroundResource(R.drawable.rectangle_card_unchecked_corner_bg);
        } else if (userType == 2) {
            textView.setText("已认证");
            textView.setBackgroundResource(R.drawable.rectangle_card_checked_corner_bg);
            textView.setTextColor(getResources().getColor(R.color.zfb331f));
        } else if (userType == 3) {
            textView.setText("未通过");
            textView.setTextColor(getResources().getColor(R.color.red));
            textView.setBackgroundResource(R.drawable.rectangle_card_unchecked_corner_bg);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
