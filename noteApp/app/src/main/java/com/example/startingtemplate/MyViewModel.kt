package com.example.startingtemplate

import androidx.lifecycle.*

class MyViewModel() : ViewModel() {

    // add all observable properties here
    var allNotes: MutableLiveData<MutableList<MutableLiveData<Note>>> = MutableLiveData<MutableList<MutableLiveData<Note>>>(
        mutableListOf())
    var displayedNotes: MutableLiveData<MutableList<MutableLiveData<Note>>> = MutableLiveData<MutableList<MutableLiveData<Note>>>(
        mutableListOf())
    var currentNote: MutableLiveData<Note> = MutableLiveData<Note>()
    private var noteIdCounter = 1
    var numDisplayedNotes: MutableLiveData<Int> = MutableLiveData<Int>(0)
    var snackBarMessage: MutableLiveData<String> = MutableLiveData<String>()

    init {
    }

    // add modelview update functions here
    fun randomNote() {
        var note = Note()
        var isImportant = (1..5).random()
        with (note) {
            id = noteIdCounter
            title = LoremIpsum.getRandomTitle()
            body = LoremIpsum.getRandomBody()
            if (isImportant == 1) {
                this.important.value = true
            }
        }
        var noteLive = MutableLiveData(note)
        allNotes.value?.add(noteLive)
        snackBarMessage.value = "Added Note #${noteIdCounter}"
        noteIdCounter += 1
    }

    fun displayedNotesRemoveAll() {
        displayedNotes.value?.clear()
        numDisplayedNotes.value = displayedNotes.value?.size
    }

    fun displayedNotesRemove(note: MutableLiveData<Note>) {
        var tmp = MutableLiveData<Note>()
        for (n in displayedNotes.value!!) {
            if (note.value?.id == n.value?.id) {
                tmp = n
            }
        }
        displayedNotes.value!!.remove(tmp)
        numDisplayedNotes.value = displayedNotes.value?.size
    }

    fun displayedNotesAdd(n: MutableLiveData<Note>) {
        displayedNotes.value?.add(n)
        numDisplayedNotes.value = displayedNotes.value?.size
    }

    fun deleteNote(id: Int) {
        var tmp: MutableLiveData<Note> = MutableLiveData<Note>()
        for (n in allNotes.value!!) {
            if (n.value?.id == id) {
                tmp = n
            }
        }
        snackBarMessage.value = "Deleted Note #${tmp.value?.id}"
        allNotes.value!!.remove(tmp)
    }

    fun clearNotes() {
        for (n in displayedNotes.value!!) {
            allNotes.value?.remove(n)
        }
        if (numDisplayedNotes.value == 1) {
            snackBarMessage.value = "Cleared ${numDisplayedNotes.value} Note"
        } else {
            snackBarMessage.value = "Cleared ${numDisplayedNotes.value} Notes"
        }
        displayedNotesRemoveAll()
    }

    fun setCurrentNote(id: Int) {
        for (n in allNotes.value!!) {
            if (n.value?.id == id) {
                currentNote = n
                break
            }
        }
    }

    fun removeCurrentNote() {
        currentNote = MutableLiveData(null)
    }

    fun updateNote(id: Int, title: String, body: String, important: Boolean) {
        for (n in allNotes.value!!) {
            if (n.value?.id  == id) {
                n.value?.title?.value = title
                n.value?.body?.value = body
                n.value?.important?.value = important
                break
            }
        }
        snackBarMessage.value = "Edited Note #${id}"
    }

    fun addNote(title: String, body: String, important: Boolean) {
        var note = Note()
        note.id = noteIdCounter
        note.title = MutableLiveData(title)
        note.body = MutableLiveData(body)
        note.important = MutableLiveData(important)
        var noteLive = MutableLiveData(note)
        allNotes.value?.add(noteLive)
        snackBarMessage.value = "Added Note #${noteIdCounter}"
        noteIdCounter += 1
    }
}