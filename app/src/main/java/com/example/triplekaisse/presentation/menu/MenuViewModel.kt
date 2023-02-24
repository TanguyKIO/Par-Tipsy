package com.example.triplekaisse.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triplekaisse.data.GameRepository
import com.example.triplekaisse.domain.model.GameMode
import com.example.triplekaisse.domain.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val gameRepo: GameRepository) : ViewModel() {

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int>
        get() = _state

    private val _gameMode = MutableLiveData<Int>()
    val gameMode: LiveData<Int>
        get() = _gameMode

    private val _playerNameError = MutableLiveData<Boolean>()
    val playerNameError: LiveData<Boolean>
        get() = _playerNameError

    fun getPlayers(): List<Player> {
        return gameRepo.getPlayers()
    }

    fun savePlayer(player: Player) {
        gameRepo.addPlayer(player)
    }

    fun setGameMode(gm: GameMode){
        gameRepo.setGameMode(gm)
        when (gm) {
            GameMode.TEAM -> _gameMode.postValue(1)
            GameMode.NORMAL -> _gameMode.postValue(0)
            else -> _gameMode.postValue(0)
        }
    }

    fun removeTeam() {
        gameRepo.removeTeam()
    }

    fun start(nb: Int) {
        gameRepo.setNumberItem(nb)
        _state.postValue(0)
    }

    fun removePlayer(player: Player) {
        gameRepo.removePlayer(player)
    }

    fun newPlayerDataChanged(text: String, players: List<Player>) {
        if (text.isNotBlank()) {
            var error = false
            for (player in players) {
                if (player.name == text) {
                    error = true
                }
            }
            if(error) _playerNameError.postValue(true)
            else _playerNameError.postValue(false)
        } else {
            _playerNameError.postValue(true)
        }
    }
}