package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.Observer
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.interfaces.IDramaVM
import com.victor.lib.coremodel.data.local.entity.FollowingDramaEntity
import com.victor.lib.coremodel.data.local.entity.HistoryDramaEntity
import com.victor.lib.coremodel.data.local.vm.FollowingDramaVM
import com.victor.lib.coremodel.data.local.vm.HistoryDramaVM
import com.victor.module.me.databinding.FragmentFollowingBinding
import com.victor.module.me.view.adapter.FollowingAdapter

class FollowingFragment : BaseFragment<FragmentFollowingBinding>(FragmentFollowingBinding::inflate),
    OnItemClickListener {

    companion object {
        fun newInstance(): FollowingFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private var mFollowingAdapter: FollowingAdapter? = null

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
        mFollowingAdapter = FollowingAdapter(context,this)
        binding.mRvDrama.adapter = mFollowingAdapter

        subscribeUi()
    }

    private fun subscribeUi() {
        getFollowingDramaVM()?.dramaData?.observe(this, Observer {
            showDramaData(it)
        })
    }

    private fun showDramaData(datas: List<FollowingDramaEntity>) {
        mFollowingAdapter?.showData(datas,binding.mTvNoData,binding.mRvDrama)
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
    }

    private fun getFollowingDramaVM(): FollowingDramaVM? {
        if (activity is IDramaVM) {
            val parentAct = activity as IDramaVM
            return parentAct.getFollowingDramaVM()
        }
        return null
    }
}