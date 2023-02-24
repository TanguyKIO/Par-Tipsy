package com.example.triplekaisse.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
class Player(
    val id: Int,
    val name: String,
    val sex: Int?,
    val n_answers: Int?,
    val point: Int?,
    val team: Int
)