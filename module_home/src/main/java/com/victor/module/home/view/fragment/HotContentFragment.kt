package com.victor.module.home.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.module.home.databinding.FragmentHotContentBinding
import com.victor.module.home.databinding.FragmentRecommendBinding
import com.victor.module.home.view.adapter.HotAdapter
import com.victor.module.home.view.data.HotType

class HotContentFragment : BaseFragment<FragmentHotContentBinding>(FragmentHotContentBinding::inflate),OnItemClickListener {

    companion object {
        fun newInstance(hotType: Int): HotContentFragment {
            return newInstance(0,hotType)
        }
        fun newInstance(id: Int,hotType: Int): HotContentFragment {
            val fragment = HotContentFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            bundle.putInt(Constant.INTENT_DATA_KEY, hotType)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private var mHotAdapter: HotAdapter? = null
    private var hotType = HotType.RECOMMEND

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mHotAdapter = HotAdapter(requireContext(),this)
        binding.mRvHot.adapter = mHotAdapter
    }

    private fun initData() {
        hotType = arguments?.getInt(Constant.INTENT_DATA_KEY,HotType.RECOMMEND) ?: HotType.RECOMMEND
        mHotAdapter?.hotType = hotType
        mHotAdapter?.clear()
        for (i in 0 .. 10) {
            mHotAdapter?.add("测试$i")
        }
        mHotAdapter?.notifyDataSetChanged()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
}