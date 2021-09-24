package com.example.schoolandroid.ui.examedit.model

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.schoolandroid.data.model.NewAnswer
import com.example.schoolandroid.data.model.NewTask
import com.google.android.material.snackbar.Snackbar

class ObservableTask {
    public val question = ObservableField("")
    public var scores = ObservableInt(1)
    val answers = ArrayList<ObservableAnswer>()

    fun getTaskForPost(): NewTask {
        val answersList = ArrayList<NewAnswer>()
        for (answer in answers) {
            answersList.add(answer.getAnswerForPost())
        }
        return NewTask(question.get()!!.trim(), scores.get(), answersList)
    }

    fun isValid(view: View): Boolean {
        if (question.get()?.trim() == "") {
            Snackbar.make(view, "Поле вопроса не может быть пустым", Snackbar.LENGTH_SHORT).show()
            return false
        }
        var isFalse = false
        var isTrue = false
        for (answer in answers) {
            if (answer.text.get()?.trim() == "") {
                Snackbar.make(view, "Поле варианта ответа не может быть пустым", Snackbar.LENGTH_SHORT).show()
                return false
            }
            if (answer.isCorrect.get()) {
                isTrue = true
            } else {
                isFalse = true
            }
        }
        if (!isTrue) {
            Snackbar.make(view, "В задании должны быть верные варианты ответа", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (!isFalse) {
            Snackbar.make(view, "В задании все ответа не могут быть верными", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}