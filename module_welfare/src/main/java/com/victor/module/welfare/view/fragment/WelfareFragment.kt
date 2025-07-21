package com.victor.module.welfare.view.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.module.welfare.databinding.FragmentWelfareBinding

@Route(path = ARouterPath.WelfareFgt)
class WelfareFragment : BaseFragment<FragmentWelfareBinding>(FragmentWelfareBinding::inflate) {

    companion object {
        fun newInstance(): WelfareFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): WelfareFragment {
            val fragment = WelfareFragment()
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