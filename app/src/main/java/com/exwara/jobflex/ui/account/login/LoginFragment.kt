package com.exwara.jobflex.ui.account.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.exwara.jobflex.R
import com.exwara.jobflex.databinding.FragmentLoginBinding
import com.exwara.jobflex.ui.account.AccountActivity
import com.exwara.jobflex.ui.main.MainActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        (activity as AccountActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            binding?.apply {
                tvCreateAccount.setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.nav_create_account)
                }
                btnLogin.setOnClickListener {
                    startActivity(Intent(activity, MainActivity::class.java))
                }
            }
        }
    }

}