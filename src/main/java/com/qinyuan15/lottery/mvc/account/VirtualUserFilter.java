package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.lang.RandomUtils;
import com.qinyuan.lib.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter elements of virtual user list to balance ascii username and other username
 * Created by qinyuan on 15-12-3.
 */
public class VirtualUserFilter {
    private final static double DEFAULT_ASCII_RATE = 0.75;
    private double asciiRate = DEFAULT_ASCII_RATE;

    public VirtualUserFilter setAsciiRate(double asciiRate) {
        validateAsciiRate(asciiRate);
        this.asciiRate = asciiRate;
        return this;
    }

    private void validateAsciiRate(double asciiRate) {
        if (asciiRate < 0 || asciiRate > 1) {
            throw new IllegalArgumentException("asciiRate must between 0 and 1");
        }
    }

    public List<String> filter(List<String> usernames) {
        List<String> asciiUsernames = new ArrayList<>();
        List<String> otherUsernames = new ArrayList<>();
        for (String username : usernames) {
            if (StringUtils.containsOnlyAscii(username)) {
                asciiUsernames.add(username);
            } else {
                otherUsernames.add(username);
            }
        }

        if (asciiRate == 0) {
            return otherUsernames;
        } else if (asciiRate == 1) {
            return asciiUsernames;
        }

        validateAsciiRate(asciiRate);
        List<String> filteredUsernames = new ArrayList<>();

        int asciiSize = asciiUsernames.size();
        int otherSize = otherUsernames.size();
        double realAsciiRate = ((double) asciiSize) / (asciiSize + otherSize);
        if (realAsciiRate == asciiRate) {
            filteredUsernames.addAll(asciiUsernames);
            filteredUsernames.addAll(otherUsernames);
        } else if (realAsciiRate > asciiRate) {
            int adjustAsciiSize = (int) (asciiRate / (1 - asciiRate) * otherSize);
            adjustAsciiSize = Math.min(asciiSize, adjustAsciiSize);
            filteredUsernames.addAll(RandomUtils.subList(asciiUsernames, adjustAsciiSize, false, false));
            filteredUsernames.addAll(otherUsernames);
        } else if (realAsciiRate < asciiRate) {
            int adjustOtherSize = (int) ((1 - asciiRate) / asciiRate * asciiSize);
            adjustOtherSize = Math.min(otherSize, adjustOtherSize);
            filteredUsernames.addAll(asciiUsernames);
            filteredUsernames.addAll(RandomUtils.subList(otherUsernames, adjustOtherSize, false, false));
        }
        return RandomUtils.disorganizeOrder(filteredUsernames);
    }
}
