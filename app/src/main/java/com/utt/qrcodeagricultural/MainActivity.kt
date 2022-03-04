package com.utt.qrcodeagricultural

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utt.qrcodeagricultural.Constant.Companion.showToastLong
import com.utt.qrcodeagricultural.base.BaseActivity
import com.utt.qrcodeagricultural.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
    }
    private lateinit var checkConnection: CheckConnection
    private val navController by lazy { navHostFragment.navController }
    override fun initViewBinding(view: View): ActivityMainBinding {
        return ActivityMainBinding.bind(view)
    }

    override fun getLayOutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        checkConnection = CheckConnection(application)
        checkConnection.observe(this) { isConnected ->
            if (isConnected) {
                applicationContext.showToastLong(getString(R.string.internet_connected))
            } else {
                applicationContext.showToastLong(getString(R.string.internet_not_connected))
            }
        }
        viewBinding?.bottomNavigation?.apply {
            setupWithNavController(navController)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> {
                    viewBinding?.groupBottomNav?.visibility = View.VISIBLE
                }
                R.id.likeFragment -> {
                    viewBinding?.groupBottomNav?.visibility = View.VISIBLE
                }
            }
        }
    }
}