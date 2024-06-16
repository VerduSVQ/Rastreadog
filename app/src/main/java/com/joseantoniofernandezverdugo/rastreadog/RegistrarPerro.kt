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
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentRegistrarPerroBinding

class RegistrarPerro : Fragment() {
    private var _binding: FragmentRegistrarPerroBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrarPerroBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegistrarPerro.setOnClickListener {
            registrarPerro()
        }
    }

    private fun registrarPerro() {
        val raza = binding.etRaza.text.toString().trim()
        val color = binding.etColor.text.toString().trim()
        val razaPadre = binding.etRazaPadre.text.toString().trim()
        val colorPadre = binding.etColorPadre.text.toString().trim()
        val razaMadre = binding.etRazaMadre.text.toString().trim()
        val colorMadre = binding.etColorMadre.text.toString().trim()
        val edad = binding.etEdad.text.toString().trim()
        val precio = binding.etPrecio.text.toString().trim().toLongOrNull()
        val ciudad = binding.etCiudad.text.toString().trim()

        if (raza.isEmpty() || color.isEmpty() || razaPadre.isEmpty() || colorPadre.isEmpty() ||
            razaMadre.isEmpty() || colorMadre.isEmpty() || edad.isEmpty() || precio == null || ciudad.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoPerro = db.collection("perros").document()

        nuevoPerro.set(
            hashMapOf(
                "uid" to nuevoPerro.id,
                "raza" to raza,
                "color" to color,
                "razaPadre" to razaPadre,
                "colorPadre" to colorPadre,
                "razaMadre" to razaMadre,
                "colorMadre" to colorMadre,
                "edad" to edad,
                "precio" to precio,
                "ciudad" to ciudad,
                "email" to auth.currentUser?.email
            )
        ).addOnSuccessListener {
            Toast.makeText(requireContext(), "Perro registrado con éxito", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registrarPerro_to_vender)
        }.addOnFailureListener { e ->
            Toast.makeText(requireContext(), "Error al registrar el perro: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
