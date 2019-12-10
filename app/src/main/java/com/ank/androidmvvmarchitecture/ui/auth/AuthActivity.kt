package androidmvvm.ui.auth

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import androidmvvm.R
import androidmvvm.data.AppDataManager
import androidmvvm.data.remote.CounterManager
import com.ank.androidmvvmarchitecture.ui.base.BaseActivity
import androidmvvm.utils.toolbar.FragmentToolbar
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class AuthActivity : BaseActivity<AuthActivityVM>(), HasSupportFragmentInjector {

    companion object {
        fun startInstanceWithBackStackCleared(context: Context?) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }
    }

    @Inject
    lateinit var authActivityVM: AuthActivityVM
    @Inject
    lateinit var appDataManager: AppDataManager
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun getViewModel(): AuthActivityVM = authActivityVM

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
        setContentView(R.layout.activity_auth)

        CounterManager.remainTimeToStop = 0

        navigationController.launchOfferFragment(this, R.id.fl_auth_container)
        //navigationController.launchAppSelectionFragment(this, R.id.fl_auth_container)
    }
}
