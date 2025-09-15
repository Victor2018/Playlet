package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Loger
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentEpisodesSelectBinding
import com.victor.module.home.view.adapter.EpisodesAdapter
import com.victor.module.home.view.adapter.EpisodesTitleAdapter

class EpisodesSelectFragment : BaseFragment<FragmentEpisodesSelectBinding>
    (FragmentEpisodesSelectBinding::inflate), AdapterView.OnItemClickListener, LMRecyclerView.OnScrollChangeListener {

    companion object {
        fun newInstance(): EpisodesSelectFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): EpisodesSelectFragment {
            val fragment = EpisodesSelectFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    var mEpisodesTitleAdapter: EpisodesTitleAdapter? = null
    var mEpisodesAdapter: EpisodesAdapter? = null

    val episodesPageSize = 30

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
        mEpisodesTitleAdapter = EpisodesTitleAdapter(context,this)
        binding.mRvTitle.adapter = mEpisodesTitleAdapter
        mEpisodesAdapter = EpisodesAdapter(context,this)
        binding.mRvEpisodes.adapter = mEpisodesAdapter
        binding.mRvEpisodes.mOnScrollChangeListener = this
    }

    private fun initData() {
        mEpisodesTitleAdapter?.clear()
        for (i in 0 until 3) {
            mEpisodesTitleAdapter?.add("${episodesPageSize * i + 1}-${episodesPageSize*(i+1)}")
        }

        mEpisodesAdapter?.notifyDataSetChanged()
        mEpisodesAdapter?.clear()
        for (i in 0 until 90) {
            mEpisodesAdapter?.add(i + 1)
        }
        mEpisodesTitleAdapter?.notifyDataSetChanged()
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
        when (v?.id) {
            R.id.mTvTitle -> {
                binding.mRvTitle.scroll2PositionCenter(position)
                mEpisodesTitleAdapter?.selectPositon = position
                mEpisodesTitleAdapter?.notifyDataSetChanged()
                val episodesIndex = episodesPageSize * position + 1
                binding.mRvEpisodes.smoothScroll2PositionTop(episodesIndex)
            }
            R.id.mTvEpisodes -> {
                mEpisodesAdapter?.selectPositon = position
                mEpisodesAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onScrollChanged(firstVisibleItem: Int) {
        Loger.i(TAG,"onScrollChanged-firstVisibleItem = $firstVisibleItem")

        var pageIndex = firstVisibleItem / episodesPageSize
        if (binding.mRvEpisodes.isScrollBottom()) {
            val episodesCount = mEpisodesAdapter?.getContentItemCount() ?: 0
            // 计算总页数
            val totalPages = (episodesCount + episodesPageSize - 1) / episodesPageSize
            pageIndex = totalPages - 1
        }
        Loger.i(TAG,"onScrollChanged-pageIndex = $pageIndex")
        if (pageIndex != mEpisodesTitleAdapter?.selectPositon) {
            binding.mRvTitle.scroll2PositionCenter(pageIndex)
            mEpisodesTitleAdapter?.selectPositon = pageIndex
            mEpisodesTitleAdapter?.notifyDataSetChanged()
        }
    }
}