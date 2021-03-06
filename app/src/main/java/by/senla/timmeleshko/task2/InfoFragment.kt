package by.senla.timmeleshko.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfoFragment : Fragment() {

    private lateinit var customAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        recyclerView = view.findViewById(R.id.infoRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.apply {
            customAdapter = object: RecyclerViewAdapter(requireActivity(), MainActivity.preparedItemsList) {
                override fun onClickItem() {
                    Navigation.findNavController(view).navigate(R.id.detailFragment)
                }
            }
            val layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = customAdapter
        }
    }
}