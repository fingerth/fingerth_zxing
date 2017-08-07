//package com.fingerth.zxinglib.permission;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//
//
//
//
//public abstract class BasePermissionActivity extends AppCompatActivity {
//    public String message = "去打開權限";
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        message = getPermissionsMessage();
//        requestPermissions();
//    }
//
//
//    //======================================================================
//    //申请权限===============================================================
//
//    /**
//     * 申请权限
//     */
//    public abstract void requestPermissions();
//
//    /**
//     * 获取权限说明
//     *
//     * @return message "去打開文件管理權限"
//     */
//    public abstract String getPermissionsMessage();
//
//    /**
//     * 申请权限成功后回调
//     */
//    public abstract void onRequestPermissionsResultSuccess();
//
////    public void requestPermissions() {
////        // Here, thisActivity is the current activity
////        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////            int permsRequestCode = 200;
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                String[] perms = {Android6Permission.READ_EXTERNAL_STORAGE};
////                requestPermissions(perms, permsRequestCode);
////            } else {
////                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, permsRequestCode);
////            }
////        }
////    }
//
//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
//        //注意，permsRequestCode为200，和工具类200对应
//        switch (permsRequestCode) {
//            case 200:
//                PackageManager pm = this.getPackageManager();
//                PackageInfo pi = null;
//                try {
//                    pi = pm.getPackageInfo(this.getPackageName(), 0);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // 得到自己的包名
//                String pkgName;
//                if (pi != null && !TextUtils.isEmpty(pi.packageName)) {
//                    pkgName = pi.packageName;
//                } else {
//                    pkgName = getString(R.string.property_package_name);
//                }
//                final String packageName = pkgName;
//
//                if (grantResults != null && grantResults.length > 0) {
//                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    if (cameraAccepted) {
//                        //授权成功之后
//                        onRequestPermissionsResultSuccess();
//                    } else {
//                        //用户授权拒绝之后，友情提示一下就可以了
//                        //Toast.makeText(CaptureActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //创建对象
//                        builder.setTitle(AppConstants.appStrMap.get(AppConstants.help)); //设置标题
//                        builder.setMessage(message);  //设置内容
//                        builder.setPositiveButton(AppConstants.appStrMap.get(AppConstants.setting), new DialogInterface.OnClickListener() {
//                            @Override
//                            //设置确定按钮 ，注意导包import android.content.DialogInterface.OnClickListener;
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent detail_intent = new Intent();
//                                detail_intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                                detail_intent.addCategory(Intent.CATEGORY_DEFAULT);
//                                detail_intent.setData(Uri.parse("package:" + packageName));//包名
//                                startActivity(detail_intent);
//                            }
//                        });
//                        builder.setNegativeButton(AppConstants.appStrMap.get(AppConstants.cancel), new DialogInterface.OnClickListener() {
//                            @Override    //设置取消按钮
//                            public void onClick(DialogInterface dialog, int which) {
//                                BasePermissionActivity.this.finish();
//                            }
//                        });
//                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialogInterface) {
//                                BasePermissionActivity.this.finish();
//                            }
//                        });
//                        AlertDialog aler = builder.create();
//                        aler.setCanceledOnTouchOutside(false);
//                        aler.show();  //show（）
//                    }
//                }
//                break;
//        }
//    }
//    //申请权限===============================================================
//    //======================================================================
//
////    public interface onRequestPermissionsResultSuccessListerner {
////        void requestPermissionsResultSuccess();
////    }
//
//}
