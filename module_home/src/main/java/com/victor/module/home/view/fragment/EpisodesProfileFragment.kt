package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.databinding.FragmentEpisodesProfileBinding

class EpisodesProfileFragment : BaseFragment<FragmentEpisodesProfileBinding>(FragmentEpisodesProfileBinding::inflate) {

    companion object {
        fun newInstance(data: DramaItemInfo?): EpisodesProfileFragment {
            return newInstance(0,data)
        }
        fun newInstance(id: Int, data: DramaItemInfo? = null): EpisodesProfileFragment {
            val fragment = EpisodesProfileFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            bundle.putSerializable(Constant.INTENT_DATA_KEY,data)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    var mDramaItemInfo: DramaItemInfo? = null

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        mDramaItemInfo = arguments?.getSerializable(Constant.INTENT_DATA_KEY) as DramaItemInfo?
        binding.mTvDescribe.text = mDramaItemInfo?.data?.description ?: ""
    }
}