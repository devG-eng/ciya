package com.example.siyaceramic.home.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import com.example.siyaceramic.base.BaseFragment
import com.example.siyaceramic.databinding.FragmentDashboardBinding
import com.example.siyaceramic.home.ui.dashboard.adapter.CatAdapter
import com.example.siyaceramic.home.ui.dashboard.model.CategoryResponse
import com.example.siyaceramic.home.ui.dashboard.model.DataCatItem
import com.example.siyaceramic.home.ui.dashboard.viewmodel.CategoryViewModel
import com.example.siyaceramic.home.ui.home.model.Category
import com.example.siyaceramic.network.ResourceStatus
import com.example.siyaceramic.network.catAPI
import com.example.siyaceramic.util.DataBaseHelper
import com.example.siyaceramic.util.TxtUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardFragment : BaseFragment(), CatAdapter.ShowList, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentDashboardBinding
    private var _binding: FragmentDashboardBinding? = null

    val catAdapter: CatAdapter by lazy {
        CatAdapter(requireContext(), this)
    }
    var categoryItem = ArrayList<Category>()
    val viewModel: CategoryViewModel by viewModel()
    val categoryList = ArrayList<DataCatItem>()
    private lateinit var dataBase: DataBaseHelper
    var context: FragmentActivity? = null

    var totalCount : Int = 0
    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
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

        /*categoryItem.add(Category("24 X 24", R.drawable.ic_tiles7))
        categoryItem.add(Category("24 X 48", R.drawable.ic_tiles5))
        categoryItem.add(Category("16 X 16", R.drawable.ic_tiles8))
        categoryItem.add(Category("12 X 12", R.drawable.ic_tiles6))
        categoryItem.add(Category("18 X 12", R.drawable.ic_tiles5))
        categoryItem.add(Category("32 X 64", R.drawable.ic_tiles6))
        categoryItem.add(Category("10 X 15", R.drawable.ic_tiles7))
        categoryItem.add(Category("08 X 40", R.drawable.ic_tiles))
        catAdapter.addData(categoryItem)*/
        binding.rvCategories.adapter = catAdapter

        binding.rrCart.setOnClickListener {
            CartActivity.start(requireContext())
        }

        totalCount = dataBase.totalCount(viewModel.getUserProfileData()?.data?.id!!)

//        Toast.makeText(requireContext(),"Total is"+totalCount,Toast.LENGTH_SHORT).show()

        if(totalCount > 0){
            binding.rrCount.visibility = View.VISIBLE
            binding.titleCount.text = totalCount.toString()
        }
        else
        {
            binding.rrCount.visibility = View.GONE
        }

        binding.searchView.setOnQueryTextListener(this)

        callApi()
    }

    override fun onResume() {
        super.onResume()
        totalCount = dataBase.totalCount(viewModel.getUserProfileData()?.data?.id!!)

        if(totalCount > 0){
            binding.rrCount.visibility = View.VISIBLE
            binding.titleCount.text = totalCount.toString()
        }
        else
        {
            binding.rrCount.visibility = View.GONE
        }
    }

    private fun callApi() {
        viewModel.getCatApi(true)
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
                    val response = it.myResp as CategoryResponse
                    binding.progressBar.progressBar.visibility = View.GONE
                    when (it.apiName) {

                        catAPI -> {

                            if (response.data != null) {
                                categoryList.clear()
                                categoryList.addAll(response.data as ArrayList<DataCatItem>)

                                for (i in 0 until response.data.size) {
                                    categoryList.let { it1 ->
                                        catAdapter.addData(
                                            it1
                                        )
                                    }
                                }
                                catAdapter.notifyDataSetChanged()


                            } else {
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
                        catAPI -> {
                            Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show()
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

    override fun onItemClickList(getTitleItem: DataCatItem) {
       /* Handler().postDelayed(Runnable {
            ProductsActivity.start(requireContext(), getTitleItem.id)
//            startActivity(Intent(requireContext(), ProductsActivity::class.java))
            activity?.finish()
        }, 1000)*/
        ProductsActivity.start(requireContext(), getTitleItem.id)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        catAdapter.getFilter().filter(query)

        return true
    }

    companion object {

        @JvmStatic
        fun newInstance() = DashboardFragment().apply {}
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        catAdapter.getFilter().filter(newText)

        return true
    }
}