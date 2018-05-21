package com.birfincankafein.mixdialog;

import java.util.List;

public interface ItemGroup {
    <T extends ItemGroup> List<T> getItems();
    String getGroupName();

}
