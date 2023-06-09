package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var wordList:MutableList<String> = mutableListOf()
    private lateinit var currentWord:String
    private val _score = MutableLiveData<Int>(0)
    private val _currentWordCount = MutableLiveData<Int>(0)
    private val _currentScrambledWord = MutableLiveData<String>()

    val score:LiveData<Int>
        get() = _score
    val currentScrambledWord: LiveData<Spannable> =
        Transformations.map(_currentScrambledWord) {
            if (it == null) {
                SpannableString("")
            } else {
                val scrambledWord = it.toString()
                val spannable: Spannable = SpannableString(scrambledWord)
                spannable.setSpan(
                    TtsSpan.VerbatimBuilder(scrambledWord).build(),
                    0,
                    scrambledWord.length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                spannable
            }
        }
    val currentWordCount:LiveData<Int>
        get() = _currentWordCount
    private fun getNextWord()
    {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if(wordList.contains(currentWord))
        {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }
    }

    fun nextWord():Boolean
    {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false
    }

    private fun increaseScore()
    {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
//        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playersWord : String):Boolean {
        if (playersWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }

    fun reinitialize()
    {
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()
    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }
}