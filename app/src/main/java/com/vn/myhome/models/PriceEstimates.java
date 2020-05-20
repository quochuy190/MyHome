package com.vn.myhome.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 19-April-2020
 * Time: 11:16
 * Version: 1.0
 */
public class PriceEstimates {
    String id;
    String hanh_trinh_id;
    String loai_xe_id;
    String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHanh_trinh_id() {
        return hanh_trinh_id;
    }

    public void setHanh_trinh_id(String hanh_trinh_id) {
        this.hanh_trinh_id = hanh_trinh_id;
    }

    public String getLoai_xe_id() {
        return loai_xe_id;
    }

    public void setLoai_xe_id(String loai_xe_id) {
        this.loai_xe_id = loai_xe_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
