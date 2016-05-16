package org.bybbs.jume;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

public class Install extends Thread {
	private Context con;
	private SharedPreferences sp;
	@SuppressWarnings("unused")
	private SuperUser su;
	private Handler handler;
	private String installPath;// 私有目录
	private String str = "";

	public Install(Context con, SharedPreferences sp, SuperUser su,
			Handler handler, String installPath) {
		this.con = con;
		this.sp = sp;
		this.su = su;
		this.handler = handler;
		this.installPath = installPath;
		//sp.edit().putBoolean("isData", true).commit();
		sp.edit().putString("zd_juid_diyUid","1000" + sp.getString("zd_juid_diyUid","")).commit();
	}

	@Override
	public void run() {
		String rw = sp.getString("rwName", "none");
		if (rw.equals("none")) {
			if ((rw = SuperUser.rwStr()).equals("none")) {
				msg("安装失败：无法挂载/System目录为可读写，请手动挂载，然后到关于里检测即可");
				// return;
			}
			sp.edit().putString("rwName", rw).commit();
		}
		System.out.println("rw in");
		try {
			// copyFileFromAssets("+ZJcheck.sh", installPath, "+ZJcheck.sh");
			// copyFileFromAssets("+ZJstart.sh", installPath, "+ZJstart.sh");
			// copyFileFromAssets("+ZJstop.sh", installPath, "+ZJstop.sh");

			copyFileFromAssets("+ZJcheck.sh", installPath, "Jume_status.sh");
			copyFileFromAssets("+ZJstart.sh", installPath, "Jume_start.sh");
			copyFileFromAssets("+ZJstop.sh", installPath, "Jume_stop.sh");

			if (android.os.Build.VERSION.SDK_INT > 19) {

				copyFileFromAssets("dnsp5", installPath, "dnsp");
				copyFileFromAssets("Hu2nl5", installPath, "Hu2nl");
				copyFileFromAssets("Jume75", installPath, "Jume7");
				copyFileFromAssets("pdnsd5", installPath, "pdnsd");
				copyFileFromAssets("redsocks5", installPath, "redsocks");
				copyFileFromAssets("u2nl5", installPath, "u2nl");
			} else {
				copyFileFromAssets("dnsp", installPath, "dnsp");
				copyFileFromAssets("Hu2nl", installPath, "Hu2nl");
				copyFileFromAssets("Jume7", installPath, "Jume7");
				copyFileFromAssets("pdnsd", installPath, "pdnsd");
				copyFileFromAssets("redsocks", installPath, "redsocks");
				copyFileFromAssets("u2nl", installPath, "u2nl");
			}
			copyFileFromAssets("sh.conf", installPath, "sh.conf");
			// copyFileFromAssets("",installPath,"");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg("安装失败：" + e);
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg("安装失败：" + e);
			return;
		}

		if (!sp.getBoolean("checkBusybox", false)) {
			boolean isBusybox = SuperUser.checkBusybox();
			sp.edit().putBoolean("isBusybox", isBusybox).commit();
			sp.edit().putBoolean("checkBusybox", true).commit();
			if (!isBusybox) {
				Message msg = new Message();
				msg.what = 5;
				msg.obj = "没有安装Busybox，请安装过后再打开防跳";
				handler.sendMessage(msg);
				return;
			}
		}
		/*
		 * copyFileFromAssets("!!QuanXian.sh", installPath, "!!QuanXian.sh");
		 * copyFileFromAssets("+ZJsee.sh", installPath, "+ZJsee.sh");
		 * copyFileFromAssets("+ZJstart.sh", installPath, "+ZJstart.sh");
		 * copyFileFromAssets("+ZJstop.sh", installPath, "+ZJstop.sh");
		 * copyFileFromAssets("dnsp5", installPath, "dnsp");
		 * copyFileFromAssets("Hu2nl5", installPath, "Hu2nl");
		 * copyFileFromAssets("Jume7.05", installPath, "Jume7.0");
		 * copyFileFromAssets("Jume7se5", installPath, "Jume7se");
		 * copyFileFromAssets("Jume7sp5", installPath, "Jume7sp");
		 * copyFileFromAssets("pdnsd5", installPath, "pdnsd");
		 * copyFileFromAssets("redsocks5", installPath, "redsocks");
		 * copyFileFromAssets("u2nl5", installPath, "u2nl");
		 */

		String jPath = "/system/xbin/JumeApp";

		String shell = "";

		shell += "busybox mount -o remount,rw " + rw + " /system \n";
		shell += "busybox mount -o remount,rw /system\n";
		shell += "busybox mount -o rw,remount /system\n";
		shell += "if [ ! -x \"/system/xbin\" ]; then\n";
		shell += "busybox mkdir /system/xbin\n";
		shell += "busybox chmod 0777 /system/xbin\n";
		shell += "fi\n";

		shell += "if [ ! -x \"" + jPath + "\" ]; then\n";
		shell += "busybox mkdir " + jPath + "\n";
		shell += "fi\n";

		shell += "if [ ! -x \"" + jPath + "/Jume\" ]; then\n";
		shell += "busybox mkdir " + jPath + "/Jume\n";
		shell += "fi\n";

		shell += "busybox chmod 0777 /system/xbin\n";
		shell += "busybox chmod -R 0777 " + jPath + "\n";
		shell += "busybox chmod -R 0777 " + installPath + "\n";
		// shell += "busybox chmod 0777 " + installPath + "/+ZJcheck.sh\n";
		// shell += "busybox chmod 0777 " + installPath + "/+ZJstop.sh\n";
		// shell += "busybox chmod 0777 " + installPath + "/+ZJstart.sh\n";
		//
		// shell += "busybox chmod 0777 " + installPath + "/Jume_start.sh\n";
		// shell += "busybox chmod 0777 " + installPath + "/Jume_stop.sh\n";
		// shell += "busybox chmod 0777 " + installPath + "/Jume_status.sh\n";
		//
		// shell += "busybox chmod 0777 " + installPath + "/dnsp\n";
		// shell += "busybox chmod 0777 " + installPath + "/Hu2nl\n";
		// shell += "busybox chmod 0777 " + installPath + "/Jume7\n";
		//
		// shell += "busybox chmod 0777 " + installPath + "/pdnsd\n";
		// shell += "busybox chmod 0777 " + installPath + "/redsocks\n";
		// shell += "busybox chmod 0777 " + installPath + "/u2nl\n";

		shell += "if [ ! -x \"/data\" ]; then\n";
		shell += "busybox mkdir /data\n";
		shell += "busybox chmod 0777 /data\n";
		shell += "fi\n";

		// shell += "busybox cp -fr " + installPath
		// + "/+ZJsee.sh /data/Jume_status.sh\n";
		// shell += "busybox cp -fr " + installPath
		// + "/+ZJstop.sh /data/Jume_stop.sh\n";
		// shell += "busybox cp -fr " + installPath
		// + "/+ZJstart.sh /data/Jume_start.sh\n";
		//
		// shell += "chmod 0777 /data/Jume_start.sh\n";
		// shell += "chmod 0777 /data/Jume_stop.sh\n";
		// shell += "chmod 0777 /data/Jume_status.sh\n";

		// shell += "busybox mv " + installPath
		// + "/+ZJcheck.sh "+jPath+"\n";
		// shell += "busybox mv " + installPath
		// + "/+ZJstop.sh "+jPath+"\n";
		// shell += "busybox mv " + installPath
		// + "/+ZJstart.sh "+jPath+"\n";
		shell += "busybox mv " + installPath + "/dnsp " + jPath + "/Jume\n";
		shell += "busybox mv " + installPath + "/Hu2nl " + jPath + "/Jume\n";
		shell += "busybox mv " + installPath + "/Jume7 " + jPath + "/Jume\n";

		shell += "busybox mv " + installPath + "/pdnsd " + jPath + "/Jume\n";
		shell += "busybox mv " + installPath + "/redsocks " + jPath + "/Jume\n";
		shell += "busybox mv " + installPath + "/u2nl " + jPath + "/Jume\n";
		shell += "busybox chmod -R 0777 " + jPath + "\n";
		shell += "echo '终极防跳安装完成 ^_^.....'\n";
		// System.out.println(shell);
		//SuperUser.writeFile(shell, installPath+"/install.sh");
		
		Message msg = new Message();
		msg.what = 0;
		msg.obj = execShell(shell);
		sp.edit().putBoolean(SuperUser.version, true).commit();

		handler.sendMessage(msg);

	}

	private String execShell(String shell) {

		PutLine pl = new PutLine() {

			@Override
			public void pLine(String line) {
				// TODO Auto-generated method stub
				str += line;
				// System.out.println("Test:" + line);

			}

		};
		SuperUser.ExecShell eShell = new SuperUser.ExecShell(shell, pl);
		try {
			eShell.start();
			eShell.join(5000);
		} catch (InterruptedException e) {
		}

		return str;
	}

	// 从ASSES文件夾複製文件出來
	public void copyFileFromAssets(String ASSETS_NAME, String savePath,
			String saveName) throws FileNotFoundException, IOException {
		String filename = savePath + "/" + saveName;
		File dir = new File(savePath);
		// 如果目录不中存在，创建这个目录
		if (!dir.exists())
			dir.mkdirs();

		if (new File(filename).exists()) {
			new File(filename).delete();
		}
		InputStream is = con.getResources().getAssets().open(ASSETS_NAME);
		FileOutputStream fos = new FileOutputStream(filename);
		byte[] buffer = new byte[7168];
		int count = 0;
		while ((count = is.read(buffer)) > 0) {
			fos.write(buffer, 0, count);
		}
		fos.close();
		is.close();

	}

	public void msg(String str) {
		Message msg = new Message();
		msg.what = 0;
		msg.obj = str;
		handler.sendMessage(msg);
	}
}
