package us.mifeng.k.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;

import us.mifeng.k.R;
import us.mifeng.k.activity.CustomGallery;


/**
 * Created by 黑夜之火 on 2016/12/10.
 * 进入图库后的界面设计
 */

public class GrallayAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    //设置可否多选
    private boolean isMultiplePick;
    private ArrayList<CustomGallery> data = new ArrayList<>();
    private int numphotos = 0;

    public GrallayAdapter(Context context, ImageLoader imageLoader){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.grallayitem,null);
            holder = new ViewHolder();
            holder.imgQueue = (ImageView) convertView.findViewById(R.id.imageQueue);
            holder.imgQueueMultiSelected = (ImageView) convertView.findViewById(R.id.isMultSelected);
            if(isMultiplePick){
                holder.imgQueueMultiSelected.setVisibility(View.VISIBLE);
            }else {
                holder.imgQueueMultiSelected.setVisibility(View.GONE);
            }
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgQueue.setTag(position);
        //holder.imgQueue.setImageBitmap(getBitmapMain(data.get(position).sdcardPath));
       // holder.imgQueue.setImageResource(R.mipmap.ic_launcher);
        imageLoader.displayImage(data.get(position).sdcardPath,holder.imgQueue);
        try {
            imageLoader.displayImage("file://"+data.get(position).sdcardPath,holder.imgQueue,new SimpleImageLoadingListener(){
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.imgQueue.setImageResource(R.mipmap.ic_launcher);
                    super.onLoadingStarted(imageUri, view);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        if(isMultiplePick){
            holder.imgQueueMultiSelected.setSelected(data.get(position).isSeleted);
        }
        return convertView;
    }

    public void setMultiplePick(boolean multiplePick) {
        this.isMultiplePick = multiplePick;
    }

    public void addAll(ArrayList<CustomGallery> galleyPhotos) {
        this.data.clear();
        this.data.addAll(galleyPhotos);
        notifyDataSetChanged();
    }

    public ArrayList<CustomGallery> getSelected() {
        ArrayList<CustomGallery> dataT = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            if (data.get(i).isSeleted){
                dataT.add(data.get(i));
            }
        }
        return dataT;
    }

    public void changeSelection(View view, int position) {
        if(numphotos<5){
            if(data.get(position).isSeleted){
                data.get(position).isSeleted = false;
                numphotos --;
            }else{
                data.get(position).isSeleted = true;
                numphotos++;
            }
        }else if(numphotos>=5 && data.get(position).isSeleted){
            data.get(position).isSeleted = false;
            numphotos --;
        }else{
            Toast.makeText(context,"最多选择5张图片",Toast.LENGTH_LONG).show();
        }


        ((ViewHolder)view.getTag()).imgQueueMultiSelected.setSelected(data.get(position).isSeleted);
    }
    class ViewHolder{
        ImageView imgQueue;
        ImageView imgQueueMultiSelected;

    }
    public Bitmap getBitmapMain(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitm = BitmapFactory.decodeFile(path,options);
        options.inSampleSize = 10;
        options.inJustDecodeBounds = false;
        Bitmap bitmap1 = BitmapFactory.decodeFile(path,options);
        return bitmap1;
    }
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }
}
