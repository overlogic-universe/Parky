package com.lucky7.parky.core.callback;

public interface RepositoryCallback<T> {
    void onSuccess(T result);
    void onError(Exception e);
}

