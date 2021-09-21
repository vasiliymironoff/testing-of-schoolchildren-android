package com.example.schoolandroid.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.schoolandroid.MainActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso
import java.lang.Exception


class ProfileFragment : Fragment() {

    companion object {
        const val PICK = 123
    }
    private val viewModel: ProfileViewModel by activityViewModels()
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        if (savedInstanceState == null) {
            viewModel.fetchMe()

        }
        viewModel.getCurrentUser().observe(viewLifecycleOwner, {
            binding.swiper.isRefreshing = false
            try {
                if (it != null) {
                    binding.name.text = "${it.firstName} ${it.lastName}"

                    binding.email.text = "Email: ${it.email}"

                    if (it.avatar != null) {
                        binding.addPhotoImage.visibility = View.GONE
                        Picasso.get()
                            .load(it.avatar)
                            .into(binding.avatar)
                    }

                    binding.avatar.setOnClickListener {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        activity?.startActivityForResult(Intent.createChooser(intent,
                            "Выберите приложения для загрузки фото"), PICK)
                    }

                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }

        })
        binding.swiper.setOnRefreshListener {
            viewModel.fetchMe()
        }
        binding.swiper.isRefreshing = true
        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}