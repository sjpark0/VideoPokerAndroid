package com.example.videopoker

import android.os.Bundle
import android.provider.MediaStore.Video
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.navigation.fragment.findNavController
import com.example.videopoker.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arrCheckBox : Array<CheckBox> = emptyArray()

        arrCheckBox += binding.checkBoxFirst
        arrCheckBox += binding.checkBoxSecond
        arrCheckBox += binding.checkBoxThird
        arrCheckBox += binding.checkBoxFourth
        arrCheckBox += binding.checkBoxFifth

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.btnGenerator.setOnClickListener {
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
            binding.textViewResult.text = this.m_Game.PrintResultForString()

        }
        binding.btnApprove.setOnClickListener{
            this.m_Game.ReplaceChangeHandIdx(m_changeIdx, m_numChange)
            this.m_Game.PrintHand()

            for(i in 0..(NUM_HAND-1)) {
                arrCheckBox[i].text = this.m_Game.PrintHandForString(i)
            }
            binding.textViewResult.text = this.m_Game.PrintResultForString()
        }
        binding.btnAutoChange.setOnClickListener{
            val changeIdx = mutableListOf<Int>()
            changeIdx.addAll(m_changeIdx)
            val numChange = m_numChange

            for(i in 0..numChange-1){
                arrCheckBox[changeIdx[i]].isChecked = false
            }

            val (change, tempChangeNum)  = this.m_Game.ChangeHandIdx()
            println(change)
            for(i in 0..(tempChangeNum-1)){
                arrCheckBox[change[i]].isChecked = true
            }
        }
        binding.btnUserInput.setOnClickListener {
            val inputString = binding.editUserTextInput.text.split(" ")
            val changeIdx = mutableListOf<Int>()
            changeIdx.addAll(m_changeIdx)
            val numChange = m_numChange

            for(i in 0..numChange-1){
                arrCheckBox[changeIdx[i]].isChecked = false
            }

            for(i in 0..(this.NUM_HAND-1)) {
                arrCheckBox[i].text = inputString[i]
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