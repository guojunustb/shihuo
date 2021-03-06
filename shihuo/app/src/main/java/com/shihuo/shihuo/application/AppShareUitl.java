
package com.shihuo.shihuo.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.shihuo.shihuo.models.GoodsTypeModel;
import com.shihuo.shihuo.models.LoginModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * SharePreferences工具类
 */
public class AppShareUitl {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "ShiHuo_SharePreferences";

    private static final String DATA_DEFAULT = "";

    private Context mContext;

    private static SharedPreferences sp;

    private static SharedPreferences.Editor editor;

    private AppShareUitl() {

    }

    public static SharedPreferences getInstance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            editor = sp.edit();
        }
        return sp;
    }

    /**
     * 保存用户信息
     *
     * @param context
     * @param strJson
     */
    public static void saveUserInfo(Context context, String strJson) {
        getInstance(context);
        editor.putString("USER_INFO", strJson);
        if (!TextUtils.isEmpty(strJson)) {
            LoginModel model = LoginModel.parseStrJson(strJson);
            if (model != null) {
                saveToken(context, model.token);
                saveStoreType(context, model.isValid, model.storeId);
            }
        }
        editor.commit();
    }

    public static LoginModel getUserInfo(Context context) {
        getInstance(context);
        LoginModel model = new LoginModel();
        String strJson = sp.getString("USER_INFO", "");
        if (!TextUtils.isEmpty(strJson)) {
            model = LoginModel.parseStrJson(strJson);
        }
        return model;
    }

    public static void saveToken(Context context, String token) {
        getInstance(context);
        editor.putString("USER_TOKEN", token);
        editor.commit();
    }

    public static String getToken(Context context) {
        getInstance(context);
        String token = sp.getString("USER_TOKEN", "");
        return token;
    }

    public static void saveStoreType(Context context, int isValid, String storeId) {
        getInstance(context);
        editor.putString("STORE_ID", storeId);
        editor.putInt("STORE_IS_VALID", isValid);
        editor.commit();
    }

    public static int getStoreType(Context context) {
        getInstance(context);
        String storeId = sp.getString("STORE_ID", "");
        int isValid = sp.getInt("STORE_IS_VALID", Contants.STORE_TYPE_FAIL);
        if (TextUtils.isEmpty(storeId)) {
            return Contants.STORE_TYPE_FAIL;
        } else {
            return isValid;
        }
    }

    /**
     * 判断是否登录
     *
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        LoginModel model = getUserInfo(context);
        if (TextUtils.isEmpty(model.token))
            return false;
        return true;
    }

    /**
     * 判断是否用户是否是商户
     *
     * @param context
     * @return
     */
    public static boolean isUserStore(Context context) {
        LoginModel model = getUserInfo(context);
        if (TextUtils.isEmpty(model.storeId))
            return false;
        return true;
    }

    /**
     * 商品系统分类
     *
     * @param context
     * @return
     */
    public static String getSysGoodsType(Context context) {
        getInstance(context);
        String token = sp.getString("SYS_GOODS_TYPE", "");
        return token;
    }

    /**
     * 商品系统分类
     *
     * @param context
     */
    public static void saveSysGoodsType(Context context, List<GoodsTypeModel> typeList) {
        getInstance(context);
        Gson gson = new Gson();
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < typeList.size(); i++) {
                array.put(i, gson.toJson(typeList.get(i), GoodsTypeModel.class).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonStr = array.toString();
        editor.putString("SYS_GOODS_TYPE", jsonStr);
        editor.commit();
    }

    /**
     * 商圈系统分类
     *
     * @param context
     */
    public static void saveSysCircleType(Context context, List<GoodsTypeModel> typeList) {
        getInstance(context);
        Gson gson = new Gson();
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < typeList.size(); i++) {
                array.put(i, gson.toJson(typeList.get(i), GoodsTypeModel.class).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonStr = array.toString();
        editor.putString("SYS_CIRCLE_TYPE", jsonStr);
        editor.commit();
    }

    /**
     * 商圈系统分类
     *
     * @param context
     * @return
     */
    public static String getSysCircleType(Context context) {
        getInstance(context);
        String token = sp.getString("SYS_CIRCLE_TYPE", "");
        return token;
    }
}
