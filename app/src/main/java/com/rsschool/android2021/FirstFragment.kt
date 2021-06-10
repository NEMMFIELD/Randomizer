package com.rsschool.android2021

import android.R.attr.button
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment


class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var interFace :ChangeFragments
    var min:EditText? = null
    var max:EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        interFace = context as ChangeFragments
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        generateButton?.isEnabled = false
        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"
         min  = view.findViewById<EditText>(R.id.min_value)
         max = view.findViewById<EditText>(R.id.max_value)

       // Button.isEnabled = false
        val editTexts = listOf(min, max)
        for (editText in editTexts) {
            editText?.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val et1 = min?.text.toString()
                    val et2 = max?.text.toString()
                    generateButton?.isEnabled = checkInput(et1,et2)
                }
                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun afterTextChanged(
                    s: Editable) {
                }
            })
        }
        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            interFace.toSecond(min?.text.toString().toInt(),max?.text.toString().toInt())
        }
    }
   private fun checkInput(min:String,max:String):Boolean {
        if (min == "") return false
        if (max == "") return false
        if (min.toLong() > 2147483647) return false
        if (max.toLong() > 2147483647) return false
        if (min.toInt() >= max.toInt()) return false
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }
        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}