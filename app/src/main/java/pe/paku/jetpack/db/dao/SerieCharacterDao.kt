package pe.paku.jetpack.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pe.paku.jetpack.db.model.SerieCharacter


@Dao
interface SerieCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(characterMovie: SerieCharacter)

    //@Delete
    //suspend fun deleteCharacter(characterMovie: SerieCharacter)

    @Query("DELETE FROM character_movie_table WHERE id = :id")
    suspend fun deleteCharacter(id: Int)

    @Query("SELECT * FROM character_movie_table ORDER BY id desc")
    fun getAllFavorites() : LiveData<List<SerieCharacter>>

    @Query("UPDATE character_movie_table set favorite = :favorite  WHERE id = :id")
    fun updateFavorite(id: Int, favorite: Boolean)

}