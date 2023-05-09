package org.libraries.guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class rangeTreeMap {
    public static void main(String[] args) {

        RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();
        experienceRangeDesignationMap.put(Range.closed(0,2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(2,5), "Senior software engineer");
        experienceRangeDesignationMap.put(Range.closed(5,8), "Lead");
        experienceRangeDesignationMap.put(Range.closed(8,12), " Associate Manager");
        experienceRangeDesignationMap.put(Range.closed(12,15), "Manager");
        System.out.println(experienceRangeDesignationMap.get(3));
    }


}
