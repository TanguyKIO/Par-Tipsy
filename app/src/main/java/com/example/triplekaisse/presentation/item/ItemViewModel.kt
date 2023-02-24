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
class ItemViewModel @Inject constructor(private val gameRepo: GameRepository) : ViewModel() {

    private val _descError = MutableLiveData<Boolean>()
    val descError: LiveData<Boolean>
        get() = _descError

    fun onAddClicked(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            gameRepo.addItem(item)
        }
    }

    fun descDataChanged(text: String){
        if (text.isNotBlank()) {
            _descError.postValue(false)
        } else {
            _descError.postValue(true)
        }
    }

}