package com.example.kocchiyomi.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kocchiyomi.MainActivity
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.FragmentLoginBinding
import com.example.kocchiyomi.ui.auth.AuthActivity
import com.example.kocchiyomi.ui.auth.register.RegisterViewModel
import com.example.kocchiyomi.utils.AuthUtil
import com.example.kocchiyomi.utils.ErrorMessage
import com.example.kocchiyomi.utils.LoadState
import com.example.kocchiyomi.utils.toast


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by activityViewModels{
        LoginViewModelFactory()
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
        binding = FragmentLoginBinding.inflate(layoutInflater)
        viewModel.enableSwitch()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvSingup.setOnClickListener {
            Log.d("d", "click")

            viewModel.switchToRegisterMutableLiveData.observe(viewLifecycleOwner, Observer { switchToRegister ->
                if (switchToRegister != null && switchToRegister) {
                    this@LoginFragment.findNavController()
                        .navigate(R.id.action_loginFragment3_to_registerFragment)
                    viewModel.doneSwitch()
                }
            })
        }

        binding.etSigninEmail.afterTextChanged { email ->
            viewModel.isEmailMatch(email).observe(viewLifecycleOwner, Observer { isEmailMatch ->
                if(!isEmailMatch) {
                    binding.tilSigninEmail.error = getString(R.string.email_wrong_format)
                }
                else {
                    binding.tilSigninEmail.isErrorEnabled = false
                }
            })
        }

        binding.etSigninPassword.afterTextChanged { password ->
            viewModel.isPasswordMatch(password).observe(viewLifecycleOwner, Observer { isPasswordMatch ->
                if(!isPasswordMatch) {
                    binding.tilSigninPassword.error = getString(R.string.password_wrong_format)
                }
                else {
                    binding.tilSigninPassword.isErrorEnabled = false
                }
            })
        }

        binding.btnSignin.setOnClickListener {
           login()
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
                            (activity as AuthActivity).toast("login successful", false)
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

        binding.etSigninPassword.setOnEditorActionListener { _, actionId, _ ->
            login()
            true
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun login() {
        val email: String = binding.etSigninEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etSigninPassword.text.toString().trim { it <= ' ' }

        if (binding.etSigninEmail.text.isEmpty() || binding.etSigninPassword.text.isEmpty() || binding.tilSigninEmail.isErrorEnabled || binding.tilSigninPassword.isErrorEnabled) {
            (activity as AuthActivity).toast(R.string.check_email_retry, false)
        }
         else {
            viewModel.login(
                AuthUtil.firebaseAuthInstance,
                email,
                password
            )
        }

    }
    private fun navigateToHome() {
        val intent = Intent(activity as AuthActivity, MainActivity::class.java)
        startActivity(intent)
        (activity as AuthActivity)!!.overridePendingTransition(0, 0)
        (activity as AuthActivity)!!.finish()
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