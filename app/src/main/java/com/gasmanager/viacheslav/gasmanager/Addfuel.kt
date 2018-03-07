package com.gasmanager.viacheslav.gasmanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.text.Spanned
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import io.realm.Realm
import io.realm.RealmResults
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern


class Addfuel : AppCompatActivity() {
    val MY_CAR = "MY_CAR"
    var nf = NumberFormat.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfuel)
        supportActionBar!!.hide()
        val mRealm: Realm = Realm.getDefaultInstance()
        val sp1 = getSharedPreferences(MY_CAR, Context.MODE_PRIVATE)
        val date = Date()
        val cal = Calendar.getInstance()
        cal.time = date
        val month = cal.get(Calendar.MONTH)
        val currency = getResources().getString(R.string.currency)
        val currencyru = getResources().getString(R.string.currencyru)

        var total = 0.0
        val odo = findViewById(R.id.odo) as EditText
        val price = findViewById(R.id.price) as EditText
        val liters = findViewById(R.id.liters) as EditText
        val pay = findViewById(R.id.payed) as TextView

        val numberPicker1 = findViewById(R.id.numberpicker1) as com.gasmanager.viacheslav.gasmanager.ScrollableNumberPicker
        val numberPicker2 = findViewById(R.id.numberpicker2) as com.gasmanager.viacheslav.gasmanager.ScrollableNumberPicker
        val numberPicker3 = findViewById(R.id.numberpicker3) as com.gasmanager.viacheslav.gasmanager.ScrollableNumberPicker

        liters.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(3, 2)))
        price.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(4, 2)))
        odo.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(6, 1)))

        val write = findViewById(R.id.write) as ImageView
        val data: RealmResults<MyData> = mRealm.where(MyData::class.java).findAll()
        val id = data.size
        var minodo = "1"
        var md = MyData(0, 0, 0.0, 0.0, 0.0, 0.0, 0.0)
        if (id > 0) {
            md = data.get(id - 1)
            minodo = DecimalFormat("#0.0").format(md.getOdometer() + 1)
            val lastliter = DecimalFormat("#0.00").format(md.getLiters())
            val lastprice = DecimalFormat("#0.00").format(md.getPrice())

            numberPicker3.setMinValue(nf.parse(minodo).toDouble())
            numberPicker1.setValue(nf.parse(lastliter).toDouble())
            numberPicker2.setValue(nf.parse(lastprice).toDouble())

            odo.setText(minodo)
            liters.setText(lastliter)
            price.setText(lastprice)
            pay.setText(currency + DecimalFormat("#0.00").format(md.paid) + currencyru)
        } else {
            minodo = "1.0"
            val lastliter = "0.00"
            val lastprice = "0.00"

            numberPicker3.setMinValue(1.0)
            numberPicker1.setValue(0.0)
            numberPicker2.setValue(0.0)

            odo.setText(minodo)
            liters.setText(lastliter)
            price.setText(lastprice)
            pay.setText(currency + lastprice + currencyru)
        }

        numberPicker1.setStepSize(0.01)
        numberPicker2.setStepSize(0.01)
        numberPicker3.setStepSize(1.0)

        numberPicker1.setListener {
            val pick1 = numberPicker1.getValue()
            liters.setText(String.format("%.2f", pick1).replace(',', '.'))
            total = numberPicker2.getValue() * pick1
            pay.setText(DecimalFormat(currency + "#0.00").format(total) + currencyru)

        }

        numberPicker2.setListener {
            val pick2 = numberPicker2.getValue()
            price.setText(String.format("%.2f", pick2).replace(',', '.'))
            total = pick2 * numberPicker1.getValue()
            pay.setText(currency + DecimalFormat("#0.00").format(total) + currencyru)
        }

        numberPicker3.setListener {
            val pick3 = numberPicker3.getValue()
            odo.setText(String.format("%.1f", pick3).replace(',', '.'))
        }
        liters.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    total = liters.getText().toString().replace(',', '.').toDouble() * price.getText().toString().replace(',', '.').toDouble()
                    pay.setText(currency + DecimalFormat("#0.00").format(total).replace(',', '.') + currencyru)
                }
                return false
            }
        })
        price.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    total = liters.getText().toString().replace(',', '.').toDouble() * price.getText().toString().replace(',', '.').toDouble()
                    pay.setText(currency + DecimalFormat("#0.00").format(total).replace(',', '.') + currencyru)
                }
                return false
            }
        })


        write.setOnClickListener {

            val currentdate = date.getTime()
            var newodo = DecimalFormat("#0.0").format(odo.getText().toString().replace(',', '.').toDouble())
            if (nf.parse(odo.getText().toString()).toDouble() <= nf.parse(minodo).toDouble())
                newodo = DecimalFormat("#0.0").format(numberPicker3.value).replace(',', '.')
            val newodo1 = newodo.replace(',', '.').toDouble()
            val distance = newodo1 - md.getOdometer()
            val newliters = liters.getText().toString().replace(',', '.').toDouble()
            val newprice = price.getText().toString().replace(',', '.').toDouble()
            val newtotal = DecimalFormat("#0.00").format(newliters * newprice).replace(',', '.').toDouble()


            val newmd = MyData(id.toLong(), currentdate, newodo1, distance, newliters, newprice, newtotal)
            val ok = sp1.edit()
            ok.putInt("month", month)
            ok.putInt("id", id)
            ok.apply()

            mRealm.beginTransaction()
            mRealm.insert(newmd)
            mRealm.commitTransaction()
            mRealm.close()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    inner class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) : InputFilter {

        internal var mPattern: Pattern

        init {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
        }

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {

            val matcher = mPattern.matcher(dest)
            return if (!matcher.matches()) "" else null
        }

    }
}
