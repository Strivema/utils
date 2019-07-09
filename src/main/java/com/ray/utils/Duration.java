package com.ray.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Ray.Ma
 * @date 2019/7/9 10:49
 */
public class Duration implements Comparable<Duration>
{

    private int years;

    private int months;

    private int days;

    public Duration()
    {
    }

    public Duration(int years, int months, int days)
    {
        this.years = years;
        this.months = months;
        this.days = days;
    }

    public int getYears() {
        return this.years;
    }
    public void setYears(int years) {
        this.years = years;
    }
    public int getMonths() {
        return this.months;
    }
    public void setMonths(int months) {
        this.months = months;
    }
    public int getDays() {
        return this.days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public int compareTo(Duration o)
    {
        if (this.years != o.years) {
            return this.years > o.years ? 1 : -1;
        }
        if (this.months != o.months) {
            return this.months > o.months ? 1 : -1;
        }
        if (this.days != o.days) {
            return this.days > o.days ? 1 : -1;
        }
        return 0;
    }
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.years).append(this.months).append(this.days).toHashCode();
    }
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Duration other = (Duration)obj;
        if (this.years != other.years) {
            return false;
        }
        if (this.months != other.months) {
            return false;
        }

        return this.days == other.days;
    }
    public int getTotalMonths()
    {
        return this.years * 12 + this.months;
    }

    /** @deprecated */
    public int getTotalDays()
    {
        return getTotalMonths() * 30 + this.days;
    }
    public Duration minusMonths(int months)
    {
        if (months < 0) {
            return null;
        }
        int totalMonths = getTotalMonths() - months;
        if (totalMonths < 0) {
            return null;
        }
        int years_ = totalMonths / 12;
        int months_ = totalMonths - 12 * years_;
        int days_ = this.days;
        return new Duration(years_, months_, days_);
    }
    public Duration plusMonths(int months)
    {
        if (months < 0) {
            return null;
        }
        int totalMonths = getTotalMonths() + months;
        int years_ = totalMonths / 12;
        int months_ = totalMonths - 12 * years_;
        int days_ = this.days;
        return new Duration(years_, months_, days_);
    }
}

