package ru.vladburchinskiy.lab5.ui.chat

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.MainScope
import ru.vladburchinskiy.lab5.MainDb
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

        val context = binding.root.context
        val db = MainDb.getDb(context)
        db.getDao().getAllPosts().asLiveData().observe(viewLifecycleOwner) {
            adapter.set(it.reversed())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}