package com.vishnu.featurescompose.viewmodel


import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vishnu.featurescompose.AppConstants.NODE_AUTHOR
import com.vishnu.featurescompose.data.remote.Author
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor() : ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val authorsDb = database.getReference(NODE_AUTHOR)

    private val _addAuthorResponse = MutableStateFlow<String?>(null)
    val addAuthorResponse: StateFlow<String?> = _addAuthorResponse

    private val _authors = MutableStateFlow<List<Author>>(emptyList())
    val authors: StateFlow<List<Author>> get() = _authors

    init {
        fetchAuthors()
    }

    fun addAuthor(author: Author) {
        author.id = authorsDb.push().key
        authorsDb.child(author.id!!).setValue(author).addOnCompleteListener {
            if (it.isSuccessful) {
                _addAuthorResponse.value = null
            } else {
                _addAuthorResponse.value = it.exception?.message
            }
        }
    }


    private fun fetchAuthors() {
        authorsDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<Author>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Author::class.java)
                        author?.id = authorSnapshot.key
                        author?.let {
                            authors.add(it)
                        }
                    }
                    _authors.value = authors
                }
            }
        })
    }

    // Function to update the value from Firebase
    fun updateDataValueFromFirebase() {
        authorsDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val authors = mutableListOf<Author>()
                    for (authorSnapshot in dataSnapshot.children) {
                        val author = authorSnapshot.getValue(Author::class.java)
                        author?.id = authorSnapshot.key
                        author?.let {
                            authors.add(it)
                        }
                    }
                    _authors.value = authors
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun deleteAuthor(author: Author) {
        authorsDb.child(author.id!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _addAuthorResponse.value = null
                } else {
                    _addAuthorResponse.value = it.exception?.message
                }
            }
    }
}
