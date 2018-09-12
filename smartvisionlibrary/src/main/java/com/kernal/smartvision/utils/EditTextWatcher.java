package com.kernal.smartvision.utils;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kernal.smartvision.adapter.VinParseResultAdapter;
import com.kernal.smartvision.ocr.Devcode;
import com.kernal.vinparseengine.VinParseInfo;

import java.util.HashMap;
import java.util.List;

public class EditTextWatcher implements TextWatcher {

	private EditText numberEditText;
	private String txt;
	int beforeLen = 0;
	int afterLen = 0;
	double screenInches;
	private Context context;
	private int width,height;
	private VinParseInfo vpi;
	private ListView list;
	private List<HashMap<String, String>> vinInfo;
	private VinParseResultAdapter VPRadapter;
	private boolean isfirst = true;
	public EditTextWatcher(Context context,EditText numberEditText, double screenInches , VinParseResultAdapter VPRadapter) {
		super();
		this.numberEditText = numberEditText;
		this.screenInches=screenInches;
		this.VPRadapter =  VPRadapter;
		this.context=context;
		vpi = new VinParseInfo(context);
	}

	public String removeAllSpace(String str) {
		String tmpstr = str.replace("\u00A0", "");
		tmpstr = tmpstr.replace(" ", "");
		return tmpstr;
		
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		txt = numberEditText.getText().toString();
		afterLen = txt.length();
		txt=removeAllSpace(txt).trim();		
		txt = change(txt);
		if(!"".equals(txt)&&txt!=null){
			 vpi = new VinParseInfo(context);
	    	 vinInfo =vpi.getVinParseInfo(Devcode.devcode,txt);
	    	 VPRadapter.SetData(vinInfo);	
	    	 VPRadapter.notifyDataSetChanged();
		}		
		if (afterLen > beforeLen) {		
			SpannableString txt01 = null;
			if(screenInches<=5.5){
			    txt01= ViewUtil.returnaddLetterSpacingString(txt, 2);
			}else if(screenInches>=7 &&screenInches<9){
				 txt01= ViewUtil.returnaddLetterSpacingString(txt, 2.7f);
			}else if(screenInches>=9){
				 txt01= ViewUtil.returnaddLetterSpacingString(txt, 2.85f);
			}else if(screenInches>5.5&&screenInches<=6.2){
				txt01= ViewUtil.returnaddLetterSpacingString(txt, 2);
			}else if(screenInches>6.2&&screenInches<7){
				txt01= ViewUtil.returnaddLetterSpacingString(txt, 2.5f);
			}			
			    numberEditText.setText(txt01, TextView.BufferType.SPANNABLE);
				numberEditText.setSelection(txt01.length());						
		}else { 			
            if (txt.startsWith(" ")) {  
                numberEditText.setText(new StringBuffer(txt).delete(
                        afterLen - 1, afterLen).toString());  
                numberEditText.setSelection(numberEditText.getText()  
                        .length());  
                
            }  
        }  
//		 System.out.println("识别结果:"+removeAllSpace(txt).trim());
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								  int arg3) {
		beforeLen = arg0.length();
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		String onchangetxt = arg0.toString();

	}
	//  字母大小写转换
	 public static String change(String str) {
		  char UPchange[] = new char[str.length()];
		  for(int i=0;i<str.length();i++){
		   
		   char charType = str.charAt(i);
		   if(charType>='a' && charType<='z')
			   charType = (char) (charType-32);		   
		   	   UPchange[i] = charType;
		  }
		  return new String(UPchange);
	 }
}
