package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import com.victor.lib.common.base.BaseFragment
import com.victor.module.me.databinding.FragmentFollowingBinding

class FollowingFragment : BaseFragment<FragmentFollowingBinding>(FragmentFollowingBinding::inflate) {

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

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}