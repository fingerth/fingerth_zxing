//package com.fingerth.zxinglib.activity;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.able.base.app.ABLEManageApplication;
//import com.able.base.error.ABLELogUtils;
//import com.able.base.eventbus.NewsTipEvent;
//import com.able.base.eventbus.ScanAddCoupon;
//import com.able.base.model.setting.AppConstants;
//import com.able.base.net.ABLEHttpsUrl;
//import com.able.base.net.RequestDataTool;
//import com.able.base.util.ABLEStaticUtils;
//import com.able.base.util.ABLEToastUtils;
//import com.able.base.util.AppInfoUtils;
//import com.able.base.util.MemberInfoUtils;
//import com.gyf.barlibrary.ImmersionBar;
//import com.uuzuche.lib_zxing.DisplayUtil;
//import com.uuzuche.lib_zxing.R;
//import com.uuzuche.lib_zxing.permission.Android6Permission;
//import com.uuzuche.lib_zxing.permission.BasePermissionActivity;
//
//import org.greenrobot.eventbus.EventBus;
//import org.json.JSONObject;
//
//import java.util.Map;
//
//public abstract class ABLEScanActivity extends BasePermissionActivity {
//    private static final String TAG = "CaptureActivity";
//    private String qrResult;
//    private Toolbar myToolbar;
//
//    @Override
//    public void setContentView(int layoutResID) {
//        super.setContentView(layoutResID);
//        setStatusBar();
//    }
//
//    protected void setStatusBar() {
//        findViewById(R.id.my_toolbar).setBackground(AppInfoUtils.getStatusBarShapColor());
//        ImmersionBar.with(this)
//                .titleBar(R.id.my_toolbar)
//                .navigationBarColor(R.color.black)
//                .init();
////        StatusBarUtil.setColor(this, Color.parseColor(ABLESharedPreferencesUtils.getThemeStyleColorStr(this)), 0);
//    }
//
//    @Override
//    public void onCreate(Bundle icicle) {
//        super.onCreate(icicle);
//        ABLEManageApplication.addActivity(this);
//        ABLEStaticUtils.getScreen(this);
//        //得到屏幕寬高，用於二維碼框框的大小
//        DisplayUtil.initDisplayOpinion(this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_ablescan);
//        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        String title = AppConstants.appStrMap.get(AppConstants.sao_yi_sao);
//        if (TextUtils.isEmpty(title)) {
//            title = "掃一掃";
//        }
//        myToolbar.setTitle(title);
//        setSupportActionBar(myToolbar);
//        myToolbar.setTitleTextColor(Color.WHITE);
//        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ABLEScanActivity.this.finish();
//                ABLEScanActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });
//
//
//        CaptureFragment captureFragment = new CaptureFragment();
//        // 为二维码扫描界面设置定制化界面
//        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
//        captureFragment.setAnalyzeCallback(analyzeCallback);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ABLEManageApplication.removeActivity(this);
//    }
//
//    @Override
//    public void requestPermissions() {
//        Android6Permission.requestPermissions(this, Android6Permission.CAMERA);
//    }
//
//    @Override
//    public String getPermissionsMessage() {
//        return AppConstants.appStrMap.get(AppConstants.TurnCameraPermissions);
//    }
//
//    @Override
//    public void onRequestPermissionsResultSuccess() {
//
//    }
//
//    /**
//     * 二维码解析回调函数
//     */
//    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
//        @Override
//        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//            qrResult = result;
//            checkQrCode(qrResult);
//            ABLELogUtils.setTag(TAG, "result-->" + qrResult);
//        }
//
//        @Override
//        public void onAnalyzeFailed() {
//            showResultDialog(AppConstants.appStrMap.get(AppConstants.msg_intent_failed));
//        }
//    };
//
//    private void showResultDialog(String msg) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //创建对象
//        builder.setTitle(AppConstants.appStrMap.get(AppConstants.tip)); //设置标题
//        builder.setMessage(msg);  //设置内容
//        builder.setPositiveButton(AppConstants.appStrMap.get(AppConstants.sure), new DialogInterface.OnClickListener() {
//            @Override
//            //设置确定按钮 ，注意导包import android.content.DialogInterface.OnClickListener;
//            public void onClick(DialogInterface dialog, int which) {
//                //startActivity(new Intent(ABLEScanActivity.this, ABLEScanActivity.class));
//                toReStart();
//            }
//        });
//        AlertDialog aler = builder.create();
//        aler.setCanceledOnTouchOutside(false);
//        aler.show();  //show（）
//    }
//
//    private void checkQrCode(String qrResult) {
//        String mId = MemberInfoUtils.getMemberId(this);
//        String url = ABLEHttpsUrl.URL_AddScan + "?siteId=" + ABLEHttpsUrl.webSiteid + "&" + ABLEHttpsUrl.LangFlag + "=" + ABLEStaticUtils.getLanguage(this) +
//                "&key=&memberId=" + mId + "&barcode=" + qrResult;
//        ABLELogUtils.i(TAG, "二维码扫描url" + url);
//
//        Map<String, String> map = ABLEHttpsUrl.getMap(this);
////        map.put("siteId", "" + ABLEHttpsUrl.webSiteid);
////        map.put(ABLEHttpsUrl.LangFlag, "" + ABLEStaticUtils.getLanguage(this));
////        map.put("key", "");
////        map.put("memberId", mId);
//        map.put("barcode", qrResult);
//        RequestDataTool.getInstance(this).requestDataUsePOST(ABLEHttpsUrl.URL_AddScan, map, new RequestDataTool.SuccessfulRequestData() {
//            @Override
//            public void xxJson(String response) {
//                ABLELogUtils.i(TAG, "二维码扫描返回结果=" + response);
//                //{"status":500,"msg":"优惠券不存在","data":null}
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String msg = jsonObject.getString("msg");
//                    showResultDialog(msg);
//                    EventBus.getDefault().post(new ScanAddCoupon());//更新優惠券
//                    EventBus.getDefault().post(new NewsTipEvent());//氣泡更新
//                    //ABLEToastUtils.showToast(CaptureActivity.this, msg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    toReStart();
//                }
//            }
//        }, new RequestDataTool.FailRequestData() {
//            @Override
//            public void failUrl(String url) {
//                ABLEToastUtils.showToast(ABLEScanActivity.this, AppConstants.appStrMap.get(AppConstants.net_error));
//                toReStart();
//            }
//        });
//    }
//
//    /**
//     * 重新啟動，掃描
//     * startActivity(new Intent(ABLEScanActivity.this, ABLEScanActivity.class));
//     */
//    public abstract void toReStart();
//}
