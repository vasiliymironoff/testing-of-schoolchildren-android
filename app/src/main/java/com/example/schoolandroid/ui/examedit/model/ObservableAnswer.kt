package com.example.schoolandroid.ui.examedit.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.schoolandroid.data.model.Answer
import com.example.schoolandroid.data.model.NewAnswer

class ObservableAnswer {
    public var id: Int? = null
    public val text = ObservableField("")
    public val isCorrect = ObservableBoolean(false)

    fun getAnswerForPost(): NewAnswer {
        return NewAnswer(id, text.get()!!.trim(), isCorrect.get())
    }

    companion object {
        fun getObservableAnswerFromAnswer(answer: Answer): ObservableAnswer {
            val observableAnswer = ObservableAnswer()
            observableAnswer.isCorrect.set(answer.isCorrect)
            observableAnswer.text.set(answer.text)
            observableAnswer.id = answer.id
            return observableAnswer
        }
    }
}