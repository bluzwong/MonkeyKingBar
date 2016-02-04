package com.github.bluzwong.monkeykingbar

import com.github.bluzwong.monkeykingbar_lib.InjectExtra
import com.github.bluzwong.monkeykingbar_lib.KeepState

/**
 * Created by Bruce-Home on 2016/2/4.
 */
class TestKotlinApt {
    @KeepState
    @InjectExtra
    lateinit var ccf:String

    @InjectExtra
    @KeepState
    @JvmField
    var miku_huya: Int? = 0
}