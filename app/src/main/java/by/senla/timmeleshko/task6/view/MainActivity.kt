package by.senla.timmeleshko.task6.view

import android.content.Context
import android.content.Intent
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
import by.senla.timmeleshko.task6.App
import by.senla.timmeleshko.task6.R
import by.senla.timmeleshko.task6.model.api.DataApi
import by.senla.timmeleshko.task6.model.paging.asMergedLoadStates
import by.senla.timmeleshko.task6.model.repository.WorkRepository
import by.senla.timmeleshko.task6.utils.dpToPx
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.COLUMNS_COUNT_EMPTY
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.DATA_VIEW_TYPE
import by.senla.timmeleshko.task6.view.MainActivity.MainActivityConstants.ITEMS_OFFSET
import by.senla.timmeleshko.task6.view.adapters.HeaderAdapter
import by.senla.timmeleshko.task6.view.adapters.WorksAdapter
import by.senla.timmeleshko.task6.view.adapters.WorksLoadStateAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var headerAdapter: HeaderAdapter
    private lateinit var worksAdapter: WorksAdapter
    private lateinit var worksConcatAdapter: ConcatAdapter
    private lateinit var concatAdapter: ConcatAdapter

    @Inject
    lateinit var workRepository: WorkRepository
    @Inject
    lateinit var dataApi: DataApi

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    object MainActivityConstants {
        const val COLUMNS_COUNT = 2
        const val COLUMNS_COUNT_EMPTY = 1
        const val ITEMS_OFFSET = 16
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent().inject(this)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.worksList)
        progressBar = findViewById(R.id.progressBar)
        initHeaderAdapter()
        initWorksAdapter()

        worksConcatAdapter = worksAdapter.withLoadStateHeaderAndFooter(
            header = WorksLoadStateAdapter(this@MainActivity.worksAdapter),
            footer = WorksLoadStateAdapter(this@MainActivity.worksAdapter)
        )
        concatAdapter = ConcatAdapter(headerAdapter, worksConcatAdapter)
        val gridLayoutManager = GridLayoutManager(this@MainActivity, COLUMNS_COUNT)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (worksAdapter.getItemViewType(position)) {
                    DATA_VIEW_TYPE -> COLUMNS_COUNT_EMPTY
                    else -> COLUMNS_COUNT
                }
            }
        }
        val itemDecoration = OffsetDecorator(dpToPx(ITEMS_OFFSET))
        recyclerView.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(itemDecoration)
            adapter = concatAdapter
        }
    }

    private val viewModel: WorksViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                @Suppress("UNCHECKED_CAST")
                return WorksViewModel(workRepository, handle) as T
            }
        }
    }

    private val filtersViewModel: FiltersViewModel by viewModels {
        FilterViewModelFactory(dataApi)
    }

    private fun initHeaderAdapter() {
        headerAdapter = object : HeaderAdapter(listOf()) {
            override fun clickChip(uri: String?) {
                if (uri != null) {
                    viewModel.showWork(uri)
                } else {
                    viewModel.showWork("")
                }
            }
        }
        filtersViewModel.getData().observe(this@MainActivity, Observer { data ->
            data?.filters.let {
                headerAdapter.updateFilters(it)
            }
        })
    }

    private fun initWorksAdapter() {
        worksAdapter = WorksAdapter(this@MainActivity)
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