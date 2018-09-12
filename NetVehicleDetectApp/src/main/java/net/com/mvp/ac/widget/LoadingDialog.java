package net.com.mvp.ac.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import net.com.mvp.ac.R;


/**
 * @author lxj
 * @version V1.0
 * @Title: LoadingDialog.java
 * @Package com.herry.shequ.widget
 * @Description: 加载中。。。。
 * @date 2016-1-13 上午8:26:24
 */
public class LoadingDialog extends Dialog {
    private TextView tv_msg;
    private String msg;

    public LoadingDialog(Context context, String msg) {
        super(context, R.style.loaddialog);
        this.msg = msg;
        // TODO Auto-generated constructor stub
    }

    public LoadingDialog(Context context, int theme, String msg) {
        super(context, theme);
        this.msg = msg;
    }

    /**
     * 监听Dialog是否按下返回键 如果按下 就做相应处理
     *
     * @author deyi
     */
    public interface onBackLsn {
        void backlsn();
    }

    onBackLsn lsn;

    public void setBackLsn(onBackLsn lsn) {
        this.lsn = lsn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.dialog_load);
        tv_msg = (TextView) findViewById(R.id.msg);
        tv_msg.setText(msg);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (lsn != null) {
                        lsn.backlsn();
                    }
                }
                return false; // 默认返回 false
            }
        });
    }


    // 使用方法
    // LoadDialog loadDialog = new LoadDialog(MainActivity.this, "加载中...");
    // 返回键的监听
    // loadDialog.setBackLsn(new onBackLsn() {
    //
    // @Override
    // public void backlsn() {
    // Toast.makeText(getApplicationContext(), "11111111111", 2000)
    // .show();
    //
    // }
    // });
    // loadDialog.show();
}
