package com.enojen.getir.service;

import com.enojen.getir.exception.StatisticsNotFoundException;
import com.enojen.getir.model.Order;
import com.enojen.getir.model.Statistic;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticRepository statisticRepository;


    public void updateStatisticsForCustomer(String userId, Order order) {

        List<Statistic> usersAllStatistics = statisticRepository.findAllByUserId(userId);

        List<Statistic> thisMonthsStatistics = usersAllStatistics
                .stream()
                .filter(p -> p.getMonth().equals(LocalDateTime.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)))
                .collect(Collectors.toList());

        Statistic statistic = null;

        if (thisMonthsStatistics.isEmpty()) {
            statistic = setNewStatistics(order, userId);
        } else if (!usersAllStatistics.isEmpty()) {
            statistic = usersAllStatistics.get(0);
            statistic.setTotalBookCount(statistic.getTotalBookCount() + order.getBookCount());
            statistic.setTotalOrderCount(statistic.getTotalOrderCount() + 1);
            statistic.setTotalPurchasedAmount(statistic.getTotalPurchasedAmount().add(order.getTotalPrice()));
        }

        assert statistic != null;
        statisticRepository.save(statistic);
    }

    private Statistic setNewStatistics(Order order, String userId) {
        Statistic statistic = new Statistic();

        statistic.setUserId(userId);
        statistic.setTotalBookCount(statistic.getTotalBookCount() + order.getBookCount());
        statistic.setTotalOrderCount(statistic.getTotalOrderCount() + 1);
        statistic.setTotalPurchasedAmount(statistic.getTotalPurchasedAmount().add(order.getTotalPrice()));
        statistic.setMonth(LocalDateTime.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        return statistic;
    }

    public BaseResponse<List<Statistic>> getStatistics(String userId) {
        List<Statistic> statisticList = statisticRepository.findAllByUserId(userId);

        if (!statisticList.isEmpty()) {
            return new BaseResponse<>(statisticList);
        } else {
            throw new StatisticsNotFoundException();
        }
    }


}
