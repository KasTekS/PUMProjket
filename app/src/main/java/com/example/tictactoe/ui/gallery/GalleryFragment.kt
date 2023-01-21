package com.example.tictactoe.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.databinding.FragmentGalleryBinding



class GalleryFragment : Fragment() {

private var _binding: FragmentGalleryBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

    _binding = FragmentGalleryBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textGalery
    galleryViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }


      val game = GameR(binding)

      binding.comupter.setOnClickListener {
          binding.tryb.visibility = View.VISIBLE
          binding.start.visibility = View.VISIBLE
          binding.tryby2.visibility = View.VISIBLE
          binding.zaczynanie.visibility = View.VISIBLE
      }
      binding.multi.setOnClickListener {
          binding.tryb.visibility = View.INVISIBLE
          binding.start.visibility = View.INVISIBLE
          binding.plansza.visibility = View.VISIBLE
          binding.tryby2.visibility = View.INVISIBLE
          binding.zaczynanie.visibility = View.INVISIBLE
          game.multi()
      }
      binding.easy.setOnClickListener {
          binding.plansza.visibility = View.VISIBLE
          game.easy()
      }
      binding.medium.setOnClickListener {
          binding.plansza.visibility = View.VISIBLE
          game.medium()
      }
      binding.hard.setOnClickListener {
          binding.plansza.visibility = View.VISIBLE
          game.thard()
      }
      binding.x.setOnClickListener {
          game.start =1
      }
      binding.o.setOnClickListener {
          game.start =0
      }

    return root



  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}