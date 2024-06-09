package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentInicioSesionBinding

class InicioSesion : Fragment() {

    private var _binding: FragmentInicioSesionBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentInicioSesionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_inicioSesion_to_registro)
        }

        setup()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        binding.loginButton.setOnClickListener {
            val email = binding.idEmailText.text.toString().trim()
            val password = binding.idPasswordText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email y contraseña son obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Inicio de sesión exitoso
                            Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            binding.loginButton.setOnClickListener {
                                findNavController().navigate(R.id.action_inicioSesion_to_selector)
                            }
                        } else {
                            // Si el inicio de sesión falla, muestra un mensaje al usuario
                            Toast.makeText(requireContext(), "Inicio de sesión fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.btnGoogle.setOnClickListener {


        }
    }
}
