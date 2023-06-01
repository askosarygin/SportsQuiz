package com.example.wallpapers_screen_ui.screen_wallpapers_store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.Wallpaper
import com.example.wallpapers_screen_ui.databinding.RecyclerViewItemWallpaperCardBinding

class RecyclerViewAdapterScreenWallpapersStore(
    private val listOfWallpapers: List<Wallpaper>,
    private val onClickWallpaper: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterScreenWallpapersStore.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        RecyclerViewItemWallpaperCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.wallpaperImage.setImageBitmap(listOfWallpapers[position].imageResource)
        holder.binding.wallpaperImage.setOnClickListener {
            onClickWallpaper(position)
        }
    }

    override fun getItemCount(): Int = listOfWallpapers.size


    class ViewHolder(
        val binding: RecyclerViewItemWallpaperCardBinding
    ) : RecyclerView.ViewHolder(binding.root)
}