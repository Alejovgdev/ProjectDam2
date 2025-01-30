package com.example.spacecraft10.adapters
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacecraft10.R
import com.example.spacecraft10.data.responseSpacecraft.Results
import com.example.spacecraft10.data.responseTranslation.TranslationRepository
import com.example.spacecraft10.databinding.ItemSpacecraftBinding



// Adapter para mostrar las naves espaciales
class SpacecraftAdapter : RecyclerView.Adapter<SpacecraftAdapter.SpacecraftViewHolder>() {

    private var spacecraftList: List<Results> = listOf()
    private val apiKey = "AIzaSyCbdttg6L-kXgzAjjN5c82lS8MEN_gmfkw"

    // Actualiza la lista de datos
    fun submitList(spacecrafts: List<Results>) {
        spacecraftList = spacecrafts
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }

    // Crea una nueva vista y la devuelve como un ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacecraftViewHolder {
        val binding = ItemSpacecraftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpacecraftViewHolder(binding)
    }

    // Vuelve a llenar el ViewHolder con los datos de la nave espacial
    override fun onBindViewHolder(holder: SpacecraftViewHolder, position: Int) {
        val spacecraft = spacecraftList[position]
        holder.bind(spacecraft)
    }

    // Devuelve el número total de elementos
    override fun getItemCount(): Int {
        return spacecraftList.size
    }

    // ViewHolder que maneja los elementos del layout
    inner class SpacecraftViewHolder(private val binding: ItemSpacecraftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(spacecraft: Results) {


            // Asocia los datos con las vistas
            binding.tvNombre.text = spacecraft.name.toString()
            binding.tvAgencia.text = spacecraft.agency.name.toString()
            binding.tvDescription.text = "No hay descripcion disponible"

            val repo = TranslationRepository()

            // Recoremos todos los item de spacecraft.family y comprobamos que no es null

            for (familyItem in spacecraft.family) {
                if (!familyItem.description.isNullOrEmpty()) {
                    val textDescription = familyItem.description

                    // Si entra en este if es porque no el texto no es null y podemos proceder a llamar al metodo traducir del repo

                    repo.translateText(textDescription, "es", apiKey) { translatedText ->
                        binding.tvDescription.text = translatedText
                    }
                }
            }




            if (spacecraft.in_use){
                binding.tvInUse.setBackgroundColor(Color.GREEN)
                binding.tvInUse.text = "Se usa actualmente"
            }else{
                binding.tvInUse.setBackgroundColor(Color.RED)
                binding.tvInUse.text = "No usada actualmente"
            }

            if (spacecraft.image != null) {
                Glide.with(itemView.context)
                    .load(spacecraft.image.image_url)  // Acceder de manera segura
                    .into(binding.imageView)
            } else {
                // Opcional: Mostrar una imagen predeterminada en caso de que no haya imagen
                Glide.with(itemView.context)
                    .load(R.drawable.img)  // Una imagen predeterminada
                    .into(binding.imageView)
            }

        }
    }


}
