package br.com.painelb.ui.main.fragments.occurrences.create.fragments.addvictim

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.painelb.R
import br.com.painelb.domain.result.Event
import br.com.painelb.model.occurrences.OccurrenceVictim
import br.com.painelb.util.EMPTY
import javax.inject.Inject
import kotlin.properties.Delegates


class AddVictimViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    var isUpdate = false
    val navigateToCreateVictim = MutableLiveData<Event<OccurrenceVictim>>()
    val navigateToUpdateVictim = MutableLiveData<Event<OccurrenceVictim>>()

    var victimId = System.currentTimeMillis()
    var occurrenceId by Delegates.notNull<Long>()

    var victimName = MutableLiveData<String>()
    var errorVictimName = MutableLiveData<String>()

    var victimStatus = MutableLiveData<String>()
    var errorVictimStatus = MutableLiveData<String>()

    var genre = MutableLiveData<String>()
    var errorGenre = MutableLiveData<String>()

    var victimDocumentType = MutableLiveData<String>()
    var errorVictimDocumentType = MutableLiveData<String>()

    var victimDocumentNumber = MutableLiveData<String>()
    var errorVictimDocumentNumber = MutableLiveData<String>()

    var victimAddress = MutableLiveData<String>()
    var errorVictimAddress = MutableLiveData<String>()

    init {
        resetValue()
        victimName.observeForever {
            if (!errorVictimName.value.isNullOrBlank()) errorVictimName.value =
                String.EMPTY
        }

        victimStatus.observeForever {
            if (!errorVictimStatus.value.isNullOrBlank()) errorVictimStatus.value =
                String.EMPTY
        }

        genre.observeForever {
            if (!errorGenre.value.isNullOrBlank()) errorGenre.value =
                String.EMPTY
        }
        victimDocumentType.observeForever {
            if (!errorVictimDocumentType.value.isNullOrBlank()) errorVictimDocumentType.value =
                String.EMPTY
        }

        victimDocumentNumber.observeForever {
            if (!errorVictimDocumentNumber.value.isNullOrBlank()) errorVictimDocumentNumber.value =
                String.EMPTY
        }
        victimAddress.observeForever {
            if (!errorVictimAddress.value.isNullOrBlank()) errorVictimAddress.value =
                String.EMPTY
        }
    }

    fun setOccurrenceVictim(occurrenceVictim: OccurrenceVictim?) {
        if (occurrenceVictim != null) {
            isUpdate = true
            victimId = occurrenceVictim.victimId
            victimName.value = occurrenceVictim.name
            victimStatus.value = occurrenceVictim.statusVictim
            genre.value = occurrenceVictim.genre
            victimDocumentType.value = occurrenceVictim.documentType
            victimDocumentNumber.value = occurrenceVictim.documentNumber
            victimAddress.value = occurrenceVictim.address
        }
    }

    private fun resetValue() {
        victimName.value = String.EMPTY
        errorVictimName.value = String.EMPTY
        victimStatus.value = String.EMPTY
        errorVictimStatus.value = String.EMPTY
        genre.value = String.EMPTY
        errorGenre.value = String.EMPTY
        victimDocumentType.value = String.EMPTY
        errorVictimDocumentType.value = String.EMPTY
        victimDocumentNumber.value = String.EMPTY
        errorVictimDocumentNumber.value = String.EMPTY
        victimAddress.value = String.EMPTY
        errorVictimAddress.value = String.EMPTY
        victimId = System.currentTimeMillis()
    }

    fun victimStatusItemSelected(item: Any) {
        (item as String).apply { victimStatus.value = item }
    }

    fun genreItemSelected(item: Any) {
        (item as String).apply { genre.value = item }
    }

    fun victimDocumentTypeItemSelected(item: Any) {
        (item as String).apply { victimDocumentType.value = item }
    }


    fun validateData() {
        when {
            victimName.value.isNullOrEmpty() -> errorVictimName.value =
                context.getString(R.string.enter_victim_name)
            victimStatus.value.isNullOrEmpty() -> errorVictimStatus.value =
                context.getString(R.string.enter_victim_status)
            genre.value.isNullOrEmpty() -> errorGenre.value =
                context.getString(R.string.enter_genre)
            victimDocumentType.value.isNullOrEmpty() -> errorVictimDocumentType.value =
                context.getString(R.string.enter_victim_document_type)
            victimDocumentNumber.value.isNullOrEmpty() -> errorVictimDocumentNumber.value =
                context.getString(R.string.enter_victim_document_number)
            //victimAddress.value.isNullOrEmpty() -> errorVictimAddress.value = context.getString(R.string.enter_victim_address)
            else -> onSubmitClick()
        }
    }

    private fun onSubmitClick() {
        val occurrenceVictim = OccurrenceVictim(
            victimId = victimId,
            occurrenceId = occurrenceId,
            address = victimAddress.value!!,
            documentNumber = victimDocumentNumber.value!!,
            documentType = victimDocumentType.value!!,
            genre = genre.value!!,
            name = victimName.value!!,
            statusVictim = victimStatus.value!!
        )
        val event = Event(occurrenceVictim)
        if (isUpdate) navigateToUpdateVictim.value = event
        else navigateToCreateVictim.value = event
    }
}
