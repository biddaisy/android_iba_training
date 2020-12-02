package na.severinchik.iba_kotlin_lesson7

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import na.severinchik.iba_kotlin_lesson7.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<DummyItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)

        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnCreateContextMenuListener {
        init {
            view.setOnCreateContextMenuListener(this)
        }

        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu?,
            view: View?,
            contextMenuInfo: ContextMenu.ContextMenuInfo?
        ) {

            val inflater = MenuInflater(context)
            inflater.inflate(R.menu.main_activity_optional_menu, contextMenu)
            contextMenu?.add(Menu.NONE, R.id.context_menu_select, adapterPosition, "Select")
        }

    }
}