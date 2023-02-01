package cc.anisimov.vlad.unsplashtest.ui.image_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import coil.load

class PhotosAdapter(private val onBookmarkClick: (Photo) -> Unit) :
    ListAdapter<Photo, PhotosAdapter.ViewHolder>(PhotoDiffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
        val ivBookmark: ImageView = view.findViewById(R.id.iv_bookmark)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val tvAuthorName: TextView = view.findViewById(R.id.tv_author_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_photo, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        val context = viewHolder.itemView.context
        with(viewHolder) {
            ivPhoto.load(item.url)
            tvDescription.isVisible = item.description != null
            tvDescription.text = item.description
            tvAuthorName.text = context.getString(R.string.author_name, item.authorName)
            val bookmarkDrawableId =
                if (item.isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
            ivBookmark.setImageResource(bookmarkDrawableId)
            ivBookmark.setOnClickListener {
                onBookmarkClick(item)
            }
        }
    }

    object PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
