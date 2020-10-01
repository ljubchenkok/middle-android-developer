package ru.skillbranch.skillarticles.ui.auth

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.extensions.hideKeyBoard
import ru.skillbranch.skillarticles.ui.RootActivity
import ru.skillbranch.skillarticles.ui.base.BaseFragment
import ru.skillbranch.skillarticles.viewmodels.auth.AuthViewModel

class RegistrationFragment() : BaseFragment<AuthViewModel>() {
    var _mockFactory: ((SavedStateRegistryOwner) -> ViewModelProvider.Factory)? = null
    override val viewModel: AuthViewModel by viewModels {
        _mockFactory?.invoke(this) ?: defaultViewModelProviderFactory
    }

    //testing constructor
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    constructor(
        mockRoot: RootActivity,
        mockFactory: ((SavedStateRegistryOwner) -> ViewModelProvider.Factory)? = null
    ) : this() {
        _mockRoot = mockRoot
        _mockFactory = mockFactory
    }

    override val layout: Int = R.layout.fragment_registration
    private val args: RegistrationFragmentArgs by navArgs()

    override fun onPause() {
        activity?.hideKeyBoard(btn_reg)
        super.onPause()
    }


    override fun setupViews() {
        et_login.addTextChangedListener(registrationMatcher)
        et_name.addTextChangedListener(registrationMatcher)
        et_password.addTextChangedListener(registrationMatcher)
        et_password_confirm.addTextChangedListener(registrationMatcher)
        btn_reg.setOnClickListener {
//            if (validateAll()) {
            viewModel.handleRegister(
                et_name.text.toString(),
                et_login.text.toString(),
                et_password.text.toString(),
                if (args.privateDestination == -1) null else args.privateDestination
            )
//            }
        }
    }

    private val registrationMatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            when (s.hashCode()) {
                et_name.text.hashCode() -> validate(s.toString(), ValidationType.NAME)
                et_login.text.hashCode() -> validate(s.toString(), ValidationType.LOGIN)
                et_password.text.hashCode() -> validate(s.toString(), ValidationType.PASSWORD)
                et_password_confirm.text.hashCode() -> validate(
                    s.toString(),
                    ValidationType.CONFIRM
                )
            }
        }
    }

    private fun validateAll(): Boolean {
        var result = validate(et_name.text.toString(), ValidationType.NAME)
        result = validate(et_login.text.toString(), ValidationType.LOGIN) && result
        result = validate(et_password.text.toString(), ValidationType.PASSWORD) && result
        result = validate(et_password_confirm.text.toString(), ValidationType.CONFIRM) && result
        return result
    }

    private fun validate(s: String, type: ValidationType): Boolean {
        return when (type) {
            ValidationType.NAME -> {
                with(wrap_name) {
                    if (!s.contains(type.value.first!!) || s.length < 3) {
                        isErrorEnabled = true
                        error = type.value.second
                        false
                    } else {
                        isErrorEnabled = false
                        error = null
                        true
                    }
                }
            }
            ValidationType.LOGIN -> {
                with(wrap_login) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                        isErrorEnabled = true
                        error = type.value.second
                        false
                    } else {
                        isErrorEnabled = false
                        error = null
                        true
                    }
                }
            }
            ValidationType.PASSWORD -> {
                with(wrap_password) {
                    if (!s.contains(type.value.first!!) || s.length < 8) {
                        isErrorEnabled = true
                        error = type.value.second
                        false
                    } else {
                        isErrorEnabled = false
                        error = null
                        true
                    }
                }
            }
            ValidationType.CONFIRM -> {
                with(wrap_password_confirm) {
                    if (s != et_password.text.toString()) {
                        isErrorEnabled = true
                        error = type.value.second
                        false
                    } else {
                        isErrorEnabled = false
                        error = null
                        true
                    }
                }
            }
        }
    }

    internal enum class ValidationType(val value: Pair<Regex?, String>) {
        NAME("^[\\w+-]+$".toRegex() to "The name must be at least 3 characters long and contain only letters and numbers and can also contain the characters \"-\" and \"_\""),
        LOGIN(null to "Incorrect Email entered"),
        PASSWORD("^[A-z0-9]+$".toRegex() to "Password must be at least 8 characters long and contain only letters and numbers"),
        CONFIRM(null to "Passwords do not match")
    }
}