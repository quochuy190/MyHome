package com.vn.myhome.callback;


public interface OnListenerItemClickObjService<T> {
    void onClickListener(T obj);

    void onItemAddImageClick(int position, String type);

    void doShowImageZom(int position, String type);
}