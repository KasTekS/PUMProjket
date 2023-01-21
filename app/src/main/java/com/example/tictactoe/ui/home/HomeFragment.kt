package com.example.tictactoe.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    if (savedInstanceState != null) {
      return inflater.inflate(R.layout.fragment_home, container, false)
    }

    val homeViewModel =
      ViewModelProvider(this).get(StandardViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textHome
    homeViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }

    val game = Game(binding)

    binding.comupter.setOnClickListener {
      binding.tryb.visibility = View.VISIBLE
      binding.start.visibility = View.VISIBLE
      binding.tryby2.visibility = View.VISIBLE
      binding.zaczynanie.visibility = View.VISIBLE
    }
    binding.multi.setOnClickListener {
      binding.tryb.visibility = View.INVISIBLE
      binding.plansza.visibility = View.VISIBLE
      binding.start.visibility = View.INVISIBLE
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

//    binding.resetall.setOnClickListener {
//      activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//
//      activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//
//    }
    return root
  }

  override fun onDestroyView() {
    super.onDestroyView()

    _binding = null
  }







  }





