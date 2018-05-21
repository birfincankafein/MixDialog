package com.birfincankafein.mixdialog;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * KeyValueItem is an item that represent the key value area. This item will be represented as
 * {@link TextView} inside the {@link LinearLayout}
 *
 * @author Metehan Toksoy
 */
public class KeyValueItem {
    /**
     * Value of this KeyValueItem
     */
    protected String mValue;

    /**
     * Key of this KeyValueItem
     */
    protected String mKey;

    /**
     * Corresponding TextView of this KeyValueItem's key
     */
    protected TextView mTextView_Key;

    /**
     * Corresponding TextView of this KeyValueItem's value
     */
    protected TextView mTextView_Value;

    public KeyValueItem(String key, String value){
        this.mKey = key;
        this.mValue = value;
    }

    /**
     * Gets the value of this KeyValueItem
     * @return Value of this KeyValueItem
     */
    public String getValue(){
        if(mTextView_Value != null){
            mValue = mTextView_Value.getText().toString();
        }
        return mValue;
    }

    /**
     * Gets the key of this KeyValueItem
     * @return Key of this KeyValueItem
     */
    public String getKey(){
        if(mTextView_Key != null){
            mKey = mTextView_Key.getText().toString();
        }
        return mKey;
    }

    public void setValue(String value){
        mValue = value;
        if(mTextView_Value != null){
            mTextView_Value.setText(mValue);
        }
    }
}
