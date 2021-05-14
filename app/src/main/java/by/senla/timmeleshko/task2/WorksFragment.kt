package by.senla.timmeleshko.task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task2.beans.ListItem

class WorksFragment : Fragment() {

    private lateinit var customAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_works, container, false)
        recyclerView = view.findViewById(R.id.worksRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.apply {
            customAdapter = object: RecyclerViewAdapter(requireActivity(), MainActivity.preparedItemsList) {
                override fun onClickItem() {
                    Navigation.findNavController(view).navigate(R.id.artActivity)
                }
            }
            val layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = customAdapter
        }
    }
}