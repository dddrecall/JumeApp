package org.bybbs.jume;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class DShell extends AsyncTask<String, Integer, String> {
	// 0 默认 不显示
	// 1 toast 显示
	// 2 Dloder显示
	// 3 handelr 显示
	private int eMode = 0;
	private Context con;
	private DLoader dloader;
	private String cmd;
	private String pline;
	private Handler handler;
	private SuperUser.ExecShell eShell;
	private SharedPreferences sp;
	private String jPath;

	private PutLine pl = new PutLine() {

		@Override
		public void pLine(String line) {
			// TODO Auto-generated method stub
			synchronized (pline) {
				pline = line;
				publishProgress(1314);
			}
		}

	};

	public DShell(Context con, String cmd, Handler handler, int eMode) {
		this.con = con;
		this.handler = handler;
		this.eMode = eMode;
		this.cmd = cmd;
		if (eMode == 2) {
			dloader = new DLoader(con, "正在处理...", "处理中...", 1, false);
			dloader.show();
		}
		this.sp = con.getSharedPreferences("org.bybbs.jume_preferences",
				Context.MODE_APPEND);
		jPath = sp.getString("qt_jume_path", SuperUser.jPath);

	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	private String execShell(String fileName, String filePath) {
		pline = "\t\t正在启动脚本:" + fileName + "\n";
		if (eMode != 1)
			publishProgress(1314);
		publishProgress(50);
		eShell = new SuperUser.ExecShell(cmd, pl);
		try {
			eShell.start();
			publishProgress(80);
			eShell.join(5000);
		} catch (InterruptedException e) {
		}
		publishProgress(100);

		return null;
	}

	public String ftStart(Context con, String tmpPath) {
		pline = "\t\t正在启动脚本: 防跳开启\n";
		publishProgress(1314);
		publishProgress(20);

		String qjProxy = sp.getString("qj_proxy", "127.0.0.1:8787");
		int iPort = qjProxy.indexOf(":");
		String qIp, qPort;
		if (iPort > 1) {
			qIp = qjProxy.substring(0, iPort);
			qPort = qjProxy.substring(iPort + 1);
		} else {
			qIp = qjProxy;
			qPort = "80";
		}

		String qjUid = sp.getString("qj_uid", "3004");
		String qjZl = tf2of(sp.getBoolean("qj_zl", false));

		// 规则 A/B
		String localGz = sp.getString("qj_bj", "A");
		String gxGz = sp.getString("qj_gx", "A");

		String mTcp = tf2of(sp.getBoolean("m_tcp", true));
		String mDns = tf2of(sp.getBoolean("m_dns", false));

		String mMTcp = sp.getString("l_tcp_m", "u2nl");

		String mTcpIp, mTcpPPort;

		mTcpIp = sp.getString("tcp_p_ip", "10.0.0.172:80");
		iPort = mTcpIp.indexOf(":");

		if (iPort > 0) {
			mTcpPPort = mTcpIp.substring(iPort + 1);
			mTcpIp = mTcpIp.substring(0, iPort);
		} else
			mTcpPPort = "80";

		String mTcpPort = sp.getString("tcp_lport", "1256");

		String mMDns = sp.getString("l_dns_m", "dnsp");
		String mDnsPip = sp.getString("dns_pip", "114.114.114.114");
		String mDnsHttp = sp.getString("dns_http", "http://jumedns.aiyun.co");
		String mDnsPort = sp.getString("dns_lport", "54321");

		String fxUid = sp.getString("zd_fxuid_diyUid", "") + " "
				+ sp.getString("zd_fxuid", "");

		String bUid = sp.getString("zd_buid_diyUid", "") + " "
				+ sp.getString("zd_buid", "");
		String jUid = sp.getString("zd_juid_diyUid", "") + " "
				+ sp.getString("zd_juid", "");
		String dHttpsUid = sp.getString("zd_dfhttps_diyUid", "") + " "
				+ sp.getString("zd_dfhttps", "");
		String dUdpUid = sp.getString("zd_dfudp_diyUid", "") + " "
				+ sp.getString("zd_dfudp", "");

		String dnsIp = sp.getString("zd_dnsip", "");
		String dnsFx = tf2of(sp.getBoolean("bj_dns_fx", true));
		String httpsFx = tf2of(sp.getBoolean("bj_https_fx", false));
		String udpFx = tf2of(sp.getBoolean("bj_udp_fx", false));
		String wifiFx = tf2of(sp.getBoolean("dy_fxwifi", true));
		String wifiWk = sp.getString("zd_wifi", "wlan0");
		String sdkPort = sp.getString("zd_sdk", "");
		String fxTcp = sp.getString("zd_fxtcp", "");
		String fxUdp = sp.getString("zd_fxudp", "");

		String gxMProxy = tf2of(sp.getBoolean("dy_mdl", true));
		String gxDnsFx = tf2of(sp.getBoolean("dy_dns", true));
		String gxUdpFx = tf2of(sp.getBoolean("dy_udp", false));
		String gxHttpsFx = tf2of(sp.getBoolean("dy_https", false));
		String gxPortTcpFx = sp.getString("dy_tcp_p", "");
		String gxPortUdpFx = sp.getString("dy_udp_p", "");

		// ///////////////////其他
		String nhjw = tf2of(sp.getBoolean("qt_nh_jw", true));
		String gwkw = tf2of(sp.getBoolean("qt_dwkw", true));
		// String jPath = sp.getString("qt_jume_path",SuperUser.jPath);

		String qtFx = tf2of(sp.getBoolean("qt_fx", false));
		String qqSp = tf2of(sp.getBoolean("qt_qqsp", false));
		String qqUid = sp.getString("qt_qquid_diyUid", "99999") + " "
				+ sp.getString("qt_qquid", "");
		String hyUid = sp.getString("qt_hyuid_diyUid", "") + " "
				+ sp.getString("qt_hyuid", "");
		String hyFxPort = sp.getString("qt_hy_dk", "5002 23");
		publishProgress(30);
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("echo \"");
		strBuffer.append("#全局代理UID\nUID0=\'" + qjUid + "\'\n\n");
		strBuffer.append("#全局/直连代理IP设置\nDIP=\'" + qIp + "\'\nPORT=\'" + qPort
				+ "\'\n\n\n");
		strBuffer.append("#本机&共享规则设置(A/B)\nMODE=\'" + localGz + "\'\nGXMOE=\'"
				+ gxGz + "\'\n\n");
		if (mTcp.equals("on")) {
			strBuffer.append("#转发给模块处理HTTPS/使用直连\nMHTTPS=\'on\'\nZHILIAN=\'"
					+ qjZl + "\'\n\n");
		} else {
			strBuffer.append("#转发给模块处理HTTPS/使用直连\nMHTTPS=\'off\'\nZHILIAN=\'"
					+ qjZl + "\'\n\n");
		}
		strBuffer.append("#--------------定义本机设置---------------#\n");
		strBuffer.append("#半免，多个uid用空格间隔\nUID1=\'" + bUid.trim() + "\'\n\n");
		strBuffer.append("#放行，多个uid用空格间隔\nUID2=\'" + fxUid.trim() + "\'\n\n");
		strBuffer.append("#禁网，多个uid用空格间隔\nUID3=\'" + jUid.trim() + "\'\n\n");
		strBuffer.append("#单放udp协议，多个uid用空格间隔\nUID4=\'" + dUdpUid.trim()
				+ "\'\n\n");
		strBuffer.append("#单放HTTPS，多个uid用空格间隔\nUID5=\'" + dHttpsUid.trim()
				+ "\'\n\n");
		strBuffer.append("#定义DNS_IP，不填将使用默认DNS\nDNSIP=\'" + dnsIp.trim()
				+ "\'\n\n");
		strBuffer.append("#DNS放行，关闭后在线视频等无法使用\nFDNS=\'" + dnsFx.trim()
				+ "\'\n\n");
		strBuffer.append("#HTTPS放行，Samp全代理版在设置里开关\nFHTTPS=\'" + httpsFx.trim()
				+ "\'\n\n");
		strBuffer.append("#UDP放行，解决部分APP或网游不联网问题\nFXUDP=\'" + udpFx.trim()
				+ "\'\n\n");

		strBuffer
				.append("#自动放行WIFI，共享无法获取IP或无网开启\nZFWIFI=\'" + wifiFx + "\'\n");

		strBuffer.append("#定义WIFI网卡，WIFI不联网的改为本机网卡\nWIFIF=\'" + wifiWk
				+ "\'\n\n");
		strBuffer.append("#指定tcp端口走回全局代理不经过模块处理\nTDK=\'" + sdkPort + "\'\n\n");
		strBuffer.append("#放行本机TCP端口\nBTCP=\'" + fxTcp + "\'\n\n");
		strBuffer.append("#放行本机UDP端口\nBUDP=\'" + fxUdp + "\'\n\n");
		strBuffer.append("#--------------定义共享设置---------------#\n");
		strBuffer.append("#共享设备免设代理，若要手动设置请关闭\nGXMM=\'" + gxMProxy + "\'\n\n");
		strBuffer.append("#共享设备DNS放行,直播或拖电脑须开启\n#必须连同本机DNS放行一起开启才有效\nGDNS=\'"
				+ gxDnsFx + "\'\n\n");
		strBuffer.append("#UDP放行,解决部分程序或网游联网问题\nGFUDP=\'" + gxUdpFx + "\'\n\n");

		strBuffer.append("#HTTPS放行,全局支持HTTPS的无需开启\nGHTTPS=\'" + gxHttpsFx
				+ "\'\n\n");
		strBuffer.append("#共享设备端口放行，多个端口用空格间隔\n\n");
		strBuffer.append("#TCP端口:\nGTCP=\'" + gxPortTcpFx + "\'\n\n");
		strBuffer.append("#UDP端口:\nGUDP=\'" + gxPortUdpFx + "\'\n\n");

		strBuffer.append("#--------------其他选项设置---------------#\n\n");

		// strBuffer.append("#开启内核禁网(若发现联网有问题请关闭)\nNHFT=\'" + nhjw + "\'\n");
		strBuffer.append("#执行脚本自动关开一遍网络防止QQ乱跳等\nZDKG=\'" + gwkw + "\'\n");
		strBuffer.append("#设置脚本'Jume'资料库文件夹存放目录位置\nJDIR=\'" + jPath + "\'\n");

		// strBuffer.append("#自动放行快牙.闪传.茄子快传数据传输协议\nZDFX=\'" + qtFx + "\'\n\n");
		strBuffer.append("#QQ视频全代理(可拨不能接)关可接但拨也不免\nQQML=\'" + qqSp + "\'\n\n");
		strBuffer.append("QUID=\'" + qqUid.trim() + "\'\n");
		strBuffer.append("#新版虎牙.YY放行5002或23解决不能看直播\n\n");
		strBuffer.append("YUID=\'" + hyUid.trim() + "\'\n\n");
		strBuffer.append("YUDP=\'" + hyFxPort + "\'\n\n");
		strBuffer.append("#--------------处理模块设置---------------#\n\n");
		strBuffer.append("#选(u2nl/Hu2nl/Hu2nl5/redsocks)代理全局\n\n");
		if (mTcp.equals("on")) {

			strBuffer.append("CHTTPS=\'" + mMTcp + "\'\n\n");
			strBuffer.append("CHDK=\'" + mTcpPort + "\'\n\n");
			strBuffer.append("#u2nl/Hu2nl代理IP/端口\n\n");
			strBuffer.append("UIP=\'" + mTcpIp + "\'\n\n");
			strBuffer.append("UDK=\'" + mTcpPPort + "\'\n\n");
		} else {
			strBuffer.append("CHTTPS=\'" + "\'\n\n");
			strBuffer.append("CHDK=\'" + mTcpPort + "\'\n\n");
			strBuffer.append("#u2nl/Hu2nl代理IP/端口\n\n");
			strBuffer.append("UIP=\'" + qIp + "\'\n\n");
			strBuffer.append("UDK=\'" + qPort + "\'\n\n");
		}
		strBuffer.append("#选(dnsp/pdnsd)代理解释DNS，不填则关闭\n\n");
		if (mDns.equals("on")) {
			strBuffer.append("MDNS=\'" + mMDns + "\'\n\n");

		} else {
			strBuffer.append("MDNS=\'\'\n\n");
		}
		// strBuffer.append("\nTest:"+mDns+":"+mMDns+"\n");
		strBuffer.append("MDDK=\'" + mDnsPort + "\'\n\n");
		strBuffer.append("#设置dnsp模块的DNS解释域名\n\n");
		strBuffer.append("DNSP=\'" + mDnsHttp + "\'\n\n");
		strBuffer.append("#设置pdnsd的DNS代理IP(多个IP可用,隔开)\n");
		strBuffer.append("PDNP=\'" + mDnsPip + "\'\n");
		strBuffer
				.append("#---------以下内容切勿修改--------#\nBY=\'Jume\'\n\" > /data/Jume7.conf\n");

		String privPath = SuperUser.getPrivFilePath(con);

		String shName = privPath + "/Jume_start.sh";

		String ftStr = "if [ ! -x \'/data\' ]; then\n"
				+ "busybox mkdir /data\n" + "busybox chmod 0777 /data\n"
				+ "fi\n" + strBuffer.toString() + "\nJDIR=\'" + jPath + "\'\n"
				+ SuperUser.readFile(shName) + "\n";

		SuperUser.deleteFile(privPath + "/pstart.sh");
		SuperUser.writeFile(ftStr, privPath + "/pstart.sh");
		ftStr = "chmod 0777 " + privPath + "/pstart.sh\nsh\n." + privPath
				+ "/pstart.sh\n" + "busybox rm -fr " + privPath
				+ "/pstart.sh\n";
		publishProgress(50);
		eShell = new SuperUser.ExecShell(ftStr, pl);

		try {
			eShell.start();
			publishProgress(80);
			eShell.join(5000);
		} catch (InterruptedException e) {

		}

		publishProgress(100);

		return "防跳配置文件更新完成";
	}

	public static String tf2of(boolean v) {
		return v ? "on" : "off";
	}

	public void ftStatus() {
		pline = "\t\t正在启动脚本: 防跳状态\n";
		publishProgress(1314);
		publishProgress(20);
		String privPath = SuperUser.getPrivFilePath(con);

		String shName = privPath + "/Jume_status.sh";

		String ftStr = SuperUser.readFile(shName);

		publishProgress(50);
		eShell = new SuperUser.ExecShell(ftStr.replace("[jPath]", jPath), pl);
		try {
			eShell.start();
			publishProgress(80);
			eShell.join(5000);
		} catch (InterruptedException e) {

		}
		publishProgress(100);

	}

	public void ftStop() {
		pline = "\t\t正在启动脚本: 防跳关闭\n";
		publishProgress(1314);
		publishProgress(20);

		String privPath = SuperUser.getPrivFilePath(con);

		String shName = privPath + "/Jume_stop.sh";

		String ftStr = SuperUser.readFile(shName);

		publishProgress(50);
		eShell = new SuperUser.ExecShell(ftStr.replace("[jPath]", jPath), pl);
		try {
			eShell.start();
			publishProgress(80);
			eShell.join(5000);
		} catch (InterruptedException e) {

		}
		publishProgress(100);

	}

	@Override
	protected String doInBackground(String... p1) {
		if (SuperUser.is_root) {
			// TODO: Implement this method
			switch (p1[0]) {
			case "sh":
				execShell(p1[1], p1[1]);
				break;
			case "FtStart":
				ftStart(con, p1[1]);
				break;
			case "FtStop":
				ftStop();
				break;
			case "FtStatus":
				ftStatus();
				break;

			}
		} else {
			pline = "请检测ROOT权限是否存在\n点击菜单或者再次打开软件即可检测!";
			publishProgress(1314);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... progresses) {
		synchronized (progresses) {
			if (progresses[0] != 1314) {
				if (eMode == 2) {
					dloader.setPnos(progresses[0]);
					dloader.setInfo("正在处理...(" + progresses[0] + "%)");
				}
			} else {
				if (eMode == 2) {
					dloader.append(pline);
				} else if (eMode == 1) {
					msg(pline);
				} else if (eMode == 3) {
					// System.out.println("Handler:" + handler);
					Message msg = new Message();
					msg.what = 0;
					msg.obj = pline;
					handler.sendMessage(msg);
				} else {
					Log.d("ExecShell", pline);
				}
				pline = "";
			}

		}
	}

	// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
	@Override
	protected void onPostExecute(String result) {
		if (eMode == 2) {
			if (!dloader.isShowing()) {
				// System.out.println("dloader again .....");
				dloader.Showagain();
			}
			dloader.setTitle("处理完成....");
			dloader.setInfo("处理完成....");
			dloader.setPnos(100);
			dloader.setPToast(false);
		}
		Log.d("ExecShell", "Thread is Over.....");

	}

	private void msg(String message) {
		Toast.makeText(con, message, Toast.LENGTH_LONG).show();
	}

}
