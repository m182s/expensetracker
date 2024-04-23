package com.example.homepage;

import java.io.Serializable;

public interface SharedDataListener extends Serializable {
    Expenses getSharedData();
    void setSharedData(Expenses value);
}
