package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var wordList:MutableList<String> = mutableListOf()
    private lateinit var currentWord:String
    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord:String

    val score:Int
        get() = _score
    val currentScrambledWord:String
        get() = _currentScrambledWord
    val currentWordCount:Int
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
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordList.add(currentWord)
        }
    }

    fun nextWord():Boolean
    {
        return if (_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false
    }

    private fun increaseScore()
    {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playersWord : String):Boolean {
        if (playersWord.equals(currentWord, true)){
            increaseScore()
            Log.d("check_scord", "Score: $_score")
            return true
        }
        return false
    }

    fun reinitialize()
    {
        _score = 0
        _currentWordCount = 0
        wordList.clear()
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }
}