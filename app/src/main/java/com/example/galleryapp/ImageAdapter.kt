package com.example.galleryapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.galleryapp.databinding.RowCustomRecyclerItemBinding


class ImageAdapter(
    private val context: Context,
    private var allPictures: ArrayList<Image>
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

   inner class ViewHolder(var binding: RowCustomRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCustomRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImage = allPictures[position]


        with(holder){
            with(allPictures[position]){
                Glide.with(context)
                    .load(currentImage.imagePath)
                    .apply(RequestOptions().centerCrop())
                    .into(binding.rowImage1)
            }
        }


        holder.binding.rowImage1?.setOnClickListener {
            val intent = Intent(context,ImageFullActivity::class.java)
            intent.putExtra("path",currentImage.imagePath)
            intent.putExtra("name",currentImage.imageName)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = allPictures.size


}
