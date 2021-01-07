package com.android.learnrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.learnrxjava.utils.Logger

class RxBindingDemo : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var editText: EditText
    private var tag:String = RxBindingDemo::class.java.simpleName+"_log"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_binding_demo)

        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textview)


        editText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Logger.logd(tag,"${s}")
                textView.setText(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        button.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                Logger.logd(tag,"onclicked")
                editText.setText("")
                textView.setText("")
            }

        })

    }
}