package com.example.schoolandroid.ui.examedit

import android.util.Log
import android.view.View
import com.example.schoolandroid.ui.examedit.model.ObservableTask
import com.google.android.material.snackbar.Snackbar

class Presenter {
    fun increment(task: ObservableTask) {

        task.scores.set(task.scores.get() + 1)
    }

    fun decrement(view: View, task: ObservableTask) {
        if (task.scores.get() == 1) {
            return
        }
        task.scores.set(task.scores.get() - 1)


    }
}