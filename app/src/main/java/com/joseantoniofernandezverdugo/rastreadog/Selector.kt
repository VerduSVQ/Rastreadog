package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentSelectorBinding

class Selector : Fragment() {
    private var _binding: FragmentSelectorBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.btnComprar.setOnClickListener {
            findNavController().navigate(R.id.action_selector_to_comprar)
        }
        binding.btnVender.setOnClickListener {
            findNavController().navigate(R.id.action_selector_to_vender)
        }
        binding.btnCerrarSesion.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_selector_to_inicioSesion)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
