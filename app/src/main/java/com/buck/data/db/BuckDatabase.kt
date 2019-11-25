package com.buck.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class BuckDatabase : RoomDatabase() {



    abstract fun userDao(): UserDao



    companion object {
        @Volatile
        private var INSTANCE: BuckDatabase? = null

        operator fun invoke(context: Context, scope: CoroutineScope): BuckDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BuckDatabase::class.java,
                    "buck.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DeviceDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        class DeviceDatabaseCallback(private val scope: CoroutineScope) : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
//                //初始化数据
//                INSTANCE?.let {
//                    scope.launch {
//                        //插入数据
//                        populateDeviceDatabase(it.deviceDao())
//                    }
//                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
//                //初始化数据
//                INSTANCE?.let {
//                    scope.launch {
//                        //插入数据
//                        populateDeviceDatabase(it.deviceDao())
//                    }
//                }

            }

        }
    }
}