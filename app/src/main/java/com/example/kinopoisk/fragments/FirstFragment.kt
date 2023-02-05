package com.example.kinopoisk.fragments

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kinopoisk.R
import com.example.kinopoisk.TITLE_FIRST
import com.example.kinopoisk.adapter.DataAdapter
import com.example.kinopoisk.data.Movie
import com.example.kinopoisk.ViewModel


class FirstFragment : Fragment() {
    private var items: MutableList<Movie> = mutableListOf()
    private lateinit var itemAdapter: DataAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel by viewModels<ViewModel>()

    private val listener: DataAdapter.OnItemClickListener = object : DataAdapter.OnItemClickListener {
        override fun onItemClick(item: Movie, position: Int) {
            commitTransaction(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = TITLE_FIRST
        itemAdapter = DataAdapter(items, listener)
        recyclerView = view.findViewById(R.id.recyclerview)
        val lManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.apply { layoutManager = lManager }
        recyclerView.adapter = itemAdapter
        viewModel.items.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            itemAdapter.setItems(it)
        })

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun commitTransaction(movie: Movie) {
        val secondFragment: Fragment = SecondFragment.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.animator.flip_right_in,
                R.animator.flip_right_out,
                R.animator.flip_left_in,
                R.animator.flip_left_out
            ).replace(R.id.container, secondFragment)
            .addToBackStack(null).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}