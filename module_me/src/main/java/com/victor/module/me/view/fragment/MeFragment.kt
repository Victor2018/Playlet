package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.module.me.databinding.FragmentMeBinding

@Route(path = ARouterPath.MeFgt)
class MeFragment : BaseFragment<FragmentMeBinding>(FragmentMeBinding::inflate) {

    companion object {
        fun newInstance(): MeFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): MeFragment {
            val fragment = MeFragment()
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