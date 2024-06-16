package com.joseantoniofernandezverdugo.rastreadog.modelo

import android.os.Parcel
import android.os.Parcelable

data class Perro(
    val uid: String,
    val raza: String,
    val color: String,
    val razaPadre: String,
    val colorPadre: String,
    val razaMadre: String,
    val colorMadre: String,
    val edad: String,
    val precio: Long,
    val ciudad: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(raza)
        parcel.writeString(color)
        parcel.writeString(razaPadre)
        parcel.writeString(colorPadre)
        parcel.writeString(razaMadre)
        parcel.writeString(colorMadre)
        parcel.writeString(edad)
        parcel.writeLong(precio)
        parcel.writeString(ciudad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Perro> {
        override fun createFromParcel(parcel: Parcel): Perro {
            return Perro(parcel)
        }

        override fun newArray(size: Int): Array<Perro?> {
            return arrayOfNulls(size)
        }
    }
}
