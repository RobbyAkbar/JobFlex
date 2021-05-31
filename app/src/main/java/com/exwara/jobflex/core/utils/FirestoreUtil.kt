package com.exwara.jobflex.core.utils

import com.exwara.jobflex.core.data.source.local.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by robby on 31/05/21.
 */
object FirestoreUtil {
    private val fireStoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = fireStoreInstance.document(
            "users/${
                FirebaseAuth.getInstance().currentUser?.uid
                ?: throw NullPointerException("UID is null.")}"
        )

    fun updateUser(userEntity: UserEntity, onComplete: (String) -> Unit) {

        val task = currentUserDocRef.set(userEntity)

        task.continueWith {
            if (it.isSuccessful) {
                onComplete("success")
            }
        }.addOnFailureListener {
            onComplete("failure")
        }
    }
}