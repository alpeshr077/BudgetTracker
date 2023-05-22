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
import androidx.drawerlayout.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alpesh1.budgettracker.databinding.TransItemBinding

class TransListAdapter(update:(TransModel)-> Unit) : RecyclerView.Adapter<TransListAdapter.TransListHolder>() {

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

    override fun onBindViewHolder(holder: TransListHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.binding.apply {
            translist.get(position).apply {

                txtCategory.text = category
                txtNotes.text = notes
                txtAmount.text = amount.toString()

                if (isexpense == 0) {
                    txtAmount.setTextColor(Color.BLACK)
                    round.setImageResource(com.alpesh1.budgettracker.R.drawable.down_arrow)
                    roundBack.setImageResource(com.alpesh1.budgettracker.R.drawable.up_arrow_design)
                } else {
                    txtAmount.setTextColor(Color.RED)
                    round.setImageResource(com.alpesh1.budgettracker.R.drawable.up_arrow)
                    roundBack.setImageResource(com.alpesh1.budgettracker.R.drawable.up_arrow_design2)
                }
            }

        }

        holder.itemView.setOnLongClickListener(object : OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {

                var popupMenu = PopupMenu(context, holder.itemView)
                popupMenu.menuInflater.inflate(
                    com.alpesh1.budgettracker.R.menu.trans_menu,
                    popupMenu.menu
                )

                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {

                        if (p0?.itemId == com.alpesh1.budgettracker.R.id.update){
                            update.invoke(translist.get(position))
                        }

                        if (p0?.itemId == com.alpesh1.budgettracker.R.id.delete){

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
}