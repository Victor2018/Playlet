package com.victor.lib.common.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.R
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.view.holder.BottomViewHolder
import com.victor.lib.common.view.holder.HeaderViewHolder
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.lib.coremodel.data.remote.entity.bean.ListData
import com.victor.lib.coremodel.util.WebConfig
import org.victor.http.lib.util.JsonUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseRecycleAdapter.java
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description:
 * -----------------------------------------------------------------
 */

abstract class BaseRecycleAdapter<T,VH: RecyclerView.ViewHolder>(
    var context: Context?, var listener:AdapterView.OnItemClickListener?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var TAG = javaClass.simpleName

    companion object {
        val LOADING = 0x0001//正在加载
        val LOADING_COMPLETE = 0x0002//加载完毕
        val LOADING_END = 0x0003
    }
    var mDatas: ArrayList<T> = ArrayList()
    var mHeaderCount = 0//头部View个数
    var mBottomCount = 0//底部View个数
    var ITEM_TYPE_HEADER = 0
    var ITEM_TYPE_CONTENT = 1
    var ITEM_TYPE_BOTTOM = 2
    private var loadState = LOADING_COMPLETE//上拉加载状态

    private var isHeaderVisible = false
    private var isFooterVisible = false

    var dataPositionMap = LinkedHashMap<Int,Int>()//有序的map可以按照list选中顺序取出
    var dataMap = LinkedHashMap<Int,T?>()//有序的map可以按照list选中顺序取出

    abstract fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder?
    abstract fun onBindHeadVHolder(viewHolder: VH, data: T?, position: Int)

    abstract fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): VH
    abstract fun onBindContentVHolder(viewHolder: VH, data: T?, position: Int)

    fun setHeaderVisible(visible: Boolean) {
        isHeaderVisible = visible
        mHeaderCount = 1
        if (!isHeaderVisible) {
            mHeaderCount = 0
        }
    }

    fun setFooterVisible(visible: Boolean) {
        isFooterVisible = visible
        mBottomCount = 1
        if (!isFooterVisible) {
            mBottomCount = 0
        }
    }

    fun inflate(layoutId: Int,parent: ViewGroup): View {
        var inflater = LayoutInflater.from(context)
        return inflater.inflate(layoutId,parent, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_HEADER) {
            return onCreateHeadVHolder(parent, viewType)!!
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return onCreateContentVHolder(parent, viewType)
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return BottomViewHolder(inflate(R.layout.recyclerview_foot, parent))
        }
        return onCreateContentVHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = getItem(position)
        if (holder is HeaderViewHolder) {
            onBindHeadVHolder(holder as VH, item, position)
        } else if (holder is BottomViewHolder) {
            setFooterViewState(holder)
        } else {
            if (isHeaderVisible) {
                item = getItem(position - 1)
                onBindContentVHolder(holder as VH, item, position - 1)
            } else {
                onBindContentVHolder(holder as VH, item, position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        var ITEM_TYPE = ITEM_TYPE_CONTENT
        val dataItemCount = getContentItemCount()
        if (mHeaderCount != 0 && position < mHeaderCount) {//头部View
            ITEM_TYPE = ITEM_TYPE_HEADER
        } else if (mBottomCount != 0 && position >= mHeaderCount + dataItemCount) {//底部View
            ITEM_TYPE = ITEM_TYPE_BOTTOM
        }
        return ITEM_TYPE
    }

    fun isHeaderView(position: Int): Boolean {
        return mHeaderCount != 0 && position < mHeaderCount
    }

    fun isBottomView(position: Int): Boolean {
        return mBottomCount != 0 && position >= mHeaderCount + getContentItemCount()
    }

    fun getContentItemCount(): Int {
        return if (mDatas == null) 0 else mDatas.size
    }

    override fun getItemCount(): Int {
        return mHeaderCount + getContentItemCount() + mBottomCount
    }

    private fun setFooterViewState(bottomViewHolder: BottomViewHolder) {
        when (loadState) {
            LOADING -> {
                bottomViewHolder.progressBar?.visibility = View.VISIBLE
                bottomViewHolder.mTvTitle?.visibility = View.VISIBLE
                bottomViewHolder.mLayoutEnd?.visibility = View.GONE
            }
            LOADING_COMPLETE -> {
                bottomViewHolder.progressBar?.visibility = View.GONE
                bottomViewHolder.mTvTitle?.visibility = View.GONE
                bottomViewHolder.mLayoutEnd?.visibility = View.GONE
            }
            LOADING_END -> {
                bottomViewHolder.progressBar?.visibility = View.GONE
                bottomViewHolder.mTvTitle?.visibility = View.GONE
                bottomViewHolder.mLayoutEnd?.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    fun setLoadState(loadState: Int) {
        this.loadState = loadState
    }

    /**
     * 获取元素
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): T? {
        //防止越界
        val index = if (position >= 0 && position < mDatas.size) position else 0
        return if (mDatas == null || mDatas.size == 0) {
            null
        } else mDatas.get(index)
    }

    /**
     * 添加元素
     *
     * @param item
     */
    fun add(item: T?) {
        if (item != null) {
            mDatas.add(item)
        }
    }

    /**
     * 添加元素
     *
     * @param item
     */
    fun add(index: Int, item: T?) {
        if (item != null) {
            mDatas.add(index, item)
        }
    }

    fun add(items: List<T>?) {
        if (items != null) {
            mDatas.addAll(items)
        }
    }

    /**
     * 重置元素
     *
     * @param items
     */
    fun setDatas(items: List<T>) {
        mDatas.clear()
        add(items)
    }

    /**
     * 移除
     *
     * @param index
     */

    fun removeItem(index: Int): T? {
        if (index >= 0 && index < mDatas.size) {
            return mDatas.removeAt(index)
        }
        return null
    }

    fun replaceItem(index: Int,data: T?) {
        if (index >= 0 && index < mDatas.size) {
            if (data != null) {
                mDatas[index] = data
            }
        }
    }

    fun getDatas(): List<T>? {
        return mDatas
    }

    fun clear() {
        mDatas.clear()
    }

    fun showData(list: List<T>?) {
        clear()
        add(list)
        notifyDataSetChanged()
    }

    fun showData(list: List<T>?,mEmptyView: View?, rv: LMRecyclerView?) {
        if (list == null || list?.size == 0) {
            mEmptyView?.show()
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        mEmptyView?.hide()
        rv?.show()
        clear()
        add(list)
        notifyDataSetChanged()
    }

    fun showData(list: List<T>?,mEmptyView: List<View>?, rv: LMRecyclerView?) {
        if (list == null || list?.size == 0) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        mEmptyView?.forEach { it?.hide() }
        rv?.show()
        clear()
        add(list)
        notifyDataSetChanged()
    }
    fun showData(list: List<T>?,mEmptyView: List<View>?, rv: LMRecyclerView?,hideRv: Boolean) {
        if (list == null || list?.size == 0) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        mEmptyView?.forEach {
            it?.hide() }
        rv?.show()
        clear()
        add(list)
        notifyDataSetChanged()
    }

    fun showData (data: ListData<T>?, mEmptyView: View?, rv: LMRecyclerView?, currentPage: Int) {
        if (data == null) {
            mEmptyView?.show()
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records == null) {
            mEmptyView?.show()
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.show()
                if (!isHeaderVisible) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.hide()
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data?.records)

        var size = data?.records?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showData (data: ListData<T>?, mEmptyView: View?, rv: LMRecyclerView?, currentPage: Int, hideRv: Boolean) {
        if (data == null) {
            mEmptyView?.show()
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records == null) {
            mEmptyView?.show()
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.show()
                if (!isHeaderVisible && hideRv) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.hide()
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data?.records)

        var size = data?.records?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showData (data: ListData<T>?, mEmptyView: View?, rv: LMRecyclerView?, currentPage: Int,
                  hideRv: Boolean, footerVisible: Boolean) {
        setFooterVisible(footerVisible)

        if (data == null) {
            mEmptyView?.show()
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records == null) {
            mEmptyView?.show()
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.show()
                if (!isHeaderVisible && hideRv) {
                    rv?.hide()
                }
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.hide()
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        add(data?.records)

        var size = data?.records?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showData (data: ListData<T>?, rv: LMRecyclerView?, currentPage: Int) {
        if (data == null) {
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records == null) {
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records?.size == 0) {
            if (currentPage == 1) {
                if (!isHeaderVisible) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data?.records)

        var size = data?.records?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showData (data: List<T>?, rv: LMRecyclerView?, currentPage: Int) {
        if (data == null) {
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data?.size == 0) {
            if (currentPage == 1) {
                if (!isHeaderVisible) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data)

        var size = data?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showRecordData (data: ListData<T>?, mEmptyView: List<View>?, rv: LMRecyclerView?, currentPage: Int, hideRv: Boolean) {
        if (data == null) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records == null) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.forEach { it?.show() }
                if (!isHeaderVisible && hideRv) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.forEach { it?.hide() }
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data?.records)

        var size = data?.records?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showItemData (data: ListData<T>?, mEmptyView: List<View>?, rv: LMRecyclerView?, currentPage: Int, hideRv: Boolean) {
        if (data == null) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.items == null) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.items?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.forEach { it?.show() }
                if (!isHeaderVisible && hideRv) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.forEach { it?.hide() }
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data?.items)

        var size = data?.items?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showData (data: ListData<T>?, mEmptyView: List<View>?, rv: LMRecyclerView?, currentPage: Int) {
        if (data == null) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records == null) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data.records?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.forEach { it?.show() }
                if (!isHeaderVisible) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.forEach { it?.hide() }
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data?.records)

        var size = data?.records?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showData (data: List<T>?, mEmptyView: View?, rv: LMRecyclerView?, currentPage: Int) {
        if (data == null) {
            mEmptyView?.show()
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.show()
                if (!isHeaderVisible) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.hide()
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data)

        var size = data?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }
    fun showData (data: List<T>?, mEmptyView: View?, rv: LMRecyclerView?, currentPage: Int,hideRv: Boolean) {
        if (data == null) {
            mEmptyView?.show()
            if (!isHeaderVisible && hideRv) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.show()
                if (!isHeaderVisible && hideRv) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.hide()
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data)

        var size = data?.size ?: 0
        if (size < WebConfig.PAGE_SIZE) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    fun showData (data: List<T>?, mEmptyView: List<View>?, rv: LMRecyclerView?, currentPage: Int,pageSize: Int) {
        if (data == null) {
            mEmptyView?.forEach { it?.show() }
            if (!isHeaderVisible) {
                rv?.hide()
            }
            setFooterVisible(false)
            clear()
            notifyDataSetChanged()
            rv?.setHasMore(false)
            return
        }
        if (data?.size == 0) {
            if (currentPage == 1) {
                mEmptyView?.forEach { it?.show() }
                if (!isHeaderVisible) {
                    rv?.hide()
                }
                setFooterVisible(false)
                clear()
                notifyDataSetChanged()
                rv?.setHasMore(false)
                return
            }
        }
        mEmptyView?.forEach { it?.hide() }
        rv?.show()
        if (currentPage == 1) {
            clear()
        }
        setFooterVisible(true)
        add(data)

        var size = data?.size ?: 0
        if (size < pageSize) {
            rv?.setHasMore(false)
            setLoadState(LOADING_END)
        } else {
            rv?.setHasMore(true)
            setLoadState(LOADING)
        }
        notifyDataSetChanged()
    }

    //////////////////////////// 选择处理 start /////////////////////////////////
    fun clearAllCheck() {
        dataPositionMap.clear()
        dataMap.clear()
    }

    fun checkItem (position: Int) {
        var data = getItem(position)
        if (isItemChecked(data)) {
            unCheck(data)
        } else {
            check(data,position)
        }
    }
    fun checkItem (data: T?,position: Int) {
        if (isItemChecked(data)) {
            unCheck(data)
        } else {
            check(data,position)
        }
    }

    fun check(data: T?,position: Int) {
        var key = getCheckKey(data)
        dataPositionMap[key.hashCode()] = position
        dataMap[key.hashCode()] = data
    }
    fun check(position: Int) {
        var data = getItem(position)
        var key = getCheckKey(data)
        dataPositionMap[key.hashCode()] = position
        dataMap[key.hashCode()] = data
    }
    fun unCheck(data: T?) {
        var key = getCheckKey(data)
        dataPositionMap.remove(key.hashCode())
        dataMap.remove(key.hashCode())
    }

    fun isItemChecked (data: T?): Boolean {
        return isPositionChecked(data) || isDataChecked(data)
    }

    fun isPositionChecked(data: T?): Boolean {
        var key = getCheckKey(data).hashCode()
        var checkPosition = dataPositionMap[key] ?: -1
        return checkPosition >= 0
    }
    fun isDataChecked(data: T?): Boolean {
        var key = getCheckKey(data).hashCode()
        return dataMap[key] != null
    }

    fun checkList(datas:List<T>?) {
        datas?.forEachIndexed { index, t ->
            check(t,index)
        }
    }

    fun unCheckList(datas:List<T>?) {
        datas?.forEach {
            unCheck(it)
        }
    }

    fun getCheckCount(): Int {
        return dataPositionMap.size
    }
    fun getCheckDataCount(): Int {
        return dataMap.size
    }

    fun getCheckList(): List<T> {
        var checkList = ArrayList<T>()
        dataPositionMap.forEach {
            getItem(it.value)?.let { it1 -> checkList.add(it1) }
        }

        return checkList
    }
    fun getCheckDataList(): List<T> {
        var checkList = ArrayList<T>()
        dataMap.forEach {
            it.value?.let { it1 -> checkList.add(it1) }
        }

        return checkList
    }

    open fun getCheckKey (data: T?): String {
        var key = JsonUtils.toJSONString(data)
        return key
    }
    //////////////////////////// 选择处理 end //////////////////////////////////

}