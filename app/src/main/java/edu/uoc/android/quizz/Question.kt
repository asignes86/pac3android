package edu.uoc.android.quizz

import com.google.firebase.firestore.DocumentSnapshot

class Question {
    var title: String
    var image: String
    var choices: MutableList<String>
    var rightChoice: Int = -1

    constructor(documentSnapshot: DocumentSnapshot) {
        title = documentSnapshot.get("title") as String
        image = documentSnapshot.get("image") as String
        choices = documentSnapshot.get("choices") as MutableList<String>
        rightChoice = (documentSnapshot.get("rightChoice") as Long).toInt()
    }
}