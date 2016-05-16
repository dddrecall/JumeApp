package org.bybbs.jume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SuperUser {
	private Context con;
	private String priFilePath;
	public static boolean is_root;
	public static boolean isBusybox;
	public final static String SHELL = "sh";
	public final static String ROOT = "su";
	public static String version = "1.22";
	public static String jPath="/system/xbin/JumeApp";
	

	public SuperUser(Context con) {
		this.con = con;
		priFilePath = con.getFilesDir().getPath();
	}

	
	
	public static boolean checkRoot() {
		Process localProcess = null;
		OutputStream localOutputStream = null;
		InputStream localInputStream1 = null;
		InputStream localInputStream2 = null;

		try {
			localProcess = Runtime.getRuntime().exec("su");
			if (localProcess == null)
				return false;
			localOutputStream = localProcess.getOutputStream();
			localOutputStream.write("echo root\nexit\n".getBytes());
			localOutputStream.flush();
			localOutputStream.close();
			localProcess.waitFor();
			localInputStream1 = localProcess.getErrorStream();
			localInputStream2 = localProcess.getInputStream();
			StringBuffer localStringBuffer = new StringBuffer();
			int i = localInputStream1.available();
			if (i > 0) {
				byte[] arrayOfByte1 = new byte[i];
				localInputStream1.read(arrayOfByte1);
				localStringBuffer.append(new String(arrayOfByte1));
			}
			int j = localInputStream2.available();
			if (j > 0) {
				byte[] arrayOfByte2 = new byte[j];
				localInputStream2.read(arrayOfByte2);
				localStringBuffer.append(new String(arrayOfByte2));
			}
			localInputStream1.close();
			localInputStream2.close();
			localProcess.destroy();
			int k = localStringBuffer.toString().indexOf("root");
			if (k > -1) {
				is_root = true;
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				localOutputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				localInputStream1.close();
			} catch (Exception e) {

			}
			try {
				localInputStream2.close();
			} catch (Exception e) {

			}
			try {
				localProcess.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return is_root;
	}

	public static boolean checkBusybox() {
		Process localProcess = null;
		OutputStream localOutputStream = null;
		InputStream localInputStream1 = null;
		InputStream localInputStream2 = null;

		try {
			localProcess = Runtime.getRuntime().exec("sh");
			if (localProcess == null)
				return false;
			localOutputStream = localProcess.getOutputStream();
			localOutputStream.write("busybox\nexit\n".getBytes());
			localOutputStream.flush();
			localOutputStream.close();
			localProcess.waitFor();
			localInputStream1 = localProcess.getErrorStream();
			localInputStream2 = localProcess.getInputStream();
			StringBuffer localStringBuffer = new StringBuffer();
			int i = localInputStream1.available();
			if (i > 0) {
				byte[] arrayOfByte1 = new byte[i];
				localInputStream1.read(arrayOfByte1);
				localStringBuffer.append(new String(arrayOfByte1));
			}
			int j = localInputStream2.available();
			if (j > 0) {
				byte[] arrayOfByte2 = new byte[j];
				localInputStream2.read(arrayOfByte2);
				localStringBuffer.append(new String(arrayOfByte2));
			}
			localInputStream1.close();
			localInputStream2.close();
			localProcess.destroy();
			int k = localStringBuffer.toString().indexOf("cp");
			if (k > -1) {
				isBusybox = true;
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				localOutputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				localInputStream1.close();
			} catch (Exception e) {

			}
			try {
				localInputStream2.close();
			} catch (Exception e) {

			}
			try {
				localProcess.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return isBusybox;
	}

	public static AlertDialog show_newfix(Context con,
			DialogInterface.OnClickListener dc) {
		LayoutInflater li = LayoutInflater.from(con);
		View vView = li.inflate(R.layout.new_fix, null);
		TextView text = (TextView) vView.findViewById(R.id.newfixTextView1);
		text.setMovementMethod(new ScrollingMovementMethod());
		text.setText(con.getString(R.string.newfix));
		return new AlertDialog.Builder(con).setTitle("更新日志").setView(vView)
				.setIcon(R.drawable.ic_dialog_info).setPositiveButton("确定", dc)
				.show();

	}

	public static String rwStr() {
		String Str = "none";
		Process localProcess = null;
		OutputStream localOutputStream = null;
		InputStream localInputStream1 = null;
		InputStream localInputStream2 = null;

		try {
			localProcess = Runtime.getRuntime().exec("sh");
			if (localProcess == null)
				return Str;
			localOutputStream = localProcess.getOutputStream();
			localOutputStream.write("mount\nexit\n".getBytes());
			localOutputStream.flush();
			localProcess.waitFor();

			localInputStream1 = localProcess.getErrorStream();
			localInputStream2 = localProcess.getInputStream();
			StringBuffer localStringBuffer = new StringBuffer();
			int i = localInputStream1.available();
			if (i > 0) {
				byte[] arrayOfByte1 = new byte[i];
				localInputStream1.read(arrayOfByte1);
				localStringBuffer.append(new String(arrayOfByte1));
			}
			int j = localInputStream2.available();
			if (j > 0) {
				byte[] arrayOfByte2 = new byte[j];
				localInputStream2.read(arrayOfByte2);
				localStringBuffer.append(new String(arrayOfByte2));
			}

			String str = localStringBuffer.toString();
			// System.out.println(str+"\n-------------------------------------------\n");
			int i1 = str.indexOf(" /system ");
			if (i1 > -1) {
				String str1 = str.substring(0, i1);
				i1 = str1.lastIndexOf("\n");
				if (i1 > -1) {
					str1 = str1.substring(i1 + 1);
					Str = new String(str1);
					// System.out.println(str1+"\n-------------------------------------------\n");
				}
			}

			localOutputStream.close();
			localInputStream1.close();
			localInputStream2.close();
			localProcess.destroy();

		} catch (Exception localException) {
			// localException.printStackTrace();
		} finally {
			try {
				localOutputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
			try {
				localInputStream1.close();
			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				localInputStream2.close();
			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				localProcess.destroy();
			} catch (Exception e) {
				// e.printStackTrace();
			}

		}
		return Str;
	}

	public static boolean rw(SharedPreferences sp, Handler handler) {
		boolean ok = false;
		String rw = sp.getString("rwName", "none");
		if (rw.equals("none")) {
			if ((rw = rwStr()).equals("none")) {
			}
			// return false;
			sp.edit().putString("rwName", rw).commit();
		}
		System.out.println("rw in");
		Process localProcess = null;
		OutputStream localOutputStream = null;
		InputStream localInputStream1 = null;
		InputStream localInputStream2 = null;

		try {
			localProcess = Runtime.getRuntime().exec("su");
			if (localProcess == null)
				return false;
			localOutputStream = localProcess.getOutputStream();
			localOutputStream
					.write(("busybox mount -o rw,remount /system\n"
							+ "mount -o remount,rw " + rw + " /system \nmkdir /system/xbin/Jume\nchmod 777 /system/xbin/Jume\ncp /data/data/org.bybbs.jume/files/+ZJsee.sh /system/xbin/Jume\nexit\n")
							.getBytes());

			localOutputStream.flush();
			localOutputStream.close();
			localProcess.waitFor();
			localInputStream1 = localProcess.getErrorStream();
			localInputStream2 = localProcess.getInputStream();
			StringBuffer localStringBuffer = new StringBuffer();
			int i = localInputStream1.available();
			if (i > 0) {
				byte[] arrayOfByte1 = new byte[i];
				localInputStream1.read(arrayOfByte1);
				localStringBuffer.append(new String(arrayOfByte1));
			}
			int j = localInputStream2.available();
			if (j > 0) {
				byte[] arrayOfByte2 = new byte[j];
				localInputStream2.read(arrayOfByte2);
				localStringBuffer.append(new String(arrayOfByte2));
			}
			localInputStream1.close();
			localInputStream2.close();
			localProcess.destroy();
			String str = localStringBuffer.toString();
			System.out.println("tt:" + str);
			Message msg = new Message();
			msg.what = 0;
			msg.obj = str.length() + ":" + str;
			handler.sendMessage(msg);
			ok = true;
		} catch (Exception localException) {
			// localException.printStackTrace();
		} finally {
			try {
				localOutputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
			try {
				localInputStream1.close();
			} catch (Exception e) {

			}
			try {
				localInputStream2.close();
			} catch (Exception e) {

			}
			try {
				localProcess.destroy();
			} catch (Exception e) {
				// e.printStackTrace();
			}

		}
		return ok;
	}

	// 从ASSES文件夾複製文件出來
	public void CopyFileFromAssets(String ASSETS_NAME, String savePath,
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

	static class ExecShell extends Thread {
		String cmd;
		PutLine pl;

		public ExecShell(String cmd, PutLine pl) {
			this.cmd = cmd;
			this.pl = pl;
		}

		public void run() {
			Process r0_Process = null;
			InputStream localInputStream1 = null;
			InputStream localInputStream2 = null;

			try {

				if (is_root)
					r0_Process = Runtime.getRuntime().exec("su\n");
				else
					r0_Process = Runtime.getRuntime().exec("sh\n");

				if (r0_Process == null) {
					pl.pLine("\n执行出错了...请检测Root权限是否拥有 !");
					return;
				} else {

					OutputStream r3_OutputStream = r0_Process.getOutputStream();
					r3_OutputStream.write(cmd.getBytes());
					r3_OutputStream.write("\nexit\n".getBytes());
					r3_OutputStream.flush();
					r3_OutputStream.close();
					
				}
				r0_Process.waitFor();
				localInputStream1 = r0_Process.getInputStream();
				localInputStream2 = r0_Process.getErrorStream();
				StringBuffer localStringBuffer = new StringBuffer();
				int i = localInputStream1.available();

				if (i > 0) {
					byte[] arrayOfByte1 = new byte[i];
					localInputStream1.read(arrayOfByte1);
					localStringBuffer.append(new String(arrayOfByte1));
				}

				int j = localInputStream2.available();
				if (j > 0) {
					byte[] arrayOfByte2 = new byte[j];
					localInputStream2.read(arrayOfByte2);
					localStringBuffer.append("\n错误信息输出[" + j + "]:\n"
							+ new String(arrayOfByte2));
				}
				pl.pLine(localStringBuffer.toString());
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				try {
					localInputStream1.close();
				} catch (Exception e) {
				}
				try {
					localInputStream2.close();
				} catch (Exception e) {
				}
				try {
					r0_Process.destroy();
				} catch (Exception e) {
				}
				// Log.e(TAG, "shell_finally");
			}
		}

	}

	public static String getPrivFilePath(Context con) {
		return con.getFilesDir().getPath();
	}

	public String getPrivFilePath() {
		return this.priFilePath;
	}

	/**
	 * 读取SD卡中文本文件
	 *
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		InputStream is = null;
		String buf = null;
		try {
			is = new FileInputStream(fileName);
			int length = is.available();
			byte[] outBuf = new byte[length];
			is.read(outBuf, 0, length);
			buf = new String(outBuf, "utf8");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "\n" + fileName + ":" + e.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "\n" + fileName + ":" + e.toString();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return buf;
	}

	public static int getUid(Context con, String pack) {
		int uid = -1;
		try {
			PackageManager pm = con.getPackageManager();
			ApplicationInfo ai = pm.getApplicationInfo(pack,
					PackageManager.GET_ACTIVITIES);
			if (ai != null)
				uid = ai.uid;
			else
				uid = -1;
		} catch (Exception e) {
			e.printStackTrace();
			uid = -1;
		}
		return uid;
	}

	public static Signature[] getRawSignature(Context context,
			String packageName) {
		if ((packageName == null) || (packageName.length() == 0)) {
			return null;
		}
		PackageManager pkgMgr = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = pkgMgr.getPackageInfo(packageName,
					PackageManager.GET_SIGNATURES);
		} catch (PackageManager.NameNotFoundException e) {
			return null;
		}
		if (info == null) {
			return null;
		}
		return info.signatures;
	}

	/**
	 * 写入内容到SD卡中的txt文本中 str为内容
	 */
	public static boolean writeFile(String str, String fileName) {

		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			fw.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	// 要复制的目录下的所有非子目录(文件夹)文件拷贝
	public static boolean copyFile(String fromFile, String toFile) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			InputStream fosfrom = new FileInputStream(fromFile);
			OutputStream fosto = new FileOutputStream(toFile);
			bis = new BufferedInputStream(fosfrom);
			bos = new BufferedOutputStream(fosto);

			byte bt[] = new byte[2048];
			int len = 0;
			while ((len = bis.read(bt)) > 0) {
				bos.write(bt, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;

	}

	// 删除文件
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file == null || !file.exists())
			return false;
		if (file.isDirectory()) {
			for (File f : file.listFiles())
				f.delete();
		}
		return file.delete();
	}
}
