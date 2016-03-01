package com.github.bluzwong.monkeykingbar

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by bluzwong on 2016/3/1.
 */
class KeepFragment : Fragment()  {

    val dataMap = HashMap<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("mkb", "onCreate " + hashCode())
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        Log.i("mkb", "onResume " + hashCode())
        super.onResume()
    }

    override fun onDetach() {
        Log.i("mkb", "onDetach " + hashCode())
        super.onDetach()
    }

    override fun onAttach(activity: Activity?) {
        Log.i("mkb", "onAttach " + hashCode())
        super.onAttach(activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("mkb", "onCreateView " + hashCode())
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPause() {
        Log.i("mkb", "onPause " + hashCode())
        super.onPause()
    }

    override fun onDestroyView() {
        Log.i("mkb", "onDestroyView " + hashCode())
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i("mkb", "onActivityCreated " + hashCode())
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.i("mkb", "onViewCreated " + hashCode())
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        Log.i("mkb", "onDestroy " + hashCode())
        super.onDestroy()
    }
}