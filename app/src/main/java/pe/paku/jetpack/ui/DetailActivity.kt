package pe.paku.jetpack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import pe.paku.jetpack.databinding.ActivityDetailBinding
import pe.paku.jetpack.ui.viewModels.DetailViewModel


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    //enlacing to UI
    private lateinit var binding: ActivityDetailBinding

    //enlacing to viewModels
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("nameCharacter")

        val idCharacter = intent.getIntExtra("idCharacter",0)

        if(idCharacter != null){
            viewModel.loadCharactersFromAPI(idCharacter = idCharacter)
        }

        viewModel.singleCharactersFromAPI.observe(this, Observer {
            if(it != null ) {
                binding.detailName.text = it[0].name.toString()
                binding.detailTvOccupation.text = it[0].occupation.toString()
                binding.detailTvStatus.text = it[0].status.toString()
                binding.detailTvPortrayed.text = it[0].portrayed.toString()

                Glide.with(applicationContext)
                    .load(it[0].img)
                    .into(binding.detailImage)
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}