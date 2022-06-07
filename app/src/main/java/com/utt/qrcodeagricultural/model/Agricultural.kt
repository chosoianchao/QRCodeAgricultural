package com.utt.qrcodeagricultural.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Agricultural")
data class Agricultural(
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    val ID: Int? = null,

    @ColumnInfo(name = "Name")
    val Name: String? = "",

    @ColumnInfo(name = "Price")
    val Price: String? = "",

    @ColumnInfo(name = "Image")
    val Image: String? = "",

    @ColumnInfo(name = "Desc")
    val Desc: String? = "",

    @ColumnInfo(name = "supplier")
    val supplier: String? = "",

    @ColumnInfo(name = "dateOfManufacture")
    val dateOfManufacture: String? = "",

    @ColumnInfo(name = "expiryDate")
    val expiryDate: String? = "",

    @ColumnInfo(name = "imageSupplier")
    val imageSupplier: String? = "",

    ) : Serializable {
    override fun toString(): String {
        return "Agricultural(ID=$ID, Name=$Name, Price=$Price, Image=$Image, Desc=$Desc, supplier=$supplier, dateOfManufacture=$dateOfManufacture, expiryDate=$expiryDate, imageSupplier=$imageSupplier)"
    }
}
