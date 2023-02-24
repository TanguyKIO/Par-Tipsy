package com.example.triplekaisse.presentation.item

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triplekaisse.R
import com.example.triplekaisse.databinding.EditFragmentBinding
import com.example.triplekaisse.databinding.QuestionFragmentBinding
import com.example.triplekaisse.domain.model.Item
import com.example.triplekaisse.presentation.question.QuestionViewModel
import com.example.triplekaisse.presentation.question.UiQuestion
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : Fragment() {
    private val viewModel: ItemListViewModel by viewModels()

    private var _binding: EditFragmentBinding? = null
    private val binding get() = _binding!!

    private var items: MutableList<Item> = mutableListOf()
    private val itemAdapter by lazy { ItemAdapter(items, items,::onUpdateClickListener) }
    private var iterator: Int = 0

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        recyclerView = binding.items
        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = viewManager
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = itemAdapter

        viewModel.gameQuestions.observe(viewLifecycleOwner) {
            itemAdapter.items = it
            itemAdapter.allItems = it
            items = it.toMutableList()
            itemAdapter.notifyDataSetChanged()
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_itemListFragment_to_menuFragment)
        }

        binding.add.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_itemListFragment_to_itemragment)
        }

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                itemAdapter.filter.filter(s.toString())
            }
        }

        binding.search.addTextChangedListener(afterTextChangedListener)
    }

    private fun onUpdateClickListener(item: Item){
        item.active = if (item.active == 0) 1 else 0
        itemAdapter.notifyDataSetChanged()
        viewModel.onHideClicked(item)
    }
}