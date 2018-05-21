package com.birfincankafein.mixdialog;

import android.widget.CompoundButton;

/**
 * CheckItem is an item that represent the Checkbox item or RadioButton based on
 * {@link CheckItemGroup}
 *
 * @author Metehan Toksoy
 */
public class CheckItem {
    /**
     * View id counter. {@link android.view.View.generateViewId()} not usable API bellow 17.
     */
    private static int idCounter = 1000;

    /**
     * Text of this CheckItem
     */
    protected String mText;

    /**
     * Checked status of this CheckItem
     */
    protected boolean mChecked;

    /**
     * View id of this CheckItem
     */
    protected final int id;

    /**
     * Corresponding view to this CheckItem
     */
    protected CompoundButton mCheckView;

    protected CheckItem(String text, boolean checked){
        this.mText = text;
        this.mChecked = checked;
        id = ++idCounter;
    }

    /**
     * Gets this CheckItem's text
     * @return Text of this CheckItem
     */
    public String getText() {
        return mText;
    }

    /**
     * Gets this CheckItem's status
     * @return Check status of this CheckItem
     */
    public boolean isChecked() {
        mChecked = mCheckView.isChecked();
        return mChecked;
    }

    /**
     * Sets this CheckItem's status
     * @param checked True to check this CheckItem, false otherwise
     */
    public void setChecked(boolean checked){
        if(mCheckView != null) {
            mCheckView.setChecked(checked);
        }
        mChecked = checked;
    }
}
