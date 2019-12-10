package com.ank.androidmvvmarchitecture.data

import android.content.Context
import com.ank.androidmvvmarchitecture.data.local.pref.AppPreferenceManager
import com.ank.androidmvvmarchitecture.data.local.pref.PreferenceSource
import com.ank.androidmvvmarchitecture.data.remote.http.RemoteDataManager
import com.ank.androidmvvmarchitecture.data.remote.http.RemoteSource
import javax.inject.Inject

class AppDataManager
@Inject
constructor(val context: Context, private var remoteDataManager: RemoteDataManager,
            private var preferenceManager: AppPreferenceManager
) : DataManager, RemoteSource by remoteDataManager,
        PreferenceSource by preferenceManager
