package br.com.painelb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.painelb.db.converter.*
import br.com.painelb.db.dao.checklist.CheckListDao
import br.com.painelb.db.dao.checklist.CheckListPhotoDao
import br.com.painelb.db.dao.occurrence.OccurrenceDao
import br.com.painelb.db.dao.occurrence.OccurrencePhotoDao
import br.com.painelb.db.dao.occurrence.OccurrenceTypeDao
import br.com.painelb.db.table.checklist.ChecklistEntity
import br.com.painelb.db.table.checklist.ChecklistPhotos
import br.com.painelb.db.table.occurrence.OccurrenceEntity
import br.com.painelb.db.table.occurrence.OccurrencePhoto
import br.com.painelb.db.table.occurrence.OccurrenceType

@Database(
    entities = [
        OccurrenceType::class,
        OccurrenceEntity::class,
        OccurrencePhoto::class,
        ChecklistEntity::class,
        ChecklistPhotos::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    value = [
        OccurrenceTypeConverters::class,
        OccurreceWitnesTypeConverters::class,
        OccurrenceVictimTypeConverters::class,
        VehicleConductorTypeConverters::class,
        ChecklistTypeConverters::class
    ]
)
abstract class PainelbDatabase : RoomDatabase() {
    abstract fun occurrenceTypeDao(): OccurrenceTypeDao
    abstract fun occurrenceDao(): OccurrenceDao
    abstract fun occurrencePhotoDao(): OccurrencePhotoDao
    abstract fun checkListDao(): CheckListDao
    abstract fun checkListPhotoDao(): CheckListPhotoDao
}
