package com.example.currencyz.presentation.currency_detail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.currencyz.R
import com.example.currencyz.domain.model.Constants.Constants.Companion.bigNumber
import com.example.currencyz.domain.model.Constants.Constants.Companion.isEmpty
import com.example.currencyz.domain.model.Constants.Constants.Companion.wrongInput
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.edit_text.EditTextHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CurrencyFragment : Fragment(R.layout.currency_fragment) {

    private var listener: ClickListener? = null

    private val viewModel: CurrencyViewModel by viewModels {
        CurrencyModelFactory(applicationContext = requireContext().applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getMyCurrency(requireArguments().getString(ARG_CURRENCY_ID))
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        if (context is ClickListener) {
            listener = context
        }
        super.onAttach(context)
    }

    override fun onDestroyView() {
        listener = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(this.viewLifecycleOwner)
        {
            setLoader(it, view)
        }
        viewModel.myCurrencyLiveData.observe(this.viewLifecycleOwner)
        {
            bindUi(it, view)
        }
        viewModel.resultLiveData.observe(this.viewLifecycleOwner)
        {
            lookingForText(it, view)
        }
        viewModel.resultIsLoading.observe(this.viewLifecycleOwner)
        {
            setLoading(it, view)
        }
    }

    private fun lookingForText(it: String, view: View) {
        when (it) {
            isEmpty -> resultError(isEmpty, view)
            wrongInput -> resultError(wrongInput, view)
            bigNumber -> resultError(bigNumber, view)
            else -> showSuccess(it, view)
        }
    }

    private fun setLoader(it: Boolean, view: View) {
        view.findViewById<ProgressBar>(R.id.currency_frag_loader)?.isVisible = it
    }

    private fun bindUi(myCurrency: RefactoredMyCurrency, view: View) {
        view.findViewById<TextView>(R.id.result_valute).text = myCurrency.charCode

        view.findViewById<TextView>(R.id.currency_name_value).text = myCurrency.name

        view.findViewById<TextView>(R.id.currency_char_code_value).text = myCurrency.charCode

        view.findViewById<TextView>(R.id.currency_num_code_value).text = myCurrency.numCode

        view.findViewById<TextView>(R.id.currency_value_value).text = myCurrency.value.toString()

        setupListeners(myCurrency, view)

        setColors(view.findViewById(R.id.currency_previous_value), myCurrency)

        doOnTextChange(
            view.findViewById(R.id.edit_text), view.findViewById(R.id.textInput)
        )
    }

    private fun doOnTextChange(editText: TextInputEditText?, textInputLayout: TextInputLayout?) {
        editText?.doOnTextChanged { text, _, _, _ ->
            if (EditTextHelper.countDigitAfterDot(text.toString())) {
                textInputLayout?.helperText = "формат ввода 1234.56"
            } else {
                textInputLayout?.helperText = "*Required"
            }
        }
    }

    private fun setupListeners(myCurrency: RefactoredMyCurrency, view: View) {
        view.findViewById<Button>(R.id.change_button)?.setOnClickListener {
            tryToChange(myCurrency.value, view)
        }
        view.findViewById<Button>(R.id.change_fragment_back_button)
            .setOnClickListener {
                listener?.backPressed()
            }
    }

    private fun tryToChange(value: Double, view: View) {
        viewModel.changeCurrency(
            view.findViewById<TextInputEditText>(R.id.edit_text).text.toString(),
            value
        )
    }

    private fun setColors(textView: TextView, myCurrency: RefactoredMyCurrency) {
        textView.text = myCurrency.previous.toString()
        if (myCurrency.positive) {
            textView.setTextColor(Color.GREEN)
        } else {
            textView.setTextColor(Color.RED)
        }
    }

    private fun setLoading(loading: Boolean, view: View) {
        view.findViewById<TextView>(R.id.result)?.isVisible = !loading
        view.findViewById<TextView>(R.id.result_valute)?.isVisible = !loading
        view.findViewById<Button>(R.id.change_button).isEnabled = !loading
        view.findViewById<ProgressBar>(R.id.currency_loader)?.isVisible = loading
    }

    private fun resultError(error: String, view: View) {
        view.findViewById<TextInputEditText>(R.id.edit_text)?.error = error
        view.findViewById<TextView>(R.id.result)?.text = ""
    }

    private fun showSuccess(resultString: String, view: View) {
        view.findViewById<Button>(R.id.change_button).isEnabled = true
        view.findViewById<TextView>(R.id.result)?.text = resultString
    }

    companion object {
        private const val ARG_CURRENCY_ID = "ARG_CURRENCY_ID"
        fun newInstance(currencyId: String): CurrencyFragment { // Fragment Instance with movieID
            val fragment = CurrencyFragment()
            fragment.arguments = bundleOf("ARG_CURRENCY_ID" to currencyId)
            return fragment
        }
    }

}
