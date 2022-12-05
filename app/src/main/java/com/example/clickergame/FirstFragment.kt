package com.example.clickergame

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.clickergame.databinding.FragmentFirstBinding
import com.example.clickergame.DBhelper
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit

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

        val db = DBhelper(requireActivity(),null)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = DBhelper(requireActivity(), null)
        val cursor = db.getTotal()
        val cursorAttack = db.getAttack()



        if (cursor != null && cursor.moveToLast()) {
            cursor.moveToLast()

            binding.textviewFirst.text =
                cursor.getString(cursor.getColumnIndex(DBhelper.TOTAL)).toString()
            cursor.close()
        }

        val cursorClick = db.getPower()

        if (cursorClick != null && cursorClick.moveToLast()) {
            cursorClick.moveToLast()

            val ClickPower = cursorClick.getString(cursorClick.getColumnIndex(DBhelper.CLICK_POWER))
            binding.clickPower.text =
                ClickPower.toDouble().toInt().toString()
            cursorClick.close()

        }
        if (cursorAttack != null && cursorAttack.moveToLast()){
            cursorAttack.moveToLast()
           // val attackMulti = getString(cursorAttack.getColumnIndex(DBhelper.ATTACK_POWER)).toString()
            cursorAttack.close()
            //val prosentti = attackMulti.toDouble().div(100)
           // val ClickPower = binding.clickPower.text.toString().toDouble()

           // binding.clickPower.text = (ClickPower * prosentti).toString()

        }


        val scaleUp = AnimationUtils.loadAnimation(context,R.anim.scale_up)
        val scaleDown = AnimationUtils.loadAnimation(context,R.anim.scale_down)

        binding.testImageButton.setOnClickListener {

            binding.textviewFirst.text = (binding.textviewFirst.text.toString().toInt() + (binding.clickPower.text.toString().toInt())).toString()
            binding.testImageButton.startAnimation(scaleUp)
            binding.testImageButton.startAnimation(scaleDown)

        }

        binding.btnPaivitukset.setOnClickListener {

            val db = DBhelper(requireActivity(), null)

            val amount = binding.textviewFirst.text.toString().toInt()
            val power = binding.clickPower.text.toString().toInt()
            db.addBoth(amount, power)

            Toast.makeText(requireActivity(), amount.toString(), Toast.LENGTH_SHORT).show()
            binding.btnPaivitukset.startAnimation(scaleUp)
            binding.btnPaivitukset.startAnimation(scaleDown)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.button2.setOnClickListener {

            val db = DBhelper(requireActivity(), null)

            val amount = binding.textviewFirst.text.toString().toInt()
            val power = binding.clickPower.text.toString().toInt()
            db.addBoth(amount, power)

            findNavController().navigate(R.id.action_FirstFragment_to_thirdFragment)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}