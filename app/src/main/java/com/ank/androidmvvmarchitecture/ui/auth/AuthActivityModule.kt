package androidmvvm.ui.auth

import androidmvvm.data.AppDataManager
import androidmvvm.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

/**
 * Created by CIS Dev 816 on 4/4/18.
 */
@Module
class AuthActivityModule {

    @ActivityScoped
    @Provides
    fun provideAuthActivityViewModel(appDataManager: AppDataManager):
            AuthActivityVM = AuthActivityVM(appDataManager)
}