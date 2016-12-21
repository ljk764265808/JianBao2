package us.mifeng.k.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.Collections;

import us.mifeng.k.R;
import us.mifeng.k.action.Action;
import us.mifeng.k.adapter.GrallayAdapter;


public class CustormActivity extends AppCompatActivity {

    private GridView custormGrid;
    private Handler handler;
    private ImageLoader imageLoader;
    private String action;
    private GrallayAdapter adapter;
    private ImageView imgBoMedia;
    private ImageView cursorImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custorm);
        action = getIntent().getAction();
        if (action==null){
            finish();
        }
        initImageLoader();
        init();
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
    private void init() {
        handler = new Handler();
        custormGrid = (GridView) findViewById(R.id.custorm_grid);
        cursorImage = (ImageView) findViewById(R.id.cursorimage);
        Button btnGalleryOk = (Button) findViewById(R.id.btnGalleryOk);
        custormGrid.setFastScrollEnabled(true);
        adapter = new GrallayAdapter(getApplicationContext(),imageLoader);
        //调用快速滑动和滑动时候的监听事件
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader,true,true);
        custormGrid.setOnScrollListener(listener);
        if(action.equalsIgnoreCase(Action.ACTION_MULT_SELECTED)){
            custormGrid.setOnItemClickListener(mItemMulClickListener);
            adapter.setMultiplePick(true);
            btnGalleryOk.setVisibility(View.VISIBLE);
        }else if(action.equalsIgnoreCase(Action.ACTION_SIGLE_SELECTED)){
            btnGalleryOk.setVisibility(View.GONE);
            custormGrid.setOnItemClickListener(mItemSingleClickListener);
            adapter.setMultiplePick(false);
        }
        custormGrid.setAdapter(adapter);
        imgBoMedia = (ImageView) findViewById(R.id.cursorimage);
        btnGalleryOk.setOnClickListener(mOnClickListener);
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        adapter.addAll(getGalleyPhotos());
                        checkImageStatus();
                    }
                });
                Looper.loop();
            }
        }.start();


    }
    AdapterView.OnItemClickListener mItemMulClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            adapter.changeSelection(view,position);
        }
    };
    AdapterView.OnItemClickListener mItemSingleClickListener  = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CustomGallery item = (CustomGallery) adapter.getItem(position);
            Intent data = new Intent();
            data.putExtra("single_path",item.sdcardPath);
            setResult(RESULT_OK,data);
            finish();
        }
    };
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<CustomGallery> selected = adapter.getSelected();
            String[]allPath = new String[selected.size()];
            for (int i=0;i<allPath.length;i++){
                allPath[i] = selected.get(i).sdcardPath;
            }
            Intent data = new Intent();
            data.putExtra("all_path",allPath);
            setResult(RESULT_OK,data);
            finish();
        }
    };
    public ArrayList<CustomGallery> getGalleyPhotos() {
        ArrayList<CustomGallery> galleryList = new ArrayList<>();
        String[] columns ={MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media._ID;
        Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,columns,null,null,orderBy);
        if(imagecursor!=null&&imagecursor.getCount()>0){
            while(imagecursor.moveToNext()){
                CustomGallery item = new CustomGallery();
                int index = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                item.sdcardPath = imagecursor.getString(index);
                galleryList.add(item);
            }
        }
        Collections.reverse(galleryList);
        return galleryList;
    }
    private void checkImageStatus(){
        if(adapter.isEmpty()){
            cursorImage.setVisibility(View.VISIBLE);
        }else{
            cursorImage.setVisibility(View.GONE);
        }
    }
}
