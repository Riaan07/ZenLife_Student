package com.csiq6823.zenlife

import android.app.Application
import com.csiq6823.zenlife.data.ZenLifeDatabase

class ZenLifeApplication : Application() {
    // Automatically creates the database single-instance when the app process runs
    val database by lazy { ZenLifeDatabase.getDatabase(this) }
}