package Adapter

import ModelClass.TransModel
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alpesh1.budgettracker.R.*
import com.alpesh1.budgettracker.R.id.update
import com.alpesh1.budgettracker.databinding.TransItemBinding
import com.google.android.material.R

class TransListAdapter(update: (TransModel) -> Unit,delete:(Int)->Unit) : RecyclerView.Adapter<TransListAdapter.TransListHolder>() {

    var delete = delete

    var update = update

    var translist = ArrayList<TransModel>()

    lateinit var context: Context

    class TransListHolder(itemView: TransItemBinding) : ViewHolder(itemView.root) {

        var binding = itemView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransListHolder {

        var binding = TransItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransListHolder(binding)
    }

    override fun getItemCount(): Int {

        return translist.size
    }

    override fun onBindViewHolder(
        holder: TransListHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {

        holder.binding.apply {
            translist.get(position).apply {

                txtCategory.text = category
                txtNotes.text = notes
                txtAmount.text = amount.toString()

                if (isexpense == 0) {
                    txtAmount.setTextColor(Color.BLACK)
                    round.setImageResource(drawable.down_arrow)
                    roundBack.setImageResource(drawable.up_arrow_design)
                } else {
                    txtAmount.setTextColor(Color.RED)
                    round.setImageResource(drawable.up_arrow)
                    roundBack.setImageResource(drawable.up_arrow_design2)
                }
            }

        }

        holder.itemView.setOnLongClickListener(object : OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {

                var popupMenu = PopupMenu(context, holder.itemView)
                popupMenu.menuInflater.inflate(menu.trans_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {

                        if (p0?.itemId ==id.update){
                            update.invoke(translist.get(position))
                        }

                        if (p0?.itemId == id.delete){

                            delete.invoke(translist.get(position).id)
                        }

                        return true
                    }

                })

                popupMenu.show()

                return true
            }

        })
    }

    fun setTrans(translist: ArrayList<TransModel>) {
        this.translist = translist
    }

    fun updateData(transaction: java.util.ArrayList<TransModel>) {
        translist = translist
        notifyDataSetChanged()
    }
}