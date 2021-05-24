package by.senla.timmeleshko.task6.model.beans

data class StatusDto(
        val seller: String?,
        val date: String?,
        val region_id: String?,
        val sale_status: String?,
        val old_sale_status: String?,
        val price: String?,
        val currency: String?,
        val is_negotiated: String?,
//        val delivery_services: List<String>?,
        val delivery_region: String?,
//        val paymants: List<String>?,
        val a_date_start: String?,
        val a_count_days: String?,
        val a_date_end: String?,
        val a_price_actual: String?,
        val a_price_min: String?,
        val a_price_buynow: String?,
        val a_antisniper_time: String?,
        val a_auto_restart: String?,
        val auction_id: String?
//        val delivery_region_ids: List<String>?,
//        val delivery_regions: List<String>?
) {
    val saleStatus = sale_status ?: "no_sale"
}