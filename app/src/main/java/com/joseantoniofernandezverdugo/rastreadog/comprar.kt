package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.joseantoniofernandezverdugo.rastreadog.adapter.PerroAdapter
import com.joseantoniofernandezverdugo.rastreadog.databinding.FragmentComprarBinding
import com.joseantoniofernandezverdugo.rastreadog.modelo.Perro
import kotlinx.coroutines.launch

class comprar : Fragment() {

    private lateinit var adapter: PerroAdapter
    private var _binding: FragmentComprarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComprarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            PerroProvider.getTotalPerros { perroMap ->
                val perroList = perroMap.map { entry ->
                    val data = entry.value as Map<String, Any?>
                    Perro(
                        uid= data["uid"] as String,
                        raza = data["raza"] as String,
                        color = data["color"] as String,
                        razaPadre = data["razaPadre"] as String,
                        colorPadre = data["colorPadre"] as String,
                        razaMadre = data["razaMadre"] as String,
                        colorMadre = data["colorMadre"] as String,
                        edad = data["edad"] as String,
                        precio = data["precio"] as Long,
                        ciudad = data["ciudad"] as String // Asegúrate de que este campo es un String en Firestore
                    )
                }
                adapter = PerroAdapter(perroList) { perro ->
                   findNavController().navigate(comprarDirections.actionComprarToComprarPerro(idPerro = perro.uid))
                }
                binding.recyclerView.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
