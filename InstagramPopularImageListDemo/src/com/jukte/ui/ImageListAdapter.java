package com.jukte.ui;

import java.util.List;

import com.applidium.shutterbug.FetchableImageView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageListAdapter extends ArrayAdapter<String>{

	private final Activity context;
	private List<String> items;
	
	static class ViewHolder {
	     public FetchableImageView thumbImage;
	  }
	
	public ImageListAdapter(Activity context,
			int textViewResourceId, List<String> objects) {
		super(context,  textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		items=objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View rowView = convertView;
	    if (rowView == null) {
	      LayoutInflater inflater = context.getLayoutInflater();
	      rowView = inflater.inflate(R.layout.row_image_list, null);
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.thumbImage=(FetchableImageView)rowView.findViewById(R.id.rowimage);
	      rowView.setTag(viewHolder);
	    }

	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    holder.thumbImage.setImage(items.get(position));
		
	    return rowView;
	  }

	
	
	}

	


