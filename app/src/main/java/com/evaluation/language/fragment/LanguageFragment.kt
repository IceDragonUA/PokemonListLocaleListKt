package com.evaluation.language.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.evaluation.R
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.databinding.LanguageLayoutBinding
import com.evaluation.language.adapter.viewholder.item.LanguageItemView
import com.evaluation.listener.FragmentListener
import com.evaluation.language.viewmodel.LanguageViewModel
import com.evaluation.utils.Run
import com.evaluation.utils.START_DELAY_MS
import com.evaluation.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
@AndroidEntryPoint
class LanguageFragment : Fragment(), AdapterItemClickListener<BaseItemView> {

    private var binding: LanguageLayoutBinding by autoCleared()

    private val viewModel: LanguageViewModel by viewModels()

    private var listener: FragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as?  FragmentListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.language_layout, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRootView()
        initLoader()
    }

    private fun initRootView() {
        binding.listView.listener = this
    }

    private fun initLoader() {
        viewModel.result.observe(viewLifecycleOwner) {
            binding.listView.adapter?.items = it
        }
    }

    override fun onClicked(item: BaseItemView) {
        when (item) {
            is LanguageItemView -> {
                viewModel.saveLanguage(item.viewItem.name).also {
                    Run.after(START_DELAY_MS) { listener?.startActivity() }
                }
            }
        }
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }

}