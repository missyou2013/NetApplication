package net.com.mvp.ac.widget;

/**
 * Created by Administrator on 2017/6/27.
 * <p>
 * 界面需要实现的接口
 */

public interface IEditTextChangeListener {

    /**
     * @param isHasContent 是否所有的edittextview都有了内容
     */

    void textChange(boolean isHasContent);
}
