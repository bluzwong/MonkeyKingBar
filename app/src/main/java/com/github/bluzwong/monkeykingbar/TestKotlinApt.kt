package com.github.bluzwong.monkeykingbar

import com.github.bluzwong.monkeykingbar_lib.InjectExtra
import com.github.bluzwong.monkeykingbar_lib.KeepState

/**
 * Created by Bruce-Home on 2016/2/4.
 */
class TestKotlinApt {
    @KeepState(asProperty = true)
    @InjectExtra(asProperty = true)
    var ccf:String = ""

    @InjectExtra(asProperty = true)
    @KeepState(asProperty = true)
    var miku_huya: Int? = 0
}