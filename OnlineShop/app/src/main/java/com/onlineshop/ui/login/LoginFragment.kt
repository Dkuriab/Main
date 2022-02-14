package com.onlineshop.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.onlineshop.databinding.LoginFragmentBinding
import com.onlineshop.viewmodel.LoginViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModel: LoginViewModel
    private var binding: LoginFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        binding = LoginFragmentBinding.inflate(inflater, container, false)

        observeViewModel()
        setListeners()
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setListeners() {
        binding?.loginButton?.setOnClickListener {
            viewModel.logIn()
        }

        binding?.createAccountButton?.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignUpFragment(binding?.login?.text.toString() ?: "")
            )
        }
    }

    private fun observeViewModel() {
        // TODO check if correct
    }
}
