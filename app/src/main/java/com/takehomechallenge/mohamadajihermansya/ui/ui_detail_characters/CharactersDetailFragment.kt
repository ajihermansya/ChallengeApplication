package com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.takehomechallenge.mohamadajihermansya.data.network.apiservive.DependencyProvider
import com.takehomechallenge.mohamadajihermansya.R
import com.takehomechallenge.mohamadajihermansya.databinding.CharactersDetailFragmentBinding
import kotlinx.coroutines.launch
import com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.adapter.AdapterCharacters
import com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.view_model.ViewModelCharacter
import com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.view_model.ViewModelFactoryCharacter

class CharactersDetailFragment : DialogFragment() {

    private lateinit var viewModel: ViewModelCharacter
    private lateinit var viewModelFactory: ViewModelFactoryCharacter
    private lateinit var binding: CharactersDetailFragmentBinding
    private val adapter = AdapterCharacters()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = CharactersDetailFragmentBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
        viewModelFactory = DependencyProvider.CharactersViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window!!.setLayout(width, height)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.apply {
            characterSlider.setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            LinearSnapHelper().attachToRecyclerView(characterSlider)
            characterSlider.layoutManager = linearLayoutManager
            val itemTouchHelper = ItemTouchHelper(swipeBack(dialog))
            itemTouchHelper.attachToRecyclerView(characterSlider)
            characterSlider.adapter = adapter

            return root
        }
    }

    class swipeBack(private val dialog: Dialog?): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP or ItemTouchHelper.DOWN){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            target: ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            dialog?.cancel()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ViewModelCharacter::class.java]

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.fetchResults(
                requireArguments().getInt(
                    resources.getString(R.string.character_id)
                )
            ).observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

        }

        adapter.setFlow(viewModel.episodesFlow, viewLifecycleOwner)

        adapter.onItemBind {
            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.fetchEpisodes(it)
            }
        }

        adapter.onShareButton {
            val shareIntent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://rickmortyviewer.com/character/$it")
                type = "text/plain"
            }
            startActivity(shareIntent)
        }
    }
}
