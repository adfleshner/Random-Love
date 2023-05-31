package com.flesh.dataaboutapp.dataaboutapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.flesh.dataaboutapp.R
import com.flesh.dataaboutapp.dataaboutapp.models.BaseDataAboutAppObject
import com.flesh.dataaboutapp.databinding.DialogDataAboutAppBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DataAboutAppDialogFragment : BottomSheetDialogFragment() {

    private var repository: DataAboutAppRepository? = null

    private val viewModel: DataAboutAppViewModel by viewModels {
        DataAboutAppViewModel.Factory(repository!!)
    }
    private val adapter: DataAboutAppAdapter by lazy {
        DataAboutAppAdapter()
    }
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }
    private val binding: DialogDataAboutAppBinding by lazy {
        DialogDataAboutAppBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listData.layoutManager = layoutManager
        binding.listData.adapter = adapter
        binding.listData.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )
        viewModel.fetchData()
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collectLatest { state ->
                Log.d("ALL DATA state", state.toString())
                when(state){
                    is DataAboutAppViewModel.DataState.Content -> {
                        Log.d("ALL DATA",state.list.joinToString { item -> item })
                        adapter.submitList(state.list.map { data ->
                            BaseDataAboutAppObject.SimpleDataAboutAppObject(data)
                        })
                        binding.titleData.text = getString(R.string.data_about, state.title)
                    }
                    DataAboutAppViewModel.DataState.Error ->{
                        // Nothing
                    }
                    DataAboutAppViewModel.DataState.Loading -> {
                    }
                }
            }
        }
    }

    companion object {
        fun showDialog(fragmentManager: FragmentManager, dataAboutAppRepository: DataAboutAppRepository) {
            val tag = DataAboutAppDialogFragment::class.java.simpleName
            if (fragmentManager.findFragmentByTag(tag) == null) {
                val dialog = DataAboutAppDialogFragment().apply {
                    repository = dataAboutAppRepository
                }
                dialog.show(fragmentManager, tag)
            }
        }
    }

}