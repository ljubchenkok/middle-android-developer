package ru.skillbranch.skillarticles.ui.auth

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.savedstate.SavedStateRegistryOwner
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.extensions.hideKeyBoard
import ru.skillbranch.skillarticles.ui.RootActivity
import ru.skillbranch.skillarticles.ui.base.BaseFragment
import ru.skillbranch.skillarticles.viewmodels.auth.AuthViewModel
import ru.skillbranch.skillarticles.viewmodels.auth.ValidationType

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
        btn_reg.isEnabled = true
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

    private fun validateAll(): Boolean {
        var result = validate(wrap_name, et_name.text.toString(), ValidationType.NAME)
        result = validate(wrap_login, et_login.text.toString(), ValidationType.LOGIN) && result
        result =
            validate(wrap_password, et_password.text.toString(), ValidationType.PASSWORD) && result
        result = et_password.text.toString() == et_password_confirm.text.toString() && result
        return result
    }


    private val registrationMatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            when (s.hashCode()) {
                et_name.text.hashCode() -> validate(wrap_name, s.toString(), ValidationType.NAME)
                et_login.text.hashCode() -> validate(wrap_login, s.toString(), ValidationType.LOGIN)
                et_password.text.hashCode() -> validate(
                    wrap_password,
                    s.toString(),
                    ValidationType.PASSWORD
                )
                et_password_confirm.text.hashCode() -> {
                    with(wrap_password_confirm) {
                        if (s.toString() != et_password.text.toString()) {
//                            btn_reg.isEnabled = false
                            isErrorEnabled = true
                            error = "Passwords do not match"
                            false
                        } else {
//                            btn_reg.isEnabled = true
                            isErrorEnabled = false
                            error = null
                            true
                        }
                    }

                }
            }
        }
    }

    private fun validate(view: TextInputLayout, s: String, type: ValidationType): Boolean {
        with(view) {
            return if (!s.contains(type.value.first)) {
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