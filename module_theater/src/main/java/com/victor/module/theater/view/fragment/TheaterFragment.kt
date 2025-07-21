package com.victor.module.theater.view.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.module.theater.databinding.FragmentTheaterBinding

@Route(path = ARouterPath.TheaterFgt)
class TheaterFragment : BaseFragment<FragmentTheaterBinding>(FragmentTheaterBinding::inflate) {

    companion object {
        fun newInstance(): TheaterFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): TheaterFragment {
            val fragment = TheaterFragment()
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