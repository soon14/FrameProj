package com.xjgj.mall.ui.improveorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.frameproj.library.util.CommonUtils;
import com.android.frameproj.library.util.TimeUtils;
import com.android.frameproj.library.util.ToastUtil;
import com.android.frameproj.library.widget.wheel.Info;
import com.android.frameproj.library.widget.wheel.time.TimePickerPopWindow;
import com.kyleduo.switchbutton.SwitchButton;
import com.xjgj.mall.R;
import com.xjgj.mall.bean.OrderCarInfo;
import com.xjgj.mall.bean.TerminiEntity;
import com.xjgj.mall.ui.BaseActivity;
import com.xjgj.mall.ui.comfirmorder.ComfirmOrderActivity;
import com.xjgj.mall.ui.extraservice.ExtraServiceActivity;
import com.xjgj.mall.ui.sentence.WriteSentenceActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xjgj.mall.Constants.REQUEST_CONFIRM_ORDER_CODE;
import static com.xjgj.mall.Constants.REQUEST_EXTRA_SERVICE_CODE;
import static com.xjgj.mall.Constants.REQUEST_WRITE_SENTENCE_CODE;
import static com.xjgj.mall.Constants.RESULT_CONFIRM_ORDER_CODE;
import static com.xjgj.mall.Constants.RESULT_EXTRA_SERVICE_CODE;
import static com.xjgj.mall.Constants.RESULT_IMPROVE_ORDER_CODE;
import static com.xjgj.mall.Constants.RESULT_IMPROVE_ORDER_CODE_FROM_DETAIL;
import static com.xjgj.mall.Constants.RESULT_WRITE_SENTENCE_CODE;
import static com.xjgj.mall.R.id.editTiJi;
import static com.xjgj.mall.R.id.textExtraService;
import static com.xjgj.mall.R.id.textShowTime;

/**
 * Created by we-win on 2017/7/27.
 * 完善订单信息
 */

public class ImproveOrderActivity extends BaseActivity {

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
    @BindView(R.id.linearClude)
    LinearLayout mLinearClude;
    @BindView(R.id.textMoney)
    TextView mTextMoney;
    @BindView(R.id.textNext)
    TextView mTextNext;
    @BindView(R.id.linearBottom)
    LinearLayout mLinearBottom;
    @BindView(textShowTime)
    TextView mTextShowTime;
    @BindView(R.id.linearUpdateTime)
    LinearLayout mLinearUpdateTime;
    @BindView(editTiJi)
    EditText mEditTiJi;
    @BindView(R.id.editWeight)
    EditText mEditWeight;
    @BindView(textExtraService)
    TextView mTextExtraService;
    @BindView(R.id.textShaoHua)
    TextView mTextShaoHua;
    @BindView(R.id.editCount)
    EditText mEditCount;
    @BindView(R.id.sb_md)
    SwitchButton mSbMd;

    private String sentenceToDriver = "";
    private String xieZhuang = "";
    private String huiDan = "";
    private String huiKuan = "";
    private String huikuanMoney = "";
    private String mCartype;
    private String mCartypeName;
    private List<TerminiEntity> tempTerminiEntity;
    private int mComeFrom;

    @Override
    public int initContentView() {
        return R.layout.activity_improve_order;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        //初始化时间选择器数据
        mComeFrom = getIntent().getIntExtra("comefrom", -1);
        initUpdateTime();
        mImageBack.setImageResource(R.drawable.btn_back);
        mImageBack.setVisibility(View.VISIBLE);
        setImgBack(mImageBack);
        mTextTitle.setText("完善订单信息");
        mCartype = getIntent().getStringExtra("cartype");
        mCartypeName = getIntent().getStringExtra("cartypeName");
        tempTerminiEntity = (List<TerminiEntity>) getIntent().getExtras().getSerializable("tempTerminiEntity");

        mEditTiJi.requestFocus();
    }

    // ==========================   时间选择控件  =================================
    private static final String[] HOUR =
            new String[]{"00时", "01时", "02时", "03时", "04时", "05时", "06时", "07时",
                    "08时", "09时", "10时", "11时", "12时", "13时", "14时", "15时",
                    "16时", "17时", "18时", "19时", "20时", "21时", "22时", "23时"};

    private static final String[] MINUT = new String[]{"00分", "10分", "20分", "30分", "40分", "50分"};
    //日期
    private List<Info> day_list = new ArrayList<Info>();
    //时
    private HashMap<String, List<Info>> hour_map = new HashMap<String, List<Info>>();
    //分
    private HashMap<String, List<Info>> minut_map = new HashMap<String, List<Info>>();
    private TimePickerPopWindow cityPickerPopWindow = null;
    private int dayPosition;

    /**
     * 修改用车时间
     */
    @OnClick(R.id.linearUpdateTime)
    public void updateTimeMethod(View view) {
        CommonUtils.hideSoftInput(this);
        if (day_list.size() > 0 && hour_map.size() > 0 && minut_map.size() > 0) {
            cityPickerPopWindow = new TimePickerPopWindow(this, "", day_list, hour_map, minut_map, 0);
            cityPickerPopWindow.showAtLocation(this.findViewById(R.id.textNext), Gravity.BOTTOM, 0, 0);
            cityPickerPopWindow.setOnInterface(new TimePickerPopWindow.OnCitySelectorListener() {
                @Override
                public void onCitySelectorListener(String dayPo, String showContent) {
                    if (TextUtils.isEmpty(dayPo) && TextUtils.isEmpty(showContent)) {

                    } else {
                        dayPosition = Integer.parseInt(dayPo);
                        if (showContent.contains("现在")) {
                            mTextShowTime.setText("现在");
                        } else {
                            String con = showContent.replace("时", "").replace("分", "");
                            mTextShowTime.setText(con);
                        }
                    }

                }
            });
        } else {
            ToastUtil.showToast("获取数据失败");
        }
    }

    /**
     * 初始化时间选择器数据
     */
    private void initUpdateTime() {
        day_list.clear();
        hour_map.clear();
        minut_map.clear();
        String currentTime = TimeUtils.getCurrentTimeMillis();
        for (int i = 0; i < 5; i++) {
            String otherTime = (Long.valueOf(currentTime) + i * 60 * 60 * 24) + "";
            String dayTime = "";
            if (i == 0) {
                dayTime = "今天";
            } else if (i == 1) {
                dayTime = "明天";
            } else {
                dayTime = TimeUtils.getDayTime(otherTime);
            }
            List<Info> dayList = new ArrayList<Info>();

            if (i == 0) {
                String hourTime = TimeUtils.getHourTime(otherTime);
                // position是当前的时间
                int position = 0;
                for (int j = 0; j < HOUR.length; j++) {
                    if (HOUR[j].equals(hourTime)) {
                        position = j;
                        break;
                    }

                }
                for (int l = position; l < HOUR.length; l++) {

                    List<Info> minutList = new ArrayList<Info>();
                    // 上面一个if没用，暂时不需要立即用车
                    if (position == l) {
                        Info info = new Info();
                        info.setId(dayTime + HOUR[l]);
                        info.setCity_name("现在");
                        dayList.add(info);
                        Info info3 = new Info();
                        info3.setId("");
                        info3.setCity_name("");
                        minutList.add(info3);
                        minut_map.put(dayTime + HOUR[l], minutList);
                    } else {
                        Info info = new Info();
                        info.setId(dayTime + HOUR[l]);
                        info.setCity_name(HOUR[l]);
                        dayList.add(info);
                        for (int k = 0; k < MINUT.length; k++) {
                            Info info3 = new Info();
                            info3.setId(MINUT[k]);
                            info3.setCity_name(MINUT[k]);
                            minutList.add(info3);
                        }
                        minut_map.put(dayTime + HOUR[l], minutList);
                    }
                }

            } else {
                for (int j = 0; j < HOUR.length; j++) {
                    Info info = new Info();
                    info.setId(HOUR[j]);
                    info.setCity_name(HOUR[j]);
                    dayList.add(info);
                    List<Info> minutList = new ArrayList<Info>();
                    for (int k = 0; k < MINUT.length; k++) {
                        Info info3 = new Info();
                        info3.setId(MINUT[k]);
                        info3.setCity_name(MINUT[k]);
                        minutList.add(info3);
                    }
                    minut_map.put(HOUR[j], minutList);
                }
            }

            Info info = new Info();
            info.setId(dayTime);
            info.setCity_name(dayTime);
            day_list.add(info);
            hour_map.put(dayTime, dayList);
        }
    }

    /**
     * 额外服务
     *
     * @param view
     */
    public void extraService(View view) {
        Intent intent = new Intent(this, ExtraServiceActivity.class);
        intent.putExtra("zhuangXie", xieZhuang);
        intent.putExtra("huiDan", huiDan);
        intent.putExtra("huiKuan", huiKuan);
        intent.putExtra("huiKuanMoney", huikuanMoney);

        startActivityForResult(intent, REQUEST_EXTRA_SERVICE_CODE);
    }

    /**
     * 给司机捎话
     *
     * @param view
     */
    public void shaoHua(View view) {
        Intent intent = new Intent(this, WriteSentenceActivity.class);
        intent.putExtra("sentence", sentenceToDriver);
        startActivityForResult(intent, REQUEST_WRITE_SENTENCE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_WRITE_SENTENCE_CODE && resultCode == RESULT_WRITE_SENTENCE_CODE) {
            sentenceToDriver = data.getStringExtra("sentence");
            mTextShaoHua.setText(sentenceToDriver);
        } else if (requestCode == REQUEST_EXTRA_SERVICE_CODE && resultCode == RESULT_EXTRA_SERVICE_CODE) {
            if (data != null) {
                xieZhuang = data.getStringExtra("zhuangXie");
                huiDan = data.getStringExtra("huiDan");
                huiKuan = data.getStringExtra("huiKuan");
                huikuanMoney = data.getStringExtra("huiKuanMoney");
                String showStr = "";
                if (TextUtils.isEmpty(xieZhuang)) {

                } else {
                    showStr = showStr + xieZhuang + "、";
                }
                if (TextUtils.isEmpty(huiDan)) {

                } else {
                    showStr = showStr + huiDan + "、";
                }

                if (TextUtils.isEmpty(huiKuan)) {

                } else {
                    showStr = showStr + huiKuan + huikuanMoney + "、";
                }
                if (!TextUtils.isEmpty(showStr)) {
                    mTextExtraService.setText(showStr.substring(0, showStr.length() - 1));
                } else {
                    mTextExtraService.setText("");
                }
            }
        } else if (requestCode == REQUEST_CONFIRM_ORDER_CODE && resultCode == RESULT_CONFIRM_ORDER_CODE) {
            if (mComeFrom == 1) { // 1代表从订单详情进入，2代表从找车进入
                setResult(RESULT_IMPROVE_ORDER_CODE_FROM_DETAIL);
            } else if (mComeFrom == 2) {
                setResult(RESULT_IMPROVE_ORDER_CODE);
            }
            finish();
        }
    }

    /**
     * 下一步
     */
    @OnClick(R.id.textNext)
    public void mTextNext() {
        String serviceTime = mTextShowTime.getText().toString().trim();
        if (!serviceTime.equals("现在")) {
            // 格式化时间
            if (serviceTime.contains("今天")) {
                String currentTime = TimeUtils.getCurrentTimeMillis();
                String otherTime = (Long.valueOf(currentTime) + 0 * 60 * 60 * 24) + "";
                String yearAndDay = TimeUtils.getYearAndDay(otherTime);
                serviceTime = serviceTime.replace("今天", yearAndDay);
            } else if (serviceTime.contains("明天")) {
                String currentTime = TimeUtils.getCurrentTimeMillis();
                String otherTime = (Long.valueOf(currentTime) + 1 * 60 * 60 * 24) + "";
                String yearAndDay = TimeUtils.getYearAndDay(otherTime);
                serviceTime = serviceTime.replace("明天", yearAndDay);
            } else {
                serviceTime.replace("月", "-").replace("日", "-");
                String currentTime = TimeUtils.getCurrentTimeMillis();
                String otherTime = (Long.valueOf(currentTime) + 0 * 60 * 60 * 24) + "";
                serviceTime = TimeUtils.getYear(otherTime).concat("-").concat(serviceTime);
            }
            serviceTime = serviceTime.concat(":00");
        } else {
            serviceTime = "";
        }

        String volume = mEditTiJi.getText().toString().trim();
        String weight = mEditWeight.getText().toString().trim();
        String count = mEditCount.getText().toString().trim();
        String serviceType = mTextExtraService.getText().toString().trim();

        OrderCarInfo orderCarInfo = new OrderCarInfo(serviceTime, volume, weight, serviceType,
                mCartype, sentenceToDriver, count, mSbMd.isChecked() ? 1 : 0);

        Intent intent = new Intent(this, ComfirmOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("cartypeName", mCartypeName);
        bundle.putSerializable("orderCarInfo", orderCarInfo);
        bundle.putSerializable("tempTerminiEntity", (Serializable) tempTerminiEntity);
        intent.putExtras(bundle);
        intent.putExtra("orderType", 0);//0:代表场外配送,1:代表场内短驳
        startActivityForResult(intent, REQUEST_CONFIRM_ORDER_CODE);
    }


}
