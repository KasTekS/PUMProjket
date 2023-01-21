package com.example.tictactoe.ui.onlyx


import android.annotation.SuppressLint
import android.widget.Button
import com.example.tictactoe.databinding.FragmentOnlyxBinding

import java.lang.Integer.max
import java.lang.Integer.min

class GameX(private val binding: FragmentOnlyxBinding) {
    var start = 0
       var ruch = 0
       var ruch2 = true
       var p1=0
    var p2=0
         var gameOver = false

         val X = "x"
    fun convertCoordinatesToSolutions(board: Array<Int>): List<Int> {
        val solution = mutableListOf<Int>()
        for ((index, item) in board.withIndex()) {
            if (item == 1) {
                solution.add(index)
            }
        }
        return solution.sorted()
    }

    fun analyzeBoard2(board: Array<Int>): Int {
        val winningBoards = listOf(
            listOf(0, 1, 3, 4),
            listOf(1, 2, 4, 5),
            listOf(4, 5, 7, 8),
            listOf(3, 4, 6, 7),
            listOf(0, 1, 4, 5, 6),
            listOf(2, 3, 4, 7, 8),
            listOf(0, 4, 5, 6, 7),
            listOf(1, 2, 3, 4, 8),
            listOf(1, 3, 5, 6, 8),
            listOf(0, 1, 5, 6, 7),
            listOf(1, 2, 3, 7, 8),
            listOf(0, 2, 3, 5, 7),
            listOf(0, 2, 6, 8),
            listOf(1, 2, 3, 6, 8),
            listOf(0, 1, 5, 6, 8),
            listOf(0, 2, 5, 6, 7),
            listOf(0, 2, 3, 7, 8),
            listOf(0, 1, 4, 5, 7, 8),
            listOf(1, 2, 3, 5, 6, 7)
        )
        val losingSolutions = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )
        for (i in 0 until 8) {
            if (board[losingSolutions[i][0]] != 0 && board[losingSolutions[i][0]] == board[losingSolutions[i][1]] && board[losingSolutions[i][0]] == board[losingSolutions[i][2]]) {
                return -1
            }
        }
        for (i in winningBoards) {
            if (convertCoordinatesToSolutions(board) == i) {
                return 1
            }
        }
        return 0
    }

    fun minimax(board: Array<Int>, player: Int, alpha: Int, beta: Int, depth: Int): Int {
        //Checking if game is finished.
        val x = analyzeBoard2(board)  //returns -1 (lose) or 1 (win) or 0 (game not finished)
        if (x != 0) {
            return (x * player * -1)  //returns score depending on the player
        }

        //initialize the best score
        var value: Int
        if (player == 1) {   //is maximizing player
            value = -2
        } else {
            value = 2
        }

        //initialize the best move
        var pos = -1

        for (i in 0 until 9) { //test all possible moves
            if (board[i] == 0) {
                board[i] = 1

                if (player == -1) {      //is minimizing player
                    val score = minimax(board, 1, alpha, beta, depth + 1)
                    if (score < value) {    //if the score is lesser than the current best score
                        value = score
                        pos = i           //update the best score and position
                    }

                } else {                   //is maximizing player
                    val score = minimax(board, -1, alpha, beta, depth + 1)
                    if (score > value) {    //if the score is greater than the current best score
                        value = score
                        pos = i           //then update the best score and position
                    }
                }

                board[i] = 0
            }
        }

        if (pos == -1) {    //if there is no more possible move
            return 0
        }
        return value
    }

    @SuppressLint("SetTextI18n")
    fun checkForWin(board: Array<Int>): String {
        val wynik = analyzeBoard2(board)
        if(wynik==-1){
            if(ruch ==0) {
                gameOver = true
                p1++
                binding.textView.text = "Player1 : $p1"
                binding.textView2.text = "Player2 : $p2"
                return "wygrywa gracz"
            }else{
                gameOver = true
                p2++
                binding.textView.text = "Player1 : $p1"
                binding.textView2.text = "Player2 : $p2"
                return "wygrywa computer"
            }
        }else  return "nie ma zwycięzcy"



    }
    @SuppressLint("SetTextI18n")
    fun checkForWin2(board: Array<Int>): String {
        val wynik = analyzeBoard2(board)
        if(wynik==-1){
            if(ruch2) {
                gameOver = true
                p1++
                binding.textView.text = "Player1 : $p1"
                binding.textView2.text = "Player2 : $p2"
                return "wygrywa gracz nr 1"
            }else{
                gameOver = true
                p2++
                binding.textView.text = "Player1 : $p1"
                binding.textView2.text = "Player2 : $p2"
                return "wygrywa gracz nr 2"
            }
        }else  return "nie ma zwycięzcy"



    }


    fun computeTurn(board: Array<Int>): Int {
        var pos = -1
        var value = -1
        var lastPossibleMove = -1
        for (i in 0 until 9) {    //test all possible moves
            if (board[i] == 0) {
                lastPossibleMove = i

                board[i] = 1
                val score = minimax(board, -1, -999, 999, 1)   //...and calculate minimax score
                board[i] = 0


                if (score > value) {    //if the score is greater than the current best score
                    value = score
                    pos = i           //then update the best score and position
                }
            }

            if (pos == -1) {   //if there is no more possible move
                pos = lastPossibleMove  //make the last possible move
            }


        }
        return pos  //make the best move
    }


    fun playerMove(board: Array<Int>, button: Button, x: Int): Array<Int> {
        val buttons = arrayOf(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8
        )
        if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
            if (button.isEnabled == true) { // Sprawdzenie czy pole jest już zajęte
                button.isEnabled = false // Zaktualizowanie stanu pola na planszy
                button.text = X // Zmiana treści przycisku na X
                board[x] = 1
                ruch = 1
                binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                var tab: Int? = null
                if(!gameOver) {
                    tab = computeTurn(board) // Ruch komputera
                    val x = tab
                    buttons[x].isEnabled =false
                    buttons[x].text = X
                    board[x] =1
                    ruch = 0
                    binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                }
            }
        }
        return board
    }
    fun playerMovemulti(board: Array<Int>, button: Button, x: Int): Array<Int> {

        if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
            if (button.isEnabled == true) { // Sprawdzenie czy pole jest już zajęte
                button.isEnabled = false // Zaktualizowanie stanu pola na planszy
                button.text = X // Zmiana treści przycisku na X
                board[x] = 1
                ruch2 = !ruch2
                binding.stan.text =  checkForWin2(board) // Sprawdzenie czy gracz wygrał

            }
        }
        return board
    }
    fun playerMoverand(board: Array<Int>, button: Button, x: Int): Array<Int> {
        val buttons = arrayOf(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8
        )
        if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
            if (button.isEnabled == true) { // Sprawdzenie czy pole jest już zajęte
                button.isEnabled = false // Zaktualizowanie stanu pola na planszy
                button.text = X // Zmiana treści przycisku na X
                board[x] = 1
                ruch = 1
                binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                var tab: Int? = null
                if(!gameOver) {
                    var randomNumber = kotlin.random.Random.nextInt(0,8)
                    var juz = true
                    while(juz){
                        randomNumber = kotlin.random.Random.nextInt(0,8)
                        if(board[randomNumber]==0)
                            juz =false
                    }
                    buttons[randomNumber].isEnabled =false
                    buttons[randomNumber].text = X
                    board[randomNumber] =1
                    ruch = 0
                    binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                }
            }
        }
        return board
    }



    fun reset(){
        binding.button0.isEnabled = true
        binding.button2.isEnabled = true
        binding.button1.isEnabled = true
        binding.button3.isEnabled = true
        binding.button4.isEnabled = true
        binding.button5.isEnabled = true
        binding.button6.isEnabled = true
        binding.button7.isEnabled = true
        binding.button8.isEnabled = true
        binding.button0.text = ""
        binding.button2.text = ""
        binding.button1.text = ""
        binding.button3.text = ""
        binding.button4.text = ""
        binding.button5.text = ""
        binding.button6.text = ""
        binding.button7.text = ""
        binding.button8.text = ""
        gameOver = false
        binding.stan.text = "stan gry: "
    }

    fun thard() {


        var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        if(start == 0){
            val buttons = arrayOf(
                binding.button0,
                binding.button1,
                binding.button2,
                binding.button3,
                binding.button4,
                binding.button5,
                binding.button6,
                binding.button7,
                binding.button8
            )
            val tab =  computeTurn(board)
            board[tab] = 1
            buttons[tab].isEnabled =false
            buttons[tab].text = X
            ruch = 0
        }
        binding.button0.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button0.isEnabled == true) {
                    board = playerMove(board, binding.button0, 0)
                }
            }
        }
        binding.button1.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button1.isEnabled == true) {
                    board = playerMove(board, binding.button1, 1)
                }
            }
        }
        binding.button2.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button2.isEnabled == true) {
                    board = playerMove(board, binding.button2,  2)
                }
            }
        }
        binding.button3.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button3.isEnabled == true) {
                    board = playerMove(board, binding.button3, 3)
                }
            }
        }
        binding.button4.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button4.isEnabled == true) {
                    board = playerMove(board, binding.button4, 4)
                }
            }
        }
        binding.button5.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button5.isEnabled == true) {
                    board = playerMove(board, binding.button5, 5)
                }
            }
        }
        binding.button6.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button6.isEnabled == true) {
                    board = playerMove(board, binding.button6, 6)
                }
            }
        }
        binding.button7.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button7.isEnabled == true) {
                    board = playerMove(board, binding.button7, 7)
                }
            }
        }
        binding.button8.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button8.isEnabled == true) {
                    board = playerMove(board, binding.button8, 8)

                }
            }
        }
        binding.reset.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            thard()
        }

        binding.resetall.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            thard()
        }



    }
    fun easy() {


        var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        if(start == 0){
            val buttons = arrayOf(
                binding.button0,
                binding.button1,
                binding.button2,
                binding.button3,
                binding.button4,
                binding.button5,
                binding.button6,
                binding.button7,
                binding.button8
            )
            var tab = kotlin.random.Random.nextInt(0,8)
            board[tab] = 1
            buttons[tab].isEnabled =false
            buttons[tab].text = X
            ruch = 0
        }
        binding.button0.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button0.isEnabled == true) {
                    board = playerMoverand(board, binding.button0, 0)
                }
            }
        }
        binding.button1.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button1.isEnabled == true) {
                    board = playerMoverand(board, binding.button1, 1)
                }
            }
        }
        binding.button2.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button2.isEnabled == true) {
                    board = playerMoverand(board, binding.button2, 2)
                }
            }
        }
        binding.button3.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button3.isEnabled == true) {
                    board = playerMoverand(board, binding.button3, 3)
                }
            }
        }
        binding.button4.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button4.isEnabled == true) {
                    board = playerMoverand(board, binding.button4, 4)
                }
            }
        }
        binding.button5.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button5.isEnabled == true) {
                    board = playerMoverand(board, binding.button5, 5)
                }
            }
        }
        binding.button6.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button6.isEnabled == true) {
                    board = playerMoverand(board, binding.button6, 6)
                }
            }
        }
        binding.button7.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button7.isEnabled == true) {
                    board = playerMoverand(board, binding.button7, 7)
                }
            }
        }
        binding.button8.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button8.isEnabled == true) {
                    board = playerMoverand(board, binding.button8, 8)

                }
            }
        }
        binding.reset.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            easy()
        }

        binding.resetall.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            easy()
        }


    }



    fun multi() {
        var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

        binding.button0.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button0.isEnabled == true) {
                    board = playerMovemulti(board, binding.button0, 0)
                }
            }
        }
        binding.button1.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button1.isEnabled == true) {
                    board = playerMovemulti(board, binding.button1, 1)
                }
            }
        }
        binding.button2.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button2.isEnabled == true) {
                    board = playerMovemulti(board, binding.button2,  2)
                }
            }
        }
        binding.button3.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button3.isEnabled == true) {
                    board = playerMovemulti(board, binding.button3, 3)
                }
            }
        }
        binding.button4.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button4.isEnabled == true) {
                    board = playerMovemulti(board, binding.button4, 4)
                }
            }
        }
        binding.button5.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button5.isEnabled == true) {
                    board = playerMovemulti(board, binding.button5, 5)
                }
            }
        }
        binding.button6.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button6.isEnabled == true) {
                    board = playerMovemulti(board, binding.button6, 6)
                }
            }
        }
        binding.button7.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button7.isEnabled == true) {
                    board = playerMovemulti(board, binding.button7, 7)
                }
            }
        }
        binding.button8.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button8.isEnabled == true) {
                    board = playerMovemulti(board, binding.button8, 8)

                }
            }
        }
        binding.reset.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            ruch2 = true
            multi()
        }

        binding.resetall.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            ruch2 = true
            multi()
        }


    }

    fun medium() {

        var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        if(start == 0){
            val buttons = arrayOf(
                binding.button0,
                binding.button1,
                binding.button2,
                binding.button3,
                binding.button4,
                binding.button5,
                binding.button6,
                binding.button7,
                binding.button8
            )
            var tab = kotlin.random.Random.nextInt(0,8)
            board[tab] = 1
            buttons[tab].isEnabled =false
            buttons[tab].text = X
            ruch = 0
        }
        binding.button0.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button0.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMove(board, binding.button0, 0)
                    else board = playerMoverand(board, binding.button0, 0)
                }
            }
        }
        binding.button1.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button1.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button1, 1)
                }
            }
        }
        binding.button2.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button2.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button2,  2)
                    else board = playerMove(board, binding.button2,  2)
                }
            }
        }
        binding.button3.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button3.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button3, 3)
                    else  board = playerMove(board, binding.button3, 3)
                }
            }
        }
        binding.button4.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button4.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button4, 4)
                    else board = playerMove(board, binding.button4, 4)
                }
            }
        }
        binding.button5.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button5.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button5, 5)
                    else  board = playerMove(board, binding.button5, 5)
                }
            }
        }
        binding.button6.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button6.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button6, 6)
                    else board = playerMove(board, binding.button6, 6)
                }
            }
        }
        binding.button7.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button7.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button7, 7)
                    else  board = playerMove(board, binding.button7, 7)
                }
            }
        }
        binding.button8.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button8.isEnabled == true) {
                    var randomNumber = kotlin.random.Random.nextInt(0,1)
                    if(randomNumber==0)
                        board = playerMoverand(board, binding.button8, 8)
                    else board = playerMove(board, binding.button8, 8)

                }
            }
        }
        binding.reset.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            medium()
        }

        binding.resetall.setOnClickListener {
            var board = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            medium()
        }



    }



}
