package com.example.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), TextWatcher, OnFocusChangeListener {
    private lateinit var editTextHexadecimal: EditText
    private lateinit var editTextBinary: EditText
    private lateinit var editTextDecimal: EditText
    private lateinit var editTextOctal: EditText
    private var currentEditText: EditText? = null

    companion object {
        private const val HEXADECIMAL = 16
        private const val BINARY = 2
        private const val DECIMAL = 10
        private const val OCTAL = 8
        private val DIGITS =
            listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListeners()

    }


    private fun initViews() {
        editTextDecimal = findViewById(R.id.et_dec)
        editTextBinary = findViewById(R.id.et_bin)
        editTextHexadecimal = findViewById(R.id.et_hex)
        editTextOctal = findViewById(R.id.et_oct)
    }

    private fun initListeners() {
        editTextDecimal.onFocusChangeListener = this
        editTextBinary.onFocusChangeListener = this
        editTextOctal.onFocusChangeListener = this
        editTextHexadecimal.onFocusChangeListener = this
    }

    private fun convertTo(number: String, fromBase: Int, toBase: Int): String {
        return if (number.isEmpty()) "" else number.toLong(fromBase).toString(toBase)
    }

    private fun isValidateCharacter(text: String, digits: List<Char>): Boolean {
        if (currentEditText?.id == editTextBinary.id && text.last() in digits.subList(
                0,
                BINARY
            )
        ) return true
        else if (currentEditText?.id == editTextOctal.id && text.last() in digits.subList(
                0,
                OCTAL
            )
        ) return true
        else if (currentEditText?.id == editTextDecimal.id && text.last() in digits.subList(
                0,
                DECIMAL
            )
        ) return true
        else if (currentEditText?.id == editTextHexadecimal.id && text.last() in digits.subList(
                0,
                HEXADECIMAL
            )
        ) return true

        return false
    }

    private fun changeTexts(number: String) {
        when (currentEditText?.id) {
            editTextBinary.id -> {
                editTextOctal.setText(convertTo(number, BINARY, OCTAL))
                editTextDecimal.setText(convertTo(number, BINARY, DECIMAL))
                editTextHexadecimal.setText(convertTo(number, BINARY, HEXADECIMAL))
            }
            editTextHexadecimal.id -> {
                editTextDecimal.setText(convertTo(number, HEXADECIMAL, DECIMAL))
                editTextOctal.setText(convertTo(number, HEXADECIMAL, OCTAL))
                editTextBinary.setText(convertTo(number, HEXADECIMAL, BINARY))
            }
            editTextOctal.id -> {
                editTextDecimal.setText(convertTo(number, OCTAL, DECIMAL))
                editTextHexadecimal.setText(convertTo(number, OCTAL, HEXADECIMAL))
                editTextBinary.setText(convertTo(number, OCTAL, BINARY))
            }
            else -> {
                editTextHexadecimal.setText(convertTo(number, DECIMAL, HEXADECIMAL))
                editTextBinary.setText(convertTo(number, DECIMAL, BINARY))
                editTextOctal.setText(convertTo(number, DECIMAL, OCTAL))

            }
        }
    }

    private fun clearAllEditTextContent() {
        when (currentEditText?.id) {
            editTextBinary.id -> {
                editTextOctal.setText("")
                editTextDecimal.setText("")
                editTextHexadecimal.setText("")
            }
            editTextHexadecimal.id -> {
                editTextDecimal.setText("")
                editTextOctal.setText("")
                editTextBinary.setText("")
            }
            editTextOctal.id -> {
                editTextDecimal.setText("")
                editTextHexadecimal.setText("")
                editTextBinary.setText("")
            }
            else -> {
                editTextHexadecimal.setText("")
                editTextBinary.setText("")
                editTextOctal.setText("")

            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text!!.isNotEmpty()) {
            if (isValidateCharacter(text.toString(), DIGITS)) {
                changeTexts(text.toString())
            } else {
                currentEditText?.setText(text.substring(0, text.length - 1))
                currentEditText?.setSelection(text.length - 1)
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_LONG).show()
            }
        } else clearAllEditTextContent()
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun onFocusChange(editText: View?, hasFocused: Boolean) {
        currentEditText?.removeTextChangedListener(this)
        currentEditText = editText as EditText?
        currentEditText?.addTextChangedListener(this)
    }
}