package androidmvvm.routing

import android.content.Context
import com.ank.androidmvvmarchitecture.routing.auth.AuthController
import com.ank.androidmvvmarchitecture.routing.auth.AuthRouteManager
import com.ank.androidmvvmarchitecture.routing.dashboard.DashboardController
import androidmvvm.routing.dashboard.DashboardRouteManager
import javax.inject.Inject

class NavigationController
@Inject
constructor
(val context: Context, private var authActivityRouteManager: AuthRouteManager,
 private var dashboardRouteManager: DashboardRouteManager) : Navigator, AuthController by authActivityRouteManager,
        DashboardController by dashboardRouteManager
