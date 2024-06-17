package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentComprarPerroBinding
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentEliminarPerroBinding
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentRegistrarPerroBinding
import kotlinx.coroutines.launch

class EliminarPerro : Fragment() {

    private var _binding: FragmentEliminarPerroBinding? = null
    private val binding get() = _binding!!
    val args: EliminarPerroArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarPerroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val perroIdVenta = args.idPerroVenta
        val db = FirebaseFirestore.getInstance()
        binding.btnEliminar.setOnClickListener {
            db.collection("perros").document(perroIdVenta).delete()
            findNavController().navigate(R.id.action_eliminarPerro_to_vender)

        }

        viewLifecycleOwner.lifecycleScope.launch {
            if (perroIdVenta.isNotEmpty()) {

                db.collection("perros").document(perroIdVenta).get()
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
                        }
                    }
            }
            val storageRef = FirebaseStorage.getInstance().reference.child("images/${perroIdVenta}")

            // Obtener la URL de descarga y cargar la imagen con Glide
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(view.context).load(uri).into(binding.ivImagen)
            }.addOnFailureListener {

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
