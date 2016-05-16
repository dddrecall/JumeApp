package org.bybbs.jume;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity {

	EditTextPreference qj_proxy, qj_uid, qj_gx, tcp_lport, tcp_p_ip, dns_http,
			dns_pip, dns_lport, zd_fxtcp, zd_fxudp, zd_dns_ip, zd_wk, zd_sdk,
			dy_tcp_p, dy_udp_p, qt_fx_port,
			jPath
			;
	SharedPreferences sp;
	PreferenceScreen ks1;
	private AlertDialog ad;
	public static PreferenceCategory k1, k2, k3, k4, k5, k6, k7;

	/*
	 * CheckBoxPreference qj_zl ,m_tcp,m_dns ,zd_dns,zd_https,zd_udp
	 * ,dy_gx,dy_dns,dy_https,dy_udp ,qt_hy,qt_qqsp ;
	 */
	ListPreference qj_gz, qj_bj, tcp_m, dns_m;

	Preference cp_uid, Run, ftStatus, zd_fxuid, zd_buid, zd_quid, zd_juid,
			zd_dfhttp, zd_dfudp, qt_qquid,qt_hyuid;

	SuperUser su;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.ftsetting);
		View_init();
		dataInit();
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void View_init() {

		// Run = (Preference) findPreference("isRunning");
		if (android.os.Build.VERSION.SDK_INT > 13) {
			Run = (SwitchPreference) findPreference("isRunning");

		} else {
			Run = (CheckBoxPreference) findPreference("isRunning");
		}

		// EditTextPreference
		// 全局IP
		qj_proxy = (EditTextPreference) findPreference("qj_proxy");
		// 全放行UID
		qj_uid = (EditTextPreference) findPreference("qj_uid");
		// qj_gx=(EditTextPreference)findPreference("qj_gx");
		tcp_p_ip = (EditTextPreference) findPreference("tcp_p_ip");
		// tcp模块监听端口
		tcp_lport = (EditTextPreference) findPreference("tcp_lport");
		// dnsp http地址
		dns_http = (EditTextPreference) findPreference("dns_http");
		dns_pip = (EditTextPreference) findPreference("dns_pip");
		// dns监听端口
		dns_lport = (EditTextPreference) findPreference("dns_lport");

		// 放行UID
		zd_fxuid = findPreference("zd_fxuid");

		// 半放行
		zd_buid = findPreference("zd_buid");
		// 全放行
		// zd_quid = (EditTextPreference)findPreference("qj_uid");
		// 禁止uid
		zd_juid = findPreference("zd_juid");
		// 单方https
		zd_dfhttp = findPreference("zd_dfhttps");
		// 单放dudp
		zd_dfudp = findPreference("zd_dfudp");
		// dns proxy ip
		zd_dns_ip = (EditTextPreference) findPreference("zd_dnsip");

		zd_sdk = (EditTextPreference) findPreference("zd_sdk");

		zd_fxtcp = (EditTextPreference) findPreference("zd_fxtcp");
		zd_fxudp = (EditTextPreference) findPreference("zd_fxudp");
		// tcp端口放行
		dy_tcp_p = (EditTextPreference) findPreference("dy_tcp_p");
		// udp端口放行
		dy_udp_p = (EditTextPreference) findPreference("dy_udp_p");
		
		jPath = (EditTextPreference) findPreference("qt_jume_path");
		// qquid
		qt_qquid = findPreference("qt_qquid");
		// hyuid
		qt_hyuid = findPreference("qt_hyuid");

		// List
		qj_bj = (ListPreference) findPreference("qj_bj");
		// 共享规则
		qj_gz = (ListPreference) findPreference("qj_gx");
		// tcp模块
		tcp_m = (ListPreference) findPreference("l_tcp_m");
		// dns模块
		dns_m = (ListPreference) findPreference("l_dns_m");

		// key

		k1 = (PreferenceCategory) findPreference("key1");
		k2 = (PreferenceCategory) findPreference("key2");
		k3 = (PreferenceCategory) findPreference("key3");
		k4 = (PreferenceCategory) findPreference("key4");
		k5 = (PreferenceCategory) findPreference("key5");
		k6 = (PreferenceCategory) findPreference("key6");
		k7 = (PreferenceCategory) findPreference("key7");
		ftStatus = findPreference("ft_status");
		cp_uid = findPreference("cpy_uid");
		set_Text_ListText(qj_bj, "规则", "A");
		set_Text_ListText(qj_gz, "规则", "A");
		set_Text_ListText(tcp_m, "启用模块:", "u2nl");
		set_Text_ListText(dns_m, "启用模块:", "dnsp");

		sp = PreferenceManager.getDefaultSharedPreferences(this);


		set_Text_0(qj_proxy, "127.0.0.1:8787");
		set_Text_0(qj_uid, "3004");
		set_Text_0(tcp_lport, "1256");
		set_Text_0(dns_http, "http://jumedns.aiyun.co");
		set_Text_0(dns_pip, "114.114.114.114");
		set_Text_0(dns_lport, "54321");
		set_Text_0(jPath, "/system/xbin/JumeApp");
		// set_Text_0(zd_quid, "3004");

		set_Text_1(zd_fxtcp, "本机放行TCP端口");
		set_Text_1(zd_fxudp, "本机放行UDP端口");

		set_Text_3(zd_fxuid, "无UID");
		set_Text_3(zd_buid, "无UID");
		// Log.e("HaP","1");
		set_Text_3(zd_juid, "1000");
		// Log.e("HaP","2");
		set_Text_3(zd_dfhttp, "无UID");
		// Log.e("HaP","3");
		set_Text_3(zd_dfudp, "无UID");
		// Log.e("HaP","4");
		set_Text_1(zd_dns_ip, "无DNS代理IP，则为默认DNS");
		// Log.e("HaP","5");
		set_Text_3(qt_qquid, "QQ的UID");
		// Log.e("HaP","6");
		set_Text_3(qt_hyuid, "虎牙，YY的UID");

		set_Text_1(zd_sdk, "不经过模块处理的端口");
		set_Text_1(dy_tcp_p, "TCP端口");
		set_Text_1(dy_udp_p, "UDP端口");

		EditListener el = new EditListener();

		Run.setOnPreferenceChangeListener(el);
		qj_proxy.setOnPreferenceChangeListener(el);
		qj_uid.setOnPreferenceChangeListener(el);

		tcp_p_ip.setOnPreferenceChangeListener(el);
		tcp_lport.setOnPreferenceChangeListener(el);
		dns_http.setOnPreferenceChangeListener(el);
		dns_pip.setOnPreferenceChangeListener(el);
		dns_lport.setOnPreferenceChangeListener(el);
		zd_fxuid.setOnPreferenceChangeListener(el);
		// zd_buid.setOnPreferenceChangeListener(el);
		// zd_juid.setOnPreferenceChangeListener(el);
		// zd_dfhttp.setOnPreferenceChangeListener(el);
		// zd_dfudp.setOnPreferenceChangeListener(el);
		zd_dns_ip.setOnPreferenceChangeListener(el);

		zd_sdk.setOnPreferenceChangeListener(el);
		zd_fxtcp.setOnPreferenceChangeListener(el);
		zd_fxudp.setOnPreferenceChangeListener(el);

		dy_tcp_p.setOnPreferenceChangeListener(el);
		dy_udp_p.setOnPreferenceChangeListener(el);
		
		jPath.setOnPreferenceChangeListener(el);
		//qt_qquid.setOnPreferenceChangeListener(el);
		//qt_hyuid.setOnPreferenceChangeListener(el);

		ListListener ll = new ListListener();
		qj_bj.setOnPreferenceChangeListener(ll);
		qj_gz.setOnPreferenceChangeListener(ll);
		tcp_m.setOnPreferenceChangeListener(ll);
		dns_m.setOnPreferenceChangeListener(ll);

		
		
		ClickListener cl = new ClickListener();
		cp_uid.setOnPreferenceClickListener(cl);
		ftStatus.setOnPreferenceClickListener(cl);
		
		zd_fxuid.setOnPreferenceClickListener(cl);
		zd_buid.setOnPreferenceClickListener(cl);
		zd_juid.setOnPreferenceClickListener(cl);
		zd_dfhttp.setOnPreferenceClickListener(cl);
		zd_dfudp.setOnPreferenceClickListener(cl);
		qt_qquid.setOnPreferenceClickListener(cl);
		qt_hyuid.setOnPreferenceClickListener(cl);

		if (sp.getBoolean("isRunning", false)) {
			Enabled(false);
		}
		// highlight.jniparse("","");

	}

	void dataInit() {
		su = new SuperUser(this);


		if (!sp.getBoolean("fix_" + SuperUser.version, false)) {
			ad = SuperUser.show_newfix(this,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							try {
								Field field = ad.getClass().getSuperclass()
										.getDeclaredField("mShowing");
								field.setAccessible(true);
								field.set(ad, true);
							} catch (Exception e) {
								e.printStackTrace();
							}
							checkRI();
							sp.edit()
									.putBoolean("fix_" + SuperUser.version,
											true).commit();

						}

					});

			try {
				Field field = ad.getClass().getSuperclass()
						.getDeclaredField("mShowing");
				field.setAccessible(true);
				field.set(ad, false);

			} catch (Exception e) {
			}
		} else
			checkRI();

	}

	void Enabled(boolean flag) {
		k1.setEnabled(flag);
		k2.setEnabled(flag);
		k3.setEnabled(flag);
		k4.setEnabled(flag);
		k5.setEnabled(flag);
		k6.setEnabled(flag);
		k7.setEnabled(flag);
	}

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(MainActivity.this, msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;

			case 5:
				noBusybox();
				break;
			case 10:
				break;

			case 11:
				break;
			default:
				super.handleMessage(msg);
			}
		}
	};

	private void checkRI() {
		if (!sp.getBoolean("checkRoot", false)
				|| !sp.getBoolean(SuperUser.version, false)) {
			ad = new AlertDialog.Builder(MainActivity.this)
					.setTitle("检测ROOT的权限")
					.setMessage("点击检测按钮，检测ROOT并安装终极防跳")
					.setIcon(R.drawable.ic_dialog_info)
					.setPositiveButton("检测ROOT",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									boolean is_root = SuperUser.checkRoot();
									sp.edit().putBoolean("isRoot", is_root)
											.commit();
									sp.edit().putBoolean("checkRoot", is_root)
											.commit();

									if (!sp.getBoolean(SuperUser.version, false)
											&& is_root) {
										try {
											Field field = ad
													.getClass()
													.getSuperclass()
													.getDeclaredField(
															"mShowing");
											field.setAccessible(true);
											field.set(ad, true);
										} catch (Exception e) {
											e.printStackTrace();
										}

										Install it = new Install(
												MainActivity.this, sp, su,
												handler, su.getPrivFilePath());
										it.start();
										Toast.makeText(MainActivity.this,
												"检测成功，正在解压资源....",
												Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(MainActivity.this,
												"检测失败，请ROOT后再打开再检测 !",
												Toast.LENGTH_LONG).show();

									}

									if (sp.getBoolean("checkBusybox", false)
											&& !sp.getBoolean("isBusybox",
													false)) {
										if (!SuperUser.checkBusybox())
											noBusybox();
									}

								}

							})
					.setNegativeButton("退出软件",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									try {
										Field field = ad.getClass()
												.getSuperclass()
												.getDeclaredField("mShowing");
										field.setAccessible(true);
										field.set(ad, true);
									} catch (Exception e) {
										e.printStackTrace();
									}
									MainActivity.this.finish();

									System.exit(0);
								}

							}).show();
			// System.out.println(SuperUser.rwStr());
			try {
				Field field = ad.getClass().getSuperclass()
						.getDeclaredField("mShowing");
				field.setAccessible(true);
				field.set(ad, false);

			} catch (Exception e) {
			}

		} else {
			SuperUser.is_root = true;
			if (sp.getBoolean("checkBusybox", false)
					&& !sp.getBoolean("isBusybox", false)) {
				if (!SuperUser.checkBusybox())
					noBusybox();
			}
		}
	}

	private void noBusybox() {
		ad = new AlertDialog.Builder(MainActivity.this)
				.setTitle("需要Busybox")
				.setMessage("检测系统并没有安装Buysbox，需要安装了BusyBox才能继续使用！")
				.setIcon(R.drawable.ic_dialog_info)
				.setPositiveButton("重新检测",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								boolean isBusybox = SuperUser.checkBusybox();
								if (isBusybox) {
									// 关闭对话框
									try {
										Field field = ad.getClass()
												.getSuperclass()
												.getDeclaredField("mShowing");
										field.setAccessible(true);
										field.set(ad, true);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								sp.edit().putBoolean("isBusybox", isBusybox)
										.commit();

							}

						})
				.setNegativeButton("退出软件",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// 关闭对话框
								try {
									Field field = ad.getClass().getSuperclass()
											.getDeclaredField("mShowing");
									field.setAccessible(true);
									field.set(ad, true);
								} catch (Exception e) {
									e.printStackTrace();
								}
								finish();
								System.exit(0);

							}

						}).show();
		try {
			Field field = ad.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(ad, false);

		} catch (Exception e) {
		}

	}


	class ClickListener implements OnPreferenceClickListener {
		Intent i;

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub

			switch (preference.getKey()) {
			case "cpy_uid":
				i = new Intent(MainActivity.this, Appinfo.class);
				startActivityForResult(i, 1);
				break;
			case "zd_fxuid":
			case "zd_buid":
			case "zd_juid":
			case "zd_dfhttps":
			case "zd_dfudp":
			case "qt_qquid":
			case "qt_hyuid":
				i = new Intent(MainActivity.this, AppManager.class);
				i.putExtra("Key", preference.getKey());
				i.putExtra("title", "设置:" + preference.getTitle());
				startActivityForResult(i, 1);
				break;
			case "ft_status":
				DShell dl = new DShell(MainActivity.this, "", handler, 2);
				dl.execute("FtStatus", "/data/data/com.HaP.Byml/mode");
				break;
			}
			return true;
		}

	}

	class EditListener implements OnPreferenceChangeListener {
		long currentTime = 0;

		@Override
		public boolean onPreferenceChange(Preference p1, Object p2) {
			// TODO: Implement this method

			switch (p1.getKey()) {
			case "isRunning":
				// System.out.println("IsRunning:" + p2);
				if (System.currentTimeMillis() - currentTime > 1000) {

					Enabled(!(boolean) p2);
					if ((boolean) p2) {
						DShell dl = new DShell(MainActivity.this, "", handler,
								2);
						dl.execute("FtStart", "防跳开启");
					} else {
						DShell dl = new DShell(MainActivity.this, "", handler,
								2);
						dl.execute("FtStop", "防跳关闭");
					}
				}
				currentTime = System.currentTimeMillis();
				break;

			case "qj_proxy":
				return set_Text_2(qj_proxy, "127.0.0.1:8787", p2.toString(),
						true);

			case "qj_uid":
				return set_Text_2(qj_uid, "3004", p2.toString(), true);

			case "tcp_p_ip":
				return set_Text_2(tcp_p_ip, "10.0.0.172:80", p2.toString(),
						true);

			case "tcp_lport":
				return set_Text_2(tcp_lport, "1314", p2.toString(), true);

			case "dns_http":
				return set_Text_2(dns_http,
						"http://dns.sturgeon.mopaas.com/nslookup.php",
						p2.toString(), true);
			case "dns_pip":
				return set_Text_2(dns_pip, "114.114.114.114", p2.toString(),
						true);
			case "dns_lport":
				return set_Text_2(dns_lport, "54321", p2.toString(), true);

			case "zd_dnsip":
				return set_Text_2(zd_dns_ip, "无DNS代理IP，则为默认DNS", p2.toString(),
						false);

			case "zd_sdk":
				return set_Text_2(zd_sdk, "不经过模块处理的端口", p2.toString(), false);
			case "zd_fxtcp":
				return set_Text_2(zd_fxtcp, "本机放行TCP端口", p2.toString(), false);
			case "zd_fxudp":
				return set_Text_2(zd_fxudp, "本机放行UDP端口", p2.toString(), false);

			case "dy_tcp_p":
				return set_Text_2(dy_tcp_p, "TCP端口", p2.toString(), false);
			case "dy_udp_p":
				return set_Text_2(dy_udp_p, "UDP端口", p2.toString(), false);
			case "qt_jume_path":
				return set_Text_2(jPath,"/system/xbin",p2.toString(),true);
			}
			return true;
		}

	}

	class ListListener implements Preference.OnPreferenceChangeListener {

		@Override
		public boolean onPreferenceChange(Preference p1, Object p2) {
			// TODO: Implement this method
			switch (p1.getKey()) {
			case "qj_bj":
				set_Text_ListText_change(qj_bj, p2.toString(), "规则");
				break;
			case "qj_gx":
				set_Text_ListText_change(qj_gz, p2.toString(), "规则");

				break;
			case "l_tcp_m":
				set_Text_ListText_change(tcp_m, p2.toString(), "启用模块:");

				break;
			case "l_dns_m":
				set_Text_ListText_change(dns_m, p2.toString(), "启用模块:");
				break;
		

			}

			return true;
		}

	}

	void set_Text_0(EditTextPreference e, String sdefault) {
		String tmp = e.getText();
		if (tmp == null || tmp.length() < 1) {
			e.setText(sdefault);
			tmp = sdefault;
		}
		e.setSummary(tmp);
	}

	void set_Text_1(EditTextPreference e, String sdefault) {
		String tmp = e.getText();
		if (tmp == null || tmp.length() < 1) {
			// e.setText(sdefault);
			tmp = sdefault;
		}
		e.setSummary(tmp);
	}

	void set_Text_1(Preference e, String sdefault) {
		String tmp = sp.getString(e.getKey(), "");
		if (tmp == null || tmp.length() < 1) {
			// e.setText(sdefault);
			tmp = sdefault;
		}
		e.setSummary(tmp);
	}

	// Edit OnChangelistener
	boolean set_Text_2(EditTextPreference e, String sdefault, String Value,
			boolean istext) {

		if (Value == null || Value.length() < 1) {
			if (!istext) {
				e.setSummary(sdefault);
				return true;
			}
			e.setText(sdefault);
			e.setSummary(sdefault);
			return false;
		}
		e.setSummary(Value);
		return true;
	}

	void set_Text_3(Preference e, String sdefault) {
		String Value = sp.getString(e.getKey() + "_diyUid", "") + " "
				+ sp.getString(e.getKey(), "");
		System.out.println(Value);
		if (Value == null || Value.length() <= 1) {
			e.setSummary(sdefault);
		} else {
			e.setSummary(Value);
		}

	}

	// List set Text
	void set_Text_ListText(ListPreference l, String sq, String sdefault) {
		String tmp = l.getValue();
		if (tmp == null || tmp.length() < 1) {
			tmp = sdefault;
			l.setValue(tmp);
		}
		l.setSummary(sq + tmp);
	}

	void set_Text_ListText_change(ListPreference l, String value, String sq) {
		l.setSummary(sq + value);
	}

	// 点击Menu出现的菜单
	public boolean onCreateOptionsMenu(Menu m) {
		m.add(0, 0, 0, "自启");
		m.add(0, 1, 0, "脚本");
		m.add(0, 2, 0, "关于");
		m.add(0, 3, 0, "退出");
		return true;
	}

	// 菜单点击事件
	public boolean onOptionsItemSelected(MenuItem menu) {
		Intent i = new Intent();
		switch (menu.getItemId()) {
		case 0:
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("是否开机自启？")
					.setMessage(
							"开启后，开机后将会自启终极防跳\n\n当前自启状态:"
									+ (sp.getBoolean("onStart", false) ? "开启中..."
											: "关闭中..."))
					.setIcon(R.drawable.ic_dialog_info)
					.setPositiveButton("开启",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									sp.edit().putBoolean("onStart", true)
											.commit();
									msg("开启了开机自启...");
								}

							})
					.setNeutralButton("关闭",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									sp.edit().putBoolean("onStart", false)
											.commit();
									msg("关闭了开机自启...");

								}

							}).show();
			break;
		case 1:
			i = new Intent(MainActivity.this, Edit_mode.class);
			startActivity(i);
			break;
		case 2:

			new AlertDialog.Builder(MainActivity.this)
					.setTitle("终极防跳v" + SuperUser.version)
					.setMessage(
							"防跳内核:终极防跳v7.0终结版\n防跳作者:Jume\n软件作者:淡淡的回忆\n\n八云出品，禁止盗版！\n官网: http://byml.net和bybbs.org")
					.setIcon(R.drawable.ic_dialog_info)
					.setPositiveButton("确定", null).show();
			break;
		case 3:
			finish();
			System.exit(0);
			break;
		}
		return true;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (resultCode) {
		case 1:
			set_Text_3(zd_fxuid, "无UID");
			break;
		case 2:
			set_Text_3(zd_buid, "无UID");
			break;
		case 3:
			set_Text_3(zd_juid, "无UID");
			break;
		case 4:
			set_Text_3(zd_dfhttp, "无UID");
			break;
		case 5:
			set_Text_3(zd_dfudp, "无UID");
			break;
		case 6:
			set_Text_3(qt_qquid, "无UID");
			break;
		case 7:
			set_Text_3(qt_hyuid, "无UID");
			break;

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void msg(String msg) {
		Message m = new Message();
		m.what = 0;
		m.obj = msg;
		handler.sendMessage(m);
	}

	long current = 0;

	public void onBackPressed() {

		if (System.currentTimeMillis() - current > 2000) {
			current = System.currentTimeMillis();
			Toast.makeText(this, "再点一次退出 ^_^ ...", Toast.LENGTH_SHORT).show();
		} else {

			finish();
			System.exit(0);
		}
	}

}
