package com.example.startingtemplate

import androidx.lifecycle.MutableLiveData

class Note {
    var id = 0
    var title: MutableLiveData<String> = MutableLiveData<String>("")
    var body: MutableLiveData<String> = MutableLiveData<String>("")
    var important: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun containsString(s: String): Boolean {
        if (this.title.value?.contains(s, true) == true
            || this.body.value?.contains(s, true) == true) {
            return true
        }
        return false
    }

}