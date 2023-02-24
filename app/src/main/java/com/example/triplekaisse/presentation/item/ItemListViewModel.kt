package com.example.triplekaisse.presentation.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triplekaisse.data.GameRepository
import com.example.triplekaisse.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(private val gameRepo: GameRepository) : ViewModel() {
    private val _gameQuestions = MutableLiveData<List<Item>>()
    val gameQuestions: LiveData<List<Item>>
        get() = _gameQuestions

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _gameQuestions.postValue(gameRepo.getItems())
        }
    }

    fun onHideClicked(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            gameRepo.updateItem(item)
        }
    }


}