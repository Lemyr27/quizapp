package ru.vladburchinskiy.lab5.ui.quiz

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.red
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import ru.vladburchinskiy.lab5.R
import ru.vladburchinskiy.lab5.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val quizQuestions = mapOf(
        "К какому семейству относятся лисы?" to mapOf(
            "Псовые" to true,
            "Лисьи" to false,
            "Волчьи" to false,
            "Заячьи" to false,
        ),
        "Какой вид лисиц является самым крупным?" to mapOf(
            "Африканская" to false,
            "Обыкновенная" to true,
            "Афганская" to false,
            "Тюменская" to false,
        ),
        "Какого цвета лапы у обыкновенной лисицы?" to mapOf(
            "Рыжие" to false,
            "Темные" to true,
            "Белые" to false,
            "Желтые" to false,
        ),
        "В каком городе лисицы живут на окраинах и иногда появляются в центре города?" to mapOf(
            "Архангельск" to false,
            "Лондон" to true,
            "Тюмень" to false,
            "Рим" to false,
        ),
        "На какое животное внешне и по поведению похожа афганская лисица?" to mapOf(
            "Собака" to true,
            "Волк" to false,
            "Белка" to false,
            "Кошка" to false,
        ),
        "Какой вид лисиц считается очень скрытным?" to mapOf(
            "Бенгальская" to false,
            "Песчаная" to false,
            "Африканская" to true,
            "Тюменская" to false,
        ),
        "Какая лисица обитает в предгорьях Южных Гималаев?" to mapOf(
            "Бенгальская" to true,
            "Корсак" to false,
            "Тибетская лисица" to false,
            "Южно-гималайская" to false,
        ),
        "Как называется самый маленький представитель семейства псовых?" to mapOf(
            "Корсак" to false,
            "Песчаная лисица" to false,
            "Фенек" to true,
            "Кошачья" to false,
        ),
        "На какой монете изображен Фенек?" to mapOf(
            "На алжирском дукате" to true,
            "На байсе" to false,
            "На миле" to false,
            "На копейке" to false,
        ),
        "Кто из перечисленных видов лисиц питается в основном термитами?" to mapOf(
            "Африканская лисица" to false,
            "Фенек" to false,
            "Большеухая лисица" to true,
            "Вкуснятина" to false,
        ),

        )
    private val questionsList = listOf(
        "К какому семейству относятся лисы?",
        "Какой вид лисиц является самым крупным?",
        "Какого цвета лапы у обыкновенной лисицы?",
        "В каком городе лисицы живут на окраинах и иногда появляются в центре города?",
        "На какое животное внешне и по поведению похожа афганская лисица?",
        "Какой вид лисиц считается очень скрытным?",
        "Какая лисица обитает в предгорьях Южных Гималаев?",
        "Как называется самый маленький представитель семейства псовых?",
        "На какой монете изображен Фенек?",
        "Кто из перечисленных видов лисиц питается в основном термитами?",
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        binding.startButton.setOnClickListener {
            binding.textQuiz.visibility = INVISIBLE
            binding.startButton.visibility = INVISIBLE
            binding.nextButton.visibility = VISIBLE
            binding.questionText.visibility = VISIBLE
            binding.questionsBlock.visibility = VISIBLE
            binding.a1.visibility = VISIBLE
            binding.a2.visibility = VISIBLE
            binding.a3.visibility = VISIBLE
            binding.a4.visibility = VISIBLE
        }

        var counter = 0
        var score = 0
        var pressedButton: MaterialButton = binding.a1
        val buttons = listOf(binding.a1, binding.a2, binding.a3, binding.a4)
        val selectButtons = mapOf(
            binding.a1 to binding.selectA1,
            binding.a2 to binding.selectA2,
            binding.a3 to binding.selectA3,
            binding.a4 to binding.selectA4,
        )

        val trueButtons = listOf(
            binding.trueA1,
            binding.trueA2,
            binding.trueA3,
            binding.trueA4,
        )

        fun showAllData() {
            if (questionsList.size > counter) {
                binding.questionsBlock.text = "Вопрос ${counter+1}/${questionsList.size}"
                val question = questionsList[counter]
                binding.questionText.text = question
                val answers = mutableListOf<String>()
                quizQuestions[question]!!.keys.forEach {
                    answers.add(it)
                }
                binding.a1.text = answers[0]
                binding.a2.text = answers[1]
                binding.a3.text = answers[2]
                binding.a4.text = answers[3]
            } else {
                binding.nextButton.visibility = INVISIBLE
                binding.questionText.visibility = INVISIBLE
                binding.questionsBlock.visibility = INVISIBLE
                binding.a1.visibility = INVISIBLE
                binding.a2.visibility = INVISIBLE
                binding.a3.visibility = INVISIBLE
                binding.a4.visibility = INVISIBLE

                binding.gratzImage.isVisible = true
                binding.gratz.isVisible = true
                binding.results.text = "Вы ответили на $score вопросов из ${questionsList.size}. Можно сказать, что вы хорошо знаете факты о лисах"
                binding.results.isVisible = true
            }
        }

        showAllData()

        fun clearSelectButtons() {
            selectButtons.forEach { it.value.isVisible = false }
        }

        fun clearTrueButtons() {
            trueButtons.forEach { it.isVisible = false }
        }

        fun clearStroke() {
            buttons.forEach { it.strokeWidth = 0 }
        }

        var isFirstTap = true
        binding.nextButton.setOnClickListener {
            val question = questionsList[counter]
            val isCorrectAnswer = quizQuestions[question]?.get(pressedButton.text) == true
            if (isFirstTap) {
                buttons.forEach {it.isEnabled = false; it.setTextColor(Color.WHITE)}
                var trueAnswer = ""
                quizQuestions[question]!!.forEach {
                    if (it.value) trueAnswer = it.key
                }
                if (!isCorrectAnswer) {
                    var id = 0
                    for (btn in buttons) {
                        if (btn.text == trueAnswer) break
                        else id += 1
                    }
                    trueButtons[id].isVisible = true
                    trueButtons[id].z = 12f
                    buttons[id].strokeWidth = 5
                    buttons[id].setStrokeColorResource(R.color.red)
                } else {
                    pressedButton.strokeWidth = 5
                    pressedButton.setStrokeColorResource(R.color.green)
                }
            } else {
                clearStroke()
                clearTrueButtons()
                buttons.forEach {it.isEnabled = true}
                if (isCorrectAnswer) score += 1
                counter += 1
                showAllData()
                binding.nextButton.isEnabled = false
                binding.nextButton.setTextColor(R.color.bad_yellow.toInt())
                clearSelectButtons()
            }
            isFirstTap = !isFirstTap
        }

        for (btn in buttons) {
            btn.setOnClickListener {
                pressedButton = btn
                binding.nextButton.apply {
                    setTextColor(Color.BLACK)
                    isEnabled = true
                }
                clearSelectButtons()
                btn.z = 1f
                selectButtons[btn]!!.apply {
                    z = 10f
                    isVisible = true
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}