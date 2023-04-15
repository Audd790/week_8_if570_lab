package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var wordList:MutableList<String> = mutableListOf()
    private lateinit var currentWord:String
    private var _score = 0
    private var currentWordCount = 0
    private lateinit var _currentScrambledWord:String

    val score:Int
        get() = _score
    val currentScrambledWord:String
        get() = _currentScrambledWord
    fun getNextWord()
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
            _currentScrambledWord = String(tempWord)
            ++currentWordCount
            wordList!!.add(currentWord)
        }
    }

    fun nextWord():Boolean
    {
        return if (currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true } else false
    }
    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}