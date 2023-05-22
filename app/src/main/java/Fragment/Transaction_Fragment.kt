package Fragment

import Adapter.TransListAdapter
import DatabaseClass.DBHelper
import ModelClass.TransModel
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alpesh1.budgettracker.databinding.FragmentTransactionBinding
import com.alpesh1.budgettracker.databinding.UpdateDiaogBinding


class Transaction_Fragment : Fragment() {

    var translist = ArrayList<TransModel>()
    lateinit var dbHelper: DBHelper
    lateinit var adapter : TransListAdapter
    lateinit var binding: FragmentTransactionBinding
    var isexpense = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTransactionBinding.inflate(layoutInflater)

        dbHelper = DBHelper(context)
        translist = dbHelper.getTransaction()

        translist = translist.reversed() as ArrayList<TransModel>

        adapter = TransListAdapter{

            updateDialog(it)

        }
        adapter.setTrans(translist)

        binding.rcvTransList.layoutManager = LinearLayoutManager(context)
        binding.rcvTransList.adapter = adapter

        return binding.root
    }

    private fun updateDialog(transModel: TransModel) {
        var dialog = Dialog(requireContext())
        var bind = UpdateDiaogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.edtAmount.setText(transModel.amount)
        bind.edtCategory.setText(transModel.category).toString()
        bind.edtNote.setText(transModel.notes).toString()

        bind.btnSubmit.setOnClickListener {
            var amount = bind.edtAmount.text.toString().toInt()
            var category = bind.edtCategory.text.toString()
            var note = bind.edtNote.text.toString()
            var modal = TransModel(transModel.id,amount, category, note,transModel.isexpense)

            dbHelper.updateTrans(modal)
            dialog.dismiss()

            bind.edtAmount.setText("")
            bind.edtCategory.setText("")
            bind.edtNote.setText("")
        }

        dialog.show()
    }

}