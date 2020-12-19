package ru.rubt.newegator.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.rubt.newegator.data.db.AppDatabase

@Module
class RoomModule(private val ctx: Context) {

    @Provides
    fun providesAppDatabase() : AppDatabase {
        return Room.databaseBuilder(ctx,
            AppDatabase::class.java, "app_database")
                .build()
    }

}