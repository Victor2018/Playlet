package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.module.home.databinding.FragmentHomeBinding

@Route(path = ARouterPath.HomeFgt)
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    companion object {
        fun newInstance(): HomeFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): HomeFragment {
            val fragment = HomeFragment()
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


    }
}