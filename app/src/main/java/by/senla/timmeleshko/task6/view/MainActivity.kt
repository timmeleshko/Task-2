package by.senla.timmeleshko.task6.view

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.ServiceLocator
import by.senla.timmeleshko.task6.model.paging.asMergedLoadStates
import by.senla.timmeleshko.task6.model.repository.WorkRepository
import by.senla.timmeleshko.task6.utils.dpToPx
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.CHIPS_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT_EMPTY
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.FOOTER_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.HORIZONTAL_COLUMN_MARGIN
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.VERTICAL_COLUMN_MARGIN
import by.senla.timmeleshko.task6.view.adapters.HeaderAdapter
import by.senla.timmeleshko.task6.view.adapters.WorksAdapter
import by.senla.timmeleshko.task6.view.adapters.WorksLoadStateAdapter
import com.rubensousa.decorator.GridSpanMarginDecoration
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var headerAdapter: HeaderAdapter
    private lateinit var worksAdapter: WorksAdapter
    private lateinit var concatAdapter: ConcatAdapter

    object MainActivityConstants {
        const val COLUMNS_COUNT = 2
        const val COLUMNS_COUNT_EMPTY = 1
        const val VERTICAL_COLUMN_MARGIN = 16
        const val HORIZONTAL_COLUMN_MARGIN = 16
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2
        const val CHIPS_VIEW_TYPE = 3
    }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        progressBar = findViewById(R.id.progressBar)
        initHeaderAdapter()
        initWorksAdapter()
        concatAdapter = ConcatAdapter(headerAdapter, worksAdapter)
        recyclerView.adapter = concatAdapter
    }

    private val viewModel: WorksViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val repo = ServiceLocator.instance(this@MainActivity).getRepository(WorkRepository.Type.DB)
                @Suppress("UNCHECKED_CAST")
                return WorksViewModel(repo, handle) as T
            }
        }
    }

    private fun initHeaderAdapter() {
        headerAdapter = object : HeaderAdapter(listOf()) {
            override fun clickChip(uri: String) {
                viewModel.showWork(uri)
            }
        }
        val filtersViewModel = ViewModelProvider(this).get(FiltersViewModel::class.java)
        filtersViewModel.getData().observe(this@MainActivity, Observer { data ->
            data.filters.let {
                headerAdapter.updateFilters(it)
            }
        })
    }

    @InternalCoroutinesApi
    private fun initWorksAdapter() {
        worksAdapter = WorksAdapter(this@MainActivity)
        val gridLayoutManager = GridLayoutManager(this@MainActivity, COLUMNS_COUNT)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    DATA_VIEW_TYPE -> COLUMNS_COUNT_EMPTY
                    FOOTER_VIEW_TYPE, CHIPS_VIEW_TYPE -> COLUMNS_COUNT
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
            adapter = this@MainActivity.worksAdapter.withLoadStateHeaderAndFooter(
                header = WorksLoadStateAdapter(this@MainActivity.worksAdapter),
                footer = WorksLoadStateAdapter(this@MainActivity.worksAdapter)
            )
        }
        lifecycleScope.launchWhenCreated {
            worksAdapter.loadStateFlow.collectLatest { loadStates ->
                progressBar.isVisible = loadStates.mediator?.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.works.collectLatest {
                worksAdapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            worksAdapter.loadStateFlow
                .asMergedLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) }
        }
    }
}