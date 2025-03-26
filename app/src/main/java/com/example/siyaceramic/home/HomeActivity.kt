package com.example.siyaceramic.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.siyaceramic.R
import com.example.siyaceramic.base.BaseActivity
import com.example.siyaceramic.databinding.ActivityHomeBinding
import com.example.siyaceramic.home.ui.dashboard.DashboardFragment
import com.example.siyaceramic.home.ui.home.HomeFragment
import com.example.siyaceramic.home.ui.notifications.NotificationsFragment
import com.example.siyaceramic.splash.viewmodel.SplashViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity<ActivityHomeBinding, SplashViewModel>(),HomeFragment.OnDataPass {
    override val layout: Int
        get() = R.layout.activity_home
    override val viewModel: SplashViewModel by viewModel()

    //    private lateinit var binding: ActivityHomeBinding
    lateinit var bottomNav: BottomNavigationView
    private var currentFragment: Fragment? = null
    private var homeFragment: HomeFragment? = null
    private var dashboardFragment: DashboardFragment? = null
    private var notificationsFragment: NotificationsFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_home -> {
               /* binding.cardMain.visibility = View.VISIBLE
                binding.txtTitle.visibility = View.GONE
                binding.ivHeader.visibility = View.VISIBLE*/
//                binding.navView.visibility = View.VISIBLE
               /* if (homeFragment != null) {
                    supportFragmentManager.beginTransaction().remove(homeFragment!!)
                        .commit()
                }
                homeFragment = HomeFragment.newInstance()
                homeFragment?.let { updateFragment(it) }*/
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
               /* binding.cardMain.visibility = View.VISIBLE
                binding.txtTitle.visibility = View.VISIBLE
                binding.ivHeader.visibility = View.GONE*/
//                binding.navView.visibility = View.VISIBLE
               /* if (dashboardFragment != null) {
                    supportFragmentManager.beginTransaction().remove(dashboardFragment!!)
                        .commit()
                }
                dashboardFragment = DashboardFragment.newInstance()
                dashboardFragment?.let { updateFragment(it) }*/
                val fragment = DashboardFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
//                binding.cardMain.visibility = View.GONE
               /* if (notificationsFragment != null) {
                    supportFragmentManager.beginTransaction().remove(notificationsFragment!!)
                        .commit()
                }
                notificationsFragment = NotificationsFragment.newInstance()
                notificationsFragment?.let { updateFragment(it) }*/
                val fragment = NotificationsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if (homeFragment != null) {
            supportFragmentManager.beginTransaction().remove(homeFragment!!)
                .commit()
        }
        homeFragment = HomeFragment.newInstance()
        homeFragment?.let { updateFragment(it) }*/
        val fragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
            .commit()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)



    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun updateFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, fragment)
        currentFragment?.let {
            if (fragment != currentFragment) transaction.hide(it)
        }
        currentFragment = fragment
        transaction.commit()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override fun onDataPass() {
      /*  val fragment = DashboardFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
            .commit()
        binding.navView.setSelectedItemId(R.id.navigation_dashboard)*/
    }

}