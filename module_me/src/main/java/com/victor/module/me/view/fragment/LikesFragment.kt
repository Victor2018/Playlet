package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.local.vm.DramaVM
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.me.databinding.FragmentLikesBinding
import com.victor.module.me.view.adapter.LikesAdapter
import kotlin.getValue

class LikesFragment : BaseFragment<FragmentLikesBinding>(FragmentLikesBinding::inflate),
    OnItemClickListener {

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

    //共享使用MainActivity 的 viewmodel
    private val mDramaVM: DramaVM by activityViewModels{
        val userId = App.get().getUserInfo()?.uid ?: ""
        InjectorUtils.provideDramaVMFactory(activity as AppCompatActivity, userId)
    }

    private var mLikesAdapter: LikesAdapter? = null

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
        mLikesAdapter = LikesAdapter(context,this)
        binding.mRvDrama.adapter = mLikesAdapter

        subscribeUi()
    }

    private fun subscribeUi() {
        mDramaVM.likedDramaData.observe(this, Observer {
            showDramaData(it)
        })
    }

    private fun showDramaData(datas: List<DramaEntity>) {
        mLikesAdapter?.showData(datas,binding.mTvNoData,binding.mRvDrama)
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
        val playInfos = mLikesAdapter?.getDatas()?.map { it.dramaItem }
        App.get().mPlayInfos = playInfos as List<DramaItemInfo>?
        val playPosition = mLikesAdapter?.getItem(position)?.playPosition ?: 0
        NavigationUtils.goPlayActivity(activity as AppCompatActivity,position, playPosition)
    }

}