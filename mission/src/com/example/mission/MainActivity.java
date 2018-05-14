package com.example.mission;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String DB_NAME = "dblogin.db";
	public static final String TABLE_NAME = "loginDB";
	public String PATH;
	public String PACKAGE_NAME;
	ListView lv_list;
	ListViewAdapter listViewAdapter;
	ArrayList<String> dataset;
	SQLiteDatabase db;

	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//PATH =  context.getDatabasePath(DB_NAME).getPath();
		context = getApplicationContext();
		dataset = new ArrayList<String>();
		lv_list = (ListView)findViewById(R.id.lv_list);
		listViewAdapter = new ListViewAdapter(this, dataset);
		lv_list.setAdapter(listViewAdapter);



		dataset.add("ENGINE");
		dataset.add("AT");
		dataset.add("AIRBAG");
		dataset.add("AUTO");
		

		//initialize_db(context);
		//db = SQLiteDatabase.openDatabase(PATH+DB_NAME, null,SQLiteDatabase.OPEN_READWRITE );
		//JoinID("1234","1234");
		//Logint("1234","1234");


		SharedPreferences pref = context.getSharedPreferences("test",Context.MODE_PRIVATE);
		int count = dataset.size();
		dataset.clear();
		for(int i=0;i<count;i++) {
			dataset.add(i, pref.getString("key"+i,""));
		}
		if(dataset.get(0).equals("")) {
			Log.d("test","test2");
			dataset.clear();
			dataset.add("ENGINE");
			dataset.add("AT");
			dataset.add("AIRBAG");
			dataset.add("AUTO");
			Log.d("test","test"+dataset);
		}
		listViewAdapter.notifyDataSetChanged();
		
		getOrderNum();


	}


	public void JoinID(String id, String password) {
		db.execSQL("INSERT INTO " + TABLE_NAME 
				+ " (id, password)  Values ('" + id + "', '" + password+"');");
	}

	public void Logint(String id, String password) {
		Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE id = '"+id+"'AND password = '"+password+"'", null);
		c.moveToNext();
		String id2 = c.getString(c.getColumnIndex("id"));
		if (!id2.isEmpty())
			Toast.makeText(context,id2, Toast.LENGTH_LONG).show();
	}



	public void initialize_db(Context context){
		File folder = new File(PATH);
		folder.mkdirs();
		File outfile = new File(PATH+DB_NAME);

		if (outfile.length() <= 0) {
			AssetManager assetManager = context.getResources().getAssets();
			try {
				InputStream is = assetManager.open(DB_NAME, AssetManager.ACCESS_BUFFER);
				long filesize = is.available();
				byte[] tempdata = new byte[(int) filesize];
				is.read(tempdata);
				is.close();
				outfile.createNewFile();
				FileOutputStream fo = new FileOutputStream(outfile);
				fo.write(tempdata);
				fo.close();
				Log.d("DB","성공");
				Toast.makeText(context, "hihi", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("DB","실패");
				Toast.makeText(context, "hihizzzz", Toast.LENGTH_LONG).show();
			}
		}

	}

	public void swap(int position_1, int position_2){
		String tmpString = dataset.get(position_1);
		dataset.set(position_1, dataset.get(position_2));
		dataset.set(position_2,tmpString);
		listViewAdapter.notifyDataSetChanged();
		SharedPreferences setting = getSharedPreferences("test", 0);
		SharedPreferences.Editor edit = setting.edit();
		for(int i=0;i<dataset.size();i++) {
			edit.putString("key"+i, dataset.get(i));
		}
		edit.commit();
	}


	public void getOrderNum() {
		String[] ecucodes = {"", "AT", "AIRBAG", "AUTO"};
		ArrayList<String> tmpEcuCodes;
		tmpEcuCodes = new ArrayList<String>();

		SharedPreferences pref = context.getSharedPreferences("test",Context.MODE_PRIVATE);
		int count = dataset.size();
		dataset.clear();
		for(int i=0;i<count;i++) {
			dataset.add(i, pref.getString("key"+i,""));
		}

		for(int i =0;i<dataset.size();i++) {
			for(int j=0;j<ecucodes.length;j++) {
				if(dataset.get(i).equals(ecucodes[j])) {
					tmpEcuCodes.add(ecucodes[j]);
					break;
				}
			}
		}
		Log.d("test","test"+tmpEcuCodes);
	}
}



