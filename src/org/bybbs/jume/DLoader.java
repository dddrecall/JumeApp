package org.bybbs.jume;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DLoader {
	private Context con;
	private AlertDialog.Builder loader;

	private View vloader;

	private ImageView iv;
	private TextView loadText, loadText2;

	private ProgressBar loadPb, loadPbc;

	private AlertDialog dialog;

	private String title, info, t1 = "";

	private int pos;

	private boolean html;

	public DLoader(Context con) {
		this.con = con;
		init("正在处理...", "处理中....", 0);
		append("处理中.....\n");
	}

	public DLoader(Context con, String title, String info, int pos, boolean html) {
		this.con = con;
		this.html = html;
		init(title, info, pos);

	}

	@SuppressLint("InflateParams")
	public void init(String title, String info, int pos) {
		vloader = LayoutInflater.from(con).inflate(R.layout.load, null);
		loader = new AlertDialog.Builder(con).setTitle(title)
				.setIcon(R.drawable.ic_dialog_info).setView(vloader)
				.setPositiveButton("隐藏", null)
				.setNegativeButton("复制", new DialogInterface.OnClickListener() {

					@SuppressLint("NewApi")
					@SuppressWarnings("deprecation")
					@Override
					public void onClick(DialogInterface p1, int p2) {
						// TODO: Implement this method
						if (android.os.Build.VERSION.SDK_INT > 11) {
							android.content.ClipboardManager c = (android.content.ClipboardManager) con.getSystemService(Context.CLIPBOARD_SERVICE);
							c.setText(loadText2.getText().toString());
						} else {
							android.text.ClipboardManager c = (android.text.ClipboardManager) con.getSystemService(Context.CLIPBOARD_SERVICE);
							c.setText(loadText2.getText().toString());
						}
						/*
						 * ClipboardManager clipboardManager =
						 * (ClipboardManager)
						 * con.getSystemService(Context.CLIPBOARD_SERVICE);
						 * clipboardManager
						 * .setPrimaryClip(ClipData.newPlainText(null,
						 * loadText2.getText().toString())); if
						 * (clipboardManager.hasPrimaryClip()){
						 * clipboardManager.
						 * getPrimaryClip().getItemAt(0).getText(); }
						 */
						Toast.makeText(con, "复制完成 !", Toast.LENGTH_LONG).show();

					}

				});
		loadText = (TextView) vloader.findViewById(R.id.loadTextView1);
		loadText2 = (TextView) vloader.findViewById(R.id.loadTextView2);
		loadText2.setMovementMethod(new ScrollingMovementMethod());
		loadPb = (ProgressBar) vloader.findViewById(R.id.loadProgressBar2);
		loadPbc = (ProgressBar) vloader.findViewById(R.id.loadProgressBar1);
		iv = (ImageView) vloader.findViewById(R.id.loadImageView1);
		loadText.setText(info + "\n");
		loadPb.setProgress(pos);

		loadText2.setText(info + "\n");
		this.title = title;
		this.info = info;

	}

	public AlertDialog show() {
		try {
			dialog = loader.show();
			return dialog;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void dimss() {
		try {
			dialog.dismiss();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void append(String str) {
		String tmp = str;
		if (html) {
			tmp = str.replace("\n", "<br>");
			tmp = tmp.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
			loadText2.append(Html.fromHtml(tmp));
		} else {
			loadText2.append(HtmlUtil.SimpleDel(tmp));
		}
		t1 += tmp;
		
	}

	public void setTitle(String str) {
		dialog.setTitle(str);
		title = str;
	}

	public void setInfo(String str) {
		loadText.setText(str);
		info = str;
	}

	public void setPnos(int pos) {
		loadPb.setProgress(pos);
		this.pos = pos;
	}

	public void setPToast(boolean toast) {
		if (toast) {
			loadPbc.setVisibility(View.VISIBLE);
			iv.setVisibility(View.INVISIBLE);
		} else {
			loadPbc.setVisibility(View.INVISIBLE);
			iv.setVisibility(View.VISIBLE);
		}

	}

	public boolean isShowing() {
		return dialog.isShowing();
	}

	public void Showagain() {
		this.init(title, info, pos);
		this.append(t1);
		this.show();
	}

	public void dnotify() {
		synchronized (con) {
			try {
				dialog.wait(0);
			} catch (Exception e) {
				// TODO: handle exception
			}
			dialog.notifyAll();
		}

	}
}
