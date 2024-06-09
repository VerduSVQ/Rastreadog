package com.joseantoniofernandezverdugo.rastreadog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class InicioSesion : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root= inflater.inflate(R.layout.fragment_inicio_sesion, container, false)

        val btnReg = root.findViewById<Button>(R.id.signUpButton)
        btnReg.setOnClickListener{
            findNavController().navigate(R.id.action_inicioSesion_to_registro)
        }
        return root
    }


}