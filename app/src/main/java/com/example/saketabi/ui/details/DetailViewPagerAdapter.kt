package com.example.saketabi.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.saketabi.data.LabelData
import com.google.gson.Gson
import com.star_zero.eternalviewpager.EternalPagerAdapter
import java.util.ArrayList

class DetailViewPagerAdapter : EternalPagerAdapter<Int>{

    constructor(fragmentManager : FragmentManager, initialKey : Int, data : List<LabelData>) : super(fragmentManager,initialKey){
        mData = data
        END = data.count() - 1
    }

    private var END : Int = 0
    private var START : Int = 0
    var mData : List<LabelData>

    override fun getItem(key: Int?): Fragment {
        val fragment = DetailViewPage()
        val bundle = Bundle()
        val gson = Gson()
        bundle.putString("data",gson.toJson(mData[key!!],LabelData::class.java))
        fragment.arguments = bundle
        return fragment
    }

    override fun getNextKey(last: Int): Int? {
        if(last == END){
            return START;
        }
        return last + 1
    }

    override fun getPrevKey(first: Int): Int? {
        if(first == START){
            return END;
        }
        return first - 1;
    }

    override fun saveKeysState(keys: ArrayList<Int>): Bundle? {
        val bundle = Bundle()
        bundle.putIntegerArrayList("keys",keys)
        return bundle
    }

    override fun restoreKeysState(bundle: Bundle): MutableList<Int> {
        return bundle.getIntegerArrayList("keys")?.toMutableList()!!
    }
}