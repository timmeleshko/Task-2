package by.senla.timmeleshko.task6.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.view.adapters.WorksAdapter
import by.senla.timmeleshko.task6.view.adapters.WorksLoadStateAdapter
import by.senla.timmeleshko.task6.model.ServiceLocator
import by.senla.timmeleshko.task6.model.repository.WorkRepository
import by.senla.timmeleshko.task6.model.paging.asMergedLoadStates
import by.senla.timmeleshko.task6.utils.dpToPx
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT_EMPTY
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.FOOTER_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.HORIZONTAL_COLUMN_MARGIN
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.VERTICAL_COLUMN_MARGIN
import com.rubensousa.decorator.GridSpanMarginDecoration
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorksAdapter

    object MainActivityConstants {
        const val COLUMNS_COUNT = 2
        const val COLUMNS_COUNT_EMPTY = 1
        const val VERTICAL_COLUMN_MARGIN = 16
        const val HORIZONTAL_COLUMN_MARGIN = 16
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2
    }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        initAdapter()
    }

    private val viewModel: WorksViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val repo = ServiceLocator.instance(this@MainActivity)
                        .getRepository(WorkRepository.Type.DB)
                @Suppress("UNCHECKED_CAST")
                return WorksViewModel(repo) as T
            }
        }
    }


    @InternalCoroutinesApi
    private fun initAdapter() {
        adapter = WorksAdapter(this)
        val gridLayoutManager = GridLayoutManager(this@MainActivity, COLUMNS_COUNT)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    DATA_VIEW_TYPE -> COLUMNS_COUNT_EMPTY
                    FOOTER_VIEW_TYPE -> COLUMNS_COUNT
                    else -> COLUMNS_COUNT
                }
            }
        }
        recyclerView.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(
                GridSpanMarginDecoration(
                    verticalMargin = dpToPx(VERTICAL_COLUMN_MARGIN),
                    horizontalMargin = dpToPx(HORIZONTAL_COLUMN_MARGIN),
                    gridLayoutManager = gridLayoutManager
                )
            )
            adapter = this@MainActivity.adapter.withLoadStateHeaderAndFooter(
                header = WorksLoadStateAdapter(this@MainActivity.adapter),
                footer = WorksLoadStateAdapter(this@MainActivity.adapter)
            )
        }
        lifecycleScope.launchWhenCreated {
            viewModel.works.collectLatest {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .asMergedLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) }
        }
    }
}