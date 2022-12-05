package com.example.clickergame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.clickergame.databinding.FragmentSecondBinding
import kotlin.math.ceil
import kotlin.math.round

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val db = DBhelper(requireActivity(), null)
        val cursor = db.getPower()
        val cursor2 = db.getTotal()
        val scaleUp = AnimationUtils.loadAnimation(context,R.anim.scale_up)
        val scaleDown = AnimationUtils.loadAnimation(context,R.anim.scale_down)
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)

        if (cursor2 != null && cursor2.moveToLast()) {
            cursor2.moveToLast()

            binding.moneyView.text =
                cursor2.getString(cursor2.getColumnIndex(DBhelper.TOTAL)).toString()
            cursor2.close()
        }

        if (cursor != null && cursor.moveToLast()) {
            cursor.moveToLast()

            binding.powerView.text =
                cursor.getString(cursor.getColumnIndex(DBhelper.CLICK_POWER)).toString()
            cursor.close()
        }

        val price = (binding.powerView.text.toString().toInt()) + round((0.05 * binding.powerView.text.toString().toInt())).toInt()
        binding.priceView.text = price.toString()

        binding.morePower.setOnClickListener {

            val price = (binding.powerView.text.toString().toInt()) + round((0.05 * binding.powerView.text.toString().toInt())).toInt()
            val currentMoney = binding.moneyView.text.toString().toInt()
            if (currentMoney > price) {
                binding.powerView.text = (binding.powerView.text.toString().toInt() + 1).toString()

                binding.moneyView.text =
                    (binding.moneyView.text.toString().toInt() - price).toString()


                binding.priceView.text = price.toString()

                binding.powerView.startAnimation(scaleUp)
                binding.powerView.startAnimation(scaleDown)
                binding.moneyView.startAnimation(scaleUp)
                binding.moneyView.startAnimation(scaleDown)
                binding.priceView.startAnimation(scaleUp)
                binding.priceView.startAnimation(scaleDown)
            }
            else {
                binding.moneyView.startAnimation(shake)

                //Toast.makeText( requireActivity(),"Click more", Toast.LENGTH_SHORT).show()
            }

        }


        binding.buttonSecond.setOnClickListener {

            val db = DBhelper(requireActivity(), null)

            val amountPower = binding.powerView.text.toString().toInt()

            val amountMoney = binding.moneyView.text.toString().toInt()

            db.addBoth(amountMoney, amountPower)

          // Toast.makeText(requireActivity(), amountPower.toString(), Toast.LENGTH_SHORT).show()



            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}