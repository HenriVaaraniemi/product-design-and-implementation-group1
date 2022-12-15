package com.example.clickergame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.clickergame.databinding.FragmentThirdBinding
import kotlin.math.round

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    _binding = FragmentThirdBinding.inflate(inflater, container, false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbH = DBhelper(requireActivity(),null)

        val priceCursor = dbH.getPrice()

        val attackCursor = dbH.getAttack()

        val cursor2 = dbH.getTotal()

        val cursor = dbH.getPower()

        val scaleUp = AnimationUtils.loadAnimation(context,R.anim.scale_up)
        val scaleDown = AnimationUtils.loadAnimation(context,R.anim.scale_down)
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)

        binding.powerView.visibility = View.INVISIBLE

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

        binding.upPower.text = binding.powerView.text

        if (priceCursor != null && priceCursor.moveToLast()) {
            priceCursor.moveToLast()

            binding.textView2.text =
                priceCursor.getString(priceCursor.getColumnIndex(DBhelper.PRICE)).toString()
            priceCursor.close()
        }

        if (attackCursor != null && attackCursor.moveToLast()) {
            attackCursor.moveToLast()

            binding.textView3.text =
                attackCursor.getString(attackCursor.getColumnIndex(DBhelper.ATTACK_POWER)).toString()
            attackCursor.close()
        }

        binding.plusButton.setOnClickListener {
            val price = binding.textView2.text.toString().toInt()
            val currentMoney = binding.moneyView.text.toString().toInt()
            val db = DBhelper(requireActivity(),null)

            if (currentMoney > price) {

                binding.moneyView.text = (currentMoney - price).toString()
                binding.textView3.text = (binding.textView3.text.toString().toInt() + 5).toString()
                binding.textView2.text = (binding.textView2.text.toString().toInt() + ((2 * price))).toString()

                val amountMoney = binding.moneyView.text.toString().toInt()
                val amountPower = binding.powerView.text.toString().toInt()
                val prosentti = binding.textView3.text.toString().toDouble().div(100)
                val bigMath = (binding.powerView.text.toString().toDouble() * prosentti)
                binding.upPower.text= (bigMath.toInt() + amountPower).toString()
                val newPower = binding.upPower.text.toString().toInt()

                db.addBoth(amountMoney,newPower)

                binding.textView3.startAnimation(scaleUp)
                binding.textView3.startAnimation(scaleDown)
            }
            else {
                binding.moneyView.startAnimation(shake)

                Toast.makeText( requireActivity(),"Click more", Toast.LENGTH_SHORT).show()
            }

        }


    binding.backButton.setOnClickListener {
        val db = DBhelper(requireActivity(),null)
        val amountPrice = binding.textView2.text.toString().toInt()
        val amountAttack = binding.textView3.text.toString().toInt()

        db.updateAttack(amountAttack, amountPrice)
        findNavController().navigate(R.id.action_thirdFragment_to_FirstFragment)
    }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}