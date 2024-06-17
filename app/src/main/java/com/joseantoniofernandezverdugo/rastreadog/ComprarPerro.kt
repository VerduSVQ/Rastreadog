package com.joseantoniofernandezverdugo.rastreadog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentComprarPerroBinding
import kotlinx.coroutines.launch

class ComprarPerro : Fragment() {

    private var _binding: FragmentComprarPerroBinding? = null
    private val binding get() = _binding!!
    val args: ComprarPerroArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComprarPerroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val perroId = args.idPerro
         var perroDueño : String = ""
        var perroNombre : String = ""

        binding.backArrow.setOnClickListener {

            findNavController().navigate(R.id.action_comprarPerro_to_comprar)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            if (perroId.isNotEmpty()) {
                val db = FirebaseFirestore.getInstance()
                db.collection("perros").document(perroId).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            binding.nombre.text = document.getString("raza")
                            binding.tvColor.text = document.getString("color")
                            binding.tvRazaPadre.text = document.getString("razaPadre")
                            binding.tvColorPadre.text = document.getString("colorPadre")
                            binding.tvRazaMadre.text = document.getString("razaMadre")
                            binding.tvColorMadre.text = document.getString("colorMadre")
                            binding.tvEdad.text = document.getString("edad")
                            binding.tvPrecio.text = document.getLong("precio").toString()
                            binding.tvCiudad.text = document.getString("ciudad")

                            perroDueño = document.getString("email").toString()
                            perroNombre = document.getString("raza").toString()

                            // Obtener la referencia de la imagen en Firebase Storage
                            val storageRef = FirebaseStorage.getInstance().reference.child("images/$perroId")

                            // Obtener la URL de descarga y cargar la imagen con Glide
                            storageRef.downloadUrl.addOnSuccessListener { uri ->
                                Glide.with(requireContext()).load(uri).into(binding.ivImagen)
                            }.addOnFailureListener {
                            }
                        }
                    }
            }
        }
        binding.btnComprar.setOnClickListener {
            sendEmail(perroDueño,perroNombre)
        }
    }


    private fun sendEmail(perroDueño : String,perroNombre : String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Solo las apps de correo electrónico deberían manejar esto
            putExtra(Intent.EXTRA_EMAIL, arrayOf("$perroDueño"))
            putExtra(Intent.EXTRA_SUBJECT, "Oferta")
            putExtra(Intent.EXTRA_TEXT, "Buenas,estoy interessado en su $perroNombre")
        }

        if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(emailIntent)
        } else {
            // Muestra un mensaje o haz algo si no hay ninguna app de correo instalada
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

