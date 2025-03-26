package com.example.siyaceramic.home.ui.dashboard

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.databinding.ActivityProductsBinding
import com.example.siyaceramic.home.ui.dashboard.adapter.ProductsAdapter
import com.example.siyaceramic.home.ui.dashboard.model.ProductData
import com.example.siyaceramic.home.ui.dashboard.model.ProductResponse
import com.example.siyaceramic.home.ui.dashboard.viewmodel.ProductViewModel
import com.example.siyaceramic.home.ui.home.model.Category
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.productdetailAPI
import com.example.siyaceramic.network.productsAPI
import com.example.siyaceramic.util.Cart
import com.example.siyaceramic.util.DataBaseHelper
import com.example.siyaceramic.util.TxtUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ProductsActivity : BaseActivity<ActivityProductsBinding, ProductViewModel>(),
    ProductsAdapter.ShowList,
    SearchView.OnQueryTextListener {
    override val layout: Int
        get() = R.layout.activity_products
    override val viewModel: ProductViewModel by viewModel()
    var productList = ArrayList<ProductData>()
    private var _counter = 0
    private var _stringVal: String? = "0"
    val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(this, this)
    }
    var dialogCart: BottomSheetDialog? = null
    var objList: ArrayList<Category> = ArrayList()
    var catTitle: String? = ""
    var sizeId: Int? = 0
    var catId: Int? = 0
    var categoryId: Int? = 0
    private lateinit var dataBase: DataBaseHelper
    var isFromHome = false
    var totalCount: Int = 0
    var userId : Int = 0
    var isRecursionEnable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserver()
    }

    private fun initView() {
//        userId = viewModel.getUserProfileData()?.data?.id!!

        try {
            dataBase = DataBaseHelper(this)
            catTitle = intent.extras?.getString("PRODUCT")
            sizeId = intent.extras?.getInt("SIZEID")
            catId = intent.extras?.getInt("CATID")
            categoryId = intent.extras?.getInt("CATEGORYID")
            isFromHome = intent.getBooleanExtra("ISFROMHOME", false)
            countTotal()
//            runInBackground()
            Log.d("CATT", categoryId.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.searchView.setOnQueryTextListener(this)
        /* proItem.add(Category("Title", R.drawable.ic_tiles))
         proItem.add(Category("Title1", R.drawable.ic_tiles1))
         proItem.add(Category("Title2", R.drawable.ic_tiles6))
         proItem.add(Category("Title3", R.drawable.ic_tiles7))
         proItem.add(Category("Title4", R.drawable.ic_tiles5))
         proItem.add(Category("Title5", R.drawable.ic_tiles8))
         proItem.add(Category("Title6", R.drawable.ic_tiles))
         proItem.add(Category("Title7", R.drawable.ic_tiles1))
         proItem.add(Category("Title8", R.drawable.ic_tiles6))
         proItem.add(Category("Title9", R.drawable.ic_tiles7))
         proItem.add(Category("Title10", R.drawable.ic_tiles5))
         proItem.add(Category("Title11", R.drawable.ic_tiles8))
         productsAdapter.addData(proItem)*/
        binding.rvProducts.adapter = productsAdapter



//        Toast.makeText(this,""+userId,Toast.LENGTH_SHORT).show()
//        countTotal()

        binding.rrBack.setOnClickListener {
            finish()
        }

        binding.rrCart.setOnClickListener {
            CartActivity.start(this)
        }


        if (isFromHome) {
            //From Home
            callApi()
        } else {
            //From Category
            callCatApi()
        }


    }

    fun runInBackground() {
        if (!isRecursionEnable) // Handle not to start multiple parallel threads
            return

        // isRecursionEnable = false; when u want to stop
        // on exception on thread make it true again
        Thread {
            // DO your work here
            // get the data
            countTotal()
        }.start()
    }

    private fun countTotal() {
        totalCount = dataBase.totalCount(viewModel.getUserProfileData()?.data?.id!!)

        if (totalCount > 0) {
            binding.rrCount.visibility = View.VISIBLE
            binding.titleCount.text = totalCount.toString()
        } else {
            binding.rrCount.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        countTotal()
    }

    private fun callApi() {
//        catTitle?.let { viewModel.getProductApi(it,true) }
        viewModel.getProductDetails(sizeId!!, catId!!, true)
    }

    private fun callCatApi() {
        viewModel.getProductsDetails(categoryId!!, true)
    }

    private fun initObserver() {

        viewModel.getUiState().observe(this) {
            when (it) {
                is ResourceStatus.Loading<*> -> {
                    binding.progressBar.progressBar.visibility = View.VISIBLE
                    binding.progressBar.ivCelebration.visibility = View.VISIBLE
//                    binding.progressBar.ivCelebration.playAnimation()
                }
                is ResourceStatus.Success -> {
                    binding.progressBar.progressBar.visibility = View.GONE
                    when (it.apiName) {

                        productdetailAPI -> {
                            val response = it.myResp as ProductResponse
                             binding.txtNoData.visibility = View.GONE
                            if (response.data != null && response.data.isNotEmpty()) {
                                productList.clear()
                                productList.addAll(response.data as ArrayList<ProductData>)

                                for (i in 0 until response.data.size) {
                                    productList.let { it1 ->
                                        productsAdapter.addData(
                                            it1
                                        )
                                    }
                                }
                                productsAdapter.notifyDataSetChanged()


                            } else {
                                  binding.txtNoData.visibility = View.VISIBLE
//                                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
                            }


                        }

                        productsAPI -> {
                            val response = it.myResp as ProductResponse
                             binding.txtNoData.visibility = View.GONE
                            if (response.data != null && response.data.isNotEmpty()) {
                                productList.clear()
                                productList.addAll(response.data as ArrayList<ProductData>)

                                for (i in 0 until response.data.size) {
                                    productList.let { it1 ->
                                        productsAdapter.addData(
                                            it1
                                        )
                                    }
                                }
                                productsAdapter.notifyDataSetChanged()


                            } else {
                                 binding.txtNoData.visibility = View.VISIBLE
//                                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
                            }


                        }
                    }

                }


                is ResourceStatus.Error -> {
                    val error = it.throwable as Throwable
                    binding.progressBar.progressBar.visibility = View.GONE
                    TxtUtils.hideKeyBoard(this, binding.root)
                    when (it.apiName) {
                        productdetailAPI -> {
                            Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
                        }
                        productsAPI -> {
                            Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                else -> {
                    binding.progressBar.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun showList(showList: Boolean) {

    }

    override fun onItemClickList(getTitleItem: ProductData) {
        dialogCart = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)

        dialogCart?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogCart?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCart?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialogCart?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        //this line is what you need:
        dialogCart?.setContentView(R.layout.row_add_cart_dialog)
        dialogCart?.setCanceledOnTouchOutside(true)

        val ivImage = dialogCart?.findViewById<View>(R.id.ivPro) as AppCompatImageView
        val rrClose = dialogCart?.findViewById<View>(R.id.ivClose) as AppCompatImageView
        val btnMinus = dialogCart?.findViewById<View>(R.id.btnMinus) as ImageButton
        val btnPlus = dialogCart?.findViewById<View>(R.id.btnPlus) as ImageButton
        val txtCount = dialogCart?.findViewById<View>(R.id.txtCount) as AppCompatEditText
        val txtProItem = dialogCart?.findViewById<View>(R.id.txtProItem) as AppCompatTextView
        val txtSizeItem = dialogCart?.findViewById<View>(R.id.txtSizeItem) as AppCompatTextView
        val txtCatItem = dialogCart?.findViewById<View>(R.id.txtCatItem) as AppCompatTextView
        val btnCart = dialogCart?.findViewById<View>(R.id.btnAddCart) as AppCompatButton

        val sizeItem = getTitleItem.productSizes
        txtProItem.text = getTitleItem.title
        txtSizeItem.text = sizeItem?.size
        txtCatItem.text = getTitleItem.category?.title

        Glide.with(this).load(getTitleItem.image.toString())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).timeout(6000)
            .placeholder(R.drawable.places)
            .centerCrop().into(ivImage)

        ivImage.setOnClickListener {
            val i = Intent(
                this,
                ImageViewActivity::class.java
            )
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("url", getTitleItem.image)
            startActivity(i)

        }

        rrClose.setOnClickListener {
            _counter = 0
            countTotal()
            dialogCart?.dismiss()
        }

        btnMinus.setOnClickListener {
            if (_counter <= 0) {
                txtCount.setText("0")
            } else {
                _counter--
                _stringVal = Integer.toString(_counter)
                txtCount.setText(_stringVal)
            }
        }
        btnPlus.setOnClickListener {
            _counter++
            _stringVal = Integer.toString(_counter)
            txtCount.setText(_stringVal)
        }

        if (dataBase.CheckIsDataAlreadyInDBorNot(getTitleItem.id!!.toInt()) == true) {
            val category = dataBase.getItemCart(getTitleItem.id)

            txtCount.setText(category.cartQty)
            _counter = category.cartQty!!.toInt()

        }

        btnCart.setOnClickListener {
            if (txtCount.text!!.equals("0")) {
                Toast.makeText(this, "Please Add Quantity", Toast.LENGTH_SHORT).show()
            } else {
                val sizeItem = getTitleItem.productSizes
                val category = Category()
                category.catName = getTitleItem.title.toString()
                category.catQty = txtCount.text.toString()
                category.catImage = getTitleItem.image.toString()
                category.catSize = sizeItem?.size.toString()
                category.catCat = getTitleItem.category?.title.toString()
                objList.add(category)
                if (dataBase.CheckIsDataAlreadyInDBorNot(getTitleItem.id!!.toInt()) == true) {
                    val categorynew = dataBase.getItemCart(getTitleItem.id)
                    _counter = categorynew.cartQty!!.toInt()

                    dataBase.updateCart(
                        Cart(
                            Objects.requireNonNull<Any>(categorynew.id) as Int,
                            getTitleItem.id.toInt(),
                            viewModel.getUserProfileData()?.data?.id!!,
                            getTitleItem.title.toString(),
                            txtCount.text.toString(),
                            getTitleItem.image.toString(),
                            sizeItem?.size.toString(),
                            getTitleItem.category?.title.toString()
                        )
                    )
                    countTotal()
                } else {
                    val newCart =
                        Cart(
                            getTitleItem.id.toInt(),
                            viewModel.getUserProfileData()?.data?.id!!,
                            getTitleItem.title.toString(),
                            txtCount.text.toString(),
                            getTitleItem.image.toString(),
                            sizeItem?.size.toString(),
                            getTitleItem.category?.title.toString()
                        )
                    dataBase.addCart(newCart)

                }
                _counter = 0
                countTotal()
                dialogCart?.dismiss()
            }
        }
        dialogCart?.setOnDismissListener(DialogInterface.OnDismissListener {
            _counter = 0
        })
        dialogCart?.show()

    }


    companion object {

        fun start(context: Context, catId: Int?) {
            context.startActivity(Intent(context, ProductsActivity::class.java).apply {
                putExtra("CATEGORYID", catId)
            })
        }

        fun start(context: Context, sizeId: Int?, catId: Int?, isFromHome: Boolean) {
            context.startActivity(Intent(context, ProductsActivity::class.java).apply {
                putExtra("SIZEID", sizeId)
                putExtra("CATID", catId)
                putExtra("ISFROMHOME", isFromHome)
            })
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        productsAdapter.filter.filter(query)

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        productsAdapter.filter.filter(newText)

        return true
    }
}