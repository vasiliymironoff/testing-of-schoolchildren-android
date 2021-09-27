package com.example.schoolandroid.ui.profileedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController


import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentProfileEditBinding
import com.example.schoolandroid.ui.examdetail.ExamDetailViewModel
import com.example.schoolandroid.ui.profileedit.ProfileEditViewModel.Companion.NAME_OR_EMAIL
import com.google.android.material.snackbar.Snackbar

class ProfileEditFragment : Fragment() {

    private val viewModel: ProfileEditViewModel by viewModels()

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        _binding = FragmentProfileEditBinding.bind(view)
        viewModel.getIsEdit().observe(viewLifecycleOwner) {
            if (it > 0) {
                if (it == NAME_OR_EMAIL) {
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("key", true)
                }
                findNavController().popBackStack()
            }
        }
        binding.editName.setOnClickListener {
            viewModel.putName(binding.newFirstName.text.toString(),
                binding.newLastName.text.toString())
        }
        binding.editEmail.setOnClickListener {
            viewModel.postEmail(binding.newEmail.text.toString(),
                binding.passwordEmail.text.toString())
        }
        binding.editPassword.setOnClickListener {
            val newPassword = binding.newPassword.text.toString().trim()
            val repeatNewPassword = binding.newPasswordRepeat.text.toString().trim()
            if (newPassword == repeatNewPassword) {
                viewModel.postPassword(binding.passwordPassword.text.toString(),
                    newPassword)
            } else {
                Snackbar.make(view,resources.getString(R.string.password_not_match), Snackbar.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}