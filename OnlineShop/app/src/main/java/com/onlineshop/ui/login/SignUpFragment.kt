package com.onlineshop.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onlineshop.databinding.SignUpFragmentBinding
import com.onlineshop.viewmodel.SignUpViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModel: SignUpViewModel
    private var binding: SignUpFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        binding = SignUpFragmentBinding.inflate(inflater, container, false)
            .also {
                it.login.text.insert(0, SignUpFragmentArgs.fromBundle(requireArguments()).login)
            }
        setListeners()
        return binding?.root
    }

    private fun setListeners() {
        binding?.signUpButton?.setOnClickListener {
            viewModel.signUp()
        }
    }
}
