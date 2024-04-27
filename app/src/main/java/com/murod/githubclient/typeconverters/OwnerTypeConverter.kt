package com.murod.githubclient.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.murod.githubclient.models.Owner

class OwnerTypeConverter {

    @TypeConverter
    fun fromOwner(owner: Owner): String {
        return Gson().toJson(owner)
    }

    @TypeConverter
    fun toOwner(ownerString: String): Owner {
        return Gson().fromJson(ownerString, Owner::class.java)
    }
}
