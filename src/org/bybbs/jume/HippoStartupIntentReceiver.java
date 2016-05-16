package org.bybbs.jume;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**/
@SuppressLint("HandlerLeak")
public class HippoStartupIntentReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, Intent intent) {
		System.out.println("HippoStart......");
		Log.e("ZJ","终极防跳开机自启....");
		final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);;
		
		if (sp.getBoolean("onStart", false)) {
			if (!sp.getBoolean("checkRoot", false)){
				sp.edit().putBoolean("checkRoot", true).commit();
				sp.edit().putBoolean("isRoot", SuperUser.checkRoot());	
			}
			else 
				SuperUser.is_root = sp.getBoolean("isRoot", false);
		
			//SuperUser su = new SuperUser(context);
			if (!SuperUser.is_root) {
				Toast.makeText(context, "没有ROOT权限，终极防跳无法开机自启...",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(context, "终极防跳正在开机自启 .....", Toast.LENGTH_SHORT)
					.show();
			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 0:
						Toast.makeText(context, msg.obj.toString(),
								Toast.LENGTH_LONG).show();
						break;
					case 5:
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

			DShell ds = new DShell(context, "", handler, 1);
			ds.execute("FtStart", "防跳开启");
			
			sp.edit().putBoolean("isRunning", true).commit();
		}

	}

}
