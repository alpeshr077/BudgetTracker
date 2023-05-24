package Fragment

import DatabaseClass.DBHelper
import ModelClass.TransModel
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.alpesh1.budgettracker.databinding.FragmentAddBinding
import java.util.Date


class Add_Fragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    var isexpense = 0
    lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(layoutInflater)

        dbHelper = DBHelper(context)

        initView()

        return binding.root
    }

    private fun initView(){

        binding.cardIncome.setOnClickListener {
            isexpense = 0
            binding.cardIncome.setCardBackgroundColor(Color.parseColor("#FAA43A"))
            binding.cardExpense.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        binding.cardExpense.setOnClickListener {
            isexpense = 1
            binding.cardIncome.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.cardExpense.setCardBackgroundColor(Color.parseColor("#FAA43A"))
        }

        binding.btnSubmit.setOnClickListener {
            var amount = binding.edtAmount.text.toString().toInt()
            var category = binding.edtCategory.text.toString()
            var note = binding.edtNote.text.toString()
            var modal = TransModel(1,amount, category, note,isexpense)

            dbHelper.addData(modal)

            binding.edtAmount.setText("")
            binding.edtCategory.setText("")
            binding.edtNote.setText("")
        }
    }

}