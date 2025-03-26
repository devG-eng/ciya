package com.example.siyaceramic.home.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.databinding.ActivityCartBinding
import com.example.siyaceramic.home.ui.dashboard.adapter.CartAdapter
import com.example.siyaceramic.home.ui.home.model.Category
import com.example.siyaceramic.home.ui.home.model.CategoryData
import com.example.siyaceramic.splash.viewmodel.SplashViewModel
import com.example.siyaceramic.util.Cart
import com.example.siyaceramic.util.DataBaseHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLEncoder


class CartActivity : BaseActivity<ActivityCartBinding, SplashViewModel>(), CartAdapter.ShowList {
    override val layout: Int
        get() = R.layout.activity_cart
    override val viewModel: SplashViewModel by viewModel()

    var cartItemList = ArrayList<CategoryData>()
    private var _counter = 0
    val cartAdapter: CartAdapter by lazy {
        CartAdapter(this, this)
    }


    var categoryList = ArrayList<Cart>()
    private lateinit var dataBase: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        dataBase = DataBaseHelper(this)
        /* cartItem.add(Category("Title1", R.drawable.ic_tiles))
         cartItem.add(Category("Title2", R.drawable.ic_tiles1))
         cartItem.add(Category("Title3", R.drawable.ic_tiles6))
         cartItem.add(Category("Title4", R.drawable.ic_tiles7))*/
        /*categoryList.clear()
        categoryList.addAll(cartItem)*/

        /* for (i in 0 until cartItem.size) {
             categoryList.let { it1 ->
                 cartAdapter.addData(
                     it1
                 )
             }
         }
         cartAdapter.notifyDataSetChanged()*/
//        cartAdapter.addData(cartItem)


        if (dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size > 0) {
//            binding.noDataTv.visibility = View.GONE
            binding.btnWpp.alpha = 1.0f
            binding.btnWpp.isEnabled = true
            categoryList.clear()
            categoryList.addAll(dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!) as java.util.ArrayList<Cart>)
            for (i in 0 until dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size) {
                categoryList.let { it1 ->
                    cartAdapter.addData(
                        it1
                    )
                }
            }
            binding.rvCart.adapter = cartAdapter
        }
        else{
            binding.btnWpp.alpha = 0.5f
            binding.btnWpp.isEnabled = false
//            Toast.makeText(this,"Disable",Toast.LENGTH_SHORT).show()
        }


        binding.rvCart.adapter = cartAdapter

       /* if(categoryList.size == 0){
            Toast.makeText(this,"Disable",Toast.LENGTH_SHORT).show()
        }*/
//        Toast.makeText(this,viewModel.getUserProfileData()?.username.toString(),Toast.LENGTH_SHORT).show()


        binding.rrBack.setOnClickListener {
            finish()
        }
        binding.btnWpp.setOnClickListener {
            val isWhatsappInstalled: Boolean = whatsappInstalledOrNot("com.whatsapp")
            if (isWhatsappInstalled) {
                try {
                    val packageManager: PackageManager = getPackageManager()
                    val i = Intent(Intent.ACTION_VIEW)

                    if (dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size > 0) {
                        binding.btnWpp.alpha = 1.0f
                        binding.btnWpp.isEnabled = true
//            binding.noDataTv.visibility = View.GONE
                        categoryList.clear()
                        categoryList.addAll(dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!) as java.util.ArrayList<Cart>)
                        for (i in 0 until dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size) {
                            categoryList.let { it1 ->
                                cartAdapter.addData(
                                    it1
                                )
                            }
                        }
                    }
                    else{
                        binding.btnWpp.alpha = 0.5f
                        binding.btnWpp.isEnabled = false
//                        Toast.makeText(this,"Disable",Toast.LENGTH_SHORT).show()
                    }
                    var url = ""

                    val list: MutableList<String?> = ArrayList()
                    for (j in categoryList.indices) {

                        val pos: Int = j + 1

                        if (categoryList[j] != null) {
                            list.add("\n$pos) " + "Product Name: " + categoryList[j].cartTitle)
                            list.add("Size: " + categoryList[j].cartSize)
                            list.add("Category: " + categoryList[j].cartCat)
                            list.add("Quantity: " + categoryList[j].cartQty)
                        }
                    }
                    val userName: String = viewModel.getUserData()?.username.toString()
                    val mobileNo: String = viewModel.getUserData()?.mobileNumber.toString()
                    val text: String = list.toString().replace(",", "").replace("[", "")
                        .replace("]", "") //remove brackets([) convert it to string
                    url =
                        "https://api.whatsapp.com/send?phone=" + "+91 9925707990" + "&text=" + URLEncoder.encode(
                            "Order List" +
                                    "\nUsername: " + userName +
                                    "\nMobile Number: " + mobileNo +
                                    " " + text,
                            "UTF-8"
                        )
                    i.setPackage("com.whatsapp")
                    i.data = Uri.parse(url)

                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(
                    applicationContext, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvClear.setOnClickListener {
            binding.rvCart.adapter = cartAdapter
            dataBase.deleteAllCart()
            cartAdapter.objList.clear()
            cartAdapter.notifyDataSetChanged()

            if (dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size > 0) {
                binding.btnWpp.alpha = 1.0f
                binding.btnWpp.isEnabled = true
//            binding.noDataTv.visibility = View.GONE
                categoryList.clear()
                categoryList.addAll(dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!) as java.util.ArrayList<Cart>)
                for (i in 0 until dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size) {
                    categoryList.let { it1 ->
                        cartAdapter.addData(
                            it1
                        )
                    }
                }

//                Toast.makeText(this, "Clear Data Successfully", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.btnWpp.alpha = 0.5f
                binding.btnWpp.isEnabled = false
//                Toast.makeText(this,"No Data in Cart",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun whatsappInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

    override fun showList(showList: Boolean) {

    }

    override fun onItemClickList(getTitleItem: Cart) {

    }

    override fun onItemRemoveClickList(getTitleItem: Cart, position: Int) {
        dataBase.deleteCart(getTitleItem.id)
        if (dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size > 0) {
            binding.btnWpp.alpha = 1.0f
            binding.btnWpp.isEnabled = true
//            binding.noDataTv.visibility = View.GONE
            categoryList.clear()
            categoryList.addAll(dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!) as java.util.ArrayList<Cart>)
            for (i in 0 until dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size) {
                categoryList.let { it1 ->
                    cartAdapter.addData(
                        it1
                    )
                }
            }
            binding.rvCart.adapter = cartAdapter
        }
        else{
            binding.btnWpp.alpha = 0.5f
            binding.btnWpp.isEnabled = false
//            Toast.makeText(this,"Disable",Toast.LENGTH_SHORT).show()
        }
    }

    /*   override fun onItemAddClickList(getTitleItem: Cart, position: Int) {
   //        Toast.makeText(this, getTitleItem.cartTitle, Toast.LENGTH_SHORT).show()

       }

       override fun onItemMinusClickList(getTitleItem: Cart, position: Int) {
   //        Toast.makeText(this, "::"+getTitleItem.cartTitle, Toast.LENGTH_SHORT).show()

       }*/
    override fun onResume() {
        super.onResume()
        if (dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size > 0) {
            binding.btnWpp.alpha = 1.0f
            binding.btnWpp.isEnabled = true
//            binding.noDataTv.visibility = View.GONE
            categoryList.clear()
            categoryList.addAll(dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!) as java.util.ArrayList<Cart>)
            for (i in 0 until dataBase.listCarts(viewModel.getUserProfileData()?.data?.id!!).size) {
                categoryList.let { it1 ->
                    cartAdapter.addData(
                        it1
                    )
                }
            }
            binding.rvCart.adapter = cartAdapter
        }
        else{
            binding.btnWpp.alpha = 0.5f
            binding.btnWpp.isEnabled = false
//            Toast.makeText(this,"Disable",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CartActivity::class.java))
        }

        fun start(context: Context, objList: ArrayList<Category>) {
            context.startActivity(Intent(context, CartActivity::class.java).apply {
                putExtra("CATLIST", objList)
            })
        }
    }
}