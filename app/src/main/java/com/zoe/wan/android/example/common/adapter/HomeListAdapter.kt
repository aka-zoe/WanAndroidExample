package com.zoe.wan.android.example.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemHomeBannerBinding
import com.zoe.wan.android.example.databinding.ItemHomeListBinding
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeBannerDataItem
import com.zoe.wan.android.example.repository.data.HomeListItemData

class HomeListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var dataList: List<HomeListItemData> = mutableListOf()
    private var bannerData: HomeBannerData? = null
    private var collectListener: ItemCollectListener? = null

    interface ItemCollectListener {
        fun itemCollect(id: String, position: Int, collect: Boolean)
    }

    companion object {
        //banner类型
        private val BannerItemType = 0

        //普通item类型
        private val NormalItemType = 1
    }

    /**
     * 设置列表数据
     */
    fun setData(list: List<HomeListItemData>?) {
        if (list != null && list.isNotEmpty()) {
            dataList = list
            notifyDataSetChanged()
        }
    }

    /**
     * 设置banner数据
     */
    fun setBannerData(data: HomeBannerData?) {
        if (!data.isNullOrEmpty()) {
            bannerData = data
            notifyDataSetChanged()
        }
    }

    fun setCollect(collect: Boolean, position: Int) {
        if (dataList.isNotEmpty()) {
            dataList[position].collect = collect
            notifyDataSetChanged()
        }
    }

    fun registerItemListener(listener: ItemCollectListener?) {
        this.collectListener = listener
    }

    class HomeListViewHolder(binding: ItemHomeListBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemBinding: ItemHomeListBinding

        init {
            itemBinding = binding
        }
    }

    class HomeBannerViewHolder(binding: ItemHomeBannerBinding) : RecyclerView.ViewHolder(
        binding
            .root
    ) {
        var bannerBinding: ItemHomeBannerBinding

        init {
            bannerBinding = binding
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return BannerItemType
        } else {
            return NormalItemType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == BannerItemType) {
            return HomeBannerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_home_banner, parent, false
                )
            )
        } else {
            return HomeListViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_home_list, parent, false
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is HomeBannerViewHolder) {
            holder.bannerBinding.itemHomeBanner.setAdapter(HomeBannerAdapter(bannerData))
                .setIndicator(CircleIndicator(holder.bannerBinding.itemHomeBanner.context))
                .addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        //当banner切换时，标题也同步切换显示
                        holder.bannerBinding.itemHomeBannerTitle.text =
                            bannerData?.get(position)?.title
                    }

                    override fun onPageScrollStateChanged(state: Int) {

                    }

                }).setOnBannerListener(object : OnBannerListener<HomeBannerDataItem?> {
                    override fun OnBannerClick(data: HomeBannerDataItem?, position: Int) {
//                        Toast.makeText(holder.bannerBinding.itemHomeBannerTitle.context,
//                            "Banner点击",Toast.LENGTH_SHORT).show()

                        ToastUtils.showShort("Banner点击")
                    }

                })

        } else if (holder is HomeListViewHolder) {
            val item = dataList[position]
            holder.itemBinding.item = item
            if (item.collect == true) {
                holder.itemBinding.itemHomeCollect.setBackgroundResource(R.drawable.img_collect)
            } else {
                holder.itemBinding.itemHomeCollect.setBackgroundResource(R.drawable.img_collect_grey)
            }

            holder.itemBinding.itemHomeCollect.setOnClickListener {
                if (collectListener != null) {
                    collectListener?.itemCollect("${item.id}", position, item.collect ?: false)
                }
            }
        }

    }


    class HomeBannerAdapter(bannerData: HomeBannerData?) :
        BannerImageAdapter<HomeBannerDataItem>(bannerData) {
        override fun onBindView(
            holder: BannerImageHolder?,
            data: HomeBannerDataItem?,
            position: Int,
            size: Int
        ) {
            holder?.imageView?.let { img ->
                Glide.with(img).load(data?.imagePath).into(holder.imageView)
            }

        }

    }

}
