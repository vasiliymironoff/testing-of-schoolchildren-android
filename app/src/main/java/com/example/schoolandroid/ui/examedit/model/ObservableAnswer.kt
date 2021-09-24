package com.example.schoolandroid.ui.examedit.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.schoolandroid.data.model.NewAnswer

class ObservableAnswer {
    public val text = ObservableField("")
    public val isCorrect = ObservableBoolean(false)

    fun getAnswerForPost(): NewAnswer {
        return NewAnswer(text.get()!!.trim(), isCorrect.get())
    }
}