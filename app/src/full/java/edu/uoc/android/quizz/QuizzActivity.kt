package edu.uoc.android.quizz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import edu.uoc.android.R
import kotlinx.android.synthetic.main.activity_quizz.*

class QuizzActivity : AppCompatActivity() {
    private val TAG = QuizzActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz)

        //RV manager
        rv_quizz.layoutManager = LinearLayoutManager(this)

        getQuestions()
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalytics.getInstance(applicationContext)
            .setCurrentScreen(this, this::class.java.simpleName, null)
    }

    private fun getQuestions() {
        val questions: MutableList<Question> = mutableListOf()
        FirebaseFirestore.getInstance().collection("Quizzes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    questions.add(Question(document))
                }
                Log.d(TAG, "Numero de preguntas: ${questions.size}")
                rv_quizz.adapter =
                    MyquestionItemRecyclerViewAdapter(questions, this)
            }
    }
}