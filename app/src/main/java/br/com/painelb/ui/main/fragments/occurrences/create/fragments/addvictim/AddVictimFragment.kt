package br.com.painelb.ui.main.fragments.occurrences.create.fragments.addvictim

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.painelb.R
import br.com.painelb.base.ui.BaseFragment
import br.com.painelb.databinding.FragmentAddVictimBinding
import br.com.painelb.di.modules.viemodel.AppViewModelFactory
import br.com.painelb.domain.result.EventObserver
import br.com.painelb.util.navigateUp
import br.com.painelb.util.viewModelProvider
import javax.inject.Inject

class AddVictimFragment : BaseFragment<FragmentAddVictimBinding>(R.layout.fragment_add_victim) {
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    lateinit var viewModel: AddVictimViewModel

    private val args: AddVictimFragmentArgs by navArgs()

    override fun haveToolbar() = true
    override fun resToolbarId() = R.id.toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.occurrenceId = args.occurrenceId
        viewModel.setOccurrenceVictim(args.occurrenceVictim)
        mBinding.viewModel = viewModel
        val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
        viewModel.navigateToCreateVictim.observe(viewLifecycleOwner, EventObserver {
            savedStateHandle?.set(EXTRA_ARGUMENT_ADD_VICTIM, it)
            navigateUp()
        })
        viewModel.navigateToUpdateVictim.observe(viewLifecycleOwner, EventObserver {
            savedStateHandle?.set(EXTRA_ARGUMENT_UPDATE_VICTIM, it)
            navigateUp()
        })
    }

    companion object {
        const val EXTRA_ARGUMENT_ADD_VICTIM = "EXTRA_ARGUMENT_ADD_VICTIM"
        const val EXTRA_ARGUMENT_UPDATE_VICTIM = "EXTRA_ARGUMENT_UPDATE_VICTIM"
    }
}