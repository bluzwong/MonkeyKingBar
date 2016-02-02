package com.github.bluzwong.monkeykingbar;

import android.content.Intent;
import android.os.Bundle;
import com.github.bluzwong.monkeykingbar_lib.MKBUtils;

/**
 * Created by wangzhijie on 2016/1/23.
 */
public class MyClass {
    public String content;

    public MyClass() {
    }

    public MyClass(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (content == null) return false;
        if (o instanceof MyClass) {
            return content.equals(((MyClass) o).content);
        }
        return false;
    }
}
