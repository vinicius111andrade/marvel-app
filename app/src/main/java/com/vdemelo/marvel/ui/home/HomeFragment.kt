package com.vdemelo.marvel.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.FragmentHomeBinding
import com.vdemelo.marvel.ui.adapters.ListLoadStateAdapter
import com.vdemelo.marvel.ui.home.adapter.MarvelCharactersAdapter
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.state.ListAction
import com.vdemelo.marvel.ui.state.ListState
import com.vdemelo.marvel.ui.state.RemotePresentationState
import com.vdemelo.marvel.ui.state.asRemotePresentationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var itemsAdapter: MarvelCharactersAdapter
    private lateinit var headerAdapter: ListLoadStateAdapter
    private lateinit var footerAdapter: ListLoadStateAdapter

    private val viewModel: HomeViewModel by viewModel()

    //TODO colocar adapters como lateinit vals aqui em cima?

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFavoritesButtonClickListener()
        initListAdapters()
        binding.bindState(
            listState = viewModel.listStateFlow,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.listAction
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListAdapters() {
        itemsAdapter = MarvelCharactersAdapter(
            openCardAction = ::openCharacterCard,
            favoriteAction = ::favoriteCharacter
        )
        headerAdapter = ListLoadStateAdapter { itemsAdapter.retry() }
        footerAdapter = ListLoadStateAdapter { itemsAdapter.retry() }
    }

    private fun openCharacterCard(characterUi: MarvelCharacterUi) {
        val action = HomeFragmentDirections.actionHomeToCharacter(characterUi)
        findNavController().navigate(action)
    }

    private fun favoriteCharacter(characterUi: MarvelCharacterUi, isFavorite: Boolean) {
        viewModel.favoriteCharacter(characterUi, isFavorite)
    }

    private fun setFavoritesButtonClickListener() {
        binding.navToFavoritesButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToFavorites()
            findNavController().navigate(action)
        }
    }

    /**
     * Binds the [ListState] provided  by the [ViewModel] to the UI,
     * and allows the UI to feed back user actions to it.
     */
    private fun FragmentHomeBinding.bindState( //TODO melhorar nome
        listState: StateFlow<ListState>,
        pagingData: Flow<PagingData<MarvelCharacterUi>>,
        uiActions: (ListAction) -> Unit
    ) {
        setupListAdapters()
        setupSearchFieldListeners(onQueryChanged = uiActions)
        setupListStateCollector(listState = listState)
        setupRetryButton()
        setupScrollListener(listState = listState, onScrollChanged = uiActions)
        pagingData.collectLatestIntoItemsAdapter()
        setupShouldScrollToTopCollection(listState)
        bindList()
    }

    private fun FragmentHomeBinding.setupListAdapters() {
        list.adapter = itemsAdapter.withLoadStateHeaderAndFooter(
            header = headerAdapter,
            footer = footerAdapter
        )
    }

    private fun FragmentHomeBinding.setupSearchFieldListeners(
        onQueryChanged: (ListAction.Search) -> Unit
    ) {
        searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListActionWithNewQuery(onQueryChanged)
                true
            } else {
                false
            }
        }
        searchField.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListActionWithNewQuery(onQueryChanged)
                true
            } else {
                false
            }
        }
    }

    private fun FragmentHomeBinding.setupListStateCollector(listState: StateFlow<ListState>) {
        //TODO da pra mandar isso aqui pra view model, criar um LiveData e observar ele, e quando mudar setar na view
        lifecycleScope.launch {
            listState
                .map { it.query }
                .distinctUntilChanged()
                .collect {
                    searchField.setText(it)
                }
        }
    }

    //TODO ele dá um trim no text, scrolla pro inicio, e atualiza o hot flow listAction
    private fun FragmentHomeBinding.updateListActionWithNewQuery(
        onQueryChanged: (ListAction.Search) -> Unit
    ) {
        searchField.text.trim().let {
            list.scrollToPosition(0)
            onQueryChanged(ListAction.Search(query = it.toString()))
        }
    }

    private fun FragmentHomeBinding.setupRetryButton() {
        retryButton.setOnClickListener { itemsAdapter.retry() }
    }

    private fun FragmentHomeBinding.setupScrollListener(
        listState: StateFlow<ListState>,
        onScrollChanged: (ListAction.Scroll) -> Unit
    ) {
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0)
                    onScrollChanged(ListAction.Scroll(currentQuery = listState.value.query))
            }
        })
    }

    private fun Flow<PagingData<MarvelCharacterUi>>.collectLatestIntoItemsAdapter() {
        lifecycleScope.launch {
            this@collectLatestIntoItemsAdapter.collectLatest(itemsAdapter::submitData)
        }
    }

    private fun createShouldScrollToTopFlow(
        itemsAdapter: MarvelCharactersAdapter,
        listState: StateFlow<ListState>
    ): Flow<Boolean> {
        val notLoading: Flow<Boolean> = itemsAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }

        val hasNotScrolledForCurrentSearch: Flow<Boolean> = listState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        return combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()
    }

    private fun setupShouldScrollToTopCollection(listState: StateFlow<ListState>) {
        val shouldScrollToTop: Flow<Boolean> = createShouldScrollToTopFlow(itemsAdapter, listState)
        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) binding.list.scrollToPosition(0)
            }
        }
    }

    //TODO tratando diversos comportamentos da lista
    private fun FragmentHomeBinding.bindList() {
        lifecycleScope.launch {
            itemsAdapter.loadStateFlow.collect { loadState ->
                // Show a retry header if there was an error refreshing, and items were previously
                // cached OR default to the default prepend state
                headerAdapter.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && itemsAdapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty = loadState.refresh is LoadState.NotLoading &&
                        itemsAdapter.itemCount == 0
                // show empty list
                emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or
                // the remote.
                list.isVisible = loadState.source.refresh is LoadState.NotLoading ||
                        loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error &&
                        itemsAdapter.itemCount == 0
                // Toast on any error, regardless of whether it came from RemoteMediator
                // or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    showErrorToast(loadStateError = it)
                }
            }
        }
    }

    private fun showErrorToast(loadStateError: LoadState.Error) {
        val errorMessage: String = getString(R.string.list_error_toast) + loadStateError.error
        Toast.makeText(
            this@HomeFragment.context,
            errorMessage,
            Toast.LENGTH_LONG
        ).show()
    }
}
