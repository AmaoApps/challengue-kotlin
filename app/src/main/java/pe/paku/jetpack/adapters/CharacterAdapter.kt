package pe.paku.jetpack.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.paku.jetpack.R
import pe.paku.jetpack.databinding.CardCharacterBinding
import pe.paku.jetpack.db.model.SerieCharacter
import pe.paku.jetpack.ui.DetailActivity
import pe.paku.jetpack.ui.viewModels.MainViewModel

class CharacterAdapter(val viewModel: MainViewModel) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding: CardCharacterBinding)
        :RecyclerView.ViewHolder(binding.root)

    val diffCallback = object : DiffUtil.ItemCallback<SerieCharacter>() {
        override fun areItemsTheSame(oldItem: SerieCharacter, newItem: SerieCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SerieCharacter, newItem: SerieCharacter): Boolean {
           return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<SerieCharacter>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val binding = CardCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        val character = differ.currentList[position]

        with(holder){
            with(character){
                binding.cardTxtName.text = name
                binding.cardTxtNickname.text = nickName
                Glide.with(holder.itemView.context)
                    .load(pathImage)
                    .into(binding.cardIvCharacter)

                if(favorite){
                    binding.cardIvFavorite.setImageResource(R.drawable.ic_heartfilled)
                } else {
                    binding.cardIvFavorite.setImageResource(R.drawable.ic_heart)
                }

                binding.cardIvFavorite.setOnClickListener {
                    favorite = !favorite
                    if(favorite){
                        character.favorite = true
                        viewModel.saveFavorite(character)
                    } else {
                        character.favorite = false
                        viewModel.saveFavorite(character)
                    }
                    notifyDataSetChanged()
                }

                binding.cardCharacter.setOnClickListener {
                    val intent : Intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra("idCharacter", character.id)
                    intent.putExtra("nameCharacter", character.name)
                    it.context.startActivity(intent)
                }

            }
        }

    }



}