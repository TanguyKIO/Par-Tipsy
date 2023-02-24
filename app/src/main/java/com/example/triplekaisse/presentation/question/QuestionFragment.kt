package com.example.triplekaisse.presentation.question

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.triplekaisse.R
import com.example.triplekaisse.databinding.QuestionFragmentBinding
import com.example.triplekaisse.domain.model.Item
import com.example.triplekaisse.domain.model.Type
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private val viewModel: QuestionViewModel by viewModels()

    private var _binding: QuestionFragmentBinding? = null
    private val binding get() = _binding!!

    private var questions: List<UiQuestion> = listOf()
    private var iterator: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuestionFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.card.setOnClickListener {
            if (iterator < questions.size) {
                val color = resources.getDrawable(questions[iterator].color, null)
                (it as CardView).background = color
                binding.question.text = questions[iterator].questions
                binding.title.text = questions[iterator].title
                iterator++
            } else {
                Navigation.findNavController(view)
                    .navigate(R.id.action_questionFragment_to_menuFragment)
            }
        }

        viewModel.gameQuestions.observe(viewLifecycleOwner) {
            questions = toUiQuestion(it)
            binding.question.text = questions[0].questions
            binding.title.text = questions[0].title
            val color = resources.getDrawable(questions[0].color, null)
            binding.card.background = color
        }

        binding.homeButton.setOnClickListener {
            questions = listOf()
            Navigation.findNavController(view)
                .navigate(R.id.action_questionFragment_to_menuFragment)
        }
    }

    private fun toUiQuestion(questions: List<Item>): List<UiQuestion> {
        val uiQuestions = mutableListOf<UiQuestion>()
        var color: Int
        var title: String
        for (item in questions) {
            color = when (item.type) {
                Type.ACTION -> R.drawable.gradient
                Type.DUAL -> R.drawable.pink_grad
                Type.QUIZZ -> R.drawable.pink_grad
                Type.QUESTION -> R.drawable.green_grad
                Type.ROLE -> R.drawable.pink_grad
            }
            title = when (item.type) {
                Type.ACTION -> "Action"
                Type.DUAL -> "Duel"
                Type.QUIZZ -> "Quizz"
                Type.QUESTION -> "Question"
                Type.ROLE -> "Rôle"
            }
            uiQuestions.add(UiQuestion(title, item.description, color))
        }
        return uiQuestions
    }

}