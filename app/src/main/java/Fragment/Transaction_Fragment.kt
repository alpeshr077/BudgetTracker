package Fragment

import Adapter.TransListAdapter
import DatabaseClass.DBHelper
import ModelClass.TransModel
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alpesh1.budgettracker.databinding.FragmentTransactionBinding
import com.alpesh1.budgettracker.databinding.UpdateDiaogBinding
import java.lang.Exception


class Transaction_Fragment : Fragment() {

    var translist = ArrayList<TransModel>()
    lateinit var dbHelper: DBHelper
    lateinit var adapter: TransListAdapter
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

        adapter = TransListAdapter({

            updateDialog(it)

        }, {

            deleteTrans(it)
        })
        adapter.setTrans(translist)

        binding.rcvTransList.layoutManager = LinearLayoutManager(context)
        binding.rcvTransList.adapter = adapter

        return binding.root
    }

    private fun deleteTrans(it: Int) {

        var dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage("Are you want sure?")
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                    dbHelper.deleteTrans(it)

                    try {
                        adapter.updateData(dbHelper.getTransaction().reversed() as java.util.ArrayList<TransModel>)

                    }catch (e:Exception){

                    }

                }

            }).setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }

            }).create()
        dialog.show()
    }

    private fun updateDialog(transModel: TransModel) {
        var dialog = Dialog(requireContext())
        var bind = UpdateDiaogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.edtAmount.setText(transModel.amount).toString()
        bind.edtCategory.setText(transModel.category).toString()
        bind.edtNote.setText(transModel.notes).toString()

        bind.btnSubmit1.setOnClickListener {
            var amount = bind.edtAmount.text.toString().toInt()
            var category = bind.edtCategory.text.toString()
            var note = bind.edtNote.text.toString()
            var modal = TransModel(transModel.id, amount, category, note, transModel.isexpense)

            dbHelper.updateTrans(modal)
            dialog.dismiss()

            adapter.updateData(dbHelper.getTransaction().reversed() as java.util.ArrayList<TransModel>)

//            bind.edtAmount.setText("")
//            bind.edtCategory.setText("")
//            bind.edtNote.setText("")
        }

        dialog.show()
    }

}