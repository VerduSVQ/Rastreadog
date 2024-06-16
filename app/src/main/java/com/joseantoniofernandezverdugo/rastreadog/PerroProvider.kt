package com.joseantoniofernandezverdugo.rastreadog

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class PerroProvider {

    companion object {
        private const val TAG = "PerroProvider"

        fun getTotalPerros(callback: (MutableMap<String, Any?>) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val mutableMap = mutableMapOf<String, Any?>()

            db.collection("perros").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        mutableMap[document.id] = document.data
                    }
                    callback(mutableMap)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                    callback(mutableMap) // Devolver el mapa vacío en caso de error
                }
        }

        fun getPerroUser(email: String, callback: (MutableMap<String, Any?>) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val mutableMap = mutableMapOf<String, Any?>()

            db.collection("perros").whereEqualTo("email", email).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        mutableMap[document.id] = document.data
                    }
                    callback(mutableMap)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                    callback(mutableMap) // Devolver el mapa vacío en caso de error
                }
        }
    }
}
