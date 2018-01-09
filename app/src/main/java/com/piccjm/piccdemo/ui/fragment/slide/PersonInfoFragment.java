package com.piccjm.piccdemo.ui.fragment.slide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.bean.UserBean;
import com.piccjm.piccdemo.presenter.slide.PersonInfoPresenter;
import com.piccjm.piccdemo.presenter.slide.PersonInfoPresenterImpl;
import com.piccjm.piccdemo.ui.activity.MainActivity;
import com.piccjm.piccdemo.ui.fragment.base.BaseFragment;
import com.piccjm.piccdemo.ui.view.LoadingDialog;
import com.piccjm.piccdemo.ui.view.dialog.BirthdayDialog;
import com.piccjm.piccdemo.ui.view.dialog.HeadDialog;
import com.piccjm.piccdemo.ui.view.dialog.SexDialog;
import com.piccjm.piccdemo.utils.GlideUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mangowangwang on 2017/12/12.
 */

public class PersonInfoFragment extends BaseFragment<PersonInfoPresenterImpl> implements PersonInfoPresenter.View {


    private static final int TAKE_PHOTO_PERMISSION_REQUEST_CODE = 0; // 拍照的权限处理返回码
    private static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1; // 读储存卡内容的权限处理返回码

    private static final int TAKE_PHOTO_REQUEST_CODE = 3; // 拍照返回的 requestCode
    private static final int CHOICE_FROM_ALBUM_REQUEST_CODE = 4; // 相册选取返回的 requestCode
    private static final int CROP_PHOTO_REQUEST_CODE = 5; // 裁剪图片返回的 requestCode

    private Uri photoUri = null;
    private Uri photoOutputUri = null; // 图片最终的输出文件的 Uri


    private UserBean mPersonInfo;

    @BindView(R.id.linear_tv_nickname)
    TextView tv_nickName;
    @BindView(R.id.linear_tv_name)
    TextView tv_name;
    @BindView(R.id.linear_tv_password)
    TextView tv_password;
    @BindView(R.id.linear_tv_birthday)
    TextView tv_birthday;
    @BindView(R.id.linear_tv_sex)
    TextView tv_sex;
    @BindView(R.id.linear_image_head)
    ImageView ig_head;

    TextView tv_update;
    String updateString;


    SexDialog sexDialog;
    BirthdayDialog birthdayDialog;
    LoadingDialog loadingDialog;
    HeadDialog headDialog;

    String selected_sex;
    String selected_birthday;

    @OnClick(R.id.linear_sex)
    public void updateSex() {
        sexDialog = new SexDialog(getContext(),tv_sex.getText().toString());
        sexDialog.setTitle("性别选择");
        sexDialog.setYesOnclickListener("确定", new SexDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                RadioButton radioButton = (RadioButton)sexDialog.findViewById(sexDialog.sexGroup.getCheckedRadioButtonId());
                selected_sex = radioButton.getText().toString();
                updateString = selected_sex;
                if (selected_sex.equals(tv_sex.getText().toString()))
                {
                    sexDialog.dismiss();
                }else
                {
                    mPresenter.updatePersonInfo("sex",selected_sex);
                    sexDialog.dismiss();
                    if (loadingDialog == null)
                    {
                        LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(getContext())
                                .setMessage("正在提交...")
                                .setCancelable(false)
                                .setCancelOutside(true);
                        loadingDialog=loadBuilder.create();
                    }
                    loadingDialog.show();
                    tv_update = tv_sex;
                }


            }
        });
//                selfDialog.setNoOnclickListener("取消", new SexDialog.onNoOnclickListener() {
//                    @Override
//                    public void onNoClick() {
//                        Toast.makeText(MainActivity.this, "点击了--取消--按钮", Toast.LENGTH_LONG).show();
//                        selfDialog.dismiss();
//                    }
//                });
        sexDialog.show();
    }
    
    @OnClick(R.id.linear_head)
    public void updateHead() {
        headDialog = new HeadDialog(getContext());
        headDialog.setTitle("头像选择");

        headDialog.setHeadButtonOnclickListener(new HeadDialog.OnHeadButtonOnclickListener() {
            @Override
            public void onNoClick() {
                headDialog.dismiss();
            }

            @Override
            public void onTakePhotoClick() {

                // 调用相机拍照
                    // 同上面的权限申请逻辑
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                    /*
                     * 下面是对调用相机拍照权限进行申请
                     */
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,}, TAKE_PHOTO_PERMISSION_REQUEST_CODE);
                    } else {
                        startCamera();
                    }
            }

            @Override
            public void onPhotoGalleryClick() {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 申请读写内存卡内容的权限
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_SDCARD_PERMISSION_REQUEST_CODE);
                }else
                {
                    choiceFromAlbum();
                }
            }
        });

        headDialog.show();
    }

    @OnClick(R.id.linear_birthday)
    public void updateBirthday(){
        String birthdayDate=  tv_birthday.getText().toString();
        birthdayDialog = new BirthdayDialog(getContext(),birthdayDate);
        birthdayDialog.setTitle("生日选择");
        birthdayDialog.setYesOnclickListener("确定", new BirthdayDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selected_birthday = birthdayDialog.dateString;
                updateString = selected_birthday;
                if (selected_birthday.equals(tv_birthday.getText().toString()))
                {
                    birthdayDialog.dismiss();
                }else
                {
                    mPresenter.updatePersonInfo("birthday",selected_birthday);
                    birthdayDialog.dismiss();
                    if (loadingDialog == null)
                    {
                        LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(getContext())
                                .setMessage("正在提交...")
                                .setCancelable(false)
                                .setCancelOutside(true);
                        loadingDialog=loadBuilder.create();
                    }
                    loadingDialog.show();
                    birthdayDialog.dismiss();
                    tv_update = tv_birthday;
                }


            }
        });
//                selfDialog.setNoOnclickListener("取消", new SexDialog.onNoOnclickListener() {
//                    @Override
//                    public void onNoClick() {
//                        Toast.makeText(MainActivity.this, "点击了--取消--按钮", Toast.LENGTH_LONG).show();
//                        selfDialog.dismiss();
//                    }
//                });
        birthdayDialog.show();
    }



    @Override
    protected void loadData() {
        mPresenter.fetchPersonInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personinfo;
    }

    @Override
    protected void initView() { }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    public void refresh() {

        mPersonInfo = mPresenter.getPersonInfo();
        tv_nickName.setText(mPersonInfo.getNickname());
        tv_birthday.setText(mPersonInfo.getBirthday());
        tv_name.setText(mPersonInfo.getName());
        tv_password.setText(mPersonInfo.getPassword());
        tv_sex.setText(mPersonInfo.getSex());
        GlideUtils.loadCircleImage(getContext(),mPersonInfo.getHead_image(),ig_head);
    }

    @Override
    public void resultOfSexUpdate(String result)
    {
        if (result.equals("succeed"))
        {
            tv_update.setText(updateString);
            loadingDialog.dismiss();
            Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();

        }else if(result.equals("fail"))
        {
            loadingDialog.dismiss();
            Toast.makeText(getContext(), "修改失败,请重试!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resultOfError() {
        loadingDialog.dismiss();
    }

    @Override
    public void resultOfUploadHeadImage(String result) {
        if (result.equals("succeed"))
        {
            System.out.println(mPersonInfo.getHead_image());
            GlideUtils.loadCircleImage(getContext(),mPersonInfo.getHead_image(),ig_head);
            loadingDialog.dismiss();
            Toast.makeText(getContext(), "更换成功", Toast.LENGTH_SHORT).show();

        }else if(result.equals("fail"))
        {
            loadingDialog.dismiss();
            Toast.makeText(getContext(), "更换失败,请重试!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 拍照
     */
    private void startCamera() {
        /**
         * 设置拍照得到的照片的储存目录，因为我们访问应用的缓存路径并不需要读写内存卡的申请权限，
         * 因此，这里为了方便，将拍照得到的照片存在这个缓存目录中
         */
        File file = new File(getActivity().getExternalCacheDir(), MainActivity.CardNumber+".jpg");
        try {
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 因 Android 7.0 开始，不能使用 file:// 类型的 Uri 访问跨应用文件，否则报异常，
         * 因此我们这里需要使用内容提供器，FileProvider 是 ContentProvider 的一个子类，
         * 我们可以轻松的使用 FileProvider 来在不同程序之间分享数据(相对于 ContentProvider 来说)
         */
        if(Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(getActivity(), "com.example.lifepicc.fileprovider", file);
        } else {
            photoUri = Uri.fromFile(file); // Android 7.0 以前使用原来的方法来获取文件的 Uri
        }
        // 打开系统相机的 Action，等同于："android.media.action.IMAGE_CAPTURE"
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置拍照所得照片的输出目录
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 从相册选取
     */
    private void choiceFromAlbum() {
        // 打开系统图库的 Action，等同于: "android.intent.action.GET_CONTENT"
        Intent choiceFromAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        // 设置数据类型为图片类型
        choiceFromAlbumIntent.setType("image/*");
        startActivityForResult(choiceFromAlbumIntent, CHOICE_FROM_ALBUM_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/"+MainActivity.CardNumber+"_head.jpg"));
        // 设置图片宽高比例
        cropPhotoIntent.putExtra("aspectX", 1);
        cropPhotoIntent.putExtra("aspectY", 1);
        // 设置图片宽高
        cropPhotoIntent.putExtra("outputX", 240);
        cropPhotoIntent.putExtra("outputY", 240);

        startActivityForResult(cropPhotoIntent, CROP_PHOTO_REQUEST_CODE);
    }

    /**
     * 在这里进行用户权限授予结果处理
     * @param requestCode 权限要求码，即我们申请权限时传入的常量
     * @param permissions 保存权限名称的 String 数组，可以同时申请一个以上的权限
     * @param grantResults 每一个申请的权限的用户处理结果数组(是否授权)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            // 调用相机拍照：
            case TAKE_PHOTO_PERMISSION_REQUEST_CODE:
                // 如果用户授予权限，那么打开相机拍照
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "拍照权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            // 打开相册选取：
            case WRITE_SDCARD_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choiceFromAlbum();
                } else {
                    Toast.makeText(getActivity(), "读写内存卡内容权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 通过这个 activity 启动的其他 Activity 返回的结果在这个方法进行处理
     * @param requestCode 返回码，用于确定是哪个 Activity 返回的数据
     * @param resultCode 返回结果，一般如果操作成功返回的是 RESULT_OK
     * @param data 返回对应 activity 返回的数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            // 通过返回码判断是哪个应用返回的数据
            switch (requestCode) {
                // 拍照
                case TAKE_PHOTO_REQUEST_CODE:
                    cropPhoto(photoUri);
                    break;
                // 相册选择
                case CHOICE_FROM_ALBUM_REQUEST_CODE:
                    cropPhoto(data.getData());
                    break;
                // 裁剪图片
                case CROP_PHOTO_REQUEST_CODE:
                    File file = new File(photoOutputUri.getPath());
                    if(file.exists()) {
                        //Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                        //GlideUtils.loadCircleImageUri(getContext(),photoOutputUri,ig_head);
                        // file.delete(); // 选取完后删除照片
                        headDialog.dismiss();
                        if (loadingDialog == null)
                        {
                            LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(getContext())
                                    .setMessage("正在提交...")
                                    .setCancelable(false)
                                    .setCancelOutside(true);
                            loadingDialog=loadBuilder.create();
                        }
                        loadingDialog.show();
                        mPresenter.uploadPersonHead(file);
                    } else {
                        Toast.makeText(getActivity(), "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }


//    // 将intent data中的URL解析成为Path 暂时没有用到
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data) {
//        String imagePath = null;
//        Uri uri = data.getData();
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            // 如果是document类型的Uri，则通过document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1]; // 解析出数字格式的id
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                imagePath = getImagePath(contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是content类型的Uri，则使用普通方式处理
//            imagePath = getImagePath(uri, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是file类型的Uri，直接获取图片路径即可
//            imagePath = uri.getPath();
//        }
//        displayImage(imagePath); // 根据图片路径显示图片
//    }
//
//    private String getImagePath(Uri uri, String selection) {
//        String path = null;
//        // 通过Uri和selection来获取真实的图片路径
//        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    private void displayImage(String imagePath) {
//        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            pictureImageView.setImageBitmap(bitmap);
//        } else {
//            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
//        }
//    }
}
