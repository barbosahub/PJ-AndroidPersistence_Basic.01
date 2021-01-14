package com.android.retrofit.pj.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cliente {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("erros")
    @Expose
    private List<String> erros = null;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }

}
