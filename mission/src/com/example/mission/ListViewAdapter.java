package com.example.mission;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{

	Context context;
	ArrayList<String> dataset;

	public ListViewAdapter(Context context, ArrayList<String> dataset) {
		this.context = context;
		this.dataset = dataset;
	}

	class ViewHolder {
		TextView tv_String;
		Button bt_1;
		Button bt_2;
	}

	@Override
	public int getCount() {
		return dataset.size();
	}

	@Override
	public Object getItem(int position) {
		return dataset.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder= null;
		final int tmpPosition = position;
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_order, null);
			holder = new ViewHolder();
			holder.tv_String=(TextView)convertView.findViewById(R.id.tv_ecucode);
			holder.bt_1 = (Button)convertView.findViewById(R.id.bt_up);
			holder.bt_2 = (Button)convertView.findViewById(R.id.bt_down);
			holder.bt_1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(tmpPosition>0)
						((MainActivity)context).swap(tmpPosition-1, tmpPosition);
				}
			});

			holder.bt_2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(tmpPosition<dataset.size()-1)
						((MainActivity)context).swap(tmpPosition, tmpPosition+1);	
				}
			});
			convertView.setTag(holder);
		}
		else holder = (ViewHolder)convertView.getTag();
		holder.tv_String.setText(dataset.get(position));
		return convertView;
	}




}
