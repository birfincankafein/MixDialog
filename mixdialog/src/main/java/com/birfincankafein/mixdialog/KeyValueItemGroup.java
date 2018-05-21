package com.birfincankafein.mixdialog;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * KeyValueItemGroup is an item group that represent key value pair using TextView and LinearLayout
 *
 * @author Metehan Toksoy
 */
public class KeyValueItemGroup implements ItemGroup {

    /**
     * Attached MixDialog instance
     */
    protected MixDialog attachedMixDialog;

    /**
     * The ratio of key/value views width. Default 1.5
     */
    protected float mKeyValueRatio = 1.5f;
    /**
     * All key value items.
     */
    protected LinkedHashMap<String, KeyValueItem> mKeyValueItems;

    /**
     * Name of the this group. Can be used as title based on {@link KeyValueItemGroup#showGroupNameAsHeader}.
     */
    protected String groupName;

    /**
     * If true the {@link KeyValueItemGroup#groupName} will be shown as title.
     */
    protected boolean showGroupNameAsHeader;

    private KeyValueItemGroup(String groupName, boolean showGroupNameAsHeader, float keyValueRatio, LinkedHashMap<String, KeyValueItem> keyValueItems){
        this.mKeyValueItems = keyValueItems;
        this.groupName = groupName;
        this.showGroupNameAsHeader = showGroupNameAsHeader;
        this.mKeyValueRatio = keyValueRatio;
    }

    /**
     * Getter for all key-value items in this group.
     * @return All key-value items inside this group.
     */
    @Override
    public List<KeyValueItem> getItems() {
        return new ArrayList<KeyValueItem>(mKeyValueItems.values());
    }

    /**
     * Getter for group name
     * @return groupName of this ItemGroup
     */
    @Override
    public String getGroupName() {
        return groupName;
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
        private LinkedHashMap<String, KeyValueItem> keyValueItems = new LinkedHashMap<String, KeyValueItem>();
        private Context mContext;
        private boolean showGroupNameAsHeader = false;
        private float mKeyValueRatio = 1.5f;

        /**
         * Creates a builder for a KeyValueItemGroup.
         * @param context The parent context
         * @param groupName The group name of building KeyValueItemGroup
         */
        public Builder(Context context, String groupName) {
            this.mContext = context;
            this.groupName = groupName;
        }

        /**
         * Creates a builder for a KeyValueItemGroup with MixDialog.
         * @param mixDialogBuilder The parent builder that will be returned with {@link Builder#buildWithParent()}
         * @param groupName The group name of building KeyValueItemGroup
         */
        public Builder(MixDialog.Builder mixDialogBuilder, String groupName) {
            this.mContext = mixDialogBuilder.params.mContext;
            this.mixDialogBuilder = mixDialogBuilder;
            this.groupName = groupName;
        }

        /**
         * Setter for show group name as header flag.
         * @param showGroupNameAsHeader If true the {@link KeyValueItemGroup#groupName} will be shown as title
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setShowGroupNameAsHeader(boolean showGroupNameAsHeader){
            this.showGroupNameAsHeader = showGroupNameAsHeader;
            return this;
        }

        /**
         * Add KeyValueItem to this group with no default value.
         * @param key Key text of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItem(String key){
            return this.addKeyValueItem(key, null);
        }

        /**
         * Add KeyValueItem to this group.
         * @param key Key text of the InputItem
         * @param defaultValue Default value text of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItem(String key, String defaultValue){
            keyValueItems.put(key, new KeyValueItem(key, defaultValue));
            return this;
        }

        /**
         * Add KeyValueItem to this group with no default value.
         * @param keyResourceId Key text resource of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItem(@StringRes int keyResourceId){
            return this.addKeyValueItem(mContext.getResources().getString(keyResourceId));
        }

        /**
         * Add KeyValueItem to this group.
         * @param keyResourceId Key text resource of the InputItem
         * @param defaultValueResourceId Default value text resource of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItem(@StringRes int keyResourceId, @StringRes int defaultValueResourceId){
            return this.addKeyValueItem(mContext.getResources().getString(keyResourceId),
                    mContext.getResources().getString(defaultValueResourceId));
        }

        /**
         * Add KeyValueItems to this group with no default value.
         * @param keys Key texts of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItems(String[] keys){
            for(int i=0 ; i<keys.length ; i++){
                this.addKeyValueItem(keys[i]);
            }
            return this;
        }

        /**
         * Add KeyValueItems to this group.
         * @param keys Key texts of the KeyValueItem
         * @param defaultValues Default value texts of the InputItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItems(String[] keys, String[] defaultValues){
            for(int i=0 ; i<keys.length ; i++){
                this.addKeyValueItem(keys[i], defaultValues[i]);
            }
            return this;
        }

        /**
         * Add KeyValueItems to this group with no default value.
         * @param keyResourceIds Key texts resource of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItems(@ArrayRes int keyResourceIds) {
            return addKeyValueItems(mContext.getResources().getStringArray(keyResourceIds));
        }

        /**
         * Add KeyValueItems to this group with.
         * @param keyResourceIds Key texts resource of the KeyValueItem
         * @param defaultValueResourceIds Default value texts resource of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItems(@ArrayRes int keyResourceIds, @ArrayRes int defaultValueResourceIds){
            return addKeyValueItems(mContext.getResources().getStringArray(keyResourceIds),
                    mContext.getResources().getStringArray(defaultValueResourceIds));
        }

        /**
         * Add KeyValueItems to this group with no default value.
         * @param keys Key texts of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItems(List<String> keys){
            for(int i=0 ; i<keys.size() ; i++){
                this.addKeyValueItem(keys.get(i));
            }
            return this;
        }

        /**
         * Add KeyValueItems to this group.
         * @param keys Key texts of the KeyValueItem
         * @param defaultValues Default value texts of the KeyValueItem
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addKeyValueItems(List<String> keys, List<String> defaultValues){
            for(int i=0 ; i<keys.size() ; i++){
                this.addKeyValueItem(keys.get(i), defaultValues.get(i));
            }
            return this;
        }

        /**
         * Set the ratio of key/value views width. Default 1.5
         * @param keyValueWidthRatio The ratio of key/value views width
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setKeyValueWidthRatio(float keyValueWidthRatio){
            this.mKeyValueRatio = keyValueWidthRatio;
            return this;
        }

        /**
         * Build InputItemGroup with these values.
         * @return created InputItemGroup
         */
        public KeyValueItemGroup build(){
            return new KeyValueItemGroup(groupName, showGroupNameAsHeader, mKeyValueRatio, keyValueItems);
        }

        /**
         * Build KeyValueItemGroup with these values and return {@link MixDialog.Builder} instance.
         * @return {@link MixDialog.Builder} object to allow for chaining of calls to set methods
         */
        public MixDialog.Builder buildWithParent(){
            return mixDialogBuilder.addKeyValueItemGroup(this.build());
        }
    }
}
