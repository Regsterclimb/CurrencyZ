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
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.di.CurrencyRepositoryProvider
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.edit_text.EditTextHelper
import com.example.currencyz.domain.model.edit_text.EditTextInteractor
import com.example.currencyz.presentation.ViewModel.CurrencyListModelFactory
import com.example.currencyz.presentation.ViewModel.CurrencyListViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers

class CurrencyFragment : Fragment(), CurrencyView {

    private val presenter = CurrencyPresenter(
        interactor = EditTextInteractor(dispatcher = Dispatchers.Default),
        mainDispatcher = Dispatchers.Main.immediate
    )

    override fun onAttach(context: Context) {
        if (context is ClickListner) {
            listner = context
        }
        super.onAttach(context)
    }

    private var listner: ClickListner? = null
    private var input: TextInputLayout? = null
    private var edit: TextInputEditText? = null
    private var changeBtn: View? = null
    private var loader: View? = null
    private var changeSuccess: TextView? = null
    private var rubText: TextView? = null

    override fun onDestroyView() {

        listner = null
        presenter.detachView()
        input = null
        edit = null
        changeBtn = null
        loader = null
        rubText = null

        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }


    private val viewModel: CurrencyListViewModel by viewModels {
        CurrencyListModelFactory(
            (requireActivity() as CurrencyRepositoryProvider).provideCurrencyRepository(),
            requireActivity().applicationContext
        )
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

        viewModel.getMyCurrency(requireArguments().getString(ARG_CURRENCY_ID))

        viewModel.loadingStateCurrency.observe(this.viewLifecycleOwner, { setLoader(it) })
        viewModel.myCurrencyLiveData.observe(this.viewLifecycleOwner, { bindUi(it, view) })
        viewModel.textWather.observe(this.viewLifecycleOwner, { lookingForText(it) })

        presenter.attachView(this)
    }

    private fun lookingForText(it: String) {
        view?.findViewById<TextView>(R.id.result)?.text = it
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

        setupListners(view, myCurrency)
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

    private fun setupListners(view: View, myCurrency: RefactoredMyCurrency) {
        changeBtn?.setOnClickListener {
            tryToChange(myCurrency.value)
        }
        view.findViewById<Button>(R.id.change_fragment_back_button).apply {
            setOnClickListener { listner?.backPressed() }
        }
    }

    private fun tryToChange(value: Double) {
        presenter.change(edit?.text.toString(), value)

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
