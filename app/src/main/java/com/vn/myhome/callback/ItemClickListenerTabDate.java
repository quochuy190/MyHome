package com.vn.myhome.callback;

public interface ItemClickListenerTabDate<T> {
    void onClickItem(int position, T item);
    void onClickItem_Lock(int position, T item);
}
