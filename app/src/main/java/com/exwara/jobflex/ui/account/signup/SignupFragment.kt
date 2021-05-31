package com.exwara.jobflex.ui.account.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.exwara.jobflex.databinding.FragmentSignupBinding
import com.exwara.jobflex.ui.account.AccountActivity

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private lateinit var viewModel: SignUpViewModel
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding?.apply {
            signUpViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        (activity as AccountActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()
        setupObservers()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            binding?.btnSignup?.setOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            Navigation.findNavController(requireView()).popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupListeners() {
        binding?.apply {
            edtFullName.doAfterTextChanged { fullname ->
                viewModel.fullName = fullname.toString()
            }
            edtEmail.doAfterTextChanged { email -> viewModel.email = email.toString() }
            edtPhone.doAfterTextChanged { phone -> viewModel.phoneNumber = phone.toString() }
            edtPassword.doAfterTextChanged { password ->
                viewModel.password = password.toString()
            }
        }
    }

    private fun setupObservers() {
        viewModel.fullNameError.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding?.edtFullName?.error = it
            }
        })
        viewModel.emailError.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding?.edtEmail?.error = it
            }
        })
        viewModel.phoneNumberError.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding?.edtPhone?.error = it
            }
        })
        viewModel.passwordError.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding?.edtPassword?.error = it
            }
        })
        viewModel.navigateToLogin.observe(viewLifecycleOwner, {
            if (it) {
                startActivity(Intent(context, AccountActivity::class.java))
                (activity as AccountActivity).finish()
                viewModel.doneNavigating()
            }
        })
    }

}