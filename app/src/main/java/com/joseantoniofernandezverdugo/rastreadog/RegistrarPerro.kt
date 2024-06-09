package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentRegistrarPerroBinding

class RegistrarPerro : Fragment() {
    private var _binding: FragmentRegistrarPerroBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrarPerroBinding.inflate(inflater, container, false)
        return binding.root
    }
}