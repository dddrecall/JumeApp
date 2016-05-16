package org.bybbs.jume;


import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListViewAdapter extends BaseAdapter {   
	private List<Map<String, Object>> listItems;    //商品信息集合   
	private LayoutInflater listContainer;           //视图容器   
  
	public final class ListItemView{                //自定义控件集合     
		public ImageView image;     
		public TextView title;     
		public TextView info;   
	}     


	public ListViewAdapter(Context context, List<Map<String, Object>> listItems) {   
		listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
		this.listItems = listItems;   
	}   

	public int getCount() {   
		// TODO Auto-generated method stub   
		return listItems.size();   
	}   

	public Object getItem(int arg0) {   
		// TODO Auto-generated method stub   
		return null;   
	}   

	public long getItemId(int arg0) {   
		// TODO Auto-generated method stub   
		return 0;   
	}   

	

	/**  
	 * ListView Item设置  
	 */  
	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {   
		// TODO Auto-generated method stub   
		//Log.e("method", "getView");   
		//自定义视图   
		ListItemView  listItemView = null;   
		if (convertView == null) {   
			listItemView = new ListItemView();    
			//获取list_item布局文件的视图   
			convertView = listContainer.inflate(R.layout.vlist, null);   
			//获取控件对象   
			
			listItemView.image = (ImageView)convertView.findViewById(R.id.img);   
			listItemView.title = (TextView)convertView.findViewById(R.id.title);   
			listItemView.info = (TextView)convertView.findViewById(R.id.info);   
			//设置控件集到convertView   
			convertView.setTag(listItemView);   
		}else {   
			listItemView = (ListItemView)convertView.getTag();   
		}   

		//设置文字和图片 
	//	listItemView.image.setBackground()
		listItemView.image.setImageDrawable((Drawable)listItems.get(position).get("img"));
		listItemView.title.setText((String) listItems.get(position).get("title")+" UID:"+(Integer)listItems.get(position).get("uid"));  
		listItemView.info.setText((String) listItems.get(position).get("info"));   
		
		return convertView;   
	}   
}  
