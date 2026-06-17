package com.bfhl.bfhlapi.dto;

import java.util.List;

public class RequestDTO {

    private List<String> data;

    public RequestDTO() {
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}