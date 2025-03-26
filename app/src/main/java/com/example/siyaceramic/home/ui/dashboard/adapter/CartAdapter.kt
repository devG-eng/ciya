package com.example.siyaceramic.home.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.siyaceramic.R
import com.example.siyaceramic.databinding.RowCartItemBinding
import com.example.siyaceramic.util.Cart
import com.example.siyaceramic.util.DataBaseHelper


class CartAdapter(val context: Context, private val showList: ShowList) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    var objList: ArrayList<Cart> = ArrayList()
    var originalObjList: ArrayList<Cart> = ArrayList()
    var sortFilterName: String = ""
//    private var _counter = 0
    private var _stringVal: String? = "0"
    private lateinit var dataBase: DataBaseHelper
    var selectedposition = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartAdapter.ViewHolder {
        val binding: RowCartItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.row_cart_item, viewGroup, false
        )
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        dataBase = DataBaseHelper(context)
        val getTitleItem = objList.get(holder.adapterPosition)
         if(dataBase.CheckIsDataAlreadyInDBorNot(getTitleItem.id!!.toInt()) == true)
         {
             val category = dataBase.getItemCart(getTitleItem.productId!!.toInt())
             holder.itemRowBinding.txtCount.setText(category.cartQty)
             val _counter = category.cartQty!!.toInt()

         }
         else {

        holder.itemRowBinding.txtCount.setText(getTitleItem.cartQty)
        }

        holder.itemRowBinding.txtProItem.text = getTitleItem.cartTitle
        holder.itemRowBinding.txtSizeItem.text = getTitleItem.cartSize
        holder.itemRowBinding.txtCatItem.text = getTitleItem.cartCat


        //onTextChanged
        holder.itemRowBinding.txtCount.doOnTextChanged { text, start, before, count ->
//            Toast.makeText(context,""+text,Toast.LENGTH_SHORT).show()
            val id = objList.get(holder.adapterPosition).productId

            dataBase.updateCarts(
                id,
                holder.itemRowBinding.txtCount.text.toString()
            )
        }

        //after Text Changed
        holder.itemRowBinding.txtCount.doAfterTextChanged {

        }


        holder.itemRowBinding.ivDlt.setOnClickListener {
            objList.removeAt(position)
            notifyDataSetChanged()
            showList.onItemRemoveClickList(getTitleItem, position)
        }

        holder.itemRowBinding.btnMinus.setOnClickListener {
            val category = dataBase.getItemCart(getTitleItem.productId!!.toInt())
            holder.itemRowBinding.txtCount.setText(category.cartQty)
            var _counter = category.cartQty!!.toInt()
            if (_counter <= 0) {
                holder.itemRowBinding.txtCount.setText("0")
            } else {

                _counter--
                _stringVal = Integer.toString(_counter)
                holder.itemRowBinding.txtCount.setText(_stringVal)

                val id = objList.get(holder.adapterPosition).productId


                dataBase.updateCarts(id ,
                    holder.itemRowBinding.txtCount.text.toString()
                )

            }

        }

        holder.itemRowBinding.btnPlus.setOnClickListener {
            val category = dataBase.getItemCart(getTitleItem.productId!!.toInt())
            holder.itemRowBinding.txtCount.setText(category.cartQty)
            var _counter = category.cartQty!!.toInt()
            _counter++
            _stringVal = Integer.toString(_counter)
            holder.itemRowBinding.txtCount.setText(_stringVal)

            val id = objList.get(holder.adapterPosition).productId

            dataBase.updateCarts(id ,
                holder.itemRowBinding.txtCount.text.toString()
            )

        }

        Glide.with(context).load(getTitleItem.cartImg)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).timeout(6000)
            .placeholder(R.drawable.places)
            .centerCrop().into(holder.itemRowBinding.ivPro)
    }

    fun addData(mObjList: ArrayList<Cart>) {
        objList = ArrayList()
        originalObjList = ArrayList()
        if (mObjList.size > 0) objList.addAll(mObjList)
        if (mObjList.size > 0) originalObjList.addAll(mObjList)
        notifyDataSetChanged()
    }

    fun updateData(mObjList: Cart) {
        objList = ArrayList()
        objList.add(mObjList)
        notifyDataSetChanged()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return objList.size
    }

    inner class ViewHolder(var itemRowBinding: RowCartItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        fun bind(obj: Any?) {
            itemRowBinding.executePendingBindings()
        }
    }

    interface ShowList {
        fun showList(showList: Boolean)
        fun onItemClickList(getTitleItem: Cart)
        fun onItemRemoveClickList(getTitleItem: Cart, position: Int)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


}