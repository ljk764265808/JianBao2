package us.mifeng.k.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKHttp工具类
 * */
public class OkHttpUtils {
	private Context ctx;
	private static OkHttpClient ok=null;
	private static final MediaType MEDIA_TYPE_PNG=MediaType.parse("image/png");
	private Map<String, String> map=new HashMap<String, String>();//存放
	private List<String> list=new ArrayList<String>();
	private static File file;
	private static String imgpath;
	private static String imageName;
	private OkHttpUtils(){

	};
	/*
	 *单例获取 
	 * */
	public static OkHttpClient getInstance() {
		if (ok == null) {
			synchronized (OkHttpUtils.class) {
				if (ok == null)
					ok = new OkHttpClient();
			}
		}
		return ok;
	}
	/*
	 * 键值对上传数据
	 * */
	public static void UploadSJ(String url,Map<String, String> map,Callback callback){
		Builder builder=new Builder();
		//遍历map中所有的参数到builder
		for (String key : map.keySet()) {
			builder.add(key, map.get(key));
		}
		Request request = new Request.Builder()
				.url(url)
				.post(builder.build())
				.build();
		Call call = getInstance().newCall(request);
		call.enqueue(callback);
	}
	/*
	 * 上传一张图片带参数
	 * */
	public static void UploadFileCS(String key1,String url,String path,Map<String, String> map,Callback callback){
		String imagpath = path.substring(0, path.lastIndexOf("/"));
		String imgName []=path.split("/");
		for(int i=0;i<imgName.length;i++){
			if(i==imgName.length-1){
				String name=imgName[i];
				file = new File(imagpath, name);
			}
		}
		MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
		RequestBody fileBody=RequestBody.create(MEDIA_TYPE_PNG, file);
		//遍历map中所有的参数到builder
		for (String key : map.keySet()) {
			builder.addFormDataPart(key, map.get(key));
		}
		//讲文件添加到builder中
		builder.addFormDataPart(key1,file.getName(),fileBody);
		//创建请求体
		RequestBody requestBody=builder.build();

		Request request=new Request.Builder().url(url).post(requestBody).build();
		Call call = getInstance().newCall(request);
		call.enqueue(callback);
	}

	/*
	 *上传多个图片文件 
	 * */
	@SuppressWarnings("unused")
	public static void UploadFileMore(String url,List<String> paths,Callback callback){
		if(paths!=null){
			//创建文件集合
			List<File> list=new ArrayList<File>();
			//遍历整个图片地址
			for (String str : paths) {
				//截取图片地址：/storage/emulated/0
				imgpath = str.substring(0, str.lastIndexOf("/"));
				//将图片路径分解成String数组
				String [] imgName=str.split("/");
				for (int i = 0; i < imgName.length; i++) {
					if(i==imgName.length-1){
						imageName = imgName[i];//获取图片名称
						File file=new File(imgpath, imageName);
						list.add(file);
					}
				}
			}
			MultipartBody.Builder builder=new MultipartBody.Builder();
			builder.setType(MultipartBody.FORM);//设置表单类型
			//遍历图片文件
			for (File file : list) {
				if(file!=null){
					builder.addFormDataPart("acrd", file.getName(),RequestBody.create(MEDIA_TYPE_PNG, file));
				}
			}
			//构建请求体
			MultipartBody requestBody=builder.build();
			Request request=new Request.Builder().url(url).post(requestBody).build();
			Call call = getInstance().newCall(request);
			call.enqueue(callback);
		}

	}
	/*
	 * 上传多张图片带参数
	 * */
	@SuppressWarnings("unused")
	public static void UploadFileSCMore(String url,List<String> paths,Map<String, String> map,Callback callback){
		if(paths!=null&&map!=null){
			//创建文件集合
			List<File> list=new ArrayList<File>();
			//遍历整个图片地址
			for (String str : paths) {
				//截取图片地址：/storage/emulated/0
				imgpath = str.substring(0, str.lastIndexOf("/"));
				//将图片路径分解成String数组
				String [] imgName=str.split("/");
				for (int i = 0; i < imgName.length; i++) {
					if(i==imgName.length-1){
						imageName = imgName[i];//获取图片名称
						File file=new File(imgpath, imageName);
						list.add(file);
					}
				}
			}
			MultipartBody.Builder builder=new MultipartBody.Builder();
			builder.setType(MultipartBody.FORM);//设置表单类型
			//遍历图片文件
			for (File file : list) {
				if(file!=null){
					builder.addFormDataPart("photo", file.getName(),RequestBody.create(MEDIA_TYPE_PNG, file));
				}
			}
			//遍历map中所有的参数到builder
			for (String key : map.keySet()) {
				builder.addFormDataPart(key, map.get(key));
			}
			RequestBody requestBody=builder.build();

			Request request=new Request.Builder().url(url).post(requestBody).build();
			Call call = getInstance().newCall(request);
			call.enqueue(callback);
		}
	}

	public interface GetEntityCallBack {
		void getEntity(Object obj);
	}

	public static OkHttpUtils.GetEntityCallBack callBack;

	public static void setGetEntityCallBack(OkHttpUtils.GetEntityCallBack callBack1) {
		callBack = callBack1;
	}

	public static Object o;
	private static final String TAG = "MOkHttp";

	public static Object post(final Context context, String path, Map<String, String> map, final Type type) {
		OkHttpClient client = new OkHttpClient();

		FormBody.Builder builder = new FormBody.Builder();
		Set<Map.Entry<String, String>> entries = map.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			builder.add(entry.getKey(), entry.getValue());
		}
		FormBody body = builder.build();
		final Request request = new Request.Builder()
				.url(path)
				.post(body)
				.build();

		Call call = client.newCall(request);

		call.enqueue(new Callback() {

			@Override
			public void onFailure(Call call, IOException e) {
				Toast.makeText(context, "I am so sorry", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.isSuccessful()) {
					String json = response.body().string();
					Log.i(TAG, "onResponse: " + json);
					Gson gson = new Gson();
					o = gson.fromJson(json, type);
					//4.
					callBack.getEntity(o);
				}
			}
		});
		return o;
	}

	public static Object post(final Context context, String path, Map<String, String> map, final Class cla) {
		OkHttpClient client = new OkHttpClient();

		FormBody.Builder builder = new FormBody.Builder();
		Set<Map.Entry<String, String>> entries = map.entrySet();
		for (Map.Entry<String, String> entry : entries
				) {
			builder.add(entry.getKey(), entry.getValue());
		}
		FormBody body = builder.build();

		final Request request = new Request.Builder()
				.url(path)
				.post(body)
				.build();

		Call call = client.newCall(request);

		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Looper.prepare();
				Toast.makeText(context, "I am so sorry", Toast.LENGTH_SHORT).show();
				Log.e(TAG, "onFailure: ",e );
				Looper.loop();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.isSuccessful()) {
					String json = response.body().string();

					Log.i(TAG, "onResponse: " + json);
					Gson gson = new Gson();
					o = gson.fromJson(json, cla);
					//4.
					callBack.getEntity(o);
				}
			}
		});
		return o;
	}
}
