package robusta.task.captureweather.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_thumbnail.view.*
import robusta.task.captureweather.R
import java.io.File

class HistoryAdapter(private val onClick: (item: ImagePath) -> Unit) :
    ListAdapter<ImagePath, HistoryAdapter.ViewHolder>(ImagePath) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_thumbnail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), onClick)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(item: ImagePath, onClick: (item: ImagePath) -> Unit) {
            Glide.with(itemView.context)
                .load(File(item.path))
                .into(itemView.img)
            itemView.setOnClickListener { onClick(item) }
        }
    }
}