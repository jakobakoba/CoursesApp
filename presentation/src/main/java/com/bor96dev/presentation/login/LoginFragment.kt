package com.bor96dev.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bor96dev.presentation.R
import com.bor96dev.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModelState()
        setupInputFilters()
    }

    private fun setupInputFilters(){
        val noCyrillicFilter = InputFilter { source, _, _, _, _, _ ->
            if (source.any {Character.UnicodeBlock.of(it) == Character.UnicodeBlock.CYRILLIC}){
                ""
            } else {
                null
            }
        }
        binding.emailEt.filters = arrayOf(noCyrillicFilter)
    }
    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    private fun setupListeners(){
        binding.emailEt.doAfterTextChanged { text ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.passwordEt.doAfterTextChanged { text ->
            viewModel.onPasswordChanged(text.toString())
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_coursesFragment)
        }

        binding.vkButton.setOnClickListener {
            openUrlInBrowser("https://vk.com")
        }
        binding.okButton.setOnClickListener {
            openUrlInBrowser("https://ok.ru")
        }
    }

    private fun observeViewModelState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.isLoginButtonEnabled.collect {isEnabled ->
                    binding.loginButton.isEnabled = isEnabled
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



