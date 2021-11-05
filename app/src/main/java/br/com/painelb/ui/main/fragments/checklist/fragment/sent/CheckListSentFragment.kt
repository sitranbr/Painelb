package br.com.painelb.ui.main.fragments.checklist.fragment.sent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.painelb.R
import br.com.painelb.base.ui.BaseFragment
import br.com.painelb.databinding.FragmentCheckListSentBinding
import br.com.painelb.di.modules.viemodel.AppViewModelFactory
import br.com.painelb.domain.result.EventObserver
import br.com.painelb.model.ItemType
import br.com.painelb.network.Status
import br.com.painelb.ui.main.fragments.checklist.CheckListFragmentDirections
import br.com.painelb.util.dividerItemDecorationVertical
import br.com.painelb.util.navigate
import br.com.painelb.util.positiveButton
import br.com.painelb.util.showDialog
import javax.inject.Inject


class CheckListSentFragment :
    BaseFragment<FragmentCheckListSentBinding>(R.layout.fragment_check_list_sent) {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by viewModels<CheckListSentViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel
        mBinding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
        viewModel.refreshState.observe(viewLifecycleOwner, Observer {
            mBinding.swipeRefresh.isRefreshing = it == Status.LOADING
        })

        mBinding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
        viewModel.messageEvent.observe(viewLifecycleOwner, EventObserver {
            run {
                showDialog {
                    setMessage(it)
                    positiveButton(context.getString(R.string.ok))
                }
            }
        })
        val occurrenceAdapter = ChecklistAdapter(viewModel)
        mBinding.recyclerView.apply {
            adapter = occurrenceAdapter
            addItemDecoration(context.dividerItemDecorationVertical)
        }

        viewModel.checkListItemLiveData.observe(viewLifecycleOwner, Observer {
            occurrenceAdapter.submitList(it) {
                val layoutManager =
                    (mBinding.recyclerView.layoutManager as LinearLayoutManager)
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) mBinding.recyclerView.scrollToPosition(
                    position
                )
            }
        })

        viewModel.navigateToUpdate.observe(viewLifecycleOwner, EventObserver {
            navigate(CheckListFragmentDirections.navigateToCreateCheckListFragment(it, ItemType.REMOTE))
        })
    }
}
