package com.example.kocchiyomi.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kocchiyomi.MainActivity
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.FragmentRegisterBinding
import com.example.kocchiyomi.ui.auth.AuthActivity
import com.example.kocchiyomi.utils.AuthUtil
import com.example.kocchiyomi.utils.ErrorMessage
import com.example.kocchiyomi.utils.LoadState
import com.example.kocchiyomi.utils.toast
import com.google.android.material.textfield.TextInputEditText


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by activityViewModels{
        RegisterViewModel.RegisterViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        if (AuthUtil.firebaseAuthInstance.currentUser != null) {
            navigateToHome()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        viewModel.enableSwitch()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvSignin.setOnClickListener {
            Log.d("d", "clcik")
            viewModel.switchToLoginMutableLiveData.observe(viewLifecycleOwner, Observer { switchToLogin ->
                if (switchToLogin != null && switchToLogin) {
                    this@RegisterFragment.findNavController()
                        .navigate(R.id.action_registerFragment_to_loginFragment3)
                    viewModel.doneSwitch()
                }
            })
        }

        binding.etSignupEmail.afterTextChanged { email ->
            viewModel.isEmailMatch(email).observe(viewLifecycleOwner, Observer { isEmailMatch ->
                if(!isEmailMatch) {
                    binding.tilSignupEmail.error = getString(R.string.email_wrong_format)
                }
                else {
                    binding.tilSignupEmail.isErrorEnabled = false
                }
            })
        }

        binding.etSignupUsername.afterTextChanged { username ->
            viewModel.isUserNameMatch(username).observe(viewLifecycleOwner, Observer { isUserNameMatch ->
                if(!isUserNameMatch) {
                    binding.tilSignupUsername.error = getString(R.string.username_wrong_format)
                }
                else {
                    binding.tilSignupUsername.isErrorEnabled = false
                }
            })
        }

        binding.etSignupPassword.afterTextChanged { password ->
            viewModel.isPasswordMatch(password).observe(viewLifecycleOwner, Observer { isPasswordMatch ->
                if(!isPasswordMatch) {
                    binding.tilSignupPassword.error = getString(R.string.password_wrong_format)
                }
                else {
                    binding.tilSignupPassword.isErrorEnabled = false
                }
            })
        }

        binding.btnSignup.setOnClickListener {
            register()
        }

        binding.btnRetry.setOnClickListener {
            binding.vDarkOverlay.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.btnRetry.visibility = View.GONE
        }

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoadState.LOADING -> {
                    binding.vDarkOverlay.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.GONE
                }
                LoadState.SUCCESS -> {
                    binding.vDarkOverlay.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.btnRetry.visibility = View.GONE
                    viewModel.navigateToHomeMutableLiveData.observe(viewLifecycleOwner, Observer { navigateToHome ->
                        if (navigateToHome != null && navigateToHome) {
                            (activity as AuthActivity).toast("Register successful", false)
                            viewModel.doneNavigating()
                            navigateToHome()
                        }
                    })
                }
                LoadState.FAILURE -> {
                    binding.vDarkOverlay.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.btnRetry.visibility = View.VISIBLE
                    binding.btnRetry.text = ErrorMessage.errorMessage
                }

            }
        })

        binding.etSignupConfirmPassword.setOnEditorActionListener { _, actionId, _ ->
            register()
            true
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun register() {

        val email: String = binding.etSignupEmail.text.toString().trim { it <= ' ' }
        val userName: String = binding.etSignupUsername.text.toString().trim { it <= ' ' }
        val password: String = binding.etSignupPassword.text.toString().trim { it <= ' ' }
        val confirmPassword: String = binding.etSignupConfirmPassword.text.toString().trim { it <= ' ' }

        if (binding.etSignupEmail.text.isEmpty() || binding.etSignupUsername.text.isEmpty() || binding.etSignupPassword.text.isEmpty() || binding.etSignupConfirmPassword.text.isEmpty() || binding.tilSignupEmail.isErrorEnabled || binding.tilSignupUsername.isErrorEnabled|| binding.tilSignupPassword.isErrorEnabled) {
                (activity as AuthActivity).toast(R.string.check_username_email_retry, false)
        }
        else {
            var isConfirmPasswordMatchValue = false

            viewModel.isConfirmPasswordMatch(password, confirmPassword).observe(viewLifecycleOwner, Observer { isConfirmPasswordMatch ->
                isConfirmPasswordMatchValue = isConfirmPasswordMatch
                if(!isConfirmPasswordMatch) {
                    (activity as AuthActivity).toast(R.string.password_does_not_match, false)
                }
            })
            if (isConfirmPasswordMatchValue) {
                viewModel.register(
                    AuthUtil.firebaseAuthInstance,
                    email,
                    password,
                    userName,
                )


            }

        }
    }

    private fun navigateToHome() {
        val intent = Intent(activity as AuthActivity, MainActivity::class.java)
        startActivity(intent)
        (activity as AuthActivity)!!.overridePendingTransition(0, 0)
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }


}


