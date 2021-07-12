package pe.paku.jetpack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.paku.jetpack.db.dao.SerieCharacterDao
import pe.paku.jetpack.db.model.SerieCharacter

@Database(
    entities = [SerieCharacter::class],
    version = 1,
    exportSchema = false
)
abstract class SerieDatabase : RoomDatabase() {

    abstract fun getSerieCharacterDAO() : SerieCharacterDao


}