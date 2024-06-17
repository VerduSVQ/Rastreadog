package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.joseantoniofernandezverdugo.rastreadog.adapter.PerroAdapter
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentVenderBinding
import com.joseantoniofernandezverdugo.rastreadog.modelo.Perro
import kotlinx.coroutines.launch

class Vender : Fragment() {

    private lateinit var adapter: PerroAdapter
    private var _binding: FragmentVenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.recyclerViewVender.layoutManager = LinearLayoutManager(context)

        binding.btnRegistroPerro.setOnClickListener {
            findNavController().navigate(R.id.action_vender_to_registrarPerro)
        }
        binding.btnCerrarSesion.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_vender_to_inicioSesion)
        }
        binding.backArrow.setOnClickListener {

            findNavController().navigate(R.id.action_vender_to_selector)
        }

        val email = auth.currentUser?.email

        if (email != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                PerroProvider.getPerroUser(email) { perrosMap ->
                    val perroList = perrosMap.map { (id, data) ->
                        val perroData = data as Map<String, Any?>
                        Perro(
                            uid = perroData["uid"] as String,
                            raza = perroData["raza"] as String,
                            color = perroData["color"] as String,
                            razaPadre = perroData["razaPadre"] as String,
                            colorPadre = perroData["colorPadre"] as String,
                            razaMadre = perroData["razaMadre"] as String,
                            colorMadre = perroData["colorMadre"] as String,
                            edad = perroData["edad"] as String,
                            precio = perroData["precio"] as Long,
                            ciudad = perroData["ciudad"] as String
                        )
                    }
                    adapter = PerroAdapter(perroList) { perro ->
                        val action = VenderDirections.actionVenderToEliminarPerro(idPerroVenta = perro.uid)
                        findNavController().navigate(action)
                    }
                    binding.recyclerViewVender.adapter = adapter
                }
            }
        } else {
            // Manejar el caso donde el email es null
            // Por ejemplo, mostrando un mensaje al usuario o redirigiéndolo al login
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
