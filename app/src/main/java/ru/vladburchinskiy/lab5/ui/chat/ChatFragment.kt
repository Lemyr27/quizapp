package ru.vladburchinskiy.lab5.ui.chat

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vladburchinskiy.lab5.R
import ru.vladburchinskiy.lab5.adapter.SimpleAdapter
import ru.vladburchinskiy.lab5.databinding.FragmentChatBinding
import ru.vladburchinskiy.lab5.model.Post

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter by lazy {
        SimpleAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Живой чат"
        (activity as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#222222")))

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.apply {
            adapter = this@ChatFragment.adapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
        }

        val posts: List<Post> = listOf(
            Post(
                name="Иванов Иван Иванович",
                message="Лисица — очень ловкое, сообразительное и хитрое животное со стройным и красивым, удлинённым телом и длинным пышным хвостом.",
                image=R.drawable.lis1
            ),
            Post(
                name="Петров Петр Петрович",
                message="Длина тела — 50–90 см, хвост длинный и составляет больше половины тела — 35–60 см.",
                image=R.drawable.lis2
            ),
            Post(
                name="Васильев Василий Васильевич",
                message="Лисы относятся к семейству псовых, что означает их родство с волками, шакалами и собаками. Они среднего размера, весом от двух до 24 килограммов, с острыми мордами, стройными фигурами и кустистыми хвостами."
            ),
            Post(
                name="Курткин Николай Львович",
                message="Как и управляемая ракета, лиса использует магнитное поле Земли в своих охотничьих целях. Другие животные, такие как птицы, акулы и черепахи, обладают этим «магнитным чувством», но лиса — первая, кто использует его для ловли добычи."
            )
        )

        adapter.set(posts)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}