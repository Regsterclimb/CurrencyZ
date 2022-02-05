package com.example.currencyz.presentation.currency_detail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.currencyz.domain.model.Constants.Constants.Companion.isEmpty
import com.example.currencyz.domain.model.Constants.Constants.Companion.wrongInput
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.edit_text.EditTextHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CurrencyFragment : Fragment(R.layout.currencies_fragment), CurrencyView {

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getMyCurrency(requireArguments().getString(ARG_CURRENCY_ID))
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        if (context is ClickListner) {
            listener = context
        }
        super.onAttach(context)
    }

    private var listener: ClickListner? = null
    private var input: TextInputLayout? = null
    private var edit: TextInputEditText? = null
    private var changeBtn: View? = null
    private var loader: View? = null
    private var changeSuccess: TextView? = null
    private var rubText: TextView? = null

    override fun onDestroyView() {

        listener = null
        input = null
        edit = null
        changeBtn = null
        loader = null
        rubText = null

        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private val viewModel: CurrencyViewModel by viewModels {
        CurrencyModelFactory(applicationContext = requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currency_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(this.viewLifecycleOwner, { setLoader(it) })
        viewModel.myCurrencyLiveData.observe(this.viewLifecycleOwner, { bindUi(it, view) })
        viewModel.resultLiveData.observe(this.viewLifecycleOwner, { lookingForText(it) })
        viewModel.isLoading.observe(this.viewLifecycleOwner, {
            setLoading(it)
        })

    }

    private fun lookingForText(it: String) {
        when (it) {
            isEmpty -> showEditTextError()
            wrongInput -> showEditTextError()
            else -> showSuccess(it)

        }
    }

    private fun setLoader(it: Boolean) {
        view?.findViewById<ProgressBar>(R.id.currency_loader)?.isVisible = it
    }

    private fun bindUi(myCurrency: RefactoredMyCurrency, view: View) {
        edit = view.findViewById(R.id.edit_text)

        input = view.findViewById(R.id.textInput)

        doOnTextChange(edit, input)

        changeBtn = view.findViewById(R.id.change_button)

        loader = view.findViewById(R.id.currency_fragment_loader)

        changeSuccess = view.findViewById(R.id.result)

        rubText = view.findViewById(R.id.result_valute)

        rubText?.text = myCurrency.charCode

        view.findViewById<TextView>(R.id.currency_name_value).text = myCurrency.name

        view.findViewById<TextView>(R.id.currency_char_code_value).text = myCurrency.charCode

        view.findViewById<TextView>(R.id.currency_num_code_value).text = myCurrency.numCode

        view.findViewById<TextView>(R.id.currency_value_value).text =
            myCurrency.value.toString()

        setupListeners(myCurrency)

        setColors(view.findViewById(R.id.currency_previous_value), myCurrency) //setColors to Курс
    }

    private fun doOnTextChange(editText: TextInputEditText?, textInputLayout: TextInputLayout?) {
        editText?.doOnTextChanged { text, _, _, _ ->
            if (EditTextHelper.countDigitAfterDot(text.toString())) {
                textInputLayout?.helperText = "формат ввода 1234.56"
            } else {
                textInputLayout?.helperText = "*required"
            }
        }
    }

    private fun setupListeners(myCurrency: RefactoredMyCurrency) {
        changeBtn?.setOnClickListener {
            tryToChange(myCurrency.value)
        }
        view?.findViewById<Button>(R.id.change_fragment_back_button)
            ?.setOnClickListener {
                listener?.backPressed()
            }
    }

    private fun tryToChange(value: Double) {
        viewModel.changeCurrency(edit?.text.toString(), value)
    }

    companion object {
        private const val ARG_CURRENCY_ID = "ARG_CURRENCY_ID"
        fun newInstance(currencyId: String): CurrencyFragment { // Fragment Instance with movieID
            val fragment = CurrencyFragment()
            fragment.arguments = bundleOf("ARG_CURRENCY_ID" to currencyId)
            return fragment
        }
    }

    private fun setColors(textView: TextView, myCurrency: RefactoredMyCurrency) { //bad TODO()
        textView.text = myCurrency.previous.toString()
        if (myCurrency.positive) {
            textView.setTextColor(Color.GREEN)
        } else {
            textView.setTextColor(Color.RED)
        }
    }

    override fun setLoading(loading: Boolean) {
        changeSuccess?.isVisible = !loading
        rubText?.isVisible = !loading
        changeBtn?.isEnabled = !loading
        loader?.isVisible = loading
    }

    override fun showEditTextError() {
        edit?.error = "Не правильный формат"
        changeSuccess?.text = ""
    }

    override fun showSuccess(resultString: String) {
        changeBtn?.isEnabled = true
        changeSuccess?.text = resultString
    }

}
