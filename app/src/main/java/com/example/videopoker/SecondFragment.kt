package com.example.videopoker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.navigation.fragment.findNavController
import com.example.videopoker.databinding.FragmentFirstBinding
import com.example.videopoker.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private final val NUM_HAND : Int = 5
    private var m_numChange : Int = 0
    private var m_changeIdx : MutableList<Int> = mutableListOf()
    private var m_Game : VideoPoker = VideoPoker()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arrCheckBox : Array<CheckBox> = emptyArray()

        arrCheckBox += binding.checkBoxFirst1
        arrCheckBox += binding.checkBoxSecond1
        arrCheckBox += binding.checkBoxThird1
        arrCheckBox += binding.checkBoxFourth1
        arrCheckBox += binding.checkBoxFifth1

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.btnGenerator1.setOnClickListener {
            val changeIdx = mutableListOf<Int>()
            changeIdx.addAll(m_changeIdx)
            val numChange = m_numChange

            for(i in 0..numChange-1){
                arrCheckBox[changeIdx[i]].isChecked = false
            }

            this.m_Game.GenerateCARD()
            this.m_Game.PrintHand()
            for(i in 0..(NUM_HAND-1)) {
                arrCheckBox[i].text = this.m_Game.PrintHandForString(i)
            }
            binding.textViewResult1.text = this.m_Game.PrintResultForString()
            binding.textViewTest.text = "Generate"

        }
        binding.btnApprove1.setOnClickListener{
            this.m_Game.ReplaceChangeHandIdx(m_changeIdx, m_numChange)
            this.m_Game.PrintHand()

            for(i in 0..(NUM_HAND-1)) {
                arrCheckBox[i].text = this.m_Game.PrintHandForString(i)
            }
            binding.textViewResult1.text = this.m_Game.PrintResultForString()
        }
        binding.btnAutoChange1.setOnClickListener{
            val changeIdx = mutableListOf<Int>()
            changeIdx.addAll(m_changeIdx)
            val numChange = m_numChange

            for(i in 0..numChange-1){
                arrCheckBox[changeIdx[i]].isChecked = false
            }

            val (change, tempChangeNum)  = this.m_Game.ChangeHandIdx()
            for(i in 0..(tempChangeNum-1)){
                arrCheckBox[change[i]].isChecked = true
            }
            m_changeIdx.sort()
            changeIdx.sort()
            if(m_numChange == numChange) {
                if(changeIdx.equals(m_changeIdx)) {
                    binding.textViewTest.text = "Correct"
                }
                else{
                    binding.textViewTest.text = "Incorrect1"
                }
            }
            else{
                binding.textViewTest.text = "Incorrect2"
            }
        }

        for(i in 0..NUM_HAND-1) {
            arrCheckBox[i].setOnCheckedChangeListener { compoundButton, b ->
                println(i)
                println(b)
                if (b) {
                    m_changeIdx.add(i)
                    m_numChange++
                } else {
                    m_changeIdx.remove(i)
                    m_numChange--
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}