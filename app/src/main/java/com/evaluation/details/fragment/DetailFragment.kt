package com.evaluation.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.evaluation.R
import com.evaluation.databinding.DetailLayoutBinding
import com.evaluation.fragment.BaseFragment
import com.evaluation.pokemons.model.item.view.language.LanguageNameView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonInfo
import com.evaluation.utils.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private var binding: DetailLayoutBinding by autoCleared()

    private var language: String = emptyString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreInstance(savedInstanceState)
    }

    private fun restoreInstance(savedInstanceState: Bundle?) {
        val language = savedInstanceState?.getString(LANGUAGE)
        if (language != null) {
            this.language = language
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LANGUAGE, language)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (language.isEmpty()) language = DetailFragmentArgs.fromBundle(requireArguments()).language
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRootView(DetailFragmentArgs.fromBundle(requireArguments()))
    }

    private fun initRootView(fromBundle: DetailFragmentArgs) {
        binding.image.loadFromUrl(fromBundle.item.front_default)
        binding.weightValue.initText(fromBundle.item.weight.toString())
        binding.heightValue.initText(fromBundle.item.height.toString())
        binding.experienceValue.initText(fromBundle.item.experience.toString())
        bindInfo(fromBundle, language)
    }

    override fun categorySwitched(category: String) {}

    private fun bindInfo(fromBundle: DetailFragmentArgs, language: String) {
        bindItem(list((fromBundle.item.stats as List<PokemonInfo>), language), binding.statsValue)
        bindItem(list((fromBundle.item.abilities as List<PokemonInfo>), language), binding.abilitiesValue)
        bindItem(list((fromBundle.item.types as List<PokemonInfo>), language), binding.typesValue)
    }

    private fun list(list: List<PokemonInfo>, language: String): List<LanguageNameView?> {
        return list
            .filter { it.names().find { name -> name.language.name == language }?.name != null }
            .map { it.names().find { name -> name.language.name == language } }
    }

    private fun bindItem(list: List<LanguageNameView?>, view: TextView) {
        if (list.isNotEmpty())
            view.initText(list.joinToString { it?.name ?: emptyString() }) else
            view.initText(emptyString())
    }

}