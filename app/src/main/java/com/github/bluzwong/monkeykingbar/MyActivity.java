package com.github.bluzwong.monkeykingbar;

import android.app.Activity;
import com.github.bluzwong.monkeykingbar_lib.InjectExtra;
import com.github.bluzwong.monkeykingbar_lib.KeepState;

/**
 * Created by bluzwong on 2016/2/2.
 */
public class MyActivity extends Activity {

    @InjectExtra
    @KeepState
    int foo;

    //    @UnSerializable
    @InjectExtra
    @KeepState
    String bar;

    //@UnSerializable
    @InjectExtra
    @KeepState(asProperty = true)
    MyClass myClass;

    public MyClass getMyClass() {
        //com.github.bluzwong.monkeykingbar.MyClass m = null;
        return myClass;
    }

    public void setMyClass(MyClass myClass) {
        this.myClass = myClass;
    }
}
