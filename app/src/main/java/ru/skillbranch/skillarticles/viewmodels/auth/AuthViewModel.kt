package ru.skillbranch.skillarticles.viewmodels.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.skillbranch.skillarticles.data.repositories.RootRepository
import ru.skillbranch.skillarticles.ui.auth.RegistrationFragment
import ru.skillbranch.skillarticles.viewmodels.base.BaseViewModel
import ru.skillbranch.skillarticles.viewmodels.base.IViewModelState
import ru.skillbranch.skillarticles.viewmodels.base.NavigationCommand
import ru.skillbranch.skillarticles.viewmodels.base.Notify

class AuthViewModel(handle: SavedStateHandle) : BaseViewModel<AuthState>(handle, AuthState()), IAuthViewModel {
    private val repository = RootRepository
    init {
        subscribeOnDataSource(repository.isAuth()){ isAuth, state ->
            state.copy(isAuth = isAuth)
        }
    }
    override fun handleLogin(login: String, pass: String, dest: Int?) {
        launchSafety {
            repository.login(login, pass)
            navigate(NavigationCommand.FinishLogin(dest))
        }

    }
    override fun handleRegister(name: String, login: String, password: String, dest: Int?) {
        if(name.isEmpty() || login.isEmpty() || password.isEmpty()){
            notify(Notify.ErrorMessage("Name, login, password it is required fields and not must be empty"))
            return
        }
        if(!name.contains(ValidationType.NAME.value.first!!) || name.length < 3){
            notify(Notify.ErrorMessage(ValidationType.NAME.value.second))
            return
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            notify(Notify.ErrorMessage(ValidationType.LOGIN.value.second))
            return
        }
        if(password.isEmpty() || !password.contains(ValidationType.PASSWORD.value.first!!) || password.length < 8 ){
            notify(Notify.ErrorMessage(ValidationType.PASSWORD.value.second))
            return
        }
        launchSafety {
            repository.register(name, login, password)
            navigate(NavigationCommand.FinishLogin(dest))
        }

    }

    internal enum class ValidationType(val value: Pair<Regex?, String>) {
        NAME("^[\\w+-]+$".toRegex() to "The name must be at least 3 characters long and contain only letters and numbers and can also contain the characters \"-\" and \"_\""),
        LOGIN(null to "Incorrect Email entered"),
        PASSWORD("^[A-z0-9]+$".toRegex() to "Password must be at least 8 characters long and contain only letters and numbers"),
    }

}

data class AuthState( val isAuth: Boolean = false) : IViewModelState