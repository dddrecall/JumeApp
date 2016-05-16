package org.bybbs.jume;

import java.util.ArrayList;

import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

public class highlight
{
	
  static {				//highlight
	System.loadLibrary("highlight");
	test();
  }
  
  public native static int test();
  public native static int[] jniparse(String text, String syntaxFile);

  public static final int GROUP_TAG_ID        = 1;
  public static final int GROUP_COMMENT_ID    = 2;
  public static final int GROUP_STRING_ID     = 3;
  public static final int GROUP_KEYWORD_ID    = 4;
  public static final int GROUP_FUNCTION_ID   = 5;
  public static final int GROUP_ATTR_NAME_ID  = 6;
  private final static String TAG = "Highlight";
  private String SyntaxFile;
  private static int color_tag;
  private static int color_string;
  private static int color_keyword;
  private static int color_function;
  private static int color_comment;
  private static int color_attr_name;
  private String mExt = "sh";
  private int mEndOffset = -1;
  private int mStartOffset = -1;
  private boolean mStop = true;
  private static int mLimitFileSize = 0; //单位：KB
  private static ArrayList<Object> mSpans = new ArrayList<Object>();

  public highlight(String SyntaxFile)
  {
	this.SyntaxFile = SyntaxFile;
	loadColorScheme();
  }

  public void stop()
  {
	this.mStop = true;
  }

  public void redraw()
  {
	this.mStop = false;
	this.mEndOffset = -1;
	this.mStartOffset = -1;
  }
  


  
  
public boolean highlight(Editable e, int startOffset, int endOffset)
  {

	if (this.mStop || "".equals(this.mExt))
	{
	  Log.d(TAG, startOffset + "stop=" + endOffset);
	  return false;
	}
	if (this.mStartOffset <= startOffset && this.mEndOffset >= endOffset)
	{
	  Log.d(TAG, startOffset + "off=" + endOffset);
	  return false;}
	//lock it 不然会因为添加了span后导致offset改变，不断地进行高亮
	this.mStop = true;
	//TimerUtil.start();
	//Log.d(TAG, startOffset + "=" + endOffset);
	if (startOffset != 0)
	{
	  int t2=e.toString().indexOf("\n", endOffset);
	  this.mStartOffset = 0;
	  if (t2 > -1)
		this.mEndOffset = t2;
	  else
		this.mEndOffset = endOffset;
	}
	else
	{
	  this.mStartOffset = startOffset;
	  this.mEndOffset = endOffset;
	}
	String text = e.subSequence(0, this.mEndOffset).toString();
    //Log.e(TAG, "start:" + mStartOffset + " end:" + endOffset + "\nText:" + text);
	int[] ret = jniparse(text, this.SyntaxFile);
	//TimerUtil.stop("hg parse");
	if (ret == null)
	{
	  //Log.d(TAG, startOffset + "retnull=" + endOffset);	  
	  this.mStop = false;
	  return false;
	}
	int len = ret.length;
	if (len < 1 || len % 3.0F != 0)
	{
	  //Log.d(TAG, startOffset + "%%=" + endOffset);
	  this.mStop = false;
	  return false;
	}
	//Log.d(TAG, "Sync Start....." + len);

	//TimerUtil.start();
	//不能清除全陪，因为滚动条需要一个span来按住拖动
	//mText.clearSpans();

	int color;
	int start;
	int end;
	ForegroundColorSpan fcs;
	/*
	 for (ForegroundColorSpan fcs2:mSpans)
	 {
	 e.removeSpan(fcs2);
	 }
	 */
	for (int i=0; i < len; i++)
	{

	  switch (ret[i])
	  {
		case GROUP_TAG_ID:
		  color = color_tag;
		  break;
		case GROUP_STRING_ID:
		  color = color_string;
		  break;
		case GROUP_KEYWORD_ID:
		  color = color_keyword;
		  break;
		case GROUP_FUNCTION_ID:
		  color = color_function;
		  break;
		case GROUP_COMMENT_ID:
		  color = color_comment;
		  break;
		case GROUP_ATTR_NAME_ID:
		  color = color_attr_name;
		  break;
		default:
		  Log.d(TAG, "获取颜色group id失败");
		  mStop = false;
		  return false;
	  }

	  start = ret[++i];
	  end   = ret[++i];
	  /*
	   if (end < startOffset)
	   continue;
	   */
	  fcs = new ForegroundColorSpan(color);

	  try
	  {
		//Log.d(TAG, start + "__" + end);		
		//Log.d(TAG, start + "-" + end + "\n " + e.toString().substring(start, end));
		e.setSpan(fcs, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
	  }
	  catch (Exception e1)
	  {
		e1.printStackTrace();
	  }

	}
	//Log.d(TAG, "Sync End....." + len);

	ret = null;
	//TimerUtil.stop("hg 1");
	this.mStop = false;
	return true;
  }




  public static void loadColorScheme()
  {
	//色彩模块
	color_tag            = Color.parseColor(ColorScheme.color_tag);
	color_string         = Color.parseColor(ColorScheme.color_string);
	color_keyword        = Color.parseColor(ColorScheme.color_keyword);
	color_function       = Color.parseColor(ColorScheme.color_function);
	color_comment        = Color.parseColor(ColorScheme.color_comment);
	color_attr_name      = Color.parseColor(ColorScheme.color_attr_name);
  }



}

