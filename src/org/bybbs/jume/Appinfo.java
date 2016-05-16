package org.bybbs.jume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class Appinfo extends Activity {

	private ListView listView;

	private ListViewAdapter listViewAdapter;
	private List<Map<String, Object>> listItems;

	private int posid;

	private load_info l_t;

	// private List<String> data = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		listView = (ListView) findViewById(R.id.listviewListView1);
		listItems = new ArrayList<Map<String, Object>>();
		listViewAdapter = new ListViewAdapter(this, listItems); // 创建适配器
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {

				Toast.makeText(
						Appinfo.this,
						"已经复制:"
								+ listItems.get(position).get("title")
										.toString() + "的UID",
						Toast.LENGTH_SHORT).show();
				if (android.os.Build.VERSION.SDK_INT > 11) {
					android.content.ClipboardManager c = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					c.setText(listItems.get(position).get("uid").toString());
				} else {
					android.text.ClipboardManager c = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					c.setText(listItems.get(position).get("uid").toString());
				}
				/*
				 * ClipboardManager clipboardManager =
				 * (ClipboardManager)getSystemService
				 * (Context.CLIPBOARD_SERVICE);
				 * clipboardManager.setPrimaryClip(ClipData.newPlainText(null,
				 * )); if (clipboardManager.hasPrimaryClip()) {
				 * clipboardManager.getPrimaryClip().getItemAt(0).getText(); }
				 */

			}

		});

		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.add(0, 0, 0, "复制应用名");
				menu.add(0, 1, 0, "复制应用包名");
				menu.add(0, 2, 0, "复制应用UID");
				AdapterContextMenuInfo menuinfo2 = (AdapterContextMenuInfo) menuInfo;

				posid = menuinfo2.position;// 这个就是要获得的item的position

			}
		});
		l_t = new load_info();
		l_t.start();
	}

	private class load_info extends Thread {
		public void run() {
			LoadListItems();
		}
	}

	// 长按菜单响应函数
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public boolean onContextItemSelected(MenuItem item) {

		// AdapterView.AdapterContextMenuInfo info =
		// (AdapterView.AdapterContextMenuInfo) item
		// .getMenuInfo();
		// int MID = (int) info.id;// 这里的info.id对应的就是数据库中_id的值

		switch (item.getItemId()) {
		case 0:
			// 应用名
			Toast.makeText(
					Appinfo.this,
					"复制了:" + listItems.get(posid).get("title").toString()
							+ "的应用名", Toast.LENGTH_SHORT).show();
			if (android.os.Build.VERSION.SDK_INT > 11) {
				android.content.ClipboardManager c = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				c.setText(listItems.get(posid).get("title").toString());
			} else {
				android.text.ClipboardManager c = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				c.setText(listItems.get(posid).get("title").toString());
			}

			break;

		case 1:
			// 应用包名

			Toast.makeText(
					Appinfo.this,
					"复制了:" + listItems.get(posid).get("title").toString()
							+ "的包名", Toast.LENGTH_SHORT).show();
			if (android.os.Build.VERSION.SDK_INT > 11) {
				android.content.ClipboardManager c = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				c.setText(listItems.get(posid).get("info").toString());
			} else {
				android.text.ClipboardManager c = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				c.setText(listItems.get(posid).get("info").toString());
			}

			break;

		case 2:
			// 应用UID
			Toast.makeText(
					Appinfo.this,
					"复制了:" + listItems.get(posid).get("title").toString()
							+ "的UID", Toast.LENGTH_SHORT).show();
			if (android.os.Build.VERSION.SDK_INT > 11) {
				android.content.ClipboardManager c = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				c.setText(listItems.get(posid).get("uid").toString());
			} else {
				android.text.ClipboardManager c = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				c.setText(listItems.get(posid).get("uid").toString());
			}

			/*
			 * clipboardManager =
			 * (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
			 * clipboardManager.setPrimaryClip(ClipData.newPlainText(null,
			 * listItems.get(posid).get("uid").toString())); if
			 * (clipboardManager.hasPrimaryClip()) {
			 * clipboardManager.getPrimaryClip().getItemAt(0).getText(); }
			 */
			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);

	}

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				listItems.add(map);
				listViewAdapter.notifyDataSetChanged();

				break;
			default:
				super.handleMessage(msg);
			}
		}
	};

	/**
	 * 初始化
	 */
	private void LoadListItems() {
		// List<Map<String, Object>> listItems = new ArrayList<Map<String,
		// Object>>();
		List<PackageInfo> packages = getPackageManager()
				.getInstalledPackages(0);
		int j = 0;
		PackageInfo packageInfo;
		Map<String, Object> map;
		Message r1_Message;
		for (int i = 0; i < packages.size(); i++) {
			j++;
			if (j > 20) {
				j = 0;
				System.gc();
			}
			packageInfo = packages.get(i);
			map = new HashMap<String, Object>();
			map.put("title",
					packageInfo.applicationInfo.loadLabel(getPackageManager())
							.toString().trim());
			map.put("uid", packageInfo.applicationInfo.uid);
			map.put("info", packageInfo.packageName.trim());
			map.put("img",
					packageInfo.applicationInfo.loadIcon(getPackageManager()));
			r1_Message = new Message();
			r1_Message.what = 0;
			r1_Message.obj = map;
			handler.sendMessage(r1_Message);

		}
	}

}
