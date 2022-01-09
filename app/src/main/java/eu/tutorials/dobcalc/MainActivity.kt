package eu.tutorials.dobcalc

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //レイアウトからButtonのidを取得
        val btnDataPicker : Button = findViewById(R.id.btnDatePicker)

        // setOnClickListenerをbtnDatePickerに設定
        btnDataPicker.setOnClickListener {
            // btnDatePickerを押下時にclickDatePickerをコール
            clickDatePicker()
        }

    }

    // を表示する関数
    private fun clickDatePicker(){
        /**
         * デフォルトのタイムゾーンとロケールを使用したカレンダーを取得
         * 戻り値の日付は、現在時刻を基にしている
         * タイムゾーンはデフォルトのまま
         */
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // レイアウトからTextViewのidを取得
        val tvSelectedDate: TextView = findViewById(R.id.tvSelectedDate)
        val tvSelectedDateInMinutes: TextView = findViewById(R.id.tvAgeInMinutes)

        // 指定した日付のピッカー・ダイアログを親要素を使用して新規作成
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                // 選択した日付を 日/月/年 の形式に設定
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                // 選択した日付をTextViewに設定
                tvSelectedDate.text = selectedDate

                /**
                 * Date Formatterのインスタンスを作成
                 * dd/MM/yyyyの形式で渡す
                 */
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                // 選択した日付をパースしてDateオブジェクトに変換
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    // Dateオブジェクトからミリ秒単位で時刻を取得
                    val selectedDataInMinutes = theDate.time / 60000
                    // 上で使用したDateFormatterで現在の日付をパースする
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        // 現在の日付を分単位で表示
                        val currentDateToMinutes = currentDate.time / 60000

                        /**
                         * 分単位の差を求める
                         * currentDateToMinutes から selectedDateToMinutes を引く
                         */
                        val differenceInMinutes = currentDateToMinutes - selectedDataInMinutes
                        tvSelectedDateInMinutes.text = differenceInMinutes.toString()
                    }
                }
            },
            year, month, day)

        /**
         * この関数がサポートする最大の日付を設定
         *  -> タイムゾーンで1970年1月1日00:00:00からのミリ秒
         */
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}