package robusta.task.captureweather.history

import androidx.recyclerview.widget.DiffUtil

data class ImagePath(val path: String) {
    companion object : DiffUtil.ItemCallback<ImagePath>() {
        override fun areItemsTheSame(oldItem: ImagePath, newItem: ImagePath) = oldItem == newItem
        override fun areContentsTheSame(oldItem: ImagePath, newItem: ImagePath) = oldItem == newItem
    }
}