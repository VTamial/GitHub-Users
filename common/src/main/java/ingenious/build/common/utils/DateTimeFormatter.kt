package ingenious.build.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.convertToReadable(): String {
    val inputFormat = SimpleDateFormat(DATE_TIME_INPUT_FORMAT, Locale.getDefault())
    val date: Date = inputFormat.parse(this) ?: return ""
    val outputFormat = SimpleDateFormat(DATE_TIME_OUTPUT_FORMAT, Locale.getDefault())
    return outputFormat.format(date)
}

private const val DATE_TIME_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val DATE_TIME_OUTPUT_FORMAT = "yyyy-MM-dd HH:mm:ss"
