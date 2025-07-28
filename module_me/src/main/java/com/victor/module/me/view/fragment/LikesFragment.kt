package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import com.victor.lib.common.base.BaseFragment
import com.victor.module.me.databinding.FragmentLikesBinding

class LikesFragment : BaseFragment<FragmentLikesBinding>(FragmentLikesBinding::inflate) {

    companion object {
        fun newInstance(): LikesFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): LikesFragment {
            val fragment = LikesFragment()
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