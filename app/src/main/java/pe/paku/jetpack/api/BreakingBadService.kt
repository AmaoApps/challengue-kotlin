package pe.paku.jetpack.api

import pe.paku.jetpack.api.response.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreakingBadService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<List<CharacterResponse>>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") idCharacter: Int
    ) : Response<List<CharacterResponse>>

}