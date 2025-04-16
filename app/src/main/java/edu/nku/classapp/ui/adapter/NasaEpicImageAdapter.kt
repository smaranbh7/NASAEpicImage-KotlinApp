package edu.nku.classapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.nku.classapp.R
import edu.nku.classapp.data.NasaEpicApi
import edu.nku.classapp.databinding.ImageCardViewBinding
import edu.nku.classapp.model.NasaEpicImageResponse
import java.text.SimpleDateFormat
import java.util.Locale

class NasaEpicImageAdapter(
    private val onImageClicked: (image: NasaEpicImageResponse.EpicImageItem, position: Int) -> Unit,
) : RecyclerView.Adapter<NasaEpicImageAdapter.NasaEpicImageViewHolder>() {

    class NasaEpicImageViewHolder(
        private val binding: ImageCardViewBinding, // Updated binding
        private val onImageClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onImageClicked(adapterPosition)
            }
        }

        fun bind(epicImage: NasaEpicImageResponse.EpicImageItem) {
            binding.imageIdentifier.text =
                binding.root.context.getString(R.string.image_identifier, epicImage.identifier)

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val date = dateFormatter.parse(epicImage.date)
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
            val formattedDate = outputFormat.format(date!!)

            binding.imageDate.text =
                binding.root.context.getString(R.string.image_date, formattedDate)
            binding.imageVersion.text =
                binding.root.context.getString(R.string.image_version, epicImage.version)
            binding.imageCaption.text =
                binding.root.context.getString(R.string.image_caption, epicImage.caption)

            val urlDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.US)
            val formattedUrlDate = urlDateFormat.format(date)

            // Construct image URL
            val imageUrl = "${NasaEpicApi.IMAGE_BASE_URL}${formattedUrlDate}/png/${epicImage.image}.png?api_key=rdEYVPEOSdbTdk1zi3PQn0FqCxBGFbaNTR7SUAGF"

            Glide.with(binding.root).load(imageUrl).into(binding.imageView)
        }
    }

    private val nasaEpicImages = mutableListOf<NasaEpicImageResponse.EpicImageItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(images: List<NasaEpicImageResponse.EpicImageItem>) {
        nasaEpicImages.clear()
        nasaEpicImages.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NasaEpicImageViewHolder {
        val binding =
            ImageCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)  // Updated binding

        return NasaEpicImageViewHolder(binding) { position ->
            onImageClicked(nasaEpicImages[position], position)
        }
    }

    override fun getItemCount() = nasaEpicImages.size

    override fun onBindViewHolder(
        holder: NasaEpicImageViewHolder,
        position: Int
    ) {
        val image = nasaEpicImages[position]
        holder.bind(image)
    }
}