package androidmvvm.ui.splash

import androidmvvm.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by CIS Dev 816 on 4/4/18.
 */
@Module
class SplashActivityModule {

    @ActivityScoped
    @Provides
    fun provideSplashActivityViewModel():
            SplashActivityVM = SplashActivityVM()
}