package com.flesh.dataaboutapp.dataaboutapp


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.flesh.dataaboutapp.dataaboutapp.models.BaseDataAboutAppObject
import com.flesh.dataaboutapp.databinding.DialogDataAboutAppBinding
import kotlinx.coroutines.launch

class DataAboutAppDialogFragment : DialogFragment() {

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

    private var _dialogOwner : LifecycleOwner? = null
    private val dialogOwner : LifecycleOwner by lazy {
        _dialogOwner!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding.listData.layoutManager = layoutManager
        binding.listData.adapter = adapter
        binding.listData.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )
        dialogOwner.lifecycleScope.launch {
            viewModel.data.flowWithLifecycle(dialogOwner.lifecycle, Lifecycle.State.STARTED).collect {
                adapter.submitList(it.map { data ->
                    BaseDataAboutAppObject.SimpleDataAboutAppObject(data)
                })
            }
        }
        viewModel.addObserverToLifeCycle(dialogOwner.lifecycle)
        return AlertDialog.Builder(requireContext())
            .setTitle("Data About ${viewModel.title}")
            .setView(binding.root)
            .create()
    }

    companion object {
        fun showDialog(fragmentManager: FragmentManager, dataAboutAppRepository: DataAboutAppRepository, owner: LifecycleOwner) {
            val tag = DataAboutAppDialogFragment::class.java.simpleName
            if (fragmentManager.findFragmentByTag(tag) == null) {
                DataAboutAppDialogFragment().apply {
                    _dialogOwner = owner
                    repository = dataAboutAppRepository
                }.show(fragmentManager, tag)
            }
        }
    }

}