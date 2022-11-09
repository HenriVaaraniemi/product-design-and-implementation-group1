package com.example.clickergame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.clickergame.databinding.FragmentFirstBinding
import com.example.clickergame.DBhelper
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)


        val db = DBhelper(requireActivity(), null)
        val cursor = db.getTotal()

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst()

            binding.textviewFirst.text =
                cursor.getString(cursor.getColumnIndex(DBhelper.TOTAL)).toString()
            cursor.close()
        }

        val cursorClick = db.getPower()

        if (cursorClick != null && cursorClick.moveToFirst()) {
            cursorClick.moveToFirst()

            binding.clickPower.text =
                cursorClick.getString(cursorClick.getColumnIndex(DBhelper.CLICK_POWER)).toString()
            cursorClick.close()
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonFirst.setOnClickListener {

            binding.textviewFirst.text = (binding.textviewFirst.text.toString().toInt() + 1).toString()
        }

        binding.btnPaivitukset.setOnClickListener {

            val db = DBhelper(requireActivity(), null)

            val amount = binding.textviewFirst.text.toString().toInt()

          db.addTotal(amount)

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}