package com.franco.mytestapplication.presentation.movies

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.franco.mytestapplication.R
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.google.android.material.card.MaterialCardView

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 2:44 p.m.
 */
class MoviesAdapter (
    /**
     * A listener for listening click events.
     */
    private val onMovieInteraction: (Movie) -> Unit
    ): PagingDataAdapter<Movie, MoviesAdapter.MovieHolder>(DIFF_CALLBACK) {

        companion object {
            val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Movie>() {

                override fun areItemsTheSame(
                    oldItem: Movie,
                    newItem: Movie
                    // Check if items are the same by comparing its id.
                ) = oldItem.tmdbId == newItem.tmdbId

                // We will update all the content of item.
                override fun getChangePayload(
                    oldItem: Movie,
                    newItem: Movie
                ): Any? {
                    return null
                }

                // Compare the content of items
                override fun areContentsTheSame(
                    oldItem: Movie,
                    newItem: Movie
                ): Boolean {
                    return when {
                        oldItem.title != newItem.title ||
                                oldItem.overview != newItem.overview -> false
                        else -> true
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = run {
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_movie, parent, false).run {
                    MovieHolder(this)
                }
        }

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            getItem(position)?.let {
                holder.bind(it)
            }
        }

        /**
         * Holder class
         */
        inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            private val preview: ImageView = itemView.findViewById(R.id.im_preview_movie)
            private val title: TextView = itemView.findViewById(R.id.tv_title_movie)
            private val description: TextView = itemView.findViewById(R.id.tv_description_movie)
            private val progressLoadingPreview: ProgressBar = itemView.findViewById(R.id.pb_image_progress_loading)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(movie: Movie) {
                title.text = movie.title
                description.text = movie.overview
                Glide
                    .with(preview)
                    // Is required load resource as bitmap to use Palette class.
                    .asBitmap()
                    .load(if (movie.urlImage.isEmpty()) null else movie.urlImage)
                    // Cache the images
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressLoadingPreview.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // Once resource is ready we will rely on Palette class to extract the
                            // dominant color and apply as a background
                            val p = Palette.from(resource!!).generate()
                            val colorPalette = p.getDominantColor(
                                ContextCompat.getColor(
                                    itemView.context,
                                    R.color.colorDark
                                )
                            )
                            // To make the text description more readable and progress bar more visible,
                            // we will use the inverse of dominant color
                            val negativeColor = colorPalette xor 0x00ffffff
                            progressLoadingPreview.indeterminateTintList = ColorStateList.valueOf(negativeColor)
                            (itemView as MaterialCardView).setCardBackgroundColor(colorPalette)
                            description.setTextColor(negativeColor)
                            return false
                        }
                    })
                    .error(R.drawable.ic_placeholder)
                    .thumbnail(0.2f)
                    .into(preview)
            }

            override fun onClick(v: View?) {
                onMovieInteraction(getItem(bindingAdapterPosition)!!)
            }
        }
}