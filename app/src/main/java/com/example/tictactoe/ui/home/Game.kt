package com.example.tictactoe.ui.home

import android.annotation.SuppressLint
import android.widget.Button
import com.example.tictactoe.databinding.FragmentHomeBinding

public class Game( private val binding: FragmentHomeBinding)  {


    var p = "0"
    var p1=0
    var p2=0
    var gameOver = false
    val player1 = "o"
    val player2 = "x"
    var start = 2


    fun isMovesLeft(board: Array<CharArray>): Boolean {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == '_') {
                    return true
                }
            }
        }
        return false
    }

    fun evaluate(b: Array<CharArray>): Int {

        for (row in 0 until 3) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == player1[0])
                    return +10
                else if (b[row][0] == player2[0])
                    return -10
            }
        }
        for (col in 0 until 3) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == player1[0])
                    return +10
                else if (b[0][col] == player2[0])
                    return -10
            }
        }

        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player1[0])
                return +10
            else if (b[0][0] == player2[0])
                return -10
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player1[0])
                return +10
            else if (b[0][2] == player2[0])
                return -10
        }

        return 0
    }


    fun minimax(board: Array<CharArray>, depth: Int, isMax: Boolean): Int {
        val score = evaluate(board)
        if (score == 10)
            return score

        if (score == -10)
            return score

        if (!isMovesLeft(board))
            return 0

        if (isMax) {
            var best = -1000

            // Traverse all cells
            for (i in 0 until 3) {
                for (j in 0 until 3) {

                    if (board[i][j] == '_') {

                        board[i][j] = player1[0]

                        best = kotlin.math.max(best, minimax(board, depth + 1, !isMax))

                        board[i][j] = '_'
                    }
                }
            }
            return best
        } else {
            var best = 1000


            for (i in 0 until 3) {
                for (j in 0 until 3) {

                    if (board[i][j] == '_') {

                        board[i][j] = player2[0]


                        best = kotlin.math.min(best, minimax(board, depth + 1, !isMax))


                        board[i][j] = '_'
                    }
                }
            }
            return best
        }
    }
    data class Move(var row: Int = -1, var col: Int = -1)

    fun findBestMove(board: Array<CharArray>): Move {
        var bestVal = -1000
        val bestMove = Move()

        for (i in 0 until 3) {
            for (j in 0 until 3) {

                if (board[i][j] == '_') {

                    board[i][j] = player1[0]

                    val moveVal = minimax(board, 0, false)

                    board[i][j] = '_'

                    if (moveVal > bestVal) {
                        bestMove.row = i
                        bestMove.col = j
                        bestVal = moveVal
                    }
                }
            }

        }

        return bestMove
    }

    @SuppressLint("SetTextI18n")
    fun checkForWin(board: Array<CharArray>): String {

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '_') {
            if(board[0][0]==player1[0]){
                p1++
            }else{
                p2++
            }
            binding.textView.text = "Player1 : $p1"
            binding.textView2.text = "Player2 : $p2"
            gameOver = true
            return "wygrywa gracz"+ board[0][0].toString()
        } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '_') {
            gameOver = true
            if(board[0][2]==player1[0]){
                p1++
            }else{
                p2++
            }
            binding.textView.text = "Player1 : "+p1
            binding.textView2.text = "Player2 : "+p2
            return "wygrywa gracz"+ board[0][2].toString()
        } else {
            for (i in 0..2) {
                if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '_') {
                    gameOver = true
                    if(board[i][0]==player1[0]){
                        p1++
                    }else{
                        p2++
                    }
                    binding.textView.text = "Player1 : $p1"
                    binding.textView2.text = "Player2 : $p2"
                    return "wygrywa gracz"+ board[i][0].toString()
                } else if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '_') {
                    gameOver = true
                    if(board[0][i]==player1[0]){
                        p1++
                    }else{
                        p2++
                    }
                    binding.textView.text = "Player1 : $p1"
                    binding.textView2.text = "Player2 : $p2"
                    return "wygrywa gracz"+ board[0][i].toString()
                }
            }
        }
        var draw = true
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == '_') {
                    draw = false
                    break
                }
            }
        }
        if (draw) {
            gameOver = true
            return "remis"
        }
        return "nie ma zwycięzcy"
    }



    fun playerMove(board: Array<CharArray>, button: Button, x: Int, y: Int, player:String): Array<CharArray> {
        if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
            if (button.isEnabled == true) { // Sprawdzenie czy pole jest już zajęte
                button.isEnabled = false // Zaktualizowanie stanu pola na planszy
                button.text = player // Zmiana treści przycisku na X
                board[x][y] = player[0]
                binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                var tab: Array<Int>? = null
                if(!gameOver) {

                    tab = computerMove(board, player1) // Ruch komputera
                    val x = tab?.get(0)
                    val y = tab?.get(1)
                    board[x!!][y!!] = player1[0]
                    binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                }
            }
        }
        return board
    }
    fun playerMoverand(board: Array<CharArray>, button: Button, x: Int, y: Int, player:String): Array<CharArray> {
        if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
            if (button.isEnabled == true) { // Sprawdzenie czy pole jest już zajęte
                button.isEnabled = false // Zaktualizowanie stanu pola na planszy
                button.text = player // Zmiana treści przycisku na X
                board[x][y] = player[0]
                binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                var tab: Array<Int>? = null
                if(!gameOver) {

                    tab = computerMoverand(board, player1) // Ruch komputera
                    val x = tab?.get(0)
                    val y = tab?.get(1)
                    board[x!!][y!!] = player1[0]
                    binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
                }
            }
        }
        return board
    }
    fun playerMovee(board: Array<CharArray>, button: Button, x: Int, y: Int, player:String): Array<CharArray> {
        if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
            if (button.isEnabled == true) { // Sprawdzenie czy pole jest już zajęte
                button.isEnabled = false // Zaktualizowanie stanu pola na planszy
                button.text = player // Zmiana treści przycisku na X
                board[x][y] = player[0]
                binding.stan.text =  checkForWin(board) // Sprawdzenie czy gracz wygrał
            }
        }
        return board
    }

    private fun computerMove(board: Array<CharArray>,player: String): Array<Int> {
        val buttonArray = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )
        val bestMove = findBestMove(board)
        val x = bestMove.row
        val y = bestMove.col
        val tab = arrayOf(x,y)
        board[x][y]= player[0]
        val button = buttonArray[x][y]
        button.text = player
        button.isEnabled = false



        return tab

    }
    private fun computerMoverand(board: Array<CharArray>,player: String): Array<Int> {

        val buttonArray = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )
        val char = "_"
        var good = true
        var x: Int = 0
        var y: Int = 0

        while(good) {
            x = (0..2).random()
            y = (0..2).random()

            if (board[x][y] == char[0])good=false
        }
        val tab = arrayOf(x,y)

        val button = buttonArray[x][y]
        button.text = player
        button.isEnabled = false



        return tab

    }

    fun reset(){
        binding.button00.isEnabled = true
        binding.button22.isEnabled = true
        binding.button01.isEnabled = true
        binding.button02.isEnabled = true
        binding.button10.isEnabled = true
        binding.button11.isEnabled = true
        binding.button12.isEnabled = true
        binding.button20.isEnabled = true
        binding.button21.isEnabled = true
        binding.button00.text = ""
        binding.button22.text = ""
        binding.button01.text = ""
        binding.button02.text = ""
        binding.button10.text = ""
        binding.button11.text = ""
        binding.button12.text = ""
        binding.button20.text = ""
        binding.button21.text = ""
        gameOver = false
        binding.stan.text = "stan gry: "
    }

    fun thard() {


            var board = arrayOf(
                charArrayOf('_', '_', '_'),
                charArrayOf('_', '_', '_'),
                charArrayOf('_', '_', '_')
            )
            if(start == 0){
                val tab =  computerMove(board,player1)
            val  x = tab[0]
            val  y = tab[1]
                board[x][y]= player1[0]
            }
            binding.button00.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button00.isEnabled == true) {
                        board = playerMove(board, binding.button00, 0, 0, player2)
                    }
                }
            }
            binding.button01.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button01.isEnabled == true) {
                        board = playerMove(board, binding.button01, 0, 1, player2)
                    }
                }
            }
            binding.button02.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button02.isEnabled == true) {
                        board = playerMove(board, binding.button02, 0, 2, player2)
                    }
                }
            }
            binding.button10.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button10.isEnabled == true) {
                        board = playerMove(board, binding.button10, 1, 0, player2)
                    }
                }
            }
            binding.button11.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button11.isEnabled == true) {
                        board = playerMove(board, binding.button11, 1, 1, player2)
                    }
                }
            }
            binding.button12.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button12.isEnabled == true) {
                        board = playerMove(board, binding.button12, 1, 2, player2)
                    }
                }
            }
            binding.button20.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button20.isEnabled == true) {
                        board = playerMove(board, binding.button20, 2, 0, player2)
                    }
                }
            }
            binding.button21.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button21.isEnabled == true) {
                        board = playerMove(board, binding.button21, 2, 1, player2)
                    }
                }
            }
            binding.button22.setOnClickListener {
                if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                    if (binding.button22.isEnabled == true) {
                        board = playerMove(board, binding.button22, 2, 2, player2)

                    }
                }
            }
            binding.reset.setOnClickListener {

              reset()
                thard()
            }

        binding.resetall.setOnClickListener {
           reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            thard()
        }



    }

    fun medium() {
        var board = arrayOf(
            charArrayOf('_', '_', '_'),
            charArrayOf('_', '_', '_'),
            charArrayOf('_', '_', '_')
        )
        if(start == 0){
            val tab =  computerMove(board,player1)
            val  x = tab[0]
            val  y = tab[1]
            board[x][y]= player1[0]
        }
        binding.button00.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button00.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button00, 0, 0, player2)
                    else board = playerMoverand(board, binding.button00, 0, 0, player2)
                }
            }
        }
        binding.button01.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button01.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button01, 0, 1, player2)
                   else board = playerMoverand(board, binding.button01, 0, 1, player2)
                }
            }
        }
        binding.button02.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button02.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button02, 0, 2, player2)
                   else board = playerMoverand(board, binding.button02, 0, 2, player2)
                }
            }
        }
        binding.button10.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button10.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button10, 1, 0, player2)
                    else  board = playerMoverand(board, binding.button10, 1, 0, player2)
                }
            }
        }
        binding.button11.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button11.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button11, 1, 1, player2)
                    else board = playerMoverand(board, binding.button11, 1, 1, player2)
                }
            }
        }
        binding.button12.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button12.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button12, 1, 2, player2)
                    else
                        board = playerMoverand(board, binding.button12, 1, 2, player2)
                }
            }
        }
        binding.button20.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button20.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button20, 2, 0, player2)
                    else
                        board = playerMoverand(board, binding.button20, 2, 0, player2)
                }
            }
        }
        binding.button21.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button21.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button21, 2, 1, player2)
                    else board = playerMoverand(board, binding.button21, 2, 1, player2)
                }
            }
        }
        binding.button22.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button22.isEnabled == true) {
                    val number = (0..1).random()
                    if (number==0)
                    board = playerMove(board, binding.button22, 2, 2, player2)
                    else board = playerMoverand(board, binding.button22, 2, 2, player2)
                }
            }
        }
        binding.reset.setOnClickListener {

            reset()
            medium()
        }

        binding.resetall.setOnClickListener {

            reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            medium()
        }


    }
    fun easy() {
        var board = arrayOf(
            charArrayOf('_', '_', '_'),
            charArrayOf('_', '_', '_'),
            charArrayOf('_', '_', '_')
        )
        if(start == 0){
            val tab =  computerMoverand(board,player1)
            val  x = tab[0]
            val  y = tab[1]
            board[x][y]= player1[0]
        }
        binding.button00.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button00.isEnabled == true) {

                     board = playerMoverand(board, binding.button00, 0, 0, player2)
                }
            }
        }
        binding.button01.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button01.isEnabled == true) {
                     board = playerMoverand(board, binding.button01, 0, 1, player2)
                }
            }
        }
        binding.button02.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button02.isEnabled == true) {
                   board = playerMoverand(board, binding.button02, 0, 2, player2)
                }
            }
        }
        binding.button10.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button10.isEnabled == true) {
                   board = playerMoverand(board, binding.button10, 1, 0, player2)
                }
            }
        }
        binding.button11.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button11.isEnabled == true) {
                   board = playerMoverand(board, binding.button11, 1, 1, player2)
                }
            }
        }
        binding.button12.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button12.isEnabled == true) {

                        board = playerMoverand(board, binding.button12, 1, 2, player2)
                }
            }
        }
        binding.button20.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button20.isEnabled == true) {

                        board = playerMoverand(board, binding.button20, 2, 0, player2)
                }
            }
        }
        binding.button21.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button21.isEnabled == true) {
                    board = playerMoverand(board, binding.button21, 2, 1, player2)
                }
            }
        }
        binding.button22.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button22.isEnabled == true) {
                    board = playerMoverand(board, binding.button22, 2, 2, player2)
                }
            }
        }
        binding.reset.setOnClickListener {

            reset()
            easy()
        }

        binding.resetall.setOnClickListener {

            reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            easy()
        }


    }
    fun multi() {
        var board = arrayOf(
            charArrayOf('_', '_', '_'),
            charArrayOf('_', '_', '_'),
            charArrayOf('_', '_', '_')
        )

        binding.button00.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button00.isEnabled == true) {
                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button00, 0, 0, player1)

                    }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button00, 0, 0, player2)
                    }
                }
            }
        }
        binding.button01.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button01.isEnabled == true) {
                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button01, 0, 1, player1) }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button01, 0, 1, player2)
                    }
                }
            }
        }
        binding.button02.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button02.isEnabled == true) {
                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button02, 0, 2, player1) }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button02, 0, 2, player2) }
                }
            }
        }
        binding.button10.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button10.isEnabled == true) {
                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button10, 1, 0, player1) }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button10, 1, 0, player2) }
                }
            }
        }
        binding.button11.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button11.isEnabled == true) {
                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button11, 1, 1, player1) }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button11, 1, 1, player2) }
                }
            }
        }
        binding.button12.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button12.isEnabled == true) {

                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button12, 1, 2, player1) }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button12, 1, 2, player2) }
                }
            }
        }
        binding.button20.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button20.isEnabled == true) {

                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button20, 2, 0, player1) }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button20, 2, 0, player2) }
                }
            }
        }
        binding.button21.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button21.isEnabled == true) {
                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button21, 2, 1, player1) }
                    else {
                        p = "0"
                        board = playerMovee(board, binding.button21, 2, 1, player2) }
                }
            }
        }
        binding.button22.setOnClickListener {
            if (!gameOver) { // Sprawdzenie czy gra się już nie skończyła
                if (binding.button22.isEnabled == true) {
                    if(p=="0"){
                        p = "1"
                        board = playerMovee(board, binding.button22, 2, 2, player1) }
                    else {
                        p = "0"
                        board = playerMovee (board, binding.button22, 2, 2, player2) }
                    }
                }
            }

        binding.reset.setOnClickListener {

            reset()
            p="0"
            multi()
        }

        binding.resetall.setOnClickListener {

            reset()
            binding.textView.text = "Player1 : 0"
            binding.textView2.text = "Player2 : 0"
            p1 = 0
            p2 = 0
            p="0"
            multi()
        }


    }




}
