package edu.uoc.android.quizz

import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.DocumentSnapshot

class Question {
    lateinit var title: String
    lateinit var image: String
    lateinit var choices: ImmutableList<String>
    var rightChoice: Int = -1

    constructor()

    constructor(documentSnapshot: DocumentSnapshot) {
        title = documentSnapshot.get("title") as String
        image = documentSnapshot.get("image") as String
        choices = documentSnapshot.get("choices") as ImmutableList<String>
        rightChoice = (documentSnapshot.get("rightChoice") as Long).toInt()
    }

}