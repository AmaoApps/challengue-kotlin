package pe.paku.jetpack.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.paku.jetpack.api.response.CharacterResponse
import pe.paku.jetpack.db.dao.SerieCharacterDao
import pe.paku.jetpack.db.model.SerieCharacter
import pe.paku.jetpack.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    val singleCharactersFromAPI = MutableLiveData<List<CharacterResponse>?>()

    fun loadCharactersFromAPI(idCharacter: Int){
        viewModelScope.launch {
            val charactersById = mainRepository.getCharacterByIdFromApi(idCharacter)

            when (charactersById.isSuccessful){
                true -> {
                    with(charactersById.body().orEmpty()){
                        singleCharactersFromAPI.postValue(charactersById.body())
                    }
                } else -> {
                Log.e("Error", "Error in service : " + charactersById.message())
            }
            }
        }
    }

}