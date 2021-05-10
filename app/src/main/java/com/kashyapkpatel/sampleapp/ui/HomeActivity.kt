package com.kashyapkpatel.sampleapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kashyapkpatel.sampleapp.R
import com.kashyapkpatel.sampleapp.databinding.ActivityMainBinding
import com.kashyapkpatel.sampleapp.di.ViewModelProviderFactory
import com.kashyapkpatel.sampleapp.interfaces.IFragmentCallbacks
import com.kashyapkpatel.sampleapp.util.newFragmentInstance
import com.kashyapkpatel.sampleapp.viewmodel.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(),
    IFragmentCallbacks,
    FragmentManager.OnBackStackChangedListener {

    companion object {
        const val TAG = "HomeActivity"
        const val KEY_CURRENT_FRAGMENT_TAG = "CurrentFragment"
        const val KEY_IS_MAIN = "IsMain"
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val homeViewModel by viewModels<HomeViewModel> { viewModelProviderFactory }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = homeViewModel
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        setSupportActionBar(binding.toolbar)
        loadInitialFragment(savedInstanceState)
        setupBindingsAndObservables()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_CURRENT_FRAGMENT_TAG, getCurrentFragment()?.tag)
        super.onSaveInstanceState(outState)
    }

    override fun updateTitle(title: String) {
        binding.toolbar.title = title
    }

    override fun showMainFragment(fragment: Fragment, tag: String?) {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStackImmediate(first.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        showFragment(fragment, tag)
    }

    /**
     * Function to display the fragment without replacing all the other
     * fragments from the back stack.
     *
     * @param fragment The fragment to display.
     * @param tag      The tag of the fragment to display.
     */
    override fun showFragment(fragment: Fragment, tag: String?) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        fragmentManager.addOnBackStackChangedListener(this)
        transaction.setCustomAnimations(R.anim.fragment_load_fade_in, R.anim.fragment_load_fade_out)
        transaction.replace(R.id.rootContainer, fragment, tag)
        fragmentManager.executePendingTransactions()
        if (fragmentManager.findFragmentByTag(fragment.id.toString()) == null) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * Function to display the fragment without replacing all the other
     * fragments from the back stack.
     *
     * @param fragment The fragment to display.
     * @param tag      The tag of the fragment to display.
     */
    override fun showFragmentWithAnimation(
        fragment: Fragment,
        tag: String?,
        enterAnim: Int,
        exitAnim: Int,
        popEnterAnim: Int,
        popExitAnim: Int
    ) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        fragmentManager.addOnBackStackChangedListener(this)
        transaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        transaction.replace(R.id.rootContainer, fragment, tag)
        fragmentManager.executePendingTransactions()
        if (fragmentManager.findFragmentByTag(fragment.id.toString()) == null) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
    }

    override fun showHamIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun showBackArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackStackChanged() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount > 1) {
            showBackArrow()
        } else {
            showHamIcon()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            popBackStackContent()
            updateTitle(getString(R.string.app_name))
        } else {
            val fragment = getCurrentFragment()
            if (fragment is MovieListFragment) {
                // If fragment is instance of MovieListFragment then on back press
                // we have to finish the app because
                // there are no other screens left to display.
                finish()
            } else {
                //If the fragment is not an instance of LoginFragment then on back press
                // we have to display Main Screen.
                showMainFragment(LoginFragment(), LoginFragment.TAG)
            }
        }
    }

    private fun loadInitialFragment(savedInstanceState: Bundle?) {
        lateinit var baseFragment: BaseFragment
        if (savedInstanceState == null) {
            baseFragment = newFragmentInstance<LoginFragment>(
                KEY_IS_MAIN to true,
            )
            showMainFragment(baseFragment, LoginFragment.TAG)
        } else {
            val currentFragmentTag = savedInstanceState.getString(KEY_CURRENT_FRAGMENT_TAG)
            baseFragment = supportFragmentManager
                .findFragmentByTag(currentFragmentTag) as BaseFragment
            val bundle: Bundle? = baseFragment.arguments
            if (bundle?.getBoolean(KEY_IS_MAIN, false) == true) {
                showMainFragment(baseFragment, baseFragment.tag)
            } else {
                showFragment(baseFragment, baseFragment.tag)
            }
        }
    }

    private fun setupBindingsAndObservables() {
        binding.isDarkTheme = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        homeViewModel.actionLiveDataShowAppInfoClicked.observe(this) { unit ->
            val appInfoFragment = newFragmentInstance<AppInfoFragment>()
            showFragment(appInfoFragment, AppInfoFragment.TAG)
        }
        homeViewModel.actionLiveDataOnThemeChanged.observe(this) { isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun getCurrentFragment(): Fragment? {
        val manager = supportFragmentManager
        val currentFragment = manager.getBackStackEntryAt(manager.backStackEntryCount - 1)
        return manager.findFragmentByTag(currentFragment.name)
    }

    private fun popBackStackContent() {
        val fragmentManager = supportFragmentManager
        getCurrentFragment()?.let {
            fragmentManager.popBackStackImmediate(it.tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}