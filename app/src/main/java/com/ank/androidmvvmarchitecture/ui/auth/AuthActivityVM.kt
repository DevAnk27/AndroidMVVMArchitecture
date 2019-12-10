package androidmvvm.ui.auth

import android.arch.lifecycle.ViewModel
import androidmvvm.data.AppDataManager
import javax.inject.Inject


/**
 * Created by CIS Dev 816 on 4/4/18.
 */
class AuthActivityVM
@Inject
constructor
(var appDataManager: AppDataManager) : ViewModel()