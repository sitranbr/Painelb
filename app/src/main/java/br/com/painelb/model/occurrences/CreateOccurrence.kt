package br.com.painelb.model.occurrences

import android.os.Parcelable
import br.com.painelb.db.table.occurrence.OccurrencePhoto
import br.com.painelb.util.IntToBoolean
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class CreateOccurrence(
    @Json(name = "occurrence")
    val occurrence: Occurrence,
    @Json(name = "occurrence_witness")
    val occurreceWitness: List<OccurreceWitnes>,
    @Json(name = "occurrence_photos")
    val occurrencePhotos: List<OccurrencePhoto>,
    @Json(name = "occurrence_victim")
    val occurrenceVictim: List<OccurrenceVictim>,
    @Json(name = "vehicle_conductor")
    val vehicleConductor: List<VehicleConductor>
) {
    fun shareText() =
        StringBuilder().apply {
            append("Tipo de ocorrência: ${occurrence.occurrenceType}\n")
            append("Data/hora: ${occurrence.date} - ${occurrence.time}\n")
            append("Local: ${occurrence.address}\n")
            append("Descrição: ${occurrence.description}\n")
            append("\n")
            vehicleConductor.forEachIndexed { index, it ->
                append("=====================\n")
                append("Dados do veículo/condutor - C${index + 1}\n")
                append("=====================\n")
                append("Placa: ${it.plateVehicle}\n")
                append("Documento do veículo: ${it.docVehicleType}\n")
                append("Número do documento: ${it.docVehicleNumber}\n")
                append("Categoria do dano: ${it.damageCategory}\n")
                append("\n")
                append("Condutor: ${it.driverName}\n")
                append("Documento do condutor: ${it.driverDocumentType}\n")
                append("Número do documento: ${it.driverDocumentNumber}\n")
                append("Procedimento: ${it.driverProcedure}\n")
            }
            occurrenceVictim.forEachIndexed { index, it ->
                append("=====================\n")
                append("Dados da vítima - V${index + 1}\n")
                append("=====================\n")
                append("Nome: ${it.name}\n")
                append("Estado de saúde: ${it.statusVictim}\n")
                append("Sexo: ${it.genre}\n")
                append("Documento da vítima: ${it.documentType}\n")
                append("Número do documento: ${it.documentNumber}\n")
                append("Endereço: ${it.address}\n")
            }

        }.toString()
}

@JsonClass(generateAdapter = true)
data class Occurrence(
    @Json(name = "address")
    val address: String,
    @Json(name = "date")
    val date: String,
    @Json(name = "description")
    val description: String,
    @IntToBoolean
    @Json(name = "has_victim")
    val hasVictim: Boolean,
    @IntToBoolean
    @Json(name = "has_witness")
    val hasWitness: Boolean,
    @Json(name = "occurrence_id")
    val occurrenceId: Long,
    @Json(name = "occurrence_type")
    val occurrenceType: String,
    @Json(name = "perimeter")
    val perimeter: String,
    @Json(name = "time")
    val time: String,
    @Json(name = "users_id")
    val usersId: Long
)

@Parcelize
@JsonClass(generateAdapter = true)
data class OccurreceWitnes(
    @Json(name = "witness_id")
    val witnessId: Long,
    @Json(name = "occurrence_id")
    val occurrenceId: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "document_type")
    val documentType: String,
    @Json(name = "document_number")
    val documentNumber: String,
    @Json(name = "address")
    val address: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class OccurrenceVictim(
    @Json(name = "address")
    val address: String,
    @Json(name = "document_number")
    val documentNumber: String,
    @Json(name = "document_type")
    val documentType: String,
    @Json(name = "genre")
    val genre: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "occurrence_id")
    val occurrenceId: Long,
    @Json(name = "status_victim")
    val statusVictim: String,
    @Json(name = "victim_id")
    val victimId: Long
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class VehicleConductor(
    @Json(name = "damage_category")
    val damageCategory: String,
    @Json(name = "doc_vehicle_number")
    val docVehicleNumber: String,
    @Json(name = "doc_vehicle_type")
    val docVehicleType: String,
    @Json(name = "driver_document_number")
    val driverDocumentNumber: String,
    @Json(name = "driver_document_type")
    val driverDocumentType: String,
    @Json(name = "driver_name")
    val driverName: String,
    @Json(name = "driver_procedure")
    val driverProcedure: String,
    @Json(name = "occurrence_id")
    val occurrenceId: Long,
    @Json(name = "plate_vehicle")
    val plateVehicle: String,
    @Json(name = "vehicle_id")
    val vehicleId: Long,
    @Json(name = "vehicle_procedure")
    val vehicleProcedure: String,
    @Json(name = "vehicle_type")
    val vehicleType: String,
    @Transient
    var isCheck: Boolean = false
) : Parcelable

