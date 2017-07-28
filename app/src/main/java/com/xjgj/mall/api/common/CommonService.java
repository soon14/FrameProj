package com.xjgj.mall.api.common;


import com.xjgj.mall.bean.AccountVersionEntity;
import com.xjgj.mall.bean.CarTypeEntity;
import com.xjgj.mall.bean.HttpResult;
import com.xjgj.mall.bean.LoginEntity;
import com.xjgj.mall.bean.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by WE-WIN-027 on 2016/9/27.
 *
 * @des ${写接口}
 */
public interface CommonService {

    // 版本控制
    @POST("version/control")
    Observable<HttpResult<AccountVersionEntity>> accountVersion();

    //登录
    @FormUrlEncoded
    @POST("mall/login")
    Observable<HttpResult<LoginEntity>> mallLogin(@Header("timestamp") long timestamp, @Header("sign") String sign,
                                                  @FieldMap Map<String, Object> params);

    // 提供从地址到经纬度坐标或者从经纬度坐标到地址的转换服务
    @GET("http://api.map.baidu.com/geocoder/v2/?")
    Observable<ResponseBody> geocoderApi(@QueryMap Map<String, Object> params);

    //用车类型
    @POST("common/car/type")
    Observable<HttpResult<List<CarTypeEntity>>> carType(@Header("timestamp") long timestamp, @Header("sign") String sign);

    //商户-下单
    @FormUrlEncoded
    @POST("mall/order/submit ")
    Observable<HttpResult<String>> orderSubmit(@Header("timestamp") long timestamp, @Header("sign") String sign,
                                               @FieldMap Map<String, Object> params, @Header("token") String token);

    //商户-查询个人信息
    @POST("mall/information")
    Observable<HttpResult<User>> mallInformation(@Header("timestamp") long timestamp, @Header("sign") String sign,
                                                 @Header("token") String token);

}