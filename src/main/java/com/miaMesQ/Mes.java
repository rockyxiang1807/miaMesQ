package com.miaMesQ;

/**
 * wenhui.xiang
 * 2023/03/21 2:37 下午
 */

public class Mes<M> {
    int retryCount = 0;
    M value;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public M getValue() {
        return value;
    }

    public void setValue(M value) {
        this.value = value;
    }

    public Mes(M value) {
        this.value = value;
    }
}
