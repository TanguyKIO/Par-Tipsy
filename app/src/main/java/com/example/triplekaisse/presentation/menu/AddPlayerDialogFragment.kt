package com.example.itonhoanandroid.ui.bluetooth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.triplekaisse.databinding.FragmentDialogBinding


class AddPlayerDialogFragment : DialogFragment() {


    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            dismiss()
        }
        return binding.root
    }


}