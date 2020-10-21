package com.example.saketabi.ui.home

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.RecyclerView
import com.example.saketabi.MainActivity
import com.example.saketabi.R
import com.example.saketabi.data.LabelData
import com.facebook.drawee.view.SimpleDraweeView

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private var mData : List<LabelData>
    private var mParent : MainActivity

    public constructor(parent : MainActivity, data : List<LabelData>) : super(){
        mData = data
        mParent = parent
    }

    override fun getItemCount(): Int {
        return if(mData.count() == 0)0 else (mData.count() - 1) /  3 + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        val vh = ViewHolder(inflate)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var index = position * 3
        holder.drawee1.setImageBitmap(BitmapFactory.decodeStream(mParent.openFileInput(mData[index].filePath)))
        holder.drawee1.setOnClickListener {
            mParent.navigateHomeToDetails(mData,index)
        }
        val index2 = index + 1
        if(index2 < mData.count()) {
            holder.drawee2.setImageBitmap(BitmapFactory.decodeStream(mParent.openFileInput(mData[index2].filePath)))
            holder.drawee2.setOnClickListener {
                mParent.navigateHomeToDetails(mData, index2)
            }
        }
        val index3 = index + 1
        if(index3 < mData.count()) {
            holder.drawee3.setImageBitmap(BitmapFactory.decodeStream(mParent.openFileInput(mData[index3].filePath)))
            holder.drawee3.setOnClickListener {
                mParent.navigateHomeToDetails(mData,index3)
            }
        }
    }


    inner class ViewHolder : RecyclerView.ViewHolder{
        var drawee1 : SimpleDraweeView
        var drawee2 : SimpleDraweeView
        var drawee3 : SimpleDraweeView

         constructor(itemView : View) : super(itemView){
            drawee1 = itemView.findViewById(R.id.imageView1)
            drawee2 = itemView.findViewById(R.id.imageView2)
            drawee3 = itemView.findViewById(R.id.imageView3)
        }
    }
}