//package com.fingerth.zxinglib.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.PointF;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.able.base.app.ABLEManageApplication;
//import com.able.base.eventbus.ConponRefreshEvent;
//import com.able.base.ui.ABLENavigationActivity;
//import com.able.base.util.ABLEStaticUtils;
//import com.able.base.util.AppInfoUtils;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.uuzuche.lib_zxing.R;
//import com.uuzuche.lib_zxing.encoding.EncodingHandler;
//
//import org.greenrobot.eventbus.EventBus;
//
//public abstract class ABLEQrCodeActivity extends ABLENavigationActivity {
//
//    Toolbar myToolbar;
//    TextView qrCode;
//    ImageView qrCode1;
//    ImageView qrCode2;
//    RelativeLayout rootView;
//
//    Handler h = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            qrCode1.setImageBitmap(b);
//        }
//    };
//
//    //二维码
//    String qrContent = "";//要生成二维码中的内容
//    Bitmap qrcodeBitmap;
//
//    //条形码
//    Bitmap b;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ABLEManageApplication.addActivity(this);
//        setContentView(R.layout.able_activity_qr_code);
//        initView();
//
//        createQrCode();
//    }
//
//    private void initView() {
//        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        qrCode = (TextView) findViewById(R.id.qr_code);
//        qrCode1 = (ImageView) findViewById(R.id.qr_code1);
//        qrCode2 = (ImageView) findViewById(R.id.qr_code2);
//        rootView = (RelativeLayout) findViewById(R.id.root_view);
//        rootView.setBackgroundColor(Color.parseColor(AppInfoUtils.getThemeColor()));
//
//        Intent intent = getIntent();
//        String title = intent.getStringExtra("title");
//        if (!TextUtils.isEmpty(title)) {
//            setToobarAndTitle(myToolbar, title);
//        } else {
//            setToobarAndTitle(myToolbar, "..");
//        }
//
//        qrContent = intent.getStringExtra("Barcode");
//        qrCode.setText(qrContent);
//    }
//
//    private void createQrCode() {
//        //条形码生成
//        new Thread() {
//            public void run() {
//                //高100dp，宽是match_parent，margin为100dp
//                int px = ABLEStaticUtils.dp2px(ABLEQrCodeActivity.this, 100);
//                int width = ABLEStaticUtils.sysWidth - px;
//
//                try {
//                    b = creatBarcode(ABLEQrCodeActivity.this, qrContent, width, px, false);
//                    h.sendMessage(new Message());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }.start();
//
//        //二维码生成
//        createQR_Code();
//    }
//
//    /**
//     * 创建二维码
//     */
//    private void createQR_Code() {
//        try {
//            int with = ABLEStaticUtils.dp2px(this, 200);
//            //生成二维码图片，第一个参数是二维码的内容，第二个参数是正方形图片的边长，单位是像素
//            qrcodeBitmap = EncodingHandler.createQRCode(qrContent, with);
//            qrCode2.setImageBitmap(qrcodeBitmap);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    //==============================================================================================
//    //==============================================================================================
//
//    /**
//     * 生成条形码
//     *
//     * @param context
//     * @param contents      需要生成的内容
//     * @param desiredWidth  生成条形码的宽带
//     * @param desiredHeight 生成条形码的高度
//     * @param displayCode   是否在条形码下方显示内容
//     * @return
//     */
//    public static Bitmap creatBarcode(Context context, String contents,
//                                      int desiredWidth, int desiredHeight, boolean displayCode) {
//        Bitmap ruseltBitmap = null;
//        /**
//         * 图片两端所保留的空白的宽度
//         */
//        int marginW = 30;
//        /**
//         * 条形码的编码类型
//         */
//        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
//
//        if (displayCode) {
//            Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat,
//                    desiredWidth, desiredHeight);
//            Bitmap codeBitmap = creatCodeBitmap(contents, desiredWidth + 2
//                    * marginW, desiredHeight, context);
//            ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
//                    0, desiredHeight));
//        } else {
//            ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,
//                    desiredWidth, desiredHeight);
//        }
//
//        return ruseltBitmap;
//    }
//
//    /**
//     * 生成条形码的Bitmap
//     *
//     * @param contents      需要生成的内容
//     * @param format        编码格式
//     * @param desiredWidth
//     * @param desiredHeight
//     * @return
//     * @throws WriterException
//     */
//    protected static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int desiredWidth, int desiredHeight) {
//        final int WHITE = 0xFFFFFFFF;
//        final int BLACK = 0xFF000000;
//
//        MultiFormatWriter writer = new MultiFormatWriter();
//        BitMatrix result = null;
//        try {
//            result = writer.encode(contents, format, desiredWidth,
//                    desiredHeight, null);
//        } catch (WriterException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        int width = result.getWidth();
//        int height = result.getHeight();
//        int[] pixels = new int[width * height];
//        // All are 0, or black, by default
//        for (int y = 0; y < height; y++) {
//            int offset = y * width;
//            for (int x = 0; x < width; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height,
//                Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//        return bitmap;
//    }
//
//    /**
//     * 生成显示编码的Bitmap
//     *
//     * @param contents
//     * @param width
//     * @param height
//     * @param context
//     * @return
//     */
//    protected static Bitmap creatCodeBitmap(String contents, int width,
//                                            int height, Context context) {
//        TextView tv = new TextView(context);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        tv.setLayoutParams(layoutParams);
//        tv.setText(contents);
//        tv.setHeight(height);
//        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//        tv.setWidth(width);
//        tv.setDrawingCacheEnabled(true);
//        tv.setTextColor(Color.BLACK);
//        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
//
//        tv.buildDrawingCache();
//        Bitmap bitmapCode = tv.getDrawingCache();
//        return bitmapCode;
//    }
//
//    /**
//     * 将两个Bitmap合并成一个
//     *
//     * @param first
//     * @param second
//     * @param fromPoint 第二个Bitmap开始绘制的起始位置（相对于第一个Bitmap）
//     * @return
//     */
//    protected static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
//                                          PointF fromPoint) {
//        if (first == null || second == null || fromPoint == null) {
//            return null;
//        }
//        int marginW = 30;
//        Bitmap newBitmap = Bitmap.createBitmap(
//                first.getWidth() + second.getWidth() + marginW,
//                first.getHeight() + second.getHeight(), Bitmap.Config.ARGB_4444);
//        Canvas cv = new Canvas(newBitmap);
//        cv.drawBitmap(first, marginW, 0, null);
//        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
//        cv.save(Canvas.ALL_SAVE_FLAG);
//        cv.restore();
//
//        return newBitmap;
//    }
//    // ==============================================================================================
//    //==============================================================================================
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ABLEManageApplication.removeActivity(this);
////        bitmap.isRecycled(); //返回该Bitmap对象是否已被回收的状态
////        bitmap.recycle();// 强制一个Bitmap对象立即回收自己。
//        if (!qrcodeBitmap.isRecycled()) {
//            Log.v("log", "==二維碼掃描，回收bitmap1==");
//            qrcodeBitmap.recycle();
//        }
//        if (!b.isRecycled()) {
//            Log.v("log", "==條形碼，回收bitmap2==");
//            b.recycle();
//        }
//        EventBus.getDefault().post(new ConponRefreshEvent());
//    }
//
//}
//
