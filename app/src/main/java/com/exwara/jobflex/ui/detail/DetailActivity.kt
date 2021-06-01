package com.exwara.jobflex.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.exwara.jobflex.R
import com.exwara.jobflex.core.domain.model.SearchItem
import com.exwara.jobflex.core.utils.AppUtils
import com.exwara.jobflex.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var jobItem: SearchItem
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.txt_detail)

        setObject()
    }

    private fun setObject() {
        jobItem = intent.getParcelableExtra<SearchItem>(EXTRA_DATA) as SearchItem
        with(binding) {
            letterTitle.text = jobItem.title[0].toString()
            tvItemTitle.text = jobItem.title
            tvItemCity.text = jobItem.city
            tvDescription.text = HtmlCompat.fromHtml(
                jobItem.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            tvRequirement.text = HtmlCompat.fromHtml(
                jobItem.requirements,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            tvDate.text = getString(
                R.string.template_date,
                AppUtils().formatDate(jobItem.startDate),
                AppUtils().formatDate(jobItem.endDate)
            )
            btnApply.setOnClickListener {
                AppUtils.sendSampleDialog(root.context, "Your application has been submitted!")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}