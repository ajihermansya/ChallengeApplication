package com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.takehomechallenge.mohamadajihermansya.data.network.apiservice.DependencyProvider
import com.takehomechallenge.mohamadajihermansya.R
import com.takehomechallenge.mohamadajihermansya.databinding.CharactersListFragmentBinding
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData
import com.takehomechallenge.mohamadajihermansya.databinding.FilterLayoutBinding
import com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.adapter.ListCharactersAdapter
import com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.adapter.StateCharactersAdapter
import com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.viemodel.ViewModelListCharacters
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharactersListFragment : Fragment() {

    private lateinit var viewModel: ViewModelListCharacters
    private lateinit var binding: CharactersListFragmentBinding
    private val adapter = ListCharactersAdapter()
    private lateinit var stateCharactersAdapter: StateCharactersAdapter
    private lateinit var filterData: FilterData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CharactersListFragmentBinding.inflate(inflater)
        binding.apply {
            charactersList.setHasFixedSize(true)
            adapter.onItemClick {
                val bundle = Bundle()
                bundle.putInt(resources.getString(R.string.character_id), it)
                findNavController().navigate(R.id.characterCardFragment, bundle)
            }
            retryButton.setOnClickListener { adapter.retry() }
            stateCharactersAdapter = StateCharactersAdapter{ adapter.retry() }
            charactersList.adapter = adapter.withLoadStateFooter(stateCharactersAdapter)
            getToolbar(binding, inflater)
            getUiState(adapter)
            scrollingSearching(adapter)
            return root
        }
    }

    private fun getUiState(adapter: ListCharactersAdapter){
        binding.apply {
            adapter.addLoadStateListener {
                errorText.isVisible = it.refresh is LoadState.Error
                progress.isVisible = it.refresh is LoadState.Loading
                if(it.refresh is LoadState.Error){
                    if((it.refresh as LoadState.Error).error.message == "404"){
                        errorWrapper.visibility = View.VISIBLE
                        retryButton.visibility = View.GONE
                        errorText.text = resources.getString(R.string.not_found)
                    }else{
                        errorText.text = resources.getString(R.string.error)
                        errorWrapper.visibility = View.VISIBLE
                        retryButton.visibility = View.VISIBLE
                    }
                }else{
                    errorWrapper.visibility = View.GONE
                    retryButton.visibility = View.GONE
                }
            }
        }
    }

    private fun getToolbar(binding: CharactersListFragmentBinding, inflater: LayoutInflater){
        setSearchView()
        binding.fiterButton.setOnClickListener {
            showDialog(inflater)
        }
    }

    private fun showDialog(inflater: LayoutInflater){
        val binding = FilterLayoutBinding.inflate(inflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)
        searchFilter(binding)
        dialog.setOnCancelListener {
            filterData.statusId = binding.dsPickerStatus.selectedIndex
            filterData.genderId = binding.dsPickerGender.selectedIndex
        }
        dialog.show()
    }

    private fun searchFilter(binding: FilterLayoutBinding){
        binding.apply {
            dsPickerStatus.selectedIndex = filterData.statusId
            dsPickerGender.selectedIndex = filterData.genderId
            startSearchButton.setOnClickListener {
                val status = dsPickerStatus.selectedItem.toString()
                val gender = dsPickerGender.selectedItem.toString()
                if(status == "no"){
                    filterData.status = null
                }else{
                    filterData.status = status
                }
                if(gender == "no"){
                    filterData.gender = null
                }else{
                    filterData.gender = gender
                }
                viewModel.fetchResultsByFilter(filterData)
            }
        }
    }

    private fun setSearchView(){
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                bouncedata(newText)
                return true
            }
        })
    }

    var handler: Handler = Handler(Looper.getMainLooper())
    private fun bouncedata(text: String){
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(
            {
                filterData.name = text
                viewModel.fetchResultsByFilter(filterData)
            },
            650)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, DependencyProvider.CharactersListViewModel())[ViewModelListCharacters::class.java]
        safeData()
    }

    private fun safeData(){
        viewModel.results.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.simpleFilter.observe(viewLifecycleOwner) {
            filterData = it
        }
    }

    private fun scrollingSearching(adapter: ListCharactersAdapter) = lifecycleScope.launch {
        getLoadStateFlow(adapter)
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.charactersList.scrollToPosition(0)
                }
            }
    }

    private fun getLoadStateFlow(adapter: ListCharactersAdapter): Flow<LoadState> {
        return adapter.loadStateFlow.map { it.refresh }
    }

    private fun Flow<LoadState>.simpleScan(count: Int): Flow<List<LoadState?>> {
        val items = List<LoadState?>(count) { null }
        return scan(items) { previous, value -> previous.drop(1) + value }
    }
}
