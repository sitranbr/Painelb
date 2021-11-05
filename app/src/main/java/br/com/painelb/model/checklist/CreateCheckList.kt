package br.com.painelb.model.checklist


import br.com.painelb.db.table.checklist.ChecklistPhotos
import br.com.painelb.util.IntToBoolean
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateCheckList(
    @Json(name = "checklist")
    val checklist: Checklist,
    @Json(name = "checklist_photos")
    val checklistPhotos: List<ChecklistPhotos>
)

@JsonClass(generateAdapter = true)
data class Checklist(
    @Json(name = "driver")
    val conductor: String,
    @IntToBoolean
    @Json(name = "back_lighting_system")
    val backLightingSystem: Boolean,
    @IntToBoolean
    @Json(name = "carpet")
    val carpet: Boolean,
    @Json(name = "check_date")
    val checkDate: String,
    @Json(name = "checklist_id")
    val checklistId: Long,
    @IntToBoolean
    @Json(name = "cones")
    val cones: Boolean,
    @Json(name = "cones_quantities")
    val conesQuantities: String,
    @IntToBoolean
    @Json(name = "crlv")
    val crlv: Boolean,
    @Json(name = "departure_time")
    val departureTime: String,
    @IntToBoolean
    @Json(name = "etilometer")
    val etilometer: Boolean,
    @IntToBoolean
    @Json(name = "flashing")
    val flashing: Boolean,
    @IntToBoolean
    @Json(name = "front_lighting_system")
    val frontLightingSystem: Boolean,
    @IntToBoolean
    @Json(name = "glacier")
    val glacier: Boolean,
    @IntToBoolean
    @Json(name = "handle")
    val handle: Boolean,
    @Json(name = "handle_quantities")
    val handleQuantities: String,
    @Json(name = "km_departure")
    val kmDeparture: String,
    @Json(name = "km_return")
    val kmReturn: String,
    @IntToBoolean
    @Json(name = "monkey")
    val monkey: Boolean,
    @IntToBoolean
    @Json(name = "new_jersey")
    val newJersey: Boolean,
    @Json(name = "new_jersey_quantities")
    val newJerseyQuantities: String,
    @Json(name = "output_fuel_quantity")
    val outputFuelQuantity: String,
    @Json(name = "plate")
    val plate: String,
    @IntToBoolean
    @Json(name = "pneus")
    val pneus: Boolean,
    @Json(name = "prisma")
    val prisma: String,
    @Json(name = "return_fuel_quantity")
    val returnFuelQuantity: String,
    @Json(name = "return_time")
    val returnTime: String,
    @IntToBoolean
    @Json(name = "sirene")
    val sirene: Boolean,
    @IntToBoolean
    @Json(name = "stereo")
    val stereo: Boolean,
    @IntToBoolean
    @Json(name = "super_cones")
    val superCones: Boolean,
    @Json(name = "super_cones_quantities")
    val superConesQuantities: String,
    @IntToBoolean
    @Json(name = "supply_card")
    val supplyCard: Boolean,
    @IntToBoolean
    @Json(name = "tire_iron")
    val tireIron: Boolean,
    @IntToBoolean
    @Json(name = "triangue")
    val triangue: Boolean,
    @Json(name = "users_id")
    val usersId: Long,
    @Json(name = "team_service")
    val teamService: String
)
