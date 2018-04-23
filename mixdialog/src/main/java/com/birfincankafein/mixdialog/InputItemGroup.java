package com.birfincankafein.mixdialog;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.widget.LinearLayout;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * InputItemGroup is an item group that represent as input with the EdiTexts inside the TextInputLayout
 *
 * @author Metehan Toksoy
 */
public class InputItemGroup implements ItemGroup {

    /**
     * All input items.
     */
    protected LinkedHashMap<String, InputItem> mInputItems;

    /**
     * Name of the this group. Can be used as title based on {@link InputItemGroup#showGroupNameAsHeader}.
     */
    protected String groupName;

    /**
     * If true the {@link InputItemGroup#groupName} will be shown as title.
     */
    protected boolean showGroupNameAsHeader;

    private InputItemGroup(String groupName, boolean showGroupNameAsHeader, LinkedHashMap<String, InputItem> mInputItems){
        this.mInputItems = mInputItems;
        this.groupName = groupName;
        this.showGroupNameAsHeader = showGroupNameAsHeader;
    }

    /**
     * Builder for a CheckItemGroup
     */
    public static class Builder {
        /**
         * Builder of the MixDialog.
         */
        private MixDialog.Builder mixDialogBuilder;
        private final String groupName;
        private LinkedHashMap<String, InputItem> mInputItems = new LinkedHashMap<String, InputItem>();
        private Context mContext;
        private boolean showGroupNameAsHeader = false;

        /**
         * Creates a builder for a InputItemGroup.
         * @param context The parent context
         * @param groupName The group name of building InputItemGroup
         */
        public Builder(Context context, String groupName) {
            this.mContext = context;
            this.groupName = groupName;
        }

        /**
         * Creates a builder for a InputItemGroup with MixDialog.
         * @param mixDialogBuilder The parent builder that will be returned with {@link Builder#buildWithParent()}
         * @param groupName The group name of building InputItemGroup
         */
        public Builder(MixDialog.Builder mixDialogBuilder, String groupName) {
            this.mContext = mixDialogBuilder.params.mContext;
            this.mixDialogBuilder = mixDialogBuilder;
            this.groupName = groupName;
        }

        /**
         * Setter for show group name as header flag.
         * @param showGroupNameAsHeader If true the {@link InputItemGroup#groupName} will be shown as title
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setShowGroupNameAsHeader(boolean showGroupNameAsHeader){
            this.showGroupNameAsHeader = showGroupNameAsHeader;
            return this;
        }

        /**
         * Add InputItem to this group with no default value.
         * @param hint Hint text of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItem(String hint){
            return this.addInputItem(hint, null);
        }

        /**
         * Add InputItem to this group.
         * @param hint Hint text of the InputItem
         * @param defaultValue Default value text of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItem(String hint, String defaultValue){
            mInputItems.put(hint, new InputItem(hint, defaultValue));
            return this;
        }

        /**
         * Add InputItem to this group with no default value.
         * @param hintResourceId Hint text resource of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItem(@StringRes int hintResourceId){
            return this.addInputItem(mContext.getResources().getString(hintResourceId));
        }

        /**
         * Add InputItem to this group.
         * @param hintResourceId Hint text resource of the InputItem
         * @param defaultValueResourceId Default value text resource of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItem(@StringRes int hintResourceId, @StringRes int defaultValueResourceId){
            return this.addInputItem(mContext.getResources().getString(hintResourceId),
                    mContext.getResources().getString(defaultValueResourceId));
        }

        /**
         * Add InputItems to this group with no default value.
         * @param hints Hint texts of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItems(String[] hints){
            for(int i=0 ; i<hints.length ; i++){
                this.addInputItem(hints[i]);
            }
            return this;
        }

        /**
         * Add InputItems to this group.
         * @param hints Hint texts of the InputItem
         * @param defaultValues Default value texts of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItems(String[] hints, String[] defaultValues){
            for(int i=0 ; i<hints.length ; i++){
                this.addInputItem(hints[i], defaultValues[i]);
            }
            return this;
        }

        /**
         * Add InputItems to this group with no default value.
         * @param hintResourceIds Hint texts resource of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItems(@ArrayRes int hintResourceIds) {
            return addInputItems(mContext.getResources().getStringArray(hintResourceIds));
        }

        /**
         * Add InputItems to this group with.
         * @param hintResourceIds Hint texts resource of the InputItem
         * @param defaultValueResourceIds Default value texts resource of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItems(@ArrayRes int hintResourceIds, @ArrayRes int defaultValueResourceIds){
            return addInputItems(mContext.getResources().getStringArray(hintResourceIds),
                    mContext.getResources().getStringArray(defaultValueResourceIds));
        }

        /**
         * Add InputItems to this group with no default value.
         * @param hints Hint texts of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItems(List<String> hints){
            for(int i=0 ; i<hints.size() ; i++){
                this.addInputItem(hints.get(i));
            }
            return this;
        }

        /**
         * Add InputItems to this group.
         * @param hints Hint texts of the InputItem
         * @param defaultValues Default value texts of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addInputItems(List<String> hints, List<String> defaultValues){
            for(int i=0 ; i<hints.size() ; i++){
                this.addInputItem(hints.get(i), defaultValues.get(i));
            }
            return this;
        }

        /**
         * Build InputItemGroup with these values.
         * @return created InputItemGroup
         */
        public InputItemGroup build(){
            return new InputItemGroup(groupName, showGroupNameAsHeader, mInputItems);
        }

        /**
         * Build InputItemGroup with these values and return {@link MixDialog.Builder} instance.
         * @return {@link MixDialog.Builder} object to allow for chaining of calls to set methods
         */
        public MixDialog.Builder buildWithParent(){
            return mixDialogBuilder.addInputItemGroup(this.build());
        }
    }
}