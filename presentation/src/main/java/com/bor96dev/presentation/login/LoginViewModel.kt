package com.bor96dev.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val emailPattern = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    )

    val isLoginButtonEnabled: StateFlow<Boolean> =
        combine(email, password){ emailVal, passwordVal ->
            isValidEmail(emailVal) && isValidPassword(passwordVal)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onEmailChanged(newEmail: String){
        _email.value = newEmail
    }
    fun onPasswordChanged(newPassword: String){
        _password.value = newPassword
    }

    private fun isValidEmail(email: String) : Boolean {
        return email.isNotBlank() && emailPattern.matcher(email).matches()
    }

    private fun isValidPassword(password: String) : Boolean {
        return password.isNotEmpty()
    }
}