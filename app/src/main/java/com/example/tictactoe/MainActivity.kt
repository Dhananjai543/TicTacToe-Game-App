package com.example.tictactoe

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.shapes.Shape
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.models.Shape.*
import kotlin.text.isEmpty as kotlinTextIsEmpty

class MainActivity : AppCompatActivity() {
    var confettiCount = 0;
    var gameActive: Boolean = true

    // player representation below
        //0 is x
        //1 is 0
    var activePlayer = 0
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
        // state meanings
        // 0 means x
        // 1 means 0
        // 2 means null

    var winningPositions = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), intArrayOf(0, 4, 8), intArrayOf(2, 4, 6))
    var count = 0
    fun playOnTap(view: View) {
        val img: ImageView = view as ImageView
        val tappedImage: Int = img.tag.toString().toInt()
//        if(!gameActive){
////            gameReset()
////        }

        if (gameState[tappedImage] == 2 && gameActive) {
            count++
            gameState[tappedImage] = activePlayer
            img.translationY = -1000f
            if(activePlayer == 0){
                img.setImageResource(R.drawable.x)
                activePlayer=1
                val status: TextView = findViewById(R.id.status)
                status.text= getString(R.string.turn0)
            }
            else{
                img.setImageResource(R.drawable.o)
                activePlayer=0
                val status: TextView = findViewById(R.id.status)
                status.text= getString(R.string.turnx)
            }
            img.animate().translationYBy(1000f).duration = 50

        }
        // check if any player has won
        if(count==9){
            val status: TextView = findViewById(R.id.status)
            status.setText(getString(R.string.gamedraw))
        }
        for ( winPositions in winningPositions){
            if(gameState[winPositions[0]] == gameState[winPositions[1]] &&
                gameState[winPositions[1]] == gameState[winPositions[2]] && gameState[winPositions[0]]!=2){
                // someone has won the game by now, now we'll also have to find
                var winnerStr: String = ""
                gameActive=false
                if(gameState[winPositions[0]] == 0){
                    winnerStr = getString(R.string.xwon)
                }
                else{
                     winnerStr = getString(R.string.owon)
                }
                // now we also have to update the status bar for winner
                val status: TextView = findViewById(R.id.status)
                status.setText(winnerStr)
                if(confettiCount == 0) {
                    confettiCount = 1;
                    viewKonfetti.build()
                        .addColors(Color.RED, Color.CYAN, Color.YELLOW)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 6f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Square, Circle)
                        .addSizes(nl.dionsegijn.konfetti.models.Size(12))
                        .setPosition(
                            -50f,
                            viewKonfetti.width + 50f,
                            -50f,
                            viewKonfetti.height + 50f
                        )
                        .streamFor(300, 3000L)
                }
            }
        }

    }

    private fun gameReset() {
        confettiCount = 0;
        count = 0;
        gameActive = true
        activePlayer = 0
        for(i in gameState.indices){
            gameState[i]=2
        }
        (findViewById<View>(R.id.imageView1) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView11) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView3) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView4) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView5) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView6) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView7) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.imageView8) as ImageView).setImageResource(0)

        val status = findViewById<TextView>(R.id.status)
        status.text = getString(R.string.turnx)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        restart.setOnClickListener{ gameReset() }
    }
}