package androidmvvm.ui.deals_dashboard

import androidmvvm.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by CIS Dev 816 on 4/4/18.
 */
@Module
class DashboardActivityModule {

    @ActivityScoped
    @Provides
    fun provideDashboardActivityViewModel():
            DashboardActivityVM = DashboardActivityVM()
}