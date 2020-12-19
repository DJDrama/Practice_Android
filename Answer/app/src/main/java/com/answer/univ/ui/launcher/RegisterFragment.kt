package com.answer.univ.ui.launcher

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.answer.univ.R
import com.answer.univ.databinding.FragmentRegisterBinding
import com.answer.univ.ui.isBlankOrEmpty
import com.answer.univ.ui.showActionBar
import com.answer.univ.ui.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register), View.OnClickListener {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        requireActivity().showActionBar()
        binding.btnRegister.setOnClickListener(this)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.universities.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it.toList()
            )
            binding.spinnerUniversities.adapter = adapter
        }
        viewModel.interests.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it
            )
            binding.spinnerInterest.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        if (p0 == binding.btnRegister) {
            val email = binding.edtEmailAddress.text.toString()
            val password = binding.edtPassword.text.toString()
            val passwordCheck = binding.edtPasswordCheck.text.toString()

            val name = binding.edtName.text.toString()
            val nickName = binding.edtNickname.text.toString()
            val phoneNumber = binding.edtPhoneNumber.text.toString()

            val university = binding.spinnerUniversities.selectedItem.toString()
            val major = binding.edtDepartment.text.toString()

            val interest = binding.spinnerInterest.selectedItem.toString()

            if (email.isBlankOrEmpty()) {
                p0.showSnackBar("이메일 주소를 입력해주세요.")
                return
            }
            if (password.isBlankOrEmpty()) {
                p0.showSnackBar("비밀번호를 입력해주세요.")
                return
            }
            if (passwordCheck.isBlankOrEmpty()) {
                p0.showSnackBar("비밀번호 확인을 입력해주세요.")
                return
            }
            if (password != passwordCheck) {
                p0.showSnackBar("비밀번호가 일치하지 않습니다.")
                return
            }
            if (name.isBlankOrEmpty()) {
                p0.showSnackBar("이름을 입력해주세요.")
                return
            }
            if (nickName.isBlankOrEmpty()) {
                p0.showSnackBar("닉네임을 입력해주세요.")
                return
            }
            if (phoneNumber.isBlankOrEmpty()) {
                p0.showSnackBar("전화번호를 입력해주세요.")
                return
            }
            if (university == viewModel.universities.value?.first()) {
                p0.showSnackBar("대학교를 선택해주세요.")
                return
            }
            if (major.isBlankOrEmpty()) {
                p0.showSnackBar("학과/전공을 입력해주세요.")
                return
            }
            if (interest == viewModel.interests.value?.first()) {
                p0.showSnackBar("관심 사항을 선택해주세요.")
                return
            }

            viewModel.register(
                email,
                password,
                name,
                nickName,
                phoneNumber,
                university,
                major,
                interest
            )
        }
    }
}