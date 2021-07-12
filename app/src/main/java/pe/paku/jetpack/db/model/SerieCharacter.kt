package pe.paku.jetpack.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "character_movie_table")
data class SerieCharacter(
    @PrimaryKey var id: Int,
    var name: String? = "",
    var nickName: String? = "",
    var pathImage: String? = "",
    var favorite : Boolean = false
) {
    //@PrimaryKey(autoGenerate = false)
    //var id: Int? = null
}