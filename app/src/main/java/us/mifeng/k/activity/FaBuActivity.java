package us.mifeng.k.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import us.mifeng.k.R;
import us.mifeng.k.action.Action;
import us.mifeng.k.app.MInterface;
import us.mifeng.k.fragment.Fragment_Goods;
import us.mifeng.k.sdFile.SaveFile;
import us.mifeng.k.utils.OkHttpUtils;
import us.mifeng.k.utils.ShareUtils;

/**
 * Created by k on 2016/12/20.
 */

public class FaBuActivity extends Activity {
    private EditText et_title, et_miaoshu, et_price, et_phone, et_qq, et_weixin;
    private Button bt_fabu;
    private ImageButton iv1;
    private static final int IMAGE = 1;
    //把获取到的图片放到list当中。
    ArrayList<String> lists = new ArrayList<String>();
    //把上传的数据放到map当中
    Map<String, String> map = new HashMap<String, String>();
    private Handler handler = new Handler();
    //创建一个集合，用来存放从照相机中获取的照片。
    ArrayList<Bitmap> photoBitmaps = new ArrayList<>();
    private GridView gridview;
    private View addView;
    private ImageLoader imageLoader;
    private ImageView iv_1, iv_2, iv_3, iv_4, iv_5, iv_6;
    private ImageView[] imageViews = new ImageView[5];
    private PopupWindow popupWindow1;
    private LinearLayout ll;
    private Button tuku, paizhao, cancel;
    //该变量表示你点击了了第几个数组中的ImageView
    private int currentImageItem = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fabu_main);
        addView = View.inflate(this, R.layout.tianjia_main, null);
        Init();
        initImageLoader();

    }

    public void Init() {
        ll = (LinearLayout) findViewById(R.id.ll);
        iv_1 = (ImageView) findViewById(R.id.iv1);
        iv_2 = (ImageView) findViewById(R.id.iv2);
        iv_3 = (ImageView) findViewById(R.id.iv3);
        iv_4 = (ImageView) findViewById(R.id.iv4);
        iv_5 = (ImageView) findViewById(R.id.iv5);
        iv_6 = (ImageView) findViewById(R.id.iv6);
        iv_2.setOnClickListener(new ImageOnClickListener());
        iv_3.setOnClickListener(new ImageOnClickListener());
        iv_4.setOnClickListener(new ImageOnClickListener());
        iv_5.setOnClickListener(new ImageOnClickListener());
        iv_6.setOnClickListener(new ImageOnClickListener());

        imageViews[0] = iv_2;
        imageViews[1] = iv_3;
        imageViews[2] = iv_4;
        imageViews[3] = iv_5;
        imageViews[4] = iv_6;


        et_title = (EditText) findViewById(R.id.et_title);
        et_miaoshu = (EditText) findViewById(R.id.et_miaoshu);
        et_price = (EditText) findViewById(R.id.et_price);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_qq = (EditText) findViewById(R.id.et_qq);
        et_weixin = (EditText) findViewById(R.id.et_weixin);
        bt_fabu = (Button) findViewById(R.id.bt_fabu);

        iv_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View pop = View.inflate(FaBuActivity.this, R.layout.popuwindow_main, null);
                paizhao = (Button) pop.findViewById(R.id.paizhao);
                tuku = (Button) pop.findViewById(R.id.tuku);
                cancel = (Button) pop.findViewById(R.id.cancel);
                paizhao.setOnClickListener(new BtnOnClickListener());
                tuku.setOnClickListener(new BtnOnClickListener());
                cancel.setOnClickListener(new BtnOnClickListener());

                popupWindow1 = new PopupWindow(pop,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow1.setTouchable(true);
                //设置点击PopupWindow以外的地方关闭PopupWindow
                popupWindow1.setOutsideTouchable(true);
                popupWindow1.setAnimationStyle(R.style.Animation);
                popupWindow1.showAtLocation(ll, Gravity.BOTTOM | Gravity
                        .CENTER_HORIZONTAL, 0, 0);
                popupWindow1.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Log.i("mengdd", "onTouch : ");

                        return false;
                    }
                });
                popupWindow1.showAsDropDown(ll);
            }
        });


        bt_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim1 = et_title.getText().toString().trim();
                String trim2 = et_miaoshu.getText().toString().trim();
                String trim3 = et_price.getText().toString().trim();
                String trim4 = et_phone.getText().toString().trim();
                String trim5 = et_qq.getText().toString().trim();
                String trim6 = et_weixin.getText().toString().trim();
                String token = (String) ShareUtils.getData(FaBuActivity.this, "token", "");
                map.put("token", "EE3776E9777444CEA8867B5F99A2CCEF");
                map.put("title", trim1);
                map.put("description", trim2);
                map.put("price", trim3);
                map.put("mobile", trim4);

                OkHttpUtils.UploadFileSCMore(MInterface.zhuji + MInterface.fabu, lists, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        System.out.println(string);
                        Log.e("tag", "lists");
                    }
                });

                if (TextUtils.isEmpty(trim1)) {
                    Toast.makeText(FaBuActivity.this, "宝贝名称不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(trim2)) {
                    Toast.makeText(FaBuActivity.this, "宝贝描述不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(trim3)) {
                    Toast.makeText(FaBuActivity.this, "宝贝价格不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(trim4)) {
                    Toast.makeText(FaBuActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (imageViews == null) {
                    Toast.makeText(FaBuActivity.this,"图片不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FaBuActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FaBuActivity.this, MainActivity.class));
                }
            }
        });

    }

    class BtnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.paizhao:
                    Intent intentCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    FaBuActivity.this.startActivityForResult(intentCapture, Action.REQUEST_CODE_OPEN_CAPTURE);
                    popupWindow1.dismiss();
                    break;
                case R.id.tuku:
                    Intent intentMul = new Intent(Action.ACTION_MULT_SELECTED);
                    FaBuActivity.this.startActivityForResult(intentMul, Action.REQUEST_CODE_MULT_ALDUM);
                    popupWindow1.dismiss();
                    break;
                case R.id.cancel:
                    popupWindow1.dismiss();
            }
        }
    }

    public void fanhui(View v) {
        startActivity(new Intent(FaBuActivity.this, Fragment_Goods.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径，
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
            String imagePath = cursor.getString(columnIndex);
            lists.add(imagePath);

//            showImage(imagePath);
            cursor.close();
        } else if (requestCode == Action.REQUEST_CODE_OPEN_CAPTURE) {
            getPicture(requestCode, resultCode, data);
            if (photoBitmaps != null) {
                //popupWindow1.dismiss();
                imageSetBitmap();
            }
        } else if (requestCode == Action.REQUEST_CODE_MULT_ALDUM && resultCode == Activity.RESULT_OK) {
            //  popupWindow1.dismiss();
            final String[] all_path = data.getStringArrayExtra("all_path");
            //将路径转换为bitmap对象
            for (int i = 0; i < all_path.length; i++) {
                Log.i("tag", "------adf----------" + all_path[i]);
                File file = new File(all_path[i]);
                lists.add(all_path[i]);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (bitmap != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = bitmap.getHeight() / 200;
                    Bitmap bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    if (photoBitmaps.size() > i) {
                        photoBitmaps.remove(i);
                    }
                    photoBitmaps.add(bitmap1);
                    if (photoBitmaps.size() > 5) {
                        Toast.makeText(FaBuActivity.this, "最多添加五张图片,请重新选择", Toast.LENGTH_SHORT).show();
                    } else {
                        imageSetBitmap();
                    }
//                                    photoAdapter.notifyDataSetChanged();
                }
            }

        }

    }

    private void imageSetBitmap() {
        for (int j = 0; j < photoBitmaps.size(); j++) {
            imageViews[j].setImageBitmap(photoBitmaps.get(photoBitmaps.size() - 1 - j));
        }
    }

    //当另外的Activity关闭时返回结果此方法中，该方法为Activity的方法
    private void getPicture(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        if (uri == null) {
            if (SaveFile.isScardOk()) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                SaveFile.saveBitmap(bitmap);
                Bitmap bt = SaveFile.getBitmap();
                //将获取的图片存放到list集合当中，然后通知适配器进行更改，并显示数据
                if (photoBitmaps.size() == 5) {
                    photoBitmaps.remove(photoBitmaps.size() - 1);
                }
                photoBitmaps.add(bt);

            }
            // }
        } else {
            //根据uri获取图片的路径
            String imagePath = getImagePath(uri);
            //根据路径获取图片的位图。
            Bitmap bitmap = getUriImage(imagePath);
            photoBitmaps.add(bitmap);
        }
    }

    private Bitmap getUriImage(String imagePath) {
        //获取图片加工厂的参数对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        Log.i("tag", "-------------------->" + bitmap);
        //设置图片的压缩比例
        int be = (int) (options.outHeight / (float) 320);
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;
        //重新读入图片
        Bitmap resultBitmap = BitmapFactory.decodeFile(imagePath, options);
        return resultBitmap;

    }

    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int coloum_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(coloum_index);
        return imagePath;
    }

    //初始化ImageLoader，并设置ImageLoader的一些属性
    private void initImageLoader() {
        //显示图片的参数设置
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                //设置现在的图片是否缓存在sd卡中。
                .cacheOnDisc(true)
                //设置现在后图片的缩放样式
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                //设置图片的解码的类型
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions)
                //超高速缓冲存储系统
                .memoryCache(
                        //WeakMemoryCache用来存放图片的弱应用对象
                        new WeakMemoryCache());
        //设置Image Loader的配置
        ImageLoaderConfiguration config = builder.build();
        //获取ImageLoader对象
        imageLoader = ImageLoader.getInstance();
        //初始化配置要求
        imageLoader.init(config);
    }

    //通过ImageLoader对象，将路径转换为Bitmap对象。
    //对ImageView进行了监听事件
    class ImageOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv2:
//                    currentImageItem = 4;
                    deleteIcon(photoBitmaps.size()-1);
                    break;
                case R.id.iv3:
//                    currentImageItem = 3;
                    deleteIcon(photoBitmaps.size()-2);
                    break;
                case R.id.iv4:
                    deleteIcon(photoBitmaps.size()-3);
//                    currentImageItem = 2;
                    break;
                case R.id.iv5:
                    deleteIcon(photoBitmaps.size()-4);
//                    currentImageItem = 1;
                    break;
                case R.id.iv6:
                    deleteIcon(0);
           //         currentImageItem = 0;
                    break;
            }

        }
    }

    private void deleteIcon(int i) {
        if (photoBitmaps!=null &&(photoBitmaps.size()>i)&&i>=0){
            photoBitmaps.remove(i);
            imageSetBitmap();
            imageViews[photoBitmaps.size()].setImageResource(R.mipmap.kongbai);
        }
        else
        {
            Toast.makeText(this, "非法操作", Toast.LENGTH_SHORT).show();
        }
    }

}