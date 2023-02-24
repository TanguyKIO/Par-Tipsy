package com.example.triplekaisse.presentation.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itonhoanandroid.ui.bluetooth.AddPlayerDialogFragment
import com.example.triplekaisse.R
import com.example.triplekaisse.databinding.MenuFragmentBinding
import com.example.triplekaisse.domain.model.GameMode
import com.example.triplekaisse.domain.model.Player
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MenuFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var recyclerViewT1: RecyclerView
    private lateinit var viewManagerT1: RecyclerView.LayoutManager

    private lateinit var recyclerViewT2: RecyclerView
    private lateinit var viewManagerT2: RecyclerView.LayoutManager

    private val viewModel: MenuViewModel by viewModels()
    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!
    private var players = mutableListOf<Player>()
    private var playersTeam1 = mutableListOf<Player>()
    private var playersTeam2 = mutableListOf<Player>()
    private var nbQuest by Delegates.notNull<Int>()

    private var gameModeSelected: GameMode = GameMode.NORMAL

    private lateinit var adapter: PlayerAdapter
    private lateinit var adapterTeam1: PlayerAdapter
    private lateinit var adapterTeam2: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = PlayerAdapter(players, ::onDeleteClickListener)
        adapterTeam1 = PlayerAdapter(playersTeam1, ::onDeleteClickListener)
        adapterTeam2 = PlayerAdapter(playersTeam2, ::onDeleteClickListener)
        _binding = MenuFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players = viewModel.getPlayers().toMutableList()


        val gameModes = resources.getStringArray(R.array.Gamemodes)

        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        recyclerView = view.findViewById(R.id.players)
        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = viewManager
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = adapter

        recyclerViewT1 = view.findViewById(R.id.players_team1)
        viewManagerT1 = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewT1.layoutManager = viewManagerT1
        recyclerViewT1.addItemDecoration(divider)
        recyclerViewT1.adapter = adapterTeam1

        recyclerViewT2 = view.findViewById(R.id.players_team2)
        viewManagerT2 = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewT2.layoutManager = viewManagerT2
        recyclerViewT2.addItemDecoration(divider)
        recyclerViewT2.adapter = adapterTeam2

        adapter.players = players
        adapterTeam1.players = playersTeam1
        adapterTeam1.players = playersTeam2
        adapter.notifyDataSetChanged()
        adapterTeam1.notifyDataSetChanged()
        adapterTeam2.notifyDataSetChanged()

        binding.playButton.setOnClickListener {
            if (players.size >= 2) {
                nbQuest = Integer.parseInt(binding.nbQuest.text.toString())
                if(viewModel.gameMode.value == 1) {
                    val team1 = Player(0,binding.team1Input.text.toString(), null, null, null, 1)
                    val team2 = Player(0,binding.team2Input.text.toString(), null, null, null, 2)
                    viewModel.savePlayer(team1)
                    viewModel.savePlayer(team2)
                    viewModel.start(nbQuest)
                } else {
                    viewModel.start(nbQuest)
                }
            } else {
                val fm: FragmentManager = childFragmentManager
                val dialogFragment = AddPlayerDialogFragment()
                dialogFragment.show(fm, "DialogFragment")
            }
        }

        binding.add.setOnClickListener {
            val newPlayer = Player(players.size+1, binding.input.text.toString(), null, null, null, if (players.size % 2 == 0) 1 else 2)
            viewModel.savePlayer(newPlayer)
            players.add(newPlayer)
            if(newPlayer.team == 1) {
                playersTeam1.add(newPlayer)
            } else {
                playersTeam2.add(newPlayer)
            }
            adapter.players = players
            adapterTeam1.players = playersTeam1
            adapterTeam2.players = playersTeam2
            adapter.notifyDataSetChanged()
            adapterTeam1.notifyDataSetChanged()
            adapterTeam2.notifyDataSetChanged()
            binding.input.setText("")
            if (players.size != 0) {
                binding.addPlayerText.visibility = View.INVISIBLE
            } else {
                binding.addPlayerText.visibility = View.VISIBLE
            }
        }

        binding.editButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_itemListFragment)
        }

        viewModel.state.observe(viewLifecycleOwner) {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_questionFragment)
        }

        val spinner = binding.gameMode
        context?.let {
            ArrayAdapter(
                it,
                R.layout.cat_spinner_item,
                gameModes
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.cat_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
        spinner.onItemSelectedListener = this

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.newPlayerDataChanged(
                    binding.input.text.toString(),
                    players
                )
            }
        }

        binding.add.isEnabled = false
        binding.input.addTextChangedListener(afterTextChangedListener)

        viewModel.playerNameError.observe(viewLifecycleOwner,
            Observer { playerNameError ->
                if (playerNameError == null) {
                    return@Observer
                }
                binding.add.isEnabled = !playerNameError
            })

        viewModel.gameMode.observe(viewLifecycleOwner) {
            when (it) {
                0 -> {
                    binding.players.visibility = View.VISIBLE
                    binding.playersTeam1.visibility = View.INVISIBLE
                    binding.playersTeam2.visibility = View.INVISIBLE
                    binding.team1Input.visibility = View.INVISIBLE
                    binding.team2Input.visibility = View.INVISIBLE
                    viewModel.removeTeam()
                }
                1 -> {
                    binding.players.visibility = View.INVISIBLE
                    binding.playersTeam1.visibility = View.VISIBLE
                    binding.playersTeam2.visibility = View.VISIBLE
                    binding.team1Input.visibility = View.VISIBLE
                    binding.team2Input.visibility = View.VISIBLE

                }
                else -> {
                    binding.players.visibility = View.VISIBLE
                    binding.playersTeam1.visibility = View.INVISIBLE
                    binding.playersTeam2.visibility = View.INVISIBLE
                    binding.team1Input.visibility = View.INVISIBLE
                    binding.team2Input.visibility = View.INVISIBLE
                    viewModel.removeTeam()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (players.size != 0) {
            binding.addPlayerText.visibility = View.INVISIBLE
        } else {
            binding.addPlayerText.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        gameModeSelected = when (p2) {
            0 -> GameMode.NORMAL
            1 -> GameMode.TEAM
            else -> GameMode.NORMAL
        }
        viewModel.setGameMode(gameModeSelected)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        gameModeSelected = GameMode.NORMAL
    }

    private fun onDeleteClickListener(player: Player) {
        players.remove(player)
        if(player.team == 1) playersTeam1.remove(player)
        else playersTeam2.remove(player)
        viewModel.removePlayer(player)
        adapter.notifyDataSetChanged()
        adapterTeam1.notifyDataSetChanged()
        adapterTeam2.notifyDataSetChanged()
        if (players.size != 0) {
            binding.addPlayerText.visibility = View.INVISIBLE
        } else {
            binding.addPlayerText.visibility = View.VISIBLE
        }
    }

}