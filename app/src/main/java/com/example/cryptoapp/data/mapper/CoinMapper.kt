package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CoinMapper @Inject constructor() {

    /**
     * Преобразует Dto объект в DmModel для работы с ним в базе данных
     * @param dto объект класса CoinInfoDto
     * @return объект класса CoinInfoDbModel
     * @throws исключения отсутствуют
     */
    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    /**
     * Преобразует Json объект в список объектов класса CoinInfoDto для дальнейшей работы с ним
     * @param jsonContainer Json объект, который прилетает из запроса
     * @return список объектов класса CoinInfoDto
     * @throws исключения отсутствуют
     */
    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    /**
     * Преобразует список имен в строку имен разделенных ",".
     *
     * @param namesListDto Котейнер имен, который содержит в себе список имен типа String
     * @return Строка имен разделенных запятой "name1,name2,name3,name4 .."
     * @throws исключения не предусмотрены, если контейнер пустой, то вернется пустая строка
     */
    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    /**
     * Преобразует объект базы данных в сущность, с которо можно будет работать в ViewModel
     * @param dbModel объект класса CoinInfoDbModel из бд
     * @return объект класса CoinInfo
     * @throws исключения отсутсвуют
     */
    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    /**
     * Преобразует время из Long в String
     * @param timestamp время типа Long
     * @return строка типа HH:mm:ss
     * @throws исключения отсутствуют
     */
    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object{
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }





















}