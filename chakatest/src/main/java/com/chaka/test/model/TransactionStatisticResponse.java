package com.chaka.test.model;

import lombok.Data;

@Data
public class TransactionStatisticResponse {
    private String sum;
    private String avg;
    private String min;
    private String max;
    private long count;

    public TransactionStatisticResponse() { }

    public TransactionStatisticResponse(String sum, String avg, String min, String max, long count) {
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.count = count;
    }
}
