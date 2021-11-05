package br.com.painelb.ui.main.fragments.occurrences.fragment.sent

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.painelb.domain.result.Event
import br.com.painelb.model.Response
import br.com.painelb.model.occurrences.CreateOccurrence
import br.com.painelb.network.Resource
import br.com.painelb.network.Status
import javax.inject.Inject

class OccurrencesSentViewModel @Inject constructor(private val sentRepository: OccurrencesSentRepository) :
    ViewModel() {

    private val result = MediatorLiveData<Resource<List<CreateOccurrence>>>()
    val occurrenceItemLiveData = MediatorLiveData<List<CreateOccurrence>>()
    val refreshState = MutableLiveData<Status>()
    val messageEvent = MutableLiveData<Event<String>>()
    val navigateToUpdate = MutableLiveData<Event<Long>>()
    val shareEvent = MutableLiveData<Event<String>>()

    private fun occurrencesResultObserver(): Observer<Resource<List<CreateOccurrence>>> {
        return Observer {
            refreshState.value = it.status
            if (it.status == Status.ERROR) {
                messageEvent.value = Event(it.message ?: "Unknown error")
            }
            it.data.let { data -> occurrenceItemLiveData.value = data }
        }
    }

    val deleteOccurrence = MediatorLiveData<Resource<Response>>()

    private fun deleteOccurrenceObserver(): Observer<Resource<Response>> {
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
        result.observeForever(occurrencesResultObserver())
        deleteOccurrence.observeForever(deleteOccurrenceObserver())
    }

    fun refresh() = loadOccurrences()

    private fun loadOccurrences() {
        val source = sentRepository.occurrence()
        result.removeSource(source)
        result.addSource(source) {
            result.value = it
            if (it.status == Status.ERROR || it.status == Status.SUCCESS)
                result.removeSource(source)
        }
    }

    fun update(item: CreateOccurrence) {
        navigateToUpdate.value = Event(item.occurrence.occurrenceId)
    }

    fun delete(item: CreateOccurrence) {
        val source = sentRepository.delete(item.occurrence.occurrenceId)
        deleteOccurrence.removeSource(source)
        deleteOccurrence.addSource(source) {
            deleteOccurrence.value = it
        }
    }

    fun share(item: CreateOccurrence) {
        shareEvent.value = Event(item.shareText())
    }

    override fun onCleared() {
        super.onCleared()
        result.removeObserver(occurrencesResultObserver())
    }
}
