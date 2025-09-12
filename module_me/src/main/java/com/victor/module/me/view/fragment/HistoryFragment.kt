package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.local.vm.DramaVM
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.me.databinding.FragmentHistoryBinding
import com.victor.module.me.view.adapter.HistoryAdapter
import kotlin.getValue

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate),OnItemClickListener {

    companion object {
        fun newInstance(): HistoryFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): HistoryFragment {
            val fragment = HistoryFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    //共享使用MainActivity 的 viewmodel
    private val mDramaVM: DramaVM by activityViewModels{
        val userId = App.get().getUserInfo()?.uid ?: ""
        InjectorUtils.provideDramaVMFactory(activity as AppCompatActivity, userId)
    }

    private var mHistoryAdapter: HistoryAdapter? = null

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mHistoryAdapter = HistoryAdapter(context,this)
        binding.mRvDrama.adapter = mHistoryAdapter

        subscribeUi()
    }

    private fun subscribeUi() {
        mDramaVM.historyDramaData.observe(this, Observer {
            if (it.size > 12) {
                val item = it[it.size - 1]
                item.isHistory = false
                mDramaVM.insert(item)
            }
            showDramaData(it)
        })
    }

    private fun showDramaData(datas: List<DramaEntity>) {
        mHistoryAdapter?.showData(datas,binding.mTvNoData,binding.mRvDrama)
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
    }

}