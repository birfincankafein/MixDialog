package com.birfincankafein.mixdialog;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * InputItem is an item that represent the input area. This item will be represented as
 * {@link EditText} inside {@link TextInputLayout}
 *
 * @author Metehan Toksoy
 */
public class InputItem {
    /**
     * Hint text of this InputItem
     */
    protected String mHint;

    /**
     * Default value of this InputItem
     */
    protected String mValue;

    /**
     * Corresponding EditText of this CheckItem
     */
    protected EditText mEditText;

    /**
     * Corresponding TextInputLayout of this CheckItem's EditText.
     */
    protected TextInputLayout mTextInputLayout;

    public InputItem(String hint, String value){
        this.mHint = hint;
        this.mValue = value;
    }

    /**
     * Gets the value of this InputItem
     * @return Value of this InputItem
     */
    public String getValue(){
        if(mEditText != null){
            mValue = mEditText.getText().toString();
        }
        return mValue;
    }

    /**
     * Sets an error to this InputItem. Will be displayed in TextInputLayout
     * @param errorMessage Error message of this InputItem.
     */
    public void setError(String errorMessage){
        if(mTextInputLayout != null){
            mTextInputLayout.setErrorEnabled(true);
            mTextInputLayout.setError(errorMessage);
        }
    }

    /**
     * Clears the error of this InputItem.
     */
    public void clearError() {
        if (mTextInputLayout != null) {
            mTextInputLayout.setError(null);
        }
    }
}
