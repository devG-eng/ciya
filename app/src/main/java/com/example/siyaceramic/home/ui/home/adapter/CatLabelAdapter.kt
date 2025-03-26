package com.example.siyaceramic.home.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setBackground
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.siyaceramic.R
import com.example.siyaceramic.databinding.RowLabelItemBinding
import com.example.siyaceramic.home.ui.home.model.SizesItem
import com.example.siyaceramic.home.ui.home.model.CategoryItem

class CatLabelAdapter (val context: Context, private val showList: ShowList) :
    RecyclerView.Adapter<CatLabelAdapter.ViewHolder>() {
    var objList: ArrayList<SizesItem> = ArrayList()
    var originalObjList: ArrayList<SizesItem> = ArrayList()
    var selectedItem : String? = null
    var selectedPosition = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CatLabelAdapter.ViewHolder {
        val binding: RowLabelItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.row_label_item, viewGroup, false
        )
        return ViewHolder(binding)
    }

    //this method is binding the data on the list

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val getTitleItem = objList.get(holder.adapterPosition)

        holder.itemRowBinding.txtCatItem.text = getTitleItem.size

        selectedItem = getTitleItem.size.toString()
        if(selectedPosition == position) {
            if(position == 0){
                selectedItem = getTitleItem.size.toString()
                showList.onItemCatClickList(getTitleItem)
                showList.onItemSizeList(getTitleItem.size.toString())
            }
            holder.itemRowBinding.rrMain.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_cat_blue_corner))
            holder.itemRowBinding.txtCatItem.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            TextViewCompat.setTextAppearance(holder.itemRowBinding.txtCatItem, R.style.txtSemiBold_14Style)
        }
        else{
            holder.itemRowBinding.rrMain.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_cat_corner))
            holder.itemRowBinding.txtCatItem.setTextColor(ContextCompat.getColor(context, R.color.text_color))
            TextViewCompat.setTextAppearance(holder.itemRowBinding.txtCatItem, R.style.txtSemiBold_14Style)
        }
        holder.itemRowBinding.rrMain.setOnClickListener {
            avoidDoubleClicks(holder.itemRowBinding.rrMain)
            selectedPosition = position;
            selectedItem = getTitleItem.size.toString()
            notifyDataSetChanged();
            showList.onItemCatClickList(getTitleItem)
            showList.onItemSizeList(getTitleItem.size!!)

        }



    }

    fun avoidDoubleClicks(view: View) {
        val DELAY_IN_MS: Long = 1000
        if (!view.isClickable) {
            return
        }
        view.isClickable = false
        view.postDelayed({ view.isClickable = true }, DELAY_IN_MS)
    }
    fun addData(mObjList: ArrayList<SizesItem>) {
        objList = ArrayList()
        originalObjList = ArrayList()
        if (mObjList.size > 0) objList.addAll(mObjList)
        if (mObjList.size > 0) originalObjList.addAll(mObjList)
        notifyDataSetChanged()
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return objList.size
    }

    inner class ViewHolder(var itemRowBinding: RowLabelItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        fun bind(obj: Any?) {
            itemRowBinding.executePendingBindings()
        }
    }


    interface ShowList {
        fun showList(showList: Boolean)
        fun onItemCatClickList(getTitleItem: SizesItem)
        fun onItemSizeList(size: String?)
    }

    fun getSize():String?{
        return selectedItem
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
}