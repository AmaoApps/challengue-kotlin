package pe.paku.jetpack.repositories

import pe.paku.jetpack.api.BreakingBadService
import pe.paku.jetpack.db.dao.SerieCharacterDao
import pe.paku.jetpack.db.model.SerieCharacter
import javax.inject.Inject

class MainRepository @Inject constructor(
    val serieCharacterDao: SerieCharacterDao,
    val breakingBadService: BreakingBadService
) {
    suspend fun insertFavoriteCharacter(character: SerieCharacter) = serieCharacterDao.addCharacter(character)
    suspend fun deleteCharacter(characterId: Int) = serieCharacterDao.deleteCharacter(characterId)
    fun getFavorites() = serieCharacterDao.getAllFavorites()
    fun updateFavorite(id:Int, status:Boolean) = serieCharacterDao.updateFavorite(id, status)

    suspend fun getCharactersFromAPI(count:Int, from: Int) = breakingBadService.getCharacters(count,from)
    suspend fun getCharacterByIdFromApi(idCharacter: Int) = breakingBadService.getCharacterById(idCharacter)
}