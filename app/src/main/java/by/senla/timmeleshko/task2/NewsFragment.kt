package by.senla.timmeleshko.task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsFragment : Fragment() {

    private lateinit var customAdapter: RecyclerViewAdapterWithButtons
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView = view.findViewById(R.id.newsRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.apply {
            customAdapter = object: RecyclerViewAdapterWithButtons(requireActivity(), MainActivity.preparedItemsList) {
                override fun goToPostFragment() {
                    Navigation.findNavController(view).navigate(R.id.postFragment)
                }

                override fun goToImageViewActivity() {
                    Navigation.findNavController(view).navigate(R.id.imageViewActivity)
                }

                override fun goToArtistFragment() {
                    Navigation.findNavController(view).navigate(R.id.artistFragment)
                }

            }
            val layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = customAdapter
        }
    }
}