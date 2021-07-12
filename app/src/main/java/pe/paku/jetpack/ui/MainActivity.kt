package pe.paku.jetpack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pe.paku.jetpack.adapters.CharacterAdapter
import pe.paku.jetpack.databinding.ActivityMainBinding
import pe.paku.jetpack.ui.viewModels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //enlacing to UI
    private lateinit var binding: ActivityMainBinding

    //enlacing to viewModels
    private val viewModel: MainViewModel by viewModels()

    //Values to instanciate
    private lateinit var characterAdapter: CharacterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.listCharacterMediator.observe(this, Observer {
            if(it != null){
                characterAdapter.submitList(it)
            }
        })

        //Events
        //viewModel.

        //Log.d("DEMO", "Characterdaos : ${serieCharacterDao.hashCode()}")
    }

    private fun setupRecyclerView() = binding.mainRvCharacters.apply {
        characterAdapter = CharacterAdapter(viewModel)
        adapter = characterAdapter
        layoutManager = LinearLayoutManager(context)
    }


}