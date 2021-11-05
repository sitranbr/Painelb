package br.com.painelb.ui.main.fragments.occurrences.fragment.sent

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.painelb.R
import br.com.painelb.base.ui.BaseFragment
import br.com.painelb.databinding.FragmentOccurrenceSentBinding
import br.com.painelb.di.modules.viemodel.AppViewModelFactory
import br.com.painelb.domain.result.EventObserver
import br.com.painelb.model.ItemType
import br.com.painelb.network.Status
import br.com.painelb.ui.main.fragments.occurrences.OccurrencesFragmentDirections
import br.com.painelb.util.dividerItemDecorationVertical
import br.com.painelb.util.navigate
import br.com.painelb.util.positiveButton
import br.com.painelb.util.showDialog
import timber.log.Timber
import javax.inject.Inject

class OccurrenceSentFragment :
    BaseFragment<FragmentOccurrenceSentBinding>(R.layout.fragment_occurrence_sent) {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by viewModels<OccurrencesSentViewModel> { viewModelFactory }

    private lateinit var occurrenceAdapter: OccurrenceAdapter

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
        occurrenceAdapter = OccurrenceAdapter(viewModel, savedInstanceState)
        mBinding.recyclerView.apply {
            adapter = occurrenceAdapter
            addItemDecoration(context.dividerItemDecorationVertical)
        }

        viewModel.occurrenceItemLiveData.observe(viewLifecycleOwner, Observer {
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
            navigate(
                OccurrencesFragmentDirections.navigateToCreateOccurrencesFragment(
                    it,
                    ItemType.REMOTE
                )
            )
        })
        viewModel.shareEvent.observe(viewLifecycleOwner, EventObserver {
            showShareDialog(it)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        occurrenceAdapter.onSaveInstanceState(outState)
    }

    private fun showShareDialog(data: String) {
        Timber.d(data)
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, data)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.occurrences))
        startActivity(shareIntent)
    }
}
