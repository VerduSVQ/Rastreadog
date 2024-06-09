package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentRegistroBinding

class Registro : Fragment() {

   private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.tvEmail.text.toString().trim()
            val password = binding.tvClave.text.toString().trim()
            val password2 = binding.tvClave2.text.toString().trim()
            val nombre = binding.tvNombre.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()


            if (email.isEmpty() || password.isEmpty() || password2.isEmpty() || nombre.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Email y contraseña son obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                if (password != password2) {
                    Toast.makeText(requireContext(), "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_registro_to_inicioSesion)
                                db.collection("usuarios").document(email).set(
                                    hashMapOf(
                                        "nombre" to nombre,
                                        "clave" to password,
                                        "telefono" to phone

                                    )
                                )

                            } else {
                                // Si el registro falla, muestra un mensaje al usuario
                                Toast.makeText(requireContext(), "Registro fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
}
