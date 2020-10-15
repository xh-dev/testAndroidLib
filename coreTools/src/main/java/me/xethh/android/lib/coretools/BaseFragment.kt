package me.xethh.android.lib.coretools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<V : ViewModel, B : ViewDataBinding>() : Fragment() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

    protected lateinit var viewModel:V
    protected lateinit var binding:B
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = ViewModelProvider(this).get(viewModelClass())
        binding = binding(inflater)
        binding.lifecycleOwner=this
        binding.setVariable(vmBinding(), viewModel)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady()
    }
    abstract fun onViewReady():Unit

    abstract fun viewModelClass():Class<V>
    abstract fun binding(inflater: LayoutInflater):B

    abstract fun vmBinding():Int
}