package com.example.siyaceramic.home.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.siyaceramic.R
import com.example.siyaceramic.databinding.RowProductItemBinding
import com.example.siyaceramic.home.ui.home.model.BestProductItem

class ProductAdapter (val context: Context, private val showList: ShowList) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>(), Filterable {
    var objList: ArrayList<BestProductItem> = ArrayList()
    var originalObjList: ArrayList<BestProductItem> = ArrayList()
    var sortFilterName: String = ""



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductAdapter.ViewHolder {
        val binding: RowProductItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.row_product_item, viewGroup, false
        )
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val getTitleItem = objList.get(position)
        val sizeItem = objList.get(position).productSizes
        holder.itemRowBinding.txtProductItem.text = getTitleItem.title
        holder.itemRowBinding.txtSizeItem.text = sizeItem?.size
        holder.itemRowBinding.txtCatItem.text = getTitleItem.category?.title

//        holder.itemRowBinding.ivProduct.setImageResource(getTitleItem.catImage)
        Glide.with(context).load(getTitleItem.image.toString())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).timeout(6000)
            .placeholder(R.drawable.places)
            .centerCrop().into(holder.itemRowBinding.ivProduct)

        holder.itemRowBinding.ivAdd.setOnClickListener {
            showList.onItemClickList(getTitleItem)
        }

    }

    fun addData(mObjList: ArrayList<BestProductItem>) {
        objList = ArrayList()
        originalObjList = ArrayList()
        if (mObjList.size > 0) objList.addAll(mObjList)
        if (mObjList.size > 0) originalObjList.addAll(mObjList)
        notifyDataSetChanged()
    }

    fun addFilterData(mObjList: ArrayList<BestProductItem>) {
        objList = ArrayList()
        if (mObjList.size > 0) objList.addAll(mObjList)
        notifyDataSetChanged()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return objList.size
    }

    inner class ViewHolder(var itemRowBinding: RowProductItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        fun bind(obj: Any?) {
            itemRowBinding.executePendingBindings()
        }
    }

    interface ShowList {
        fun showList(showList: Boolean)
        fun onItemClickList(getTitleItem: BestProductItem)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filterList: ArrayList<BestProductItem> = ArrayList()
//                Log.d("results", "constraint -> "+constraint)
                if (constraint.isNullOrBlank()) {
//                    Log.d("results", "Awaiting -> 1")
                    filterList.addAll(originalObjList)
                } else{
//                    Log.d("results", "Awaiting -> 3")
                    val yourArray: List<String> = constraint.split(", ")

                    val firstListObjectIds = yourArray.map { it.trim() }.toSet()


                    firstListObjectIds.map {
                        originalObjList.filter { it1 ->
                            val catProperty = it1.title
                            val pincode =if(catProperty!!.isNotBlank()) catProperty else ""
                            val dateVal =it1.title?:""
                            dateVal.contains(
                                constraint,
                                ignoreCase = true
                            )|| pincode.contains(
                                constraint,
                                ignoreCase = true
                            )
                        }.also { filteredList ->
//                            Log.d("results", "4" + frilteredList.toString())
                            if (filteredList.isEmpty()) {
                                filterList.clear()
                            } else {
                                filterList.clear()
                                filterList.addAll(filteredList)
                            }
                        }
                    }
                }

                if (sortFilterName == "mostRecent" || sortFilterName == "highestMatch") {
                    when (sortFilterName) {
                        "mostRecent" -> {

                            filterList.sortByDescending { student -> student.title}
                        }
                        "highestMatch" -> {
                            filterList.sortByDescending { student -> student.title }
                        }

                    }
//                    Log.d("results", "Awaiting -> 7" + filterList.toString())
                }

                filterResults.values = filterList
                filterResults.count = filterList.size
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                Log.d("results", "awaiting is::" + (results?.values?.toString() ?: ""))
                if (results?.count ?: 0 <= 0) {
                    showList.showList(false)
                    addFilterData(results?.values as ArrayList<BestProductItem>)
                    // Log.e("results", "empty")
                } else {
                    showList.showList(results?.count ?: 0 > 0)
                    addFilterData(results?.values as ArrayList<BestProductItem>)
                    //Log.e("results", "" + results.count + "" + constraint)
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
}