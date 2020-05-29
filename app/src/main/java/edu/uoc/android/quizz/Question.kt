package edu.uoc.android.quizz

import com.google.firebase.firestore.DocumentSnapshot

class Question(documentSnapshot: DocumentSnapshot) {
    var title: String
    var image: String
    var choices: MutableList<String>
    var rightChoice: Int = -1

    init {
        title = documentSnapshot.get("title") as String
        image = documentSnapshot.get("image") as String
        choices = documentSnapshot.get("choices") as MutableList<String>
        rightChoice = (documentSnapshot.get("rightChoice") as Long).toInt()
    }
}