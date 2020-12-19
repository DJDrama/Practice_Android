package com.answer.univ.repository

import com.answer.univ.data.Result
import com.answer.univ.data.UNKNOWN_ERROR
import com.answer.univ.data.getInterestList
import com.answer.univ.data.getUniversityList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class LauncherRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : LauncherRepository {

    override fun getUniversities(): Set<String> = getUniversityList()
    override fun getInterests(): List<String> = getInterestList()

    override suspend fun login(email: String, password: String): Result {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                Result(true)
            } ?: Result(false, UNKNOWN_ERROR)
        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthInvalidUserException -> {
                    Result(false, "일치하는 회원이 없습니다.")
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    Result(false, "이메일 비밀번호를 확인해주세요.")
                }
                else ->
                    Result(false, UNKNOWN_ERROR)
            }
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phoneNum: String,
        university: String,
        major: String,
        interest: String
    ) {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    }


}