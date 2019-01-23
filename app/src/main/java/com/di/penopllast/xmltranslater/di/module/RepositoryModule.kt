package com.di.penopllast.xmltranslater.di.module

import android.content.Context
import com.di.penopllast.xmltranslater.data.repository.RepositoryDb
import com.di.penopllast.xmltranslater.data.repository.RepositoryNetwork
import com.di.penopllast.xmltranslater.data.repository.RepositoryPreference
import com.di.penopllast.xmltranslater.data.repository.impl.RepositoryDbImpl
import com.di.penopllast.xmltranslater.data.repository.impl.RepositoryNetworkImpl
import com.di.penopllast.xmltranslater.data.repository.impl.RepositoryPreferenceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepositoryNetwork(): RepositoryNetwork {
        return RepositoryNetworkImpl()
    }

    @Provides
    @Singleton
    internal fun provideRepositoryPreference(context: Context): RepositoryPreference {
        return RepositoryPreferenceImpl(context)
    }

    @Provides
    @Singleton
    internal fun provideRepositoryDb(context: Context): RepositoryDb {
        return RepositoryDbImpl(context)
    }


}