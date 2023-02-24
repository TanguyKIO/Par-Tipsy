package com.example.triplekaisse.presentation.item

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.triplekaisse.R
import com.example.triplekaisse.databinding.NewItemBinding
import com.example.triplekaisse.domain.model.Item
import com.example.triplekaisse.domain.model.Theme
import com.example.triplekaisse.domain.model.Type
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val viewModel: ItemViewModel by viewModels()

    private var _binding: NewItemBinding? = null
    private val binding get() = _binding!!
    private var typeSelected: Type = Type.QUESTION
    private var themeSelected: Theme = Theme.FUN

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val roles = resources.getStringArray(R.array.Role)
        super.onViewCreated(view, savedInstanceState)

        val spinner = binding.itemRole
        context?.let {
            ArrayAdapter(
                it,
                R.layout.cat_spinner_item,
                roles
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.cat_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
        spinner.onItemSelectedListener = this

        binding.add.setOnClickListener {
            val text = binding.addDesc.text.toString()
            val nbPlaceholder = countPlaceholder(text)
            val item = Item(null, typeSelected, themeSelected, text, null, nbPlaceholder, 1, "")
            viewModel.onAddClicked(item)
            Navigation.findNavController(view)
                .navigate(R.id.action_itemFragment_to_itemListFragment)
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_itemFragment_to_itemListFragment)
        }


        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.descDataChanged(
                    binding.addDesc.text.toString(),
                )
            }
        }

        binding.add.isEnabled = false
        binding.addDesc.addTextChangedListener(afterTextChangedListener)

        viewModel.descError.observe(viewLifecycleOwner,
            Observer { descError ->
                if (descError == null) {
                    return@Observer
                }
                binding.add.isEnabled = !descError
            })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        typeSelected = when (p2) {
            0 -> Type.ACTION
            1 -> Type.QUESTION
            2 -> Type.ROLE
            3 -> Type.QUIZZ
            4 -> Type.DUAL
            else -> Type.ACTION
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        typeSelected = Type.ACTION
    }

    private fun countPlaceholder(text: String): Int {
        var count = 0
        for (word in text.split(" ")) {
            if (word.contains("%s", true)) count++
        }
        return count
    }
}