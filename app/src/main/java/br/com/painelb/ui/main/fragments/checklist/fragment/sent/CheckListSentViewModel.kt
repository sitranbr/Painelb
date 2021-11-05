package br.com.painelb.ui.main.fragments.checklist.fragment.sent

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.painelb.domain.result.Event
import br.com.painelb.model.Response
import br.com.painelb.model.checklist.CreateCheckList
import br.com.painelb.network.Resource
import br.com.painelb.network.Status
import javax.inject.Inject

class CheckListSentViewModel @Inject constructor(private val sentRepository: ChecklistSentRepository) :
    ViewModel() {

    private val result = MediatorLiveData<Resource<List<CreateCheckList>>>()
    val checkListItemLiveData = MediatorLiveData<List<CreateCheckList>>()
    val refreshState = MutableLiveData<Status>()
    val messageEvent = MutableLiveData<Event<String>>()
    val navigateToUpdate = MutableLiveData<Event<Long>>()

    private fun checkListResultObserver(): Observer<Resource<List<CreateCheckList>>> {
        return Observer {
            refreshState.value = it.status
            if (it.status == Status.ERROR) {
                messageEvent.value = Event(it.message ?: "Unknown error")
            }
            it.data.let { data -> checkListItemLiveData.value = data }
        }
    }

    val deleteChecklist = MediatorLiveData<Resource<Response>>()

    private fun deleteChecklistObserver(): Observer<Resource<Response>> {
        return Observer {
            when {
                Status.SUCCESS == it.status -> {
                    loadOccurrences()
                    messageEvent.value = Event(it.data?.message ?: "")
                }
                Status.ERROR == it.status -> messageEvent.value = Event(it.message ?: "")
            }
        }
    }

    init {
        loadOccurrences()
        result.observeForever(checkListResultObserver())
        deleteChecklist.observeForever(deleteChecklistObserver())
    }

    fun refresh() = loadOccurrences()

    private fun loadOccurrences() {
        val source = sentRepository.checklist()
        result.removeSource(source)
        result.addSource(source) {
            result.value = it
            if (it.status == Status.ERROR || it.status == Status.SUCCESS)
                result.removeSource(source)
        }
    }

    fun update(item: CreateCheckList) {
        navigateToUpdate.value = Event(item.checklist.checklistId)
    }

    fun delete(item: CreateCheckList) {
        val source = sentRepository.delete(item.checklist.checklistId)
        deleteChecklist.removeSource(source)
        deleteChecklist.addSource(source) {
            deleteChecklist.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        result.removeObserver(checkListResultObserver())
    }
}
