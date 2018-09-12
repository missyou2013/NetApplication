package com.kernal.smartvision.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alex on 2016/6/5.
 */
public class ViewUtil {
	/**
	 * 增大文本水平间距
	 * 
	 * @param view
	 *            TextView | Button | EditText
	 * @param letterSpace
	 *            字间距 [-0.5, 4F] 之间较为合适， 精度为 0.001F
	 */
	public static void addLetterSpacing(View view, float letterSpace) {
	
		if ((view == null)) {
			return;
		}
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			addLetterSpacing(view, textView.getText().toString(), letterSpace);
		}
		if (view instanceof Button) {
			Button button = (Button) view;
			addLetterSpacing(view, button.getText().toString(), letterSpace);
		}
		if (view instanceof EditText) {
			EditText editText = (EditText) view;
			addLetterSpacing(view, editText.getText().toString(), letterSpace);
		}
	}

	/**
	 * 增大文本水平间距
	 * 
	 * @param view
	 *            TextView | Button | EditText
	 * @param letterSpace
	 *            字间距 [-0.5, 4F] 之间较为合适， 精度为 0.001F
	 */
	public static void addLetterSpacing(View view, String text,
										float letterSpace) {
		if ((view == null) || (text == null)) {
			return;
		}
		if (letterSpace == 0F) {
			/* 0 没有效果， 0.001F是最接近0 的 数了，在小一些，也就没有效果了 */
			letterSpace = 0.001F;
		}
		/*
		 * 先把 String 拆成 字符数组，在每个字符后面添加一个空格，并对这个进来的空格进行 X轴上 缩放
		 */
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			builder.append(text.charAt(i));
			if (i + 1 < text.length()) {
				builder.append("\u00A0");
			}
		}
		SpannableString finalText = new SpannableString(builder.toString());
		for (int i = 1; (builder.toString().length() > 1)
				&& (i < builder.toString().length()); i += 2) {
			finalText.setSpan(new ScaleXSpan(letterSpace), i, i + 1,
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		if (view instanceof TextView) {
			TextView textView = (TextView) view;
			textView.setText(finalText, TextView.BufferType.SPANNABLE);
		}
		if (view instanceof Button) {
			Button button = (Button) view;
			button.setText(finalText, TextView.BufferType.SPANNABLE);
		}
		if (view instanceof EditText) {
			EditText editText = (EditText) view;
			editText.setText(finalText, TextView.BufferType.SPANNABLE);
		}
	}

	public static SpannableString returnaddLetterSpacingString(String text, float letterSpace) {
		if (letterSpace == 0F) {
			/* 0 没有效果， 0.001F是最接近0 的 数了，在小一些，也就没有效果了 */
			letterSpace = 0.001F;
		}
		/*
		 * 先把 String 拆成 字符数组，在每个字符后面添加一个空格，并对这个进来的空格进行 X轴上 缩放
		 */
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			builder.append(text.charAt(i));
			if (i + 1 < text.length()) {
				builder.append("\u00A0");
			}
		}
		SpannableString finalText = new SpannableString(builder.toString());
		for (int i = 1; (builder.toString().length() > 1)
				&& (i < builder.toString().length()); i += 2) {
			finalText.setSpan(new ScaleXSpan(letterSpace), i, i + 1,
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		return finalText;
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
