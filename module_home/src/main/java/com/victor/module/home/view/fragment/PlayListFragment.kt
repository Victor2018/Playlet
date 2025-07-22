package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import com.victor.lib.common.base.BaseFragment
import com.victor.module.home.databinding.FragmentPlayListBinding

class PlayListFragment : BaseFragment<FragmentPlayListBinding>(FragmentPlayListBinding::inflate) {

    companion object {
        fun newInstance(): PlayListFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): PlayListFragment {
            val fragment = PlayListFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }


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
    }

    private fun initData() {
    }
}