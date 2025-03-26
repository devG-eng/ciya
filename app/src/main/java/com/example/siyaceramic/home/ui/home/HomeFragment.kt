package com.example.siyaceramic.home.ui.home

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseFragment
import com.example.siyaceramic.databinding.FragmentHomeBinding
import com.example.siyaceramic.home.ui.dashboard.CartActivity
import com.example.siyaceramic.home.ui.dashboard.DashboardFragment
import com.example.siyaceramic.home.ui.dashboard.ProductsActivity
import com.example.siyaceramic.home.ui.dashboard.model.CategorySizeResponse
import com.example.siyaceramic.home.ui.home.adapter.CatLabelAdapter
import com.example.siyaceramic.home.ui.home.adapter.CategoryAdapter
import com.example.siyaceramic.home.ui.home.adapter.ProductAdapter
import com.example.siyaceramic.home.ui.home.model.*
import com.example.siyaceramic.home.ui.home.viewmodel.HomeViewModel
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.catSizeAPI
import com.example.siyaceramic.network.homeAPI
import com.example.siyaceramic.util.DataBaseHelper
import com.example.siyaceramic.util.SharedPrefCons
import com.example.siyaceramic.util.TxtUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment(), CatLabelAdapter.ShowList, CategoryAdapter.ShowList,
    ProductAdapter.ShowList,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private var _binding: FragmentHomeBinding? = null

    val viewModel: HomeViewModel by viewModel()

    val catLabelAdapter: CatLabelAdapter by lazy {
        CatLabelAdapter(requireContext(), this)
    }

    private lateinit var categoryAdapter: CategoryAdapter
//    val categoryAdapter: CategoryAdapter by lazy {
//        CategoryAdapter(requireContext(), this,selectedSize)
//    }
    val productAdapter: ProductAdapter by lazy {
        ProductAdapter(requireContext(), this)
    }
    var objList: ArrayList<Category> = ArrayList()
    val categoryList = ArrayList<CategoriesItem>()
    val sizeList = ArrayList<SizesItem>()
    val productsList = ArrayList<BestProductItem>()
    val categoriesList = ArrayList<CategoriesItem>()
    lateinit var dataPasser: OnDataPass
    var category = ArrayList<CategoryItem>()
    var categoryItem = ArrayList<Category>()
    var productItem = ArrayList<Category>()
    var dialogCart: BottomSheetDialog? = null
    private var _counter = 0
    private var _stringVal: String? = "0"
    var sizeId: Int? = 0
    private lateinit var dataBase: DataBaseHelper
    var totalCount : Int = 0
    private val pref: SharedPreferences by inject()
    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
    var context: FragmentActivity? = null
    var selectedSize: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()

    }

    private fun initView() {
        context = activity
        dataBase = DataBaseHelper(requireActivity())
        val sizes = CatLabelAdapter(requireActivity(), this).getSize()



        /*category.add(CategoryItem("24 X 24"))
        category.add(CategoryItem("24 X 48"))
        category.add(CategoryItem("16 X 16"))
        category.add(CategoryItem("12 X 12"))
        category.add(CategoryItem("18 X 12"))
        catLabelAdapter.addData(category)*/
        binding.rvCategory.adapter = catLabelAdapter
//        binding.rvCategory.findViewHolderForAdapterPosition(0)?.itemView?.performClick()

        /*catList.clear()
        catList.add(Category("Title",R.drawable.ic_tiles))
        catList.add(Category("Title1",R.drawable.ic_tiles1))
        catList.add(Category("Title2",R.drawable.ic_tiles2))
        catList.add(Category("Title3",R.drawable.ic_tiles))
        catList.add(Category("Title4",R.drawable.ic_tiles1))
        catList.add(Category("Title5",R.drawable.ic_tiles2))
        categoryAdapter.addData(catList)*/
        categoryAdapter = CategoryAdapter(requireContext(), this,selectedSize)
        binding.rvCatItems.adapter = categoryAdapter

        /*productItem.add(Category("Title",R.drawable.ic_tiles1))
        productItem.add(Category("Title1",R.drawable.ic_tiles3))
        productItem.add(Category("Title2",R.drawable.ic_tiles))
        productItem.add(Category("Title3",R.drawable.ic_tiles4))
        productItem.add(Category("Title4",R.drawable.ic_tiles))
        productItem.add(Category("Title5",R.drawable.ic_tiles3))
        productItem.add(Category("Title6",R.drawable.ic_tiles1))
        productItem.add(Category("Title7",R.drawable.ic_tiles3))
        productItem.add(Category("Title8",R.drawable.ic_tiles))
        productAdapter.addData(productItem)*/
//        binding.rvBstProduct.adapter = productAdapter
        /* val userProfileData = pref.getString(SharedPrefCons.userProfileData, "")
         val data = Gson().fromJson(userProfileData, LoginResponse::class.java)
         Log.d("COMPANY",data.data?.companyName.toString())
 */
        binding.rrCart.setOnClickListener {
            CartActivity.start(requireContext())
        }

        /* binding.rrArrow.setOnClickListener {
              dataPasser.onDataPass()
         }*/


        countTotal()

        binding.searchView.setOnQueryTextListener(this)


        callApi()
    }

    override fun onResume() {
        super.onResume()
        countTotal()
    }



    private fun callApi() {
        viewModel.getHomeApi(true)
    }

    //category api
    private fun callCatApi() {
        viewModel.getCatApi(sizeId!!,false)
    }

    private fun initObserver() {

        viewModel.getUiState().observe(requireActivity()) {
            when (it) {
                is ResourceStatus.Loading<*> -> {
                    binding.progressBar.progressBar.visibility = View.VISIBLE
                    binding.progressBar.ivCelebration.visibility = View.VISIBLE
//                    binding.progressBar.ivCelebration.playAnimation()
                }
                is ResourceStatus.Success -> {

                    binding.progressBar.progressBar.visibility = View.GONE
                    when (it.apiName) {

                        homeAPI -> {
                            val response = it.myResp as HomeResponse
                            if (response.data != null) {
                                sizeList.clear()
                                sizeList.addAll(response.data.sizes as ArrayList<SizesItem>)

                                categoryList.clear()
                                categoryList.addAll(response.data.categories as ArrayList<CategoriesItem>)

                               /* productsList.clear()
                                productsList.addAll(response.data.bestProduct as ArrayList<BestProductItem>)
*/
                                for (i in 0 until response.data.categories.size) {
                                    categoryList.let { it1 ->
                                        categoryAdapter.addData(
                                            it1
                                        )
                                    }
                                }
                                categoryAdapter.notifyDataSetChanged()

                                for (i in 0 until response.data.sizes!!.size) {
                                    sizeList.let { it1 ->
                                        catLabelAdapter.addData(
                                            it1
                                        )
                                    }
                                }
                                catLabelAdapter.notifyDataSetChanged()
                               /* for (i in 0 until response.data.bestProduct.size) {
                                    productsList.let { it2 ->
                                        productAdapter.addData(
                                            it2
                                        )
                                    }
                                }
                                productAdapter.notifyDataSetChanged()*/

                                val stringData = Gson().toJson(response.data.user)
                                pref.edit(commit = true) {
                                    putString(SharedPrefCons.homeProfileData, stringData)
                                }

                            } else {
//                                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                            }

                        }

                        catSizeAPI -> {
                            val response = it.myResp as CategorySizeResponse
                            if (!response.data.isNullOrEmpty()) {
                                binding.titleCat.visibility = View.VISIBLE
                                categoriesList.clear()
                                categoriesList.addAll(response.data as ArrayList<CategoriesItem>)
                                binding.rvCatItems.visibility = View.VISIBLE

                                for (i in 0 until response.data.size) {
                                    categoriesList.let { it1 ->
                                        categoryAdapter.addData(
                                            it1
                                        )
                                    }
                                }
                                categoryAdapter.notifyDataSetChanged()


                            } else {
                                categoryList.clear()
                                categoriesList.clear()
                                categoryAdapter.objList.clear()
                                binding.titleCat.visibility = View.GONE
                                binding.rvCatItems.visibility = View.GONE
                                categoryAdapter.notifyDataSetChanged()
                                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }


                is ResourceStatus.Error -> {
                    val error = it.throwable as Throwable
                    binding.progressBar.progressBar.visibility = View.GONE
                    TxtUtils.hideKeyBoard(context, binding.root)
                    when (it.apiName) {
                        homeAPI -> {
//                            Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show()
                        }
                        catSizeAPI -> {
//                            Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                else -> {
                    binding.progressBar.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    override fun showList(showList: Boolean) {

    }

    override fun onItemClickList(getTitleItem: CategoriesItem) {
        if(sizeId == 0){
            Toast.makeText(requireContext(),"Please click on Size",Toast.LENGTH_SHORT).show()
        }
        else {
//            ProductsActivity.start(requireContext(), getTitleItem.title)
            ProductsActivity.start(requireContext(), sizeId,getTitleItem.id,true)
        }
    }



    override fun onItemCatClickList(getTitleItem: SizesItem) {
        sizeId = getTitleItem.id
        val size = CatLabelAdapter(requireContext(), this).getSize()
//        Toast.makeText(context,""+size.toString(),Toast.LENGTH_SHORT).show()
        categoryAdapter = CategoryAdapter(requireContext(), this,selectedSize)
        binding.rvCatItems.adapter = categoryAdapter

        /*selectedSize = getTitleItem.size.toString()
        categoryAdapter = CategoryAdapter(requireContext(), this,selectedSize)*/
//        val sizes = CatLabelAdapter(requireActivity(), this).getSize()
//        categoryAdapter = CategoryAdapter(requireContext(), this,sizes)
//        showSize?.showSize(getTitleItem.size)
        callCatApi()
    }


    override fun onItemSizeList(size: String?) {
       /* selectedSize = size
        categoryAdapter = CategoryAdapter(requireContext(), this,selectedSize)
*/
        selectedSize = size!!
//        Toast.makeText(context,""+size+"::"+selectedSize,Toast.LENGTH_SHORT).show()
        categoryAdapter = CategoryAdapter(requireContext(), this,selectedSize)
        binding.rvCatItems.adapter = categoryAdapter

    }

    override fun onItemClickList(getTitleItem: BestProductItem) {
        dialogCart = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)

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
        val btnCart = dialogCart?.findViewById<View>(R.id.btnAddCart) as AppCompatButton

        txtProItem.text = getTitleItem.title

        Glide.with(requireActivity()).load(getTitleItem.image.toString())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).timeout(6000)
            .placeholder(R.drawable.ic_tiles5)
            .centerCrop().into(ivImage)
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
               /* dataBase.updateCart(
                    Cart(
                        Objects.requireNonNull<Any>(getTitleItem.id) as Int,
                        getTitleItem.title.toString(), _stringVal.toString(), getTitleItem.image.toString()
                    )
                )*/

            }
        }
        btnPlus.setOnClickListener {
            _counter++
            _stringVal = Integer.toString(_counter)
            txtCount.setText(_stringVal)
           /* dataBase.updateCart(
                Cart(
                    Objects.requireNonNull<Any>(getTitleItem.id) as Int,
                    getTitleItem.title.toString(), _stringVal.toString(), getTitleItem.image.toString()
                )
            )*/
        }

        btnCart.setOnClickListener {
            if (txtCount.text!!.equals("0")){
                Toast.makeText(requireContext(),"Please Add Quantity",Toast.LENGTH_SHORT).show()
            }
            else {
                val sizeItem = getTitleItem.productSizes
                val category = Category()
                category.catName = getTitleItem.title.toString()
                category.catQty = txtCount.text.toString()
                category.catImage = getTitleItem.image.toString()
                category.catSize = sizeItem?.size.toString()
                category.catCat= getTitleItem.category?.title.toString()

                objList.add(category)
//                CartActivity.start(requireContext(), objList)

                if(dataBase.CheckIsDataAlreadyInDBorNot(getTitleItem.id!!.toInt()) == true)
                {
                   Toast.makeText(requireContext(),"TRUE",Toast.LENGTH_SHORT).show()
                }
                /*val newCart =
                        Cart(getTitleItem.title.toString(), getTitleItem.id!!.toInt(),txtCount.text.toString(), getTitleItem.image.toString(),sizeItem?.width +" X "+ sizeItem?.height,getTitleItem.category?.title.toString())
                    dataBase.addCart(newCart)*/

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



/*    override fun onItemClickList(getTitleItem: CategoryItem) {

        when (getTitleItem.category) {
            "24 X 24" -> {
                catList.clear()
                catList.add(Category("Title",R.drawable.ic_tiles))
                catList.add(Category("Title1",R.drawable.ic_tiles1))
                catList.add(Category("Title2",R.drawable.ic_tiles2))
                catList.add(Category("Title3",R.drawable.ic_tiles))
                catList.add(Category("Title4",R.drawable.ic_tiles1))
                catList.add(Category("Title5",R.drawable.ic_tiles2))
//                categoryAdapter.addData(catList)

            }
            "24 X 48" -> {
                catList.clear()
                catList.add(Category("Title",R.drawable.ic_tiles1))
                catList.add(Category("Title1",R.drawable.ic_tiles3))
                catList.add(Category("Title2",R.drawable.ic_tiles2))
                catList.add(Category("Title3",R.drawable.ic_tiles))
                catList.add(Category("Title4",R.drawable.ic_tiles1))
//                categoryAdapter.addData(catList)

            }
            "16 X 16" -> {
                catList.clear()
                catList.add(Category("Title",R.drawable.ic_tiles3))
                catList.add(Category("Title1",R.drawable.ic_tiles2))
                catList.add(Category("Title2",R.drawable.ic_tiles))
                catList.add(Category("Title3",R.drawable.ic_tiles1))
//                categoryAdapter.addData(catList)

            }
            "12 X 12" -> {
                catList.clear()
                catList.add(Category("Title",R.drawable.ic_tiles2))
                catList.add(Category("Title1",R.drawable.ic_tiles1))
                catList.add(Category("Title2",R.drawable.ic_tiles2))
//                categoryAdapter.addData(catList)
            }
            "18 X 12" -> {
                catList.clear()
                catList.clear()
                catList.add(Category("Title",R.drawable.ic_tiles3))
                catList.add(Category("Title1",R.drawable.ic_tiles1))
//                categoryAdapter.addData(catList)
            }
            else -> {

            }
        }

    }*/

    private fun countTotal(){
        totalCount = dataBase.totalCount(viewModel.getUserProfileData()?.data?.id!!)

        if (totalCount > 0){
            binding.rrCount.visibility = View.VISIBLE
            binding.titleCount.text = totalCount.toString()
        }
        else{
            binding.rrCount.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass

    }

    interface OnDataPass {
        fun onDataPass()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        categoryAdapter.getFilter().filter(query)
        productAdapter.getFilter().filter(query)

        return true
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }


        override fun onQueryTextChange(newText: String?): Boolean {
        categoryAdapter.getFilter().filter(newText)
        productAdapter.getFilter().filter(newText)

        return true
    }

}