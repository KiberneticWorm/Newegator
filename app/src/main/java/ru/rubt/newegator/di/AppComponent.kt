package ru.rubt.newegator.di

import dagger.Component
import ru.rubt.newegator.App
import ru.rubt.newegator.activity.MainActivity
import ru.rubt.newegator.activity.SplashActivity
import ru.rubt.newegator.activity.ViewActivity
import ru.rubt.newegator.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, RetrofitModule::class,
    PreferencesModule::class, ConverterModule::class, NewsModule::class])
interface AppComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(viewActivity: ViewActivity)
    fun inject(app: App)
}