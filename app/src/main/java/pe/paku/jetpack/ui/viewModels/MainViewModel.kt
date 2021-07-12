package pe.paku.jetpack.ui.viewModels

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.paku.jetpack.api.response.CharacterResponse
import pe.paku.jetpack.db.model.SerieCharacter
import pe.paku.jetpack.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    val listCharactersFromAPI = MutableLiveData<List<SerieCharacter>?>()

    val listCharacterFromDB = mainRepository.getFavorites()

    val listCharacterMediator = MediatorLiveData<List<SerieCharacter>?>()


    fun currentListCharacters() = listCharactersFromAPI

    fun saveFavorite(serieCharacter: SerieCharacter) = viewModelScope.launch {
        mainRepository.insertFavoriteCharacter(serieCharacter)
    }

    fun deleteFavorite(serieCharacteIdr: Int) = viewModelScope.launch {
        mainRepository.deleteCharacter(serieCharacteIdr)
    }

    init {
        listCharacterMediator.addSource(listCharactersFromAPI) { result ->
            val listMergeResults : ArrayList<SerieCharacter> = ArrayList(result)
            listCharacterMediator.value?.let {
                listMergeResults.addAll(it)
            }
            var listFilered : ArrayList<SerieCharacter> = filteredListResult(listMergeResults)
            listCharacterMediator.value = listFilered
        }
        listCharacterMediator.addSource(listCharacterFromDB) { result ->
            val listMergeResults : ArrayList<SerieCharacter> = ArrayList(result)
            listCharacterMediator.value?.let {
                listMergeResults.addAll(it)
            }
            var listFilered : ArrayList<SerieCharacter> = filteredListResult(listMergeResults)
            listCharacterMediator.value = listFilered
        }
        loadCharactersFromAPI()
        //loadCharactersFromDB()
    }

    private fun loadCharactersFromAPI(){
        viewModelScope.launch {
            val characters = mainRepository.getCharactersFromAPI(10,10)

            when (characters.isSuccessful){
                true -> {
                    with(characters.body().orEmpty()){
                        var listCharacterToAdd = listOf<SerieCharacter>()
                        forEach {
                            listCharacterToAdd = listCharacterToAdd + SerieCharacter(it.char_id, it.name, it.nickname, it.img, false)
                        }
                        listCharactersFromAPI.postValue(listCharacterToAdd)

                    }
                } else -> {
                    Log.e("Error", "Error in service : " + characters.message())
                }
            }
        }
    }

    private fun filteredListResult(initialList: ArrayList<SerieCharacter>) : ArrayList<SerieCharacter> {
        var listSortedFavorite = initialList.sortedByDescending { it.favorite }
        var listRemoveDuplicated = listSortedFavorite.distinctBy { it.id }
        return ArrayList(listRemoveDuplicated)
    }

}