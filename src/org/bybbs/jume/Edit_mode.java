package org.bybbs.jume;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit_mode extends Activity {

	private ViewPagerIndicatorView viewPagerIndicatorView;

	private View v1, v2, v3;

	private Button save;// mr, rename;

	private EditText e1, e2, e3;

	private b_listener bl;

	private Intent in;

	private SharedPreferences sp;

	private String s1 = "", s2 = "", s3 = "";

	private SuperUser Su;

	@SuppressLint("InflateParams")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_mode);

		this.viewPagerIndicatorView = (ViewPagerIndicatorView) findViewById(R.id.edit_viewpager_indicator_view);
		final Map<String, View> map = new HashMap<String, View>();
		v1 = LayoutInflater.from(this).inflate(R.layout.edit_1, null);
		v2 = LayoutInflater.from(this).inflate(R.layout.edit_1, null);
		v3 = LayoutInflater.from(this).inflate(R.layout.edit_1, null);
		map.put("关闭", v1);
		map.put("开启", v2);
		map.put("状态", v3);
		this.viewPagerIndicatorView.onPageSelected(1);
		this.viewPagerIndicatorView.setupLayout(map);
		view_init();
		data_init();

	}

	private void data_init() {
		this.in = getIntent();
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		Su = new SuperUser(this);
		// mTextWatcher =;
		e1.addTextChangedListener(new mTextWatcher());
		e2.addTextChangedListener(new mTextWatcher());
		e3.addTextChangedListener(new mTextWatcher());

		in.getIntExtra("i_n", 0);
		in.getStringExtra("m_name");
		in.getStringExtra("file_path");

		s1 = SuperUser.readFile(Su.getPrivFilePath() + "/Jume_stop.sh");
		s2 = SuperUser.readFile(Su.getPrivFilePath() + "/Jume_start.sh");
		s3 = SuperUser.readFile(Su.getPrivFilePath() + "/Jume_status.sh");
		
		
		e1.setText(s1);
		e2.setText(s2);
		e3.setText(s3);

	}

	private void view_init() {
		save = (Button) this.findViewById(R.id.e_bsave);
		// mr = (Button) this.findViewById(R.id.e_bmr);
		// rename = (Button) this.findViewById(R.id.e_brename);
		bl = new b_listener();
		save.setOnClickListener(bl);
		// mr.setOnClickListener(bl);
		// rename.setOnClickListener(bl);

		e1 = (EditText) v1.findViewById(R.id.e_e1);
		e2 = (EditText) v2.findViewById(R.id.e_e1);
		e3 = (EditText) v3.findViewById(R.id.e_e1);

	}

	private class b_listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO: Implement this method
			switch (v.getId()) {
			case R.id.e_bsave:

				SuperUser.deleteFile(Su.getPrivFilePath() + "/Jume_stop.sh");
				SuperUser.deleteFile(Su.getPrivFilePath() + "/Jume_start.sh");
				SuperUser.deleteFile(Su.getPrivFilePath() + "/Jume_status.sh");

				SuperUser.writeFile(e1.getText().toString(),
						Su.getPrivFilePath() + "/Jume_stop.sh");
				SuperUser.writeFile(e2.getText().toString(),
						Su.getPrivFilePath() + "/Jume_start.sh");
				SuperUser.writeFile(e3.getText().toString(),
						Su.getPrivFilePath() + "/Jume_status.sh");

				DShell ds = new DShell(Edit_mode.this,
								  "chmod 0777 "+Su.getPrivFilePath() + "/Jume_stop.sh\n"
								+ "chmod 0777 "+Su.getPrivFilePath() + "/Jume_start.sh\n"
								+ "chmod 0777 "+Su.getPrivFilePath() + "/Jume_status.sh\n"
								+ "echo '保存成功！'\n", handler, 1);
				ds.execute("sh", "保存");
				sp.edit().putBoolean("isData", true).commit();
				finish();
				break;
			// case R.id.e_bmr:
			// new AlertDialog.Builder(Edit_mode.this)
			// .setTitle("是否使用默认模板？")
			// .setMessage(
			// "使用默认模板会清空当前模式所有内容！\n并把透明代理的模式内容复制到当前3种请求当中!")
			// .setIcon(android.R.drawable.ic_dialog_info)
			// .setPositiveButton("确定",
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface p1,
			// int p2) {
			// // TODO: Implement this method
			// e1.setText(SuperUser.readFile(file_path
			// + "/mode/connection.hp"));
			// e2.setText(SuperUser.readFile(file_path
			// + "/mode/get.hp"));
			// e3.setText(SuperUser.readFile(file_path
			// + "/mode/post.hp"));
			//
			// }
			// }).setNegativeButton("返回", null).show();
			//
			// break;
			}
		}
	}

	private class mTextWatcher implements TextWatcher {
		highlight h;

		public mTextWatcher() {
			h = new highlight(Su.getPrivFilePath() + "/sh.conf");
			h.redraw();
		}

		int flag = 1;

		public tThread t = new tThread();
		private Editable e;
		private int abc = 0, start = 0, end = 0;

		@SuppressLint("HandlerLeak")
		public Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					h.redraw();
					h.highlight(e, start, end);
					flag = 1;
					abc = 0;

				}
			}
		};

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			this.flag = 1;
			if (this.abc == 0) {
				this.start = start;
				if (start == 0)
					this.end = after;
				this.abc = 1;
			}

			// Log.e("before_edit", "start:" + this.start +" end:"+this.end+
			// "count:" + count + "after:" + after);
			if (!t.isAlive()) {
				t.start();
			}

		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			this.flag = 1;
			if (start == 0)
				this.end = count;
			else
				this.end = start + count;
			// if(s.charAt(start)=='\n')
			// Log.e("on_edit", "start:" + start + " before:" + before +
			// " count:" + count);
		}

		public void afterTextChanged(Editable s) {
			flag = 0;
			this.e = s;
		}

		boolean get_start() {
			if (flag == 1)
				return true;
			else
				return false;
		}

		class tThread extends Thread {
			boolean r = true;

			@Override
			public String toString() {
				this.r = false;
				flag = 0;
				// System.out.println("rrrrrr_:"+r);
				// TODO: Implement this method
				return super.toString();
			}

			public void run() {
				while (r) {
					while (get_start()) {
						try {
							// System.out.println("___");
							sleep(100);
						} catch (InterruptedException e) {
						}
					}
					try {
						sleep(900);
					} catch (InterruptedException e) {
					}
					System.gc();
					if (!get_start()) {
						Message msg = new Message();
						msg.what = 0;
						handler.sendMessage(msg);
						try {
							sleep(900);
						} catch (InterruptedException e) {
						}
					}

				}

			}

		}
	}

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(Edit_mode.this, (String) msg.obj,
						Toast.LENGTH_LONG).show();
				break;

			case 1:

				break;
			case 2:
				break;

			default:
				super.handleMessage(msg);
			}
		}
	};

	// 点击Menu出现的菜单
	public boolean onCreateOptionsMenu(Menu m) {
		// m.add(0, 0, 0, "语法帮助");
		// m.add(0, 1, 0, "帮助");
		// m.add(0, 2, 0,
		// "关于");//.setIcon(android.R.drawable.ic_lock_power_off);
		// m.add(0, 3, 0, "优化");
		// m.add(0, 4, 0, "安装");
		// m.add(0, 5, 0, "退出");
		return true;
	}

	// 菜单点击事件
	public boolean onOptionsItemSelected(MenuItem menu) {
		switch (menu.getItemId()) {
		case 0:
			// i = new Intent(Edit_mode.this, Code_help.class);
			// startActivity(i);
			break;
		}
		return true;
	}

	long current = 0;

	public void onBackPressed() {
		new AlertDialog.Builder(Edit_mode.this).setTitle("是否返回首页？")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface p1, int p2) {
						// TODO: Implement this method
						finish();

					}
				}).setNegativeButton("返回", null).show();
	}

}
